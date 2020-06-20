package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class RecommandExhibition extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    //private String gen;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView ResultList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommand_exhibition);
        setTitle("추천 전시회");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ResultList = (RecyclerView) findViewById(R.id.recommandlist);
        ResultList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firebaseFirestore.collection("user").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String gen =  document.getData().get("genre").toString();

                    Query query = firebaseFirestore.collection("Exhibition").whereEqualTo("genre",gen);
                    FirestoreRecyclerOptions<ExhibitionModel> options = new FirestoreRecyclerOptions.Builder<ExhibitionModel>()
                            .setQuery(query, ExhibitionModel.class).build();
                    adapter = new FirestoreRecyclerAdapter<ExhibitionModel, CustomViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull ExhibitionModel model) {
                            final String name = model.getName();
                            final String place = model.getPlace();
                            final String poster = model.getImg();
                            final String detail = model.getDetail();
                            final String startdate = model.getStartdate();
                            final String finishdate = model.getFinishdate();
                            holder.list_name.setText(model.getName());
                            holder.list_place.setText(model.getPlace());
                            holder.list_date.setText(model.getStartdate()+" ~ "+model.getFinishdate());
                            Picasso.get().load(model.getImg()).into(holder.list_image);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ExhibitionDetail.class);
                                    intent.putExtra("title", name);
                                    intent.putExtra("place", place);
                                    intent.putExtra("poster", poster);
                                    intent.putExtra("detail", detail);
                                    intent.putExtra("startdate", startdate);
                                    intent.putExtra("finishdate", finishdate);
                                    startActivity(intent);
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                            return new CustomViewHolder((view));
                        }
                    };
                    ResultList.setHasFixedSize(true);
                    ResultList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    ResultList.setAdapter(adapter);

                    onStart();



                } else {
                    Toast.makeText(getApplicationContext(),"데이터 불러오기 오류",Toast.LENGTH_LONG);
                }
            }
        });



    }


    private class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView list_name;
        private TextView list_place;
        private TextView list_date;
        private ImageView list_image;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_place = itemView.findViewById(R.id.list_place);
            list_date = itemView.findViewById(R.id.list_date);
            list_image = itemView.findViewById(R.id.list_image);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null)
            adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
    }
}


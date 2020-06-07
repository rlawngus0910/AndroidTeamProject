package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class Search extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private EditText SearchField;
    private Button SearchBtn;
    private RecyclerView ResultList;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setTitle("검색");

        firebaseFirestore = FirebaseFirestore.getInstance();
        SearchField = (EditText)findViewById(R.id.search_field);
        SearchBtn = (Button) findViewById(R.id.search_btn);
        ResultList = (RecyclerView)findViewById(R.id.result_list);
        ResultList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        SearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String searchText = SearchField.getText().toString();
                ExhibitionSearch(searchText);
            }
        });
    }
    private void ExhibitionSearch(String searchText){
        Query query = firebaseFirestore.collection("Exhibition").orderBy("name").startAt(searchText).endAt(searchText+"\uf8ff");
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
        ResultList.setLayoutManager(new LinearLayoutManager(this));
        ResultList.setAdapter(adapter);

        onStart();
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
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart(){
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }
}

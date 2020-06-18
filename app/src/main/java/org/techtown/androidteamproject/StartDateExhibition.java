package org.techtown.androidteamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartDateExhibition extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView DateExhibitionList;
    private FirestoreRecyclerAdapter adapter;
    private Date stdate;
    private Date fndate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printing_date_exhibtion);
        setTitle("날짜별 전시회");

        firebaseFirestore = FirebaseFirestore.getInstance();
        DateExhibitionList = (RecyclerView) findViewById(R.id.dateex_list);
        DateExhibitionList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        Intent intent = getIntent();
        String startDate = intent.getStringExtra("startDate");
        String finishDate = intent.getStringExtra("finishDate");
        try {
            stdate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            fndate = new SimpleDateFormat("yyyy-MM-dd").parse(finishDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Query query = firebaseFirestore.collection("Exhibition").orderBy("start").startAt(stdate).endAt(fndate);
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
                        Intent intent = new Intent(getApplicationContext(),ExhibitionDetail.class);
                        intent.putExtra("title", name);
                        intent.putExtra("place",place);
                        intent.putExtra("poster",poster);
                        intent.putExtra("detail",detail);
                        intent.putExtra("startdate",startdate);
                        intent.putExtra("finishdate",finishdate);
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
        DateExhibitionList.setHasFixedSize(true);
        DateExhibitionList.setLayoutManager(new LinearLayoutManager(this));
        DateExhibitionList.setAdapter(adapter);
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
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    public void onStart(){
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }
}
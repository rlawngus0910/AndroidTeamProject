package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ReviewList extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reviewlistboard, container,false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = (RecyclerView) view.findViewById(R.id.firestore_list);
        mFirestoreList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        Query query = firebaseFirestore.collection("Reviews");
        FirestoreRecyclerOptions<ReviewInfo> options = new FirestoreRecyclerOptions.Builder<ReviewInfo>()
                .setQuery(query, ReviewInfo.class).build();

        adapter = new FirestoreRecyclerAdapter<ReviewInfo,CustomViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull ReviewInfo reviewInfo) {
                final String title = reviewInfo.getTitle();
                final String content = reviewInfo.getContent();

                holder.list_title.setText(reviewInfo.getTitle());
                holder.list_content.setText(reviewInfo.getContent());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),ExhibitionDetail.class);
                        intent.putExtra("title", title);
                        intent.putExtra("place", content);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewform,parent,false);
                return new CustomViewHolder((view));
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);
        return view;

    }



    private class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView list_title;
        private TextView list_content;


        public CustomViewHolder(View itemView){
            super(itemView);
            list_title = itemView.findViewById(R.id.review_list_title);//
            list_content = itemView.findViewById(R.id.review_list_content);

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
        adapter.startListening();
    }
}

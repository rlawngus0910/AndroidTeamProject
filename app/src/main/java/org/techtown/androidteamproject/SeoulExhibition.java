package org.techtown.androidteamproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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


public class SeoulExhibition extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_seoul_exhibition, container,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = (RecyclerView) view.findViewById(R.id.firestore_list);

        Query query = firebaseFirestore.collection("Exhibition").whereEqualTo("location","서울");
        FirestoreRecyclerOptions<ExhibitionModel> options = new FirestoreRecyclerOptions.Builder<ExhibitionModel>()
                .setQuery(query, ExhibitionModel.class).build();

        adapter = new FirestoreRecyclerAdapter<ExhibitionModel, CustomViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull ExhibitionModel model) {
                holder.list_name.setText(model.getName());
                holder.list_place.setText(model.getPlace());
                Picasso.get().load(model.getImg()).into(holder.list_image);
            }

            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new CustomViewHolder((view));
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);
        return view;

    }

    private class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView list_name;
        private TextView list_place;
        private ImageView list_image;

        public CustomViewHolder(View itemView){
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_place = itemView.findViewById(R.id.list_place);
            list_image = itemView.findViewById(R.id.list_image);
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        adapter.startListening();
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }
}

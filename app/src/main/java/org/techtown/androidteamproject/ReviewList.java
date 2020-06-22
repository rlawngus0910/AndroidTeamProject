package org.techtown.androidteamproject;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
public class ReviewList extends AppCompatActivity {

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference notebookRef=db.collection("Reviews");

    private NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewlistboard);
        setTitle("리뷰 보기");

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        Query query= notebookRef.orderBy("title",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ReviewInfo> options=new FirestoreRecyclerOptions.Builder<ReviewInfo>()
                .setQuery(query,ReviewInfo.class)
                .build();

        adapter=new NoteAdapter(options);

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}

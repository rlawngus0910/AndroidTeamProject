package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewBoard extends AppCompatActivity {
    private static final String TAG="ReviewBoard";

    private FirebaseUser user;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewmakingboard);
        user = FirebaseAuth.getInstance().getCurrentUser();

        findViewById(R.id.reviewcomplete).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.reviewcomplete:
                    reviewUpdate();
                    break;
            }
        }
    };

    private void reviewUpdate(){
        final String title = ((EditText)findViewById(R.id.review_title)).getText().toString();
        final String content = ((EditText)findViewById(R.id.review_content)).getText().toString();
        final String reviewer = user.getEmail();

        if(title.length()>0&&content.length()>0){
            user= FirebaseAuth.getInstance().getCurrentUser();
            ReviewInfo reviewInfo = new ReviewInfo(title,content,reviewer);
            uploader(reviewInfo);
        }
        else{
            startToast("리뷰를 입력해주세요.");
        }

    }

    private void uploader(ReviewInfo reviewInfo){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Reviews").add(reviewInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"DocumentSnapshot written with ID: "+documentReference.getId());
                        startToast("리뷰 작성 완료.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error adding document",e);

                    }
                });
    }

    private void startToast(String msg){ Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();}

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goback_btn:
                Intent intent = new Intent(getApplicationContext(),Board.class);
                startActivity(intent);
                break;

        }
    }
}





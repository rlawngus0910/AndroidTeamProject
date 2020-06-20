package org.techtown.androidteamproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecommandExhibition extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommand_exhibition);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        setTitle("추천 전시회");



    }
}

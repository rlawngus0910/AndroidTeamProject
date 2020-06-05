package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewBoard extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewmakingboard);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goback_btn:
                Intent intent = new Intent(getApplicationContext(), Board.class);
                startActivity(intent);
                break;
        }
    }
}


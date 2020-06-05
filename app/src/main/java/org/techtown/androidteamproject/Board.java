package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Board extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reviewlistbtn:
                Intent intent = new Intent(getApplicationContext(), ReviewList.class);
                startActivity(intent);
                break;
            case R.id.reviewmakingbtn:
                Intent intent1= new Intent(getApplicationContext(),ReviewBoard.class);
                startActivity(intent1);
                break;

        }
    }
}

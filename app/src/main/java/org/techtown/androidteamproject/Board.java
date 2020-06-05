package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Board extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewlistboard);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reviewbtn:
                Intent intent = new Intent(getApplicationContext(), ReviewBoard.class);
                startActivity(intent);
                break;

        }
    }
}

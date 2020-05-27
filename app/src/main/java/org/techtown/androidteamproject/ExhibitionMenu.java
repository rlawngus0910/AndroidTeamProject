package org.techtown.androidteamproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ExhibitionMenu extends AppCompatActivity {
    TextView location_exhibit;
    TextView date_exhibit;
    TextView recommand_exhibit;
    TextView board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibition_menu);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.location_exhibition:
                Intent intent = new Intent(getApplicationContext(),LocationExhibition.class);
                startActivity(intent);
                break;
            case R.id.date_exhibition:
                Intent intent2 = new Intent(getApplicationContext(),DateExhibition.class);
                startActivity(intent2);
                break;
            case R.id.recommand_exhibition:
                Intent intent3 = new Intent(getApplicationContext(),RecommandExhibition.class);
                startActivity(intent3);
                break;
            case R.id.board:
                Intent intent4 = new Intent(getApplicationContext(),Board.class);
                startActivity(intent4);
        }
    }
}

package org.techtown.androidteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class ExhibitionDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibition_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String place = intent.getStringExtra("place");
        String poster = intent.getStringExtra("poster");
        String detail = intent.getStringExtra("detail");
        String startdate = intent.getStringExtra("startdate");
        String finishdate = intent.getStringExtra("finishdate");

        toolbar.setTitle(title);

        ImageView detail_poster = (ImageView)findViewById(R.id.detail_poster);
        Picasso.get().load(poster).into(detail_poster);
        TextView detail_title = (TextView)findViewById(R.id.detail_title);
        detail_title.setText(title);
        TextView detail_place = (TextView)findViewById(R.id.detail_place);
        detail_place.setText(place);
        TextView detail_date = (TextView)findViewById(R.id.detail_date);
        detail_date.setText(startdate + " ~ " +finishdate);
        TextView detail_information = (TextView)findViewById(R.id.detail_information);
        detail_information.setText(detail);
    }
}

package org.techtown.androidteamproject;


import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateExhibition extends AppCompatActivity {

    CalendarView calendarView;
    private Date stdate;
    private Date fndate;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_exhibition2);
        setTitle("날짜 선택");
        Toast.makeText(this,"시작날짜와 종료날짜를 선택하세요.",Toast.LENGTH_LONG).show();
        calendarView = (CalendarView)findViewById(R.id.calendar_view);
        calendarView.setSelectionType(SelectionType.RANGE);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.date_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.startdatelist:
                List<Calendar> days = calendarView.getSelectedDates();
                for(int i=0;i<days.size();i++) {
                    Calendar calendar = days.get(i);
                    if(i==0) {
                        stdate = new Date(calendar.getTimeInMillis());

                    }
                    else if(i==(days.size()-1)) {
                        fndate = new Date(calendar.getTimeInMillis());
                    }
                }
                if(stdate == null || fndate == null)
                    Toast.makeText(this, "날짜를 선택해주세요.", Toast.LENGTH_LONG).show();
                else {
                    String st = new SimpleDateFormat("yyyy-MM-dd").format(stdate);
                    String fn = new SimpleDateFormat("yyyy-MM-dd").format(fndate);

                    Intent intent2 = new Intent(getApplicationContext(), StartDateExhibition.class);
                    intent2.putExtra("startDate",st);
                    intent2.putExtra("finishDate",fn);

                    startActivity(intent2);
                    Toast.makeText(this, "전시회의 시작 날짜로 조회합니다.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.finishdatelist:
                List<Calendar> days2 = calendarView.getSelectedDates();
                for(int i=0;i<days2.size();i++) {
                    Calendar calendar = days2.get(i);
                    if(i==0) {
                        stdate = new Date(calendar.getTimeInMillis());

                    }
                    else if(i==(days2.size()-1)) {
                        fndate = new Date(calendar.getTimeInMillis());
                    }
                }
                if(stdate == null || fndate == null)
                    Toast.makeText(this, "날짜를 선택해주세요.", Toast.LENGTH_LONG).show();
                else {
                    String st = new SimpleDateFormat("yyyy-MM-dd").format(stdate);
                    String fn = new SimpleDateFormat("yyyy-MM-dd").format(fndate);

                    Intent intent2 = new Intent(getApplicationContext(), FinishDateExhibition.class);
                    intent2.putExtra("startDate", st);
                    intent2.putExtra("finishDate", fn);

                    startActivity(intent2);
                    Toast.makeText(this, "전시회의 종료 날짜로 조회합니다.", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
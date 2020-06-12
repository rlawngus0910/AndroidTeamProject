package org.techtown.androidteamproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateExhibition extends Activity {

    CalendarView calendarView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private Button print;
    private RecyclerView DateList;
    private Date stdate;
    private Date fndate;

    /*private TextView tvDate;
    private GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridView;
    private Calendar mCal;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_exhibition2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Toast.makeText(this,"시작날짜와 종료날짜를 선택하세요.",Toast.LENGTH_LONG).show();
        calendarView = (CalendarView)findViewById(R.id.calendar_view);
        calendarView.setSelectionType(SelectionType.RANGE);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        DateList = (RecyclerView) findViewById(R.id.date_list);
        print = (Button) findViewById(R.id.printdate);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Calendar> days = calendarView.getSelectedDates();
                for(int i=0;i<days.size();i++) {
                    Calendar calendar = days.get(i);
                     new Date(calendar.getTimeInMillis());
                    if(i==0) {
                        stdate = new Date(calendar.getTimeInMillis());
                    }
                    else if(i==(days.size()-1)) {
                        fndate = new Date(calendar.getTimeInMillis());
                    }
                }
                PrintDateExhibition(stdate, fndate);

            }
        });

    }
    private void PrintDateExhibition(Date start, Date finish){
        Query query = firebaseFirestore.collection("Exhibition").orderBy("startDate").startAt(start).endAt(finish);
        FirestoreRecyclerOptions<ExhibitionModel> options = new FirestoreRecyclerOptions.Builder<ExhibitionModel>()
                .setQuery(query, ExhibitionModel.class).build();
        adapter = new FirestoreRecyclerAdapter<ExhibitionModel, CustomViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CustomViewHolder holder, int position, @NonNull ExhibitionModel model) {

                final String name = model.getName();
                final String place = model.getPlace();
                final String poster = model.getImg();
                final String detail = model.getDetail();
                final String startdate = model.getStartdate();
                final String finishdate = model.getFinishdate();
                holder.list_name.setText(model.getName());
                holder.list_place.setText(model.getPlace());
                holder.list_date.setText(model.getStartdate()+" ~ "+model.getFinishdate());
                Picasso.get().load(model.getImg()).into(holder.list_image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ExhibitionDetail.class);
                        intent.putExtra("title", name);
                        intent.putExtra("place", place);
                        intent.putExtra("poster", poster);
                        intent.putExtra("detail", detail);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new CustomViewHolder(view);
            }
        };

        DateList.setHasFixedSize(true);
        DateList.setLayoutManager(new LinearLayoutManager(this));
        DateList.setAdapter(adapter);

        onStart();


    }
    private class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView list_name;
        private TextView list_place;
        private TextView list_date;
        private ImageView list_image;

        public CustomViewHolder(View itemView){
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_place = itemView.findViewById(R.id.list_place);
            list_date = itemView.findViewById(R.id.list_date);
            list_image = itemView.findViewById(R.id.list_image);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

        /*tvDate = (TextView)findViewById(R.id.tv_date);
        gridView = (GridView)findViewById(R.id.gridview);


        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);


        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));


        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");

        mCal = Calendar.getInstance();


        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);

    }


    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }

    }


    private class GridAdapter extends BaseAdapter {

        private final List<String> list;

        private final LayoutInflater inflater;


        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.date_exhibition_gridview, parent, false);
                holder = new ViewHolder();

                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));


            mCal = Calendar.getInstance();

            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) {
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.design_default_color_primary));
            }
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }*/

}
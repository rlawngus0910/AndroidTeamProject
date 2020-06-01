package org.techtown.androidteamproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class LocationExhibition extends AppCompatActivity {

    SeoulExhibition seoulfragment;
    GyeonggiExhibition gyunggifragment;
    ChungcheongExhibition chungcheongfragment;
    GyeongsangExhibition gyeongsangfragment;
    JeonraExhibition jeonrafragment;
    GangwonExhibition gangwonfragment;
    JejuExhibition jejufragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_exhibition);
        setTitle("지역별 전시회");


        TabLayout location_tab = (TabLayout) findViewById(R.id.location_tabs);
        seoulfragment = new SeoulExhibition();
        gyunggifragment = new GyeonggiExhibition();
        chungcheongfragment = new ChungcheongExhibition();
        gyeongsangfragment = new GyeongsangExhibition();
        jeonrafragment = new JeonraExhibition();
        gangwonfragment = new GangwonExhibition();
        jejufragment = new JejuExhibition();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, seoulfragment).commit();
        location_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = seoulfragment;
                else if(position == 1)
                    selected = gyunggifragment;
                else if(position == 2)
                    selected = chungcheongfragment;
                else if(position == 3)
                    selected = gyeongsangfragment;
                else if(position == 4)
                    selected = jeonrafragment;
                else if(position == 5)
                    selected = gangwonfragment;
                else if(position == 6)
                    selected = jejufragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

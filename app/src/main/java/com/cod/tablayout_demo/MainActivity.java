package com.cod.tablayout_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.cod.tablayout_demo.utilities.UtilitiesAlertDialog;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.cod.tablayout_demo.controllers.PagerController;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;

    private PagerController pagerAdapter;

    private int count = 0;
    private static final int TIME_TO_EXIT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.enforceIcon();

        this.mapping();

        this.init();

        this.setProperties();

        this.setEvents();

    }

    private void init() {
        this.pagerAdapter = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
    }

    private void setEvents() {
        this.tabLayout.setOnTabSelectedListener(this);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setProperties() {
        this.viewPager.setAdapter(pagerAdapter);
    }

    private void mapping() {
        this.tabLayout = findViewById(R.id.tablayout);
        this.viewPager = findViewById(R.id.viewpager);

        // this not used
        this.tab1 = findViewById(R.id.tablistadeespera);
        this.tab2 = findViewById(R.id.tabcomandas);
        this.tab3 = findViewById(R.id.tabmesas);
    }

    private void enforceIcon() {
        this.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    //EVENTOS
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
        this.pagerAdapter.notifyDataSetChanged();

        // Toast solo para indentificar los eventos
        //Toast.makeText(MainActivity.this, "onTabSelected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {


        // Toast solo para indentificar los eventos
        //Toast.makeText(MainActivity.this, "onTabUnselected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {


        // Toast solo para indentificar los eventos
        //Toast.makeText(MainActivity.this, "onTabReselected", Toast.LENGTH_SHORT).show();
    }


    // proteccion para salir de la aplicacion
    @Override
    public void onBackPressed() {

        if (this.count == 0){
            Toast.makeText(this, UtilitiesAlertDialog.TOAST_CONFIRM_EXIT, Toast.LENGTH_SHORT).show();
            this.count++;
        }else{
            super.onBackPressed();
        }

        new CountDownTimer(TIME_TO_EXIT, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                // vacio
            }

            @Override
            public void onFinish() {
                count = 0;
            }
        }.start();

    }
}
package com.cod.tablayout_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.cod.tablayout_demo.controllers.PagerController;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tab1, tab2, tab3;

    PagerController pagerAdapter;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enforceIcon();

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        // this not used
        tab1 = findViewById(R.id.tablistadeespera);
        tab2 = findViewById(R.id.tabcomandas);
        tab3 = findViewById(R.id.tabmesas);

        pagerAdapter = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        // set de eventos
        tabLayout.setOnTabSelectedListener(this);

        // change the active icon at the tab
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void enforceIcon() {
        this.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    //EVENTOS
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        pagerAdapter.notifyDataSetChanged();

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

        if (count == 0){
            Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
            count++;
        }else{
            super.onBackPressed();
        }

        new CountDownTimer(3000, 1000){

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
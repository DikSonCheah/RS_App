package com.example.kokwei217.rs_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabAll;
    private TabItem tabElectric;
    private TabItem tabMotor;
    private TabItem tabSensors;
    private TabItem tabMicro;
    private PageAdapter pageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        tabLayout = findViewById(R.id.tabLayout_main);
        tabAll = findViewById(R.id.All_TI);
        tabElectric = findViewById(R.id.ElectricComponent_TI);
        tabMotor = findViewById(R.id.MotorDriver_TI);
        tabSensors = findViewById(R.id.Sensors_TI);
        tabMicro = findViewById(R.id.Microcontrollers_TI);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager = findViewById(R.id.viewPager_main);
        viewPager.setAdapter(pageAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //setting up the drawerlayout
        drawerLayout = findViewById(R.id.drawer_main);
        navigationView = findViewById(R.id.nv_main);

        //To get the identity of the current user
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String EmailAddress = firebaseUser.getEmail();

        //set the text to the id of current user in the drawer
        View headerView = navigationView.getHeaderView(0);
        TextView email_drawerHeader = headerView.findViewById(R.id.EmailAddress_mainactivity);
        email_drawerHeader.setText(EmailAddress);

        //Set onclicklistener to the drawerlayout items
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                drawerLayout.closeDrawers();

                switch (item.getItemId()) {

                    case R.id.Signout_mainactivity:
                        signOut();
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void signOut() {
        firebaseAuth.signOut();
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        } else {
            super.onBackPressed();
        }
    }
}

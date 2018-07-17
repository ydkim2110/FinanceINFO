package com.example.anti2110.financeinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Adapter.SectionPagerAdapter;
import com.example.anti2110.financeinfo.Fragment.BookmarkFragment;
import com.example.anti2110.financeinfo.Fragment.NoticeFragment;
import com.example.anti2110.financeinfo.Fragment.ProfileFragment;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.ViewHolder.InfoListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private long lastTimeBackPressed;
    
    private Toolbar toolbar;

    // Navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Info, InfoListViewHolder> mAdapter;

    // Firebase
    private FirebaseDatabase database;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        
        loadToolbar();
        initWidget();
        setupViewPager();

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
                finish();
                return;
            }
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        }
    }

    private void setupViewPager() {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new NoticeFragment()); // index 0
        adapter.addFragment(new BookmarkFragment()); // index 1
        adapter.addFragment(new ProfileFragment()); // index 2

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("채용공고");
        tabLayout.getTabAt(1).setText("북마크");
        tabLayout.getTabAt(2).setText("정보");
    }

    private void loadToolbar() {
        Log.d(TAG, "loadToolbar: started");
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Finance Info");
    }

    private void initWidget() {
        Log.d(TAG, "initWidget: started");
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.nav_open_draw,
                R.string.nav_close_draw);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;

        switch (id) {
            case R.id.nav_inbox:
                break;
        }

        if(fragment != null) {

        } else  {

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


}

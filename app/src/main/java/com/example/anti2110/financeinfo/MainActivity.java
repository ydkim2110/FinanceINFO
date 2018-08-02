package com.example.anti2110.financeinfo;

import android.content.Intent;
import android.graphics.Color;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Adapter.SectionPagerAdapter;
import com.example.anti2110.financeinfo.Fragment.BookmarkFragment;
import com.example.anti2110.financeinfo.Fragment.NoticeFragment;
import com.example.anti2110.financeinfo.Fragment.PostFragment;
import com.example.anti2110.financeinfo.Login.LoginActivity;
import com.example.anti2110.financeinfo.Login.SettingsActivity;
import com.example.anti2110.financeinfo.Login.SignOutActivity;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.ViewHolder.InfoListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth auth;
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
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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
        adapter.addFragment(new PostFragment()); // index 1
        adapter.addFragment(new BookmarkFragment()); // index 2

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Notice");
        tabLayout.getTabAt(1).setText("Post");
        tabLayout.getTabAt(2).setText("Bookmark");
    }

    private void loadToolbar() {
        Log.d(TAG, "loadToolbar: started");
        toolbar = (Toolbar) findViewById(R.id.app_bar_layout);
        toolbar.setTitle("Finance INFO");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }

    private void initWidget() {
        Log.d(TAG, "initWidget: started");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.nav_open_draw,
                R.string.nav_close_draw);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView email = (TextView) headerView.findViewById(R.id.nav_header_email_text);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            email.setText(currentUser.getEmail());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;

        switch (id) {
            case R.id.nav_bank:
                intent = new Intent(MainActivity.this, BankActivity.class);
                break;
            case R.id.nav_account_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                break;
            case R.id.nav_question:
                intent = new Intent(MainActivity.this, QuestionActivity.class);
                break;
            case R.id.nav_logout:
                intent = new Intent(MainActivity.this, SignOutActivity.class);
                break;
        }

        if(fragment != null) {

        } else  {
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}

package com.example.anti2110.financeinfo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.anti2110.financeinfo.Adapter.SectionPagerAdapter;
import com.example.anti2110.financeinfo.Fragment.BookmarkFragment;
import com.example.anti2110.financeinfo.Fragment.NoticeFragment;
import com.example.anti2110.financeinfo.Fragment.ProfileFragment;
import com.example.anti2110.financeinfo.Interface.ItemClickListener;
import com.example.anti2110.financeinfo.Model.Info;
import com.example.anti2110.financeinfo.ViewHolder.InfoListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private long lastTimeBackPressed;
    
    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Info, InfoListViewHolder> mAdapter;

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
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
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

    }

}

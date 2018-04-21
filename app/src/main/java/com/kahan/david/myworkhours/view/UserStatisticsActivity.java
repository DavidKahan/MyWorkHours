package com.kahan.david.myworkhours.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kahan.david.myworkhours.R;
import com.kahan.david.myworkhours.model.StatItem;
import com.kahan.david.myworkhours.model.StatsDbHelper;

import java.util.ArrayList;
import java.util.List;

public class UserStatisticsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<StatItem> mStatItems = new ArrayList<>();
    private StatsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_statistics);

        mRecyclerView = findViewById(R.id.recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDbHelper = StatsDbHelper.getInstance(this);
        mStatItems = mDbHelper.getAllStats();
        // specify an adapter (see also next example)
        mAdapter = new UserStatsAdapter(mStatItems);
        mRecyclerView.setAdapter(mAdapter);
    }
}

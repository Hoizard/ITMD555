package com.example.erickcruz.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.erickcruz.finalproject.R;
import com.example.erickcruz.finalproject.GlobalApplication;

public class JobViewActivity extends AppCompatActivity {

    private ListView jobListView;
    private TextView noJobsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobview);

        this.initUI();

    }

    private void initUI() {
        noJobsTextView = (TextView)findViewById(R.id.noJobsTextView);
        jobListView = (ListView)findViewById(R.id.jobListView);

        if (GlobalApplication.jobArray != null) {
            jobListView.setVisibility(View.VISIBLE);
            noJobsTextView.setVisibility(View.INVISIBLE);

            // Show the jobs in ListView


        } else {
            jobListView.setVisibility(View.INVISIBLE);
            noJobsTextView.setVisibility(View.VISIBLE);
        }
    }

}

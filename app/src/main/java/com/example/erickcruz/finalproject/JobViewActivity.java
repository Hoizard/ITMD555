package com.example.erickcruz.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class JobViewActivity extends AppCompatActivity {

    //Created objects
    private ListView jobListView;
    SQLiteDatabase sqLiteDatabase;
    JobDatabaseHelper myDb;
    Cursor c;
    JobViewAdapter jobViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobview);

        //Initializing the objects
        jobListView = (ListView)findViewById(R.id.jobListView);
        jobViewAdapter = new JobViewAdapter(getApplicationContext(),R.layout.activity_jobdescription);
        jobListView.setAdapter(jobViewAdapter);
        myDb = new JobDatabaseHelper(getApplicationContext());
        sqLiteDatabase = myDb.getReadableDatabase();

        //Getting information from the database by initializing the database, passing the sqLiteDatabase
        c = myDb.getAllRows(sqLiteDatabase);

        //Analyzing the cursor, making sure there is information
        if(c.moveToFirst()){
            do {
                String companyName, jobTitle, jobDescription;
                companyName = c.getString(0);
                jobTitle = c.getString(1);
                jobDescription = c.getString(2);

                DataProvider dataProvider = new DataProvider(companyName, jobTitle, jobDescription);
                jobViewAdapter.add(dataProvider);

            }while(c.moveToNext());
        }
    }
}

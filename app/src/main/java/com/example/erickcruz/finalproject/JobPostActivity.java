package com.example.erickcruz.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erickcruz.finalproject.R;
import com.example.erickcruz.finalproject.GlobalApplication;

public class JobPostActivity extends AppCompatActivity {

    private EditText company_EditTxt, fullname_EditTxt, jobTitle_EditTxt, jobdescription_EditTxt;
    private Button postBtn;

    JobDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobpost);

        //Declaring methods
        this.initUI();
        this.setControlEvents();
    }

    //method that grabs edit text and button to post
    private void initUI() {
        company_EditTxt = (EditText)findViewById(R.id.companyname);
        fullname_EditTxt = (EditText)findViewById(R.id.fullname);
        jobTitle_EditTxt = (EditText)findViewById(R.id.jobtitle);
        jobdescription_EditTxt = (EditText)findViewById(R.id.jobdescription);

        company_EditTxt.setText(GlobalApplication.companyName);
        fullname_EditTxt.setText(GlobalApplication.nameStr);

        postBtn = (Button)findViewById(R.id.postBtn);

        mDatabaseHelper = new JobDatabaseHelper(this);
    }

    //Setting text for posting job
    private  void setControlEvents() {
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyStr = company_EditTxt.getText().toString();
                String fullnameStr = fullname_EditTxt.getText().toString();
                String jobTitleStr = jobTitle_EditTxt.getText().toString();
                String jobDescriptionStr = jobdescription_EditTxt.getText().toString();

                if (companyStr.length() !=0 && fullnameStr.length() != 0 && jobTitleStr.length() !=0 && jobDescriptionStr.length() != 0) {
                    jobTitle_EditTxt.setText("");
                    jobdescription_EditTxt.setText("");
                    // Save the job in SQLite database
                    AddData(companyStr, fullnameStr, jobTitleStr, jobDescriptionStr);
                    toastMessage("Successfully posted.");
                } else {
                    toastMessage("Please enter all items to post the job.");
                }
            }
        });
    }

    //Method to add data to table
    public void AddData(String companyStr, String fullnameStr, String jobTitleStr, String jobDescriptionStr) {
        boolean insertData = mDatabaseHelper.addData(companyStr, fullnameStr, jobTitleStr, jobDescriptionStr);
        if(insertData) {
            GlobalApplication.companyName = companyStr;
            GlobalApplication.nameStr = fullnameStr;
            GlobalApplication.jobTitle = jobTitleStr;
            GlobalApplication.jobDescription = jobDescriptionStr;
        } else {
            toastMessage("Posting Error.");
        }
    }

    //Method for toast
    private  void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Hide keyboard when user click anywhere of screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}

package com.example.erickcruz.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erickcruz.finalproject.UserDatabaseHelper;
import com.example.erickcruz.finalproject.R;
import com.example.erickcruz.finalproject.GlobalApplication;

public class RegisterActivity extends AppCompatActivity {

    private EditText companyname_EditTxt, fullname_EditTxt, username_EditTxt, password_EditTxt;
    private Button registerBtn;
    private TextView typeTextView;

    UserDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.initUI();
        this.setControlEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalApplication.typeStr == "Administrator") {
            companyname_EditTxt.setVisibility(View.VISIBLE);
            typeTextView.setText("Register as Administrator.");
        } else if (GlobalApplication.typeStr == "Employer") {
            companyname_EditTxt.setVisibility(View.GONE);
            typeTextView.setText("Register as Employer.");
        }
    }

    private void initUI() {
        companyname_EditTxt = (EditText)findViewById(R.id.companyname);
        fullname_EditTxt = (EditText)findViewById(R.id.fullname);
        username_EditTxt = (EditText)findViewById(R.id.username);
        password_EditTxt = (EditText)findViewById(R.id.password);
        typeTextView = (TextView)findViewById(R.id.typeTextView);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        mDatabaseHelper = new UserDatabaseHelper(this);
    }

    private  void setControlEvents() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyname = "Employer";
                String fullname = fullname_EditTxt.getText().toString();
                String username = username_EditTxt.getText().toString();
                String password = password_EditTxt.getText().toString();
                String usertype = GlobalApplication.typeStr;

                if (GlobalApplication.typeStr == "Administrator") {
                    companyname = companyname_EditTxt.getText().toString();
                }

                //If company name, full name, and password are empty, then output toast - Please enter all items to register
                if (companyname.length() != 0 && fullname.length() != 0 && username.length() !=0 && password.length() != 0) {
                    //If data added to fields, add to fields called companyname, fullname, username, password, usertype
                    if (mDatabaseHelper.viewData(username) != null) {
                        Cursor cursor = mDatabaseHelper.viewData(username);
                        if (cursor.getCount() > 0) {
                            toastMessage("Same User exists.");
                            if (!cursor.isClosed())
                            {
                                cursor.close();
                            }
                            //Close
                        } else {
                            if (!cursor.isClosed())
                            {
                                cursor.close();
                            }
                            AddData(companyname, fullname, username, password, usertype);
                        }
                    } else {
                        AddData(companyname, fullname, username, password, usertype);
                    }
                    initUI();
                } else {
                    toastMessage("Please enter all items to register.");
                }
            }
        });
    }

    private void gotoJobPostScreen() {
        Intent mainIntent = new Intent(RegisterActivity.this, JobPostActivity.class);
        startActivity(mainIntent);
    }

    private void gotoJobViewScreen() {
        Intent mainIntent = new Intent(RegisterActivity.this, JobViewActivity.class);
        startActivity(mainIntent);
    }

    //Method to add data to table
    public void AddData(String company, String fullname, String username, String password, String usertype) {
        boolean insertData = mDatabaseHelper.addData(company, fullname, username, password, usertype);
        if(insertData) {
            GlobalApplication.companyName = company;
            GlobalApplication.nameStr = fullname;
            GlobalApplication.userName = username;
            GlobalApplication.password = password;
            if (GlobalApplication.typeStr == "Administrator") {
                gotoJobPostScreen();
            } else if (GlobalApplication.typeStr == "Employer") {
                gotoJobViewScreen();
            }
        } else {
            toastMessage("Register Error.");
        }
    }

    //Method for Toast
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

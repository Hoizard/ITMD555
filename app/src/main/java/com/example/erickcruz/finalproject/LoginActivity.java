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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erickcruz.finalproject.UserDatabaseHelper;
import com.example.erickcruz.finalproject.R;
import com.example.erickcruz.finalproject.GlobalApplication;

public class LoginActivity extends AppCompatActivity {

    //listing buttons, edit text and text views
    UserDatabaseHelper mDatabaseHelper;
    private EditText username_EditTxt, password_EditTxt;
    private Button loginBtn;
    private LinearLayout registerBtn;
    private TextView typeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //setting up methods
        this.initUI();
        this.setControlEvents();
    }

    private void initUI() {
        typeTextView = (TextView)findViewById(R.id.typeTextView);
        username_EditTxt = (EditText)findViewById(R.id.username);
        password_EditTxt = (EditText)findViewById(R.id.password);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        registerBtn = (LinearLayout)findViewById(R.id.registerBtn);

        mDatabaseHelper = new UserDatabaseHelper(this);

        //If user clicks on Administrator or Employer button, the text of the login
        //screen will change according to button that they have clicked
        if (GlobalApplication.typeStr == "Administrator") {
            typeTextView.setText("Login as Administrator.");
        } else if (GlobalApplication.typeStr == "Employer") {
            typeTextView.setText("Login as Employer.");
        }
    }

    private  void setControlEvents() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Capturing string that user inputs
                String username = username_EditTxt.getText().toString();
                String password = password_EditTxt.getText().toString();

                //If username and password is blank, send toast of making sure the fields are filled
                if (username.length() !=0 && password.length() != 0) {
                    //If there is data input, then fill out columns of database
                    if (mDatabaseHelper.viewData(username) != null) {
                        Cursor cursor = mDatabaseHelper.viewData(username);
                        cursor.moveToFirst();
                        String companyStr = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL1));
                        String fullnameStr = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL2));
                        String usernameStr = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL3));
                        String passwordStr = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL4));
                        String usertypeStr = cursor.getString(cursor.getColumnIndex(UserDatabaseHelper.COL5));
                        //Close database
                        if (!cursor.isClosed())
                        {
                            cursor.close();
                        }

                        //If the username and password match the database
                        if (GlobalApplication.typeStr.equals(usertypeStr) ) {
                            //
                            if (password.equals(passwordStr)) {
                                GlobalApplication.companyName = companyStr;
                                GlobalApplication.nameStr = fullnameStr;
                                GlobalApplication.userName = usernameStr;
                                GlobalApplication.password = passwordStr;
                                //If user matches database as Administrator, show job posting screen
                                if (GlobalApplication.typeStr == "Administrator") {
                                    gotoJobPostScreen();
                                    //If user matches database as Employer, show job viewing screen
                                } else if (GlobalApplication.typeStr == "Employer") {
                                    gotoJobViewScreen();
                                }
                                //If username and password does not match database
                            } else {
                                toastMessage("User Name and Password Wrong.");
                            }
                            //If username and password does not match database
                        } else {
                            toastMessage("User Name and Password Wrong.");
                        }
                        //If username and password does not match database
                    } else {
                        toastMessage("User Name and Password Wrong.");
                    }
                    //If username and password is not filled out
                } else {
                    toastMessage("Please enter the user name and password.");
                }
            }
        });

        //User clicks on registering link, sends to Register screen
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegisterScreen();
            }
        });
    }

    //Intent to send to next Activity
    private void gotoJobPostScreen() {
        Intent mainIntent = new Intent(LoginActivity.this, JobPostActivity.class);
        startActivity(mainIntent);
    }

    //Intent to send to next Activity
    private void gotoJobViewScreen() {
        Intent mainIntent = new Intent(LoginActivity.this, JobViewActivity.class);
        startActivity(mainIntent);
    }

    //Intent to send to next Activity
    private void gotoRegisterScreen() {
        Intent mainIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(mainIntent);
    }

    //Toast message method
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

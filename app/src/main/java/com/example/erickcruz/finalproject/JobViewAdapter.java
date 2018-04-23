package com.example.erickcruz.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//This class is being added each of the data provider class objects into the list


public class JobViewAdapter extends ArrayAdapter{
    List list = new ArrayList();
    public JobViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    //Creating static objects for the 3 text view
    static class LayoutHandler{
        TextView companyname, jobDescription, jobTitle;
    }

    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    //Passing the number of items in the list
    @Override
    public int getCount() {
        return list.size();
    }

    //Pass objects of the current position
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    //Viewing each row of data
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        //Checking to see if there is data or not, if not, to create one
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_jobdescription, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.companyname = (TextView)row.findViewById(R.id.companyname);
            layoutHandler.jobTitle = (TextView)row.findViewById(R.id.jobTitle);
            layoutHandler.jobDescription = (TextView)row.findViewById(R.id.jobDescription);
            row.setTag(layoutHandler);
        }
        else{
            layoutHandler = (LayoutHandler)row.getTag();
        }
        DataProvider dataProvider = (DataProvider)this.getItem(position);
        layoutHandler.companyname.setText(dataProvider.getCompanyName());
        layoutHandler.jobTitle.setText(dataProvider.getJobTitle());
        layoutHandler.jobDescription.setText(dataProvider.getJobDesc());

        return row;
    }
}

package com.example.stuffexchanger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    String fname,lname,uname;
    int uid,rateuserID;
    String rateUserEmail,rateusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Intent in= getIntent();
        Bundle b = in.getExtras();
        rateuserID=0;

        if(b!=null)
        {
            uid =(Integer) b.get("uid");
            fname =(String) b.get("fname");
            lname =(String) b.get("lname");
            uname =(String) b.get("uname");
            rateUserEmail=(String)b.get("rateUser");
            rateuserID=(Integer)b.get("rateuserID");
            rateusername=(String)b.get("rateusername");
        }

        TextView usernametv = (TextView) findViewById(R.id.usernameRA);
        usernametv.setText(rateusername);

    }

    public void BackToHome(View view)
    {
        Intent myIntent = new Intent(RatingActivity.this,ItemListActivity.class);
        myIntent.putExtra("uid", uid);
        myIntent.putExtra("fname", fname);
        myIntent.putExtra("lname", lname);
        myIntent.putExtra("uname", uname);
        startActivity(myIntent);
    }
    public void updaterating(View view)
    {
        RatingBar rb=(RatingBar)findViewById(R.id.ratingBarRA);
        double rating=rb.getRating();
        if(rating!=0.0&&rateusername!=null)
        {
            ItemSqlDBHelper itemsql=new ItemSqlDBHelper(this);
            itemsql.refreshRating(rating,rateUserEmail);

            // close dbase
            itemsql.close();
        }

    }
}

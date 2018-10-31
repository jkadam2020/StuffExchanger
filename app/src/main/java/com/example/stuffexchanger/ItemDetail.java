package com.example.stuffexchanger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemDetail extends AppCompatActivity {

    String fname,lname,uname,query;
    int uid,position;
    String rateUser,rateusername;
    int rateuserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent in= getIntent();
        Bundle b = in.getExtras();

        rateUser="value";
        rateuserID=0;

        TextView tv=(TextView)findViewById(R.id.usernameID);
        if(b!=null)
        {
            fname =(String) b.get("fname");
            lname =(String) b.get("lname");
            uname =(String) b.get("uname");
            uid =(Integer) b.get("uid");
            position =(Integer) b.get("position");
            query =(String) b.get("query");
        }
        tv.setText(fname+" "+lname);


        Item item=new Item();

        ItemSqlDBHelper db = new ItemSqlDBHelper(this);
        List<Item> list = new ArrayList<Item>();
        if(query.isEmpty()) {
            list = db.getAllItems();
        }
        else{
            list=db.getAllItemsOnCondition(query);
        }
        TextView ItemName = (TextView)findViewById(R.id.itemnameID);
        TextView Price = (TextView)findViewById(R.id.priceID);
        ImageView iv=(ImageView)findViewById(R.id.imageID);
        TextView description = (TextView)findViewById(R.id.descriptionID);
        TextView postedby = (TextView)findViewById(R.id.postedbyID);
        TextView contact = (TextView)findViewById(R.id.contactID);
        TextView email = (TextView)findViewById(R.id.emailTextView);



        iv.setImageBitmap(DbBitmapUtility.getImage(list.get(position).getImage()));
        ItemName.setText(list.get(position).getItemname());
        Price.setText(Double.toString(list.get(position).getPrice()));
        description.setText(list.get(position).getDescription());
        postedby.setText(list.get(position).getUsername());
        contact.setText(Long.toString(list.get(position).getContact()));
        email.setText(list.get(position).getEmail());

        rateUser=list.get(position).getEmail();
        rateuserID=list.get(position).getID();
        rateusername=list.get(position).getUsername();
        // close dbase
        db.close();
    }
    public void BackBtnClick(View view)
    {
        Intent myIntent = new Intent(ItemDetail.this,ItemListActivity.class);
        myIntent.putExtra("uid", uid);
        myIntent.putExtra("fname", fname);
        myIntent.putExtra("lname", lname);
        myIntent.putExtra("uname", uname);
        startActivity(myIntent);
    }
    public void ratingBtnClick(View view)
    {
        Intent i = new Intent(ItemDetail.this, RatingActivity.class);

        i.putExtra("uid", uid);
        i.putExtra("fname", fname);
        i.putExtra("lname", lname);
        i.putExtra("uname", uname);
        i.putExtra("rateUser", rateUser);
        i.putExtra("rateuserID", rateuserID);
        i.putExtra("rateusername", rateusername);
        startActivity(i);

    }
}

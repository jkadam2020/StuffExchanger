package com.example.stuffexchanger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String fname,lname,uname;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Intent in= getIntent();
        Bundle b = in.getExtras();


        TextView tv=(TextView)findViewById(R.id.UserDetailsTextView);
        if(b!=null)
        {
            fname =(String) b.get("fname");
            lname =(String) b.get("lname");
            uname =(String) b.get("uname");
            uid =(Integer) b.get("uid");
        }
        tv.setText(fname+" "+lname);

        //===============
        // Spinner element
        Spinner spinner;
        spinner = (Spinner) findViewById(R.id.spinner);

        //Create spinner item listing
        List<String> blist = new ArrayList<String>();

        blist.add("High priced Items First");
        blist.add("Items below price 50");
       // blist.add("Items from High rated sellers");
        blist.add("Latest Items");
        blist.add("Low priced Items First");

        //Sort list in Alphabetical order
        Collections.sort(blist, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        blist.add(0, "Select Analytics...");

        ArrayAdapter<String> adapter = new
                ArrayAdapter<String>(ItemListActivity.this,
                android.R.layout.simple_spinner_item, blist);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setWillNotDraw(false);
        spinner.setOnItemSelectedListener(this);

//========================


        ItemSqlDBHelper db = new ItemSqlDBHelper(this);

        ListView listContent = (ListView) findViewById(R.id.list);
        List<Item> list = new ArrayList<Item>();
        try {
            list = db.getAllItems();
        }
        catch (SQLiteException e)
        {
            Log.d("Error: ","Items table not created yet");
        }
        //get data from the table by the ListAdapter
        ListAdapter customAdapter = new ListAdapter(this, R.layout.itemlistrow,list);
        listContent.setAdapter(customAdapter);


        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int position,
                                    long arg3) {
                Intent i = new Intent(ItemListActivity.this, ItemDetail.class);

                i.putExtra("position", position);
                i.putExtra("uid", uid);
                i.putExtra("fname", fname);
                i.putExtra("lname", lname);
                i.putExtra("uname", uname);
                startActivity(i);
            }
        });

        // close dbase
        db.close();
    }
    public void addlistingbtnclick(View view)
    {
        Intent myIntent = new Intent(ItemListActivity.this,AddListingActivity.class);
        myIntent.putExtra("uid", uid);
        myIntent.putExtra("fname", fname);
        myIntent.putExtra("lname", lname);
        myIntent.putExtra("uname", uname);
        startActivity(myIntent);
    }
    public void logoutbtnclick(View view)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("username");
        Intent intent  = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {


        String query="SELECT * FROM items";
        switch (position) {
            case 1:
                query="SELECT * FROM items order by price desc";
                break;
            case 2:
                query="SELECT * FROM items where price<=50";
                break;
            case 3:
                //latest items
                query="SELECT * FROM items order by datetime(pdate) desc";
                break;
            case 4:
                query="SELECT * FROM items order by price asc";
                break;
        }
        ItemSqlDBHelper db = new ItemSqlDBHelper(this);

        ListView listContent = (ListView) findViewById(R.id.list);
        List<Item> list = new ArrayList<Item>();

        try {
            list=db.getAllItemsOnCondition(query);
        }
        catch (SQLiteException e)
        {
            Log.d("Error:","Items table not created yet");
        }


        //get data from the table by the ListAdapter
        ListAdapter customAdapter = new ListAdapter(this, R.layout.itemlistrow,list);
        listContent.setAdapter(customAdapter);

        final String q=query;
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View arg1, int position,
                                    long arg3) {
                Intent i = new Intent(ItemListActivity.this, ItemDetail.class);

                i.putExtra("position", position);
                i.putExtra("uid", uid);
                i.putExtra("fname", fname);
                i.putExtra("lname", lname);
                i.putExtra("uname", uname);
                i.putExtra("query", q);
                startActivity(i);
            }
        });

        // close dbase
        db.close();

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}

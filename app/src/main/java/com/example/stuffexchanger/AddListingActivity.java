package com.example.stuffexchanger;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddListingActivity extends AppCompatActivity{
    private static final int CAMERA_REQUEST = 0;
    private int PICK_IMAGE_REQUEST = 1;

    Bitmap bitmap;

    String fname,lname,uname;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        Intent in= getIntent();
        Bundle b = in.getExtras();

        if(b!=null)
        {
            fname =(String) b.get("fname");
            lname =(String) b.get("lname");
            uname =(String) b.get("uname");
            uid =(Integer) b.get("uid");
        }
    }


    public void addlistingbtnclick(View view)
    {

            EditText ItemNameEditText = (EditText)findViewById(R.id.ItemNameEditText);
            String ItemName=ItemNameEditText.getText().toString();

            EditText PriceEditText = (EditText)findViewById(R.id.PriceEditText);
            String sprice=PriceEditText.getText().toString();

            EditText ContactEditText = (EditText)findViewById(R.id.ContactEditText);
            String scontact=ContactEditText.getText().toString();


            EditText descriptionEditText = (EditText)findViewById(R.id.DescriptionEditText);
            String description=descriptionEditText.getText().toString();

            if(ItemName.isEmpty()||sprice.isEmpty()||scontact.isEmpty()||description.isEmpty()) {
                Toast.makeText(this, "Some values missing, please fill all the details", Toast.LENGTH_SHORT).show();
            }
            else
            {
                double price=Double.parseDouble(sprice);
                long contact=Long.parseLong(scontact);
                String username=fname+" "+lname;
                String email=null;
                email=uname;

                byte[] image=DbBitmapUtility.getBytes(bitmap);

                    ItemSqlDBHelper db=new ItemSqlDBHelper(this);

                    Date d=new Date();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date=df.format(d);
                    long newRowId=db.addItem(new Item(uid,ItemName,username,price,contact,image,description,date,email));

                    if(newRowId==-1)
                    {
                        Toast.makeText(this, "Error Occurred, Please contact the support desk.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Your Item listing has been published successfully!! ", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(AddListingActivity.this,ItemListActivity.class);
                        myIntent.putExtra("uid", uid);
                        myIntent.putExtra("fname", fname);
                        myIntent.putExtra("lname", lname);
                        myIntent.putExtra("uname", uname);
                        startActivity(myIntent);
                    }

                // 4. close dbase
                db.close();

            }

    }

    public void browsebtnclick(View view)
    {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

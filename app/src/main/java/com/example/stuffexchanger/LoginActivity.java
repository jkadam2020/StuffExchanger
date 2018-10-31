package com.example.stuffexchanger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.preference.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void onSignUpClick(View view)
    {
        Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        // myIntent.putExtra("key", 1); //Optional parameters
        startActivity(myIntent);
    }
    public void onLoginClick(View view)
    {
        EditText usernameEditText = (EditText)findViewById(R.id.username);
        String Tusername=usernameEditText.getText().toString();

        EditText passwordEditText = (EditText)findViewById(R.id.password);
        String Tpassword=passwordEditText.getText().toString();

        int uid=0;

        if(Tusername.isEmpty()||Tpassword.isEmpty())
        {
            Toast.makeText(this, "Values are missing, please fill the details", Toast.LENGTH_SHORT).show();
        }
        else {
            UserDBHelper mDbHelper = new UserDBHelper(this);
            String query;
            query="select * from userdb where email=\""+Tusername+"\" and password=\""+Tpassword+"\"";
            int rc=0;
            rc=mDbHelper.UserCheck(query);
            if(rc==0)
            {
                Toast.makeText(this, "Wrong Credentials Or account does not exist.", Toast.LENGTH_SHORT).show();
            }
            else {

                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    User user=mDbHelper.getUserDetails(query);

                    Intent myIntent = new Intent(LoginActivity.this, ItemListActivity.class);
                    myIntent.putExtra("uid", user.getId());
                    myIntent.putExtra("fname", user.getFirstname());
                    myIntent.putExtra("lname", user.getLastname());
                    myIntent.putExtra("uname", user.getEmail());
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", user.getEmail());

                    startActivity(myIntent);
            }
            // close dbase
            mDbHelper.close();
        }
    }
}

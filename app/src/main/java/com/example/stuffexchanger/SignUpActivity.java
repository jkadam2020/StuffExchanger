package com.example.stuffexchanger;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

    }
    public void SignUpBtnClick(View view)
    {
        EditText FirstNameEditText = (EditText)findViewById(R.id.firstname);
        String FirstName=FirstNameEditText.getText().toString();

        EditText LastNameEditText = (EditText)findViewById(R.id.lastname);
        String LastName=LastNameEditText.getText().toString();

        EditText usernameEditText = (EditText)findViewById(R.id.signupusername);
        String email=usernameEditText.getText().toString();

        EditText password1EditText = (EditText)findViewById(R.id.password1);
        String password1=password1EditText.getText().toString();

        EditText password2EditText = (EditText)findViewById(R.id.password2);
        String password2=password2EditText.getText().toString();

        if(FirstName.isEmpty()||LastName.isEmpty()||email.isEmpty()||password1.isEmpty()||password2.isEmpty()) {
            Toast.makeText(this, "Some values missing, please fill all the details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!password1.equals(password2)) {
                Toast.makeText(this, "Passwords do not match, Please re-enter password.", Toast.LENGTH_SHORT).show();
                password1EditText.setText("");
                password2EditText.setText("");
            }
            else
            {
                UserDBHelper mDbHelper=new UserDBHelper(this);
                long newRowId = mDbHelper.addUser(new User(FirstName,LastName,email, password1));
                    if(newRowId==-1)
                        {
                            Toast.makeText(this, "Error Occurred, Please contact the support desk.", Toast.LENGTH_SHORT).show();
                        }
                    else
                        {
                            Toast.makeText(this, "Congratulations! You are successfully signed up", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(myIntent);
                        }

                // close dbase
                mDbHelper.close();
            }
        }
    }
}

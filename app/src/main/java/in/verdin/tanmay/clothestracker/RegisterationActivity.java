package in.verdin.tanmay.clothestracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterationActivity  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_activtiy);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



    }

    public void onRegisterClick(View view){

        EditText nameField = (EditText) findViewById(R.id.nameField);
        EditText userIDField = (EditText) findViewById(R.id.userIDField);
        EditText passField = (EditText) findViewById(R.id.passField);

        String name = nameField.getText().toString().trim();
        String userID = userIDField.getText().toString().trim();
        String pass = passField.getText().toString().trim();



        LoginDbHelper mdpHelper = new LoginDbHelper(getApplicationContext());

        SQLiteDatabase db = mdpHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LoginDetailsEntry.FeedEntry.NAME_COLUMN, name);
        values.put(LoginDetailsEntry.FeedEntry.USERID_COLUMN, userID);
        values.put(LoginDetailsEntry.FeedEntry.PASS_COLUMN, pass);

         long newRowId = db.insert(
                LoginDetailsEntry.FeedEntry.TABLE_NAME,
                null,
                values);

        if(newRowId<0){
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setMessage("UserID already taken!");
            alert.create().show();

        }

        else{
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setMessage("Registration Successful \n Now Login with your credentials!");
            alert.create().show();
            final Intent intent=new Intent(this, LoginActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(intent);
                }
            }, 2000);
            this.finish();





        }
    }
}












package in.verdin.tanmay.clothestracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }





    public void onLoginClick
    (View view) {
        EditText userIDField = (EditText) findViewById(R.id.userIDLoginField);
        EditText passField = (EditText) findViewById(R.id.passLoginField);

        String userID = userIDField.getText().toString().trim();
        String pass = passField.getText().toString().trim();

        LoginDbHelper mDbHelper = new LoginDbHelper(getApplicationContext());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] columnsReturn, columnsValues;

        columnsReturn = new String[]{
                LoginDetailsEntry.FeedEntry.USERID_COLUMN,
                LoginDetailsEntry.FeedEntry.PASS_COLUMN};

        String columnsWhere = new String();
        columnsWhere= LoginDetailsEntry.FeedEntry.USERID_COLUMN +" = ? and " + LoginDetailsEntry.FeedEntry.PASS_COLUMN + " = ?";
        columnsValues = new String[]{
                userID,
                pass};

        Cursor c = db.query(
                LoginDetailsEntry.FeedEntry.TABLE_NAME,
                columnsReturn,
                columnsWhere,
                columnsValues,
                null,
                null,
                null
        );
        if(c.getCount()==0)
        {AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Invalid Credentials");
        alert.create().show();}
        else{
            c.moveToFirst();
        String retUserID = c.getString(
                c.getColumnIndex(LoginDetailsEntry.FeedEntry.USERID_COLUMN));
        String retPass = c.getString(
                c.getColumnIndex(LoginDetailsEntry.FeedEntry.PASS_COLUMN));

        if(retUserID.equals(userID) && retPass.equals(pass)){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Login Successful");
           // alert.create().show();

            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            this.finish();
        }
        else if(!(retUserID.equals(userID)) || !(retPass.equals(pass))){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Login Unsuccessful");
            alert.create().show();
        }
    }}

    public void onRegisterClick(View view){
        Intent intent=new Intent(this, RegisterationActivity.class);
        startActivity(intent);
    }
}
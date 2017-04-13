package in.verdin.tanmay.clothestracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.BaseColumns;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChangeStatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener,GestureDetector.OnGestureListener {


    private GestureDetectorCompat gesture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        TextView date = (TextView) findViewById(R.id.dateTimeDisplayer);
        date.setText(currentDateTimeString);

        gesture = new GestureDetectorCompat(this, this);
        gesture.setIsLongpressEnabled(true);
        Spinner statusSpinner = (Spinner) findViewById(R.id.newStatus);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.chooseStatus2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter2);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                newStatus = (String) parentView.getItemAtPosition(position);

                if(newStatus.equals("Status :"))
                {

                }
                else {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        callDisplay();

    }





    String type;
    String uid;
    String currentStatus;

    String newStatus;

    public void callDisplay(){
        displayValues(type, uid, currentStatus);
    }

    public void displayValues(String s1, String s2, String s3){
        TextView typeView = (TextView) findViewById(R.id.typeDisplayer);
        TextView uidView = (TextView) findViewById(R.id.uidDisplayer);
        TextView statusView = (TextView) findViewById(R.id.statusDisplayer);
        typeView.setText(getIntent().getStringExtra("type"));
        uidView.setText(getIntent().getStringExtra("uid"));
        statusView.setText(getIntent().getStringExtra("currentStatus"));

        File imageFile = new File(Environment.getExternalStorageDirectory().toString() + "/Android/data/" + getApplication().getPackageName() + "/Files/Images/"
                +getIntent().getStringExtra("uid")+ ".jpg");
        if(imageFile.exists()){
            Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ImageView image = (ImageView) findViewById(R.id.itemImage);
            image.setImageBitmap(bmp);
        }
        else{
            Toast.makeText(getBaseContext(), imageFile.getAbsolutePath() + "Image not found", Toast.LENGTH_LONG ).show();
        }
    }
    public void saveTransaction(View view) {
        TextView typeTextView = (TextView) findViewById(R.id.typeDisplayer);
        TextView uidTextView = (TextView) findViewById(R.id.uidDisplayer);


        String uid = (String) uidTextView.getText();
        try {
            ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();

            String query = "UPDATE items SET currentStatus ='" + newStatus + "' WHERE itemUID ='" + uid + "' ;";
            db.execSQL(query);

            Toast.makeText(getBaseContext(), "Status Updated", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e + "", Toast.LENGTH_LONG).show();
        }
    }

        public void saveTrans(){
        TextView date = (TextView) findViewById(R.id.dateTimeDisplayer);
        String dateTime = (String) date.getText();

        String typeText = getIntent().getStringExtra("type");
        String uidText = getIntent().getStringExtra("uid");
        String currentStatus = getIntent().getStringExtra("currentStatus");


        try {
            TransactionsDbHelper helper =  new TransactionsDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();



            String query = "INSERT INTO " + TransactionsEntry.NewTransaction.TABLE_NAME
                    + "( "+ TransactionsEntry.NewTransaction.DATE_AND_TIME_COLUMN
                    + ", "
                    + TransactionsEntry.NewTransaction.TYPE_COLUMN
                    + ", "
                    + TransactionsEntry.NewTransaction.UID_COLUMN
                    + ", "
                    + TransactionsEntry.NewTransaction.OLD_STATUS
                    + ", "
                    + TransactionsEntry.NewTransaction.NEW_STATUS
                    + ") "
                    + "VALUES ('"
                    + dateTime
                    + "', '"
                    + typeText
                    + "', '"
                    + uidText
                    + "', '"
                    + currentStatus
                    + "', '"
                    + newStatus
                    + "')";
            db.execSQL(query);

            Toast.makeText(getApplicationContext(), "Transaction Done", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e){

            TransactionsDbHelper helper = new TransactionsDbHelper(getApplicationContext());

            SQLiteDatabase db = helper.getWritableDatabase();

            String query = TransactionsEntry.NewTransaction.CREATE_ENTRY;

            db.execSQL(query);

            saveTrans();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Toast.makeText(getApplicationContext(), "Long Press", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}



 package in.verdin.tanmay.clothestracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class CurrentStatus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private GestureDetectorCompat gesture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_status);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Spinner typeSpinner = (Spinner) findViewById(R.id.typeChooser_status);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.itemTypes2, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter1);

        final Spinner statusChooser = (Spinner) findViewById(R.id.statusChooser_status);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.chooseStatus2, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusChooser.setAdapter(adapter2);





        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedItem = (String) parentView.getItemAtPosition(position);

                if(selectedItem.equals("Type :")) {
                    int i=0;
                    i++;
                    if (i == 1) {
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Choose Item Type!", Toast.LENGTH_LONG);
                    }
                }
                else {
                    getData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }



        });

        statusChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedStatus = (String) parentView.getItemAtPosition(position);
                    int i=0;
                if(selectedStatus.equals("Choose Current Status :"))
                {
                    i++;
                    if (i == 1) {
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Choose Status!", Toast.LENGTH_LONG);
                    }
                }
                else {
                    getData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void deleteItem(String uid){
        try {
            ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());

            SQLiteDatabase db = helper.getReadableDatabase();

            String query = "DELETE FROM items WHERE itemUID ='" + uid + "';";
            db.execSQL(query);

            Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG).show();
        }
       catch(SQLiteException e){
           Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
       }
    }
String userChoosenTask;
    public void onTableRowClicked(final String s1, final String s2, final String s3){
        final CharSequence[] items = { "Change Status","Delete Item", "See Previous Transactions",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Operation!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(getApplicationContext());

                if (items[item].equals("Change Status")) {
                    userChoosenTask="Change Status";
                    if(result)
                        changeStatusOpener(s1, s2, s3);

                }
                else if (items[item].equals("See Previous Transactions")) {
                    userChoosenTask="See Previous Transactions";
                    if(result)
                        previousTransactionsOpener();

                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
                else if (items[item].equals("Delete Item")){

                    deleteItem(s2);
                }
            }
        });
        builder.show();
    }


    public void changeStatusOpener(String s1, String s2, String s3){
        Intent intent =  new Intent(this, ChangeStatus.class);
        intent.putExtra("type",s1);
        intent.putExtra("uid",s2);
        intent.putExtra("currentStatus",s3);

        startActivity(intent);

        ChangeStatus cs = new ChangeStatus();





    }

    public void previousTransactionsOpener(){
        Intent intent =  new Intent (this, Transactions.class);
        startActivity(intent);

    }
    public String selectedItem;
    public String selectedStatus;

    public void getData() {
        if (selectedItem.equals("Type :") && selectedStatus.equals("Status :")) {

        } else if (selectedItem.equals("Type :") && !selectedStatus.equals("Status :")) {
            ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            String query = "SELECT * FROM items WHERE currentStatus ='" + selectedStatus + "';";
            Cursor cursor = db.rawQuery(query, null);

            TableLayout table = (TableLayout) findViewById(R.id.table);
            table.removeAllViews();
            TableRow rowHeader = new TableRow(getApplicationContext());

            rowHeader.setBackgroundColor(Color.parseColor("#16776961"));
            rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] headerText={"Type :       ","UID :       ","Status :       "};
            for(String c:headerText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackground(getDrawable(R.drawable.cell_shape_header));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setTextColor(Color.parseColor("#1C1C1C"));
                tv.setPadding(5, 5, 5, 5);
                tv.setText(c);
                rowHeader.addView(tv);

            }
            table.addView(rowHeader);



            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    final String itemType = cursor.getString(cursor.getColumnIndex("itemType"));
                    final String currentStatus = cursor.getString(cursor.getColumnIndex("currentStatus"));
                    final String UID = cursor.getString(cursor.getColumnIndex("itemUID"));


                    TableRow row = new TableRow(getApplicationContext());
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {itemType, UID, currentStatus};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setClickable(true);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                longClick(itemType, UID);

                            }
                        });
                        tv.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                onTableRowClicked(itemType, UID, currentStatus);
                                return true;
                            }
                        });
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setBackground(getDrawable(R.drawable.cell_shape));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setTextColor(Color.parseColor("#1C1C1C"));
                        tv.setPadding( 5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    table.addView(row);

                }
            }
        }
        else if(!selectedItem.equals("Type :") && !selectedStatus.equals("Status :")){
            ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            String query = "SELECT * FROM items WHERE currentStatus ='" + selectedStatus + "' AND itemType ='" + selectedItem + "' ;";
            Cursor cursor = db.rawQuery(query, null);

            TableLayout table = (TableLayout) findViewById(R.id.table);
            table.removeAllViews();
            TableRow rowHeader = new TableRow(getApplicationContext());

            rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
            rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] headerText={"Type :       ","UID :        ","Status :      "};
            for(String c:headerText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#1C1C1C"));
                tv.setBackground(getDrawable(R.drawable.cell_shape_header));
                tv.setTextSize(18);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(c);
                rowHeader.addView(tv);

            }
            table.addView(rowHeader);



            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    final String itemType = cursor.getString(cursor.getColumnIndex("itemType"));
                    final String currentStatus = cursor.getString(cursor.getColumnIndex("currentStatus"));
                    final String UID = cursor.getString(cursor.getColumnIndex("itemUID"));


                    TableRow row = new TableRow(getApplicationContext());
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {itemType, UID, currentStatus};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                longClick(itemType, UID);
                            }
                        });
                        tv.setOnLongClickListener(new View.OnLongClickListener(){
                            @Override
                            public boolean onLongClick(View view) {
                                onTableRowClicked(itemType, UID, currentStatus);

                                return true;
                            }
                        });

                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setBackground(getDrawable(R.drawable.cell_shape));

                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.parseColor("#1C1C1C"));
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    table.addView(row);

                }
            }
        }

        else if(!selectedItem.equals("Type :") && selectedStatus.equals("Status :")){

            ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            String query = "SELECT * FROM items WHERE  itemType ='" + selectedItem + "' ;";
            Cursor cursor = db.rawQuery(query, null);

            TableLayout table = (TableLayout) findViewById(R.id.table);
            table.removeAllViews();
            TableRow rowHeader = new TableRow(getApplicationContext());

            rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
            rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] headerText={"Type :       ","UID :        ","Status :      "};
            for(String c:headerText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#1C1C1C"));
                tv.setBackground(getDrawable(R.drawable.cell_shape_header));
                tv.setTextSize(18);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(c);
                rowHeader.addView(tv);

            }
             table.addView(rowHeader);



            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    final String itemType = cursor.getString(cursor.getColumnIndex("itemType"));
                    final String currentStatus = cursor.getString(cursor.getColumnIndex("currentStatus"));
                    final String UID = cursor.getString(cursor.getColumnIndex("itemUID"));


                    TableRow row = new TableRow(getApplicationContext());
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText = {itemType, UID, currentStatus};
                    for (String text : colText) {
                        TextView tv = new TextView(this);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                longClick(itemType, UID);

                            }
                        });
                        tv.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                onTableRowClicked(itemType, UID, currentStatus);

                                return true;
                            }
                        });

                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setBackground(getDrawable(R.drawable.cell_shape));

                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.parseColor("#1C1C1C"));
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    table.addView(row);

                }
            }

        }
    }

    public void longClick(String type, String uid){

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.image_layout,
                (ViewGroup) findViewById(R.id.layout_root));


        ImageView image = (ImageView) layout.findViewById(R.id.fullimage);

        imageDialog.setView(layout);

        File imageFile = new File(Environment.getExternalStorageDirectory().toString()
                 + "/Android/data/" + getApplication().getPackageName()
                 + "/Files/Images/" +
                 uid + ".jpg");
        if(imageFile.exists()){
            Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            image.setImageBitmap(bmp);
        }
        else
        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();

        imageDialog.create();
        imageDialog.show();

    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


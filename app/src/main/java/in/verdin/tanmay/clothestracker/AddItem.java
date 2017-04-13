package in.verdin.tanmay.clothestracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String selectedItem;
    String selectedStatus;
    private String userChoosenTask;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Spinner typeSpinner = (Spinner) findViewById(R.id.typeChooser);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.itemTypes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter1);

        Spinner statusSpinner = (Spinner) findViewById(R.id.statusChooser);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.chooseStatus, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter2);


        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedItem = (String) parentView.getItemAtPosition(position);

                if(selectedItem.equals("Choose Item Type :"))
                {

                }
                else {
                    setNewUID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedStatus = (String) parentView.getItemAtPosition(position);

                if(selectedStatus.equals("Choose Current Status :"))
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

    }

   /* public void deleteEverything(View view){
        ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM items");

        Toast.makeText(getBaseContext(), "Deleted Everything",Toast.LENGTH_LONG).show();

    }*/
public int newUID;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
   /* public void getUID(View view){

        ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemDetailsEntry.NewUID.TYPE_COLUMN, ITEM_TYPE);
        values.put(ItemDetailsEntry.NewUID.ITEM_NUM, newUID);
        values.put(ItemDetailsEntry.NewUID.UID_COLUMN, UID_COLUMN);
        values.put(ItemDetailsEntry.NewUID.STATUS_COLUMN, CURRENT_STATUS);

        long newRod = db.insert(
                ItemDetailsEntry.NewUID.TABLE_NAME,
                null,
                values
        );

        if(newRod < 0){
            Toast.makeText(getBaseContext(), "Table not created", Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getBaseContext(), "Table created", Toast.LENGTH_LONG).show();

        }
    }*/

    public void setNewUID(){

        if(selectedItem.equals("Choose Item Type :"))
            Toast.makeText(getApplicationContext(), "Choose Item Type!",Toast.LENGTH_LONG).show();

        try{
            ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());


        SQLiteDatabase db = helper.getWritableDatabase();


        String[] columnsReturn, columnsValues;
        String columnsWhere = ItemDetailsEntry.NewUID.TYPE_COLUMN + " = ? ";

        columnsReturn = new String[] {
                 ItemDetailsEntry.NewUID.ITEM_NUM
        };

        columnsValues = new String[]{
                AddItem.this.selectedItem
        };
        Cursor c = db.query(
                ItemDetailsEntry.NewUID.TABLE_NAME,
                columnsReturn,
                columnsWhere,
                columnsValues,
                null,
                null,
                "itemNumber desc"
        );

        if(c!=null){

        }


        if(c.getCount()==0){
            newUID = 1;
        }
        else{
            c.moveToFirst();
            int maxUID = c.getInt(c.getColumnIndex("itemNumber"));

            newUID = maxUID + 1;

        }

        TextView UID = (TextView) findViewById(R.id.uid_display);
        UID.setText(selectedItem + "++" + newUID + "");
    }
        catch (SQLiteException e){
             if(e.getMessage().toString().contains("no such table")){

                 ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());

                 SQLiteDatabase db = helper.getWritableDatabase();

                 String query = ItemDetailsEntry.NewUID.CREATE_ENTRY;

                 db.execSQL(query);

                 setNewUID();

             }
        }}

   public void saveNewItem(View view){

       if(selectedItem.equals("Choose Item Type :"))
           Toast.makeText(getApplicationContext(), "Choose Item Type  :", Toast.LENGTH_LONG).show();
       if(selectedStatus.equals("Choose Current Status :"))
           Toast.makeText(getApplicationContext(), "Choose Status :", Toast.LENGTH_LONG).show();

       ItemsDbHelper helper = new ItemsDbHelper(getApplicationContext());
       SQLiteDatabase db = helper.getWritableDatabase();

       ContentValues values = new ContentValues();
       values.put(ItemDetailsEntry.NewUID.TYPE_COLUMN, selectedItem);
       values.put(ItemDetailsEntry.NewUID.ITEM_NUM, newUID);
       values.put(ItemDetailsEntry.NewUID.UID_COLUMN, selectedItem + "++" + newUID);
       values.put(ItemDetailsEntry.NewUID.STATUS_COLUMN, selectedStatus );

       long newRod = db.insert(
               ItemDetailsEntry.NewUID.TABLE_NAME,
               null,
               values);

       if(newRod < 0)
       {
           Toast.makeText(getBaseContext(),"Item Not Added",Toast.LENGTH_LONG).show();

       }
       else{
           Toast.makeText(getBaseContext(), "Item Added", Toast.LENGTH_LONG).show();

       }

       try {
           destination.createNewFile();
           fo = new FileOutputStream(destination);
           fo.write(bytes.toByteArray());
           fo.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

       Spinner typeSpinner = (Spinner) findViewById(R.id.typeChooser);
       typeSpinner.setSelection(0);

       TextView uidDisplay = (TextView) findViewById(R.id.uid_display);
       uidDisplay.setText("");

       Spinner statusChoose = (Spinner) findViewById(R.id.statusChooser);
       statusChoose.setSelection(0);

       String uri = "@drawable/ic_action_name";
       int imageResource = getResources().getIdentifier(uri, null, getPackageName());
       Drawable res = getResources().getDrawable(imageResource);
       ImageView image = (ImageView) findViewById(R.id.itemImage);
       image.setImageDrawable(res);


   }

    public void selectImage(View view) {


        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(context);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private static int REQUEST_CAMERA = 3898;
    private static int SELECT_FILE = 3899;
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    File destination;
    FileOutputStream fo;
    ByteArrayOutputStream bytes;
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        Bitmap bmp=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                bytes = new ByteArrayOutputStream();
                bmp = Bitmap.createScaledBitmap(bm, 266, 200, false);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                String root = Environment.getExternalStorageDirectory().toString();
                File temp = new File(root + "/Android/data/" + getApplication().getPackageName() + "/Files/Images");
                temp.mkdirs();
                destination = new File(temp,selectedItem + "++" + newUID + ".jpg");



            }
                catch (IOException e) {
                e.printStackTrace();
            }
        }
        ImageView ivImage = (ImageView) findViewById(R.id.itemImage);
        ivImage.setImageBitmap(bm);
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap bmp=null;
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        bmp = Bitmap.createScaledBitmap(thumbnail, 266, 200, false);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String root = Environment.getExternalStorageDirectory().toString();
        File temp = new File(root + "/Android/data/" + getApplication().getPackageName() + "/Files/Images");
        temp.mkdirs();
        destination = new File(temp,selectedItem + "++" + newUID + ".jpg");






        ImageView ivImage = (ImageView) findViewById(R.id.itemImage);
        ivImage.setImageBitmap(thumbnail);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
      //
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    //
    }
}


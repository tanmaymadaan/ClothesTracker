package in.verdin.tanmay.clothestracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tanmay on 03-07-2016.
 */
public class ItemsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=4;
    public static final String DATABASE_NAME="appDatabase.db";

    public ItemsDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ItemDetailsEntry.NewUID.CREATE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ItemDetailsEntry.NewUID.DELETE_ENTRY);
        onCreate(sqLiteDatabase);
    }
}

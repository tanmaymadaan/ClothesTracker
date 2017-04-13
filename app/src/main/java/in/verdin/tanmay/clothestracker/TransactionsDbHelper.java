package in.verdin.tanmay.clothestracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tanmay on 06-07-2016.
 */
public class TransactionsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "appDatabase.db";

    public TransactionsDbHelper(Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TransactionsEntry.NewTransaction.CREATE_ENTRY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TransactionsEntry.NewTransaction.DELETE_ENTRY);
        onCreate(sqLiteDatabase);
    }
}

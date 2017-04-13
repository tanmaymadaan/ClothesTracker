package in.verdin.tanmay.clothestracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Tanmay on 02-07-2016.
 */
public class LoginDetailsEntry {

    public LoginDetailsEntry() {
    }


    public static abstract class FeedEntry implements BaseColumns {



        public static final String TABLE_NAME = "loginCredentials";
        public static final String NAME_COLUMN = "name";
        public static final String USERID_COLUMN = "userID";
        public static final String PASS_COLUMN = "pass";

        public static final String CREATE_ENTRY = "CREATE TABLE " + FeedEntry.TABLE_NAME + " ("
                + FeedEntry.NAME_COLUMN + " VARCHAR(255), "
                + FeedEntry.USERID_COLUMN + " VARCHAR(255) PRIMARY KEY, "
                + FeedEntry.PASS_COLUMN + "   VARCHAR(255))";

        public final static String DELETE_ENTRY = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    }


}



package in.verdin.tanmay.clothestracker;

import android.provider.BaseColumns;

/**
 * Created by Tanmay on 03-07-2016.
 */
public class ItemDetailsEntry {


public static abstract class NewUID implements BaseColumns {


    public static final String TABLE_NAME = "items";
    public static final String TYPE_COLUMN = "itemType";
    public static final String ITEM_NUM = "itemNumber";
    public static final String UID_COLUMN = "itemUID";
    public static final String STATUS_COLUMN = "currentStatus";

    public static final String CREATE_ENTRY = "CREATE TABLE IF NOT EXISTS " + NewUID.TABLE_NAME + " ("
            + NewUID.TYPE_COLUMN + " VARCHAR(255), "
            + NewUID.ITEM_NUM + " INT(7), "
            + NewUID.UID_COLUMN + " VARCHAR(255) PRIMARY KEY, "
            + NewUID.STATUS_COLUMN + " VARCHAR(255))";

    public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + NewUID.TABLE_NAME;
}
}

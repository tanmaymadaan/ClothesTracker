package in.verdin.tanmay.clothestracker;

import android.provider.BaseColumns;

/**
 * Created by Tanmay on 06-07-2016.
 */
public class TransactionsEntry {

    public static class NewTransaction implements BaseColumns{

        public static final String TABLE_NAME = "transactions";
        public static final String TRANSACTION_NUMBER = "transNum";
        public static final String DATE_AND_TIME_COLUMN = "dateTimeColumn";
        public static final String TYPE_COLUMN = "typeColumn";
        public static final String UID_COLUMN = "uidColumn";
        public static final String OLD_STATUS = "oldStatus";
        public static final String NEW_STATUS = "newStatus";

        public static final String CREATE_ENTRY = "CREATE TABLE " + NewTransaction.TABLE_NAME + " ("
                + NewTransaction.TRANSACTION_NUMBER + "INTEGER AUTOINCREMENT, "
                + NewTransaction.DATE_AND_TIME_COLUMN + "VARCHAR(255), "
                + NewTransaction.TYPE_COLUMN + "VARCHAR(255), "
                + NewTransaction.UID_COLUMN + "VARCHAR(255), "
                + NewTransaction.OLD_STATUS + "VARCHAR(255), "
                + NewTransaction.NEW_STATUS + "VARCHAR(255))";

        public static final String DELETE_ENTRY = "DROP TABLE IF EXISTS " + NewTransaction.TABLE_NAME;

    }
}

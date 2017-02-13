package ch.usi.inf.mc.awareapp.Database;

import ch.usi.inf.mc.awareapp.Database.LocalTables;

import static android.R.attr.data;

/**
 * Created by usi on 18/01/17.
 */

public class LocalDbUtility {
    private final static int DATA_TABLES_COUNT = 8;

    public static int getDataTablesCount() {
        return DATA_TABLES_COUNT;
    }

    public static String getTableName(LocalTables table) {
        switch (table) {
            case RegistrationTable:
                return DatabaseHandler.TABLE_REGISTRATION;
            default:
                return null;
        }
    }

    public static String[] getTableColumns(LocalTables table) {
        switch (table) {
            case RegistrationTable:
                return DatabaseHandler.getColumnsRegistration();
            default:
                return null;
        }
    }

}

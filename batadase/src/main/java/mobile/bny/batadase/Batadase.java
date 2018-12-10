package mobile.bny.batadase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by BNY on 11/11/2017.
 */

public abstract class Batadase<T> extends SQLiteOpenHelper {

    public abstract BatadaseTable[] getTables();
    protected abstract T fromCursor(Cursor cursor);
    protected abstract ContentValues getContentValues (T t);

    protected Batadase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Superclass methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        if(getTables() == null || getTables().length < 1)
            throw new NullPointerException("Classes inheriting from Batadase must implement getTables() and return at least 1 BatadaseTable");

        for(BatadaseTable table : getTables()) {
            if(table.hasPrimaryKey())
                db.execSQL(table.toString());
            else
                throw new IllegalArgumentException(String.format("BatadaseTable with Name = %s has no PRIMARY KEY. Need minimum one!",table.getName()));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + getDatabaseName());
        onCreate(db);
    }

    //Batadase methods
    //Insert and update

    /**
     * Method that inserts a list of objects of the type associated with this database in the database table with the given name.
     *
     * @param array_list ArrayList<T>: the list of objects to be inserted
     * @param tableName String: the name of the target table in the database
     */
    public final void insertAll(ArrayList<T> array_list, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertAll(db, array_list, tableName);
        db.close();
    }

    /**
     * Method that inserts a list of objects of the type associated with this database in the database table with the given name.
     *
     * @param db SQLiteDatabase: the database used to write the objects in the table, usually this.getWritableDatabase()
     * @param array_list ArrayList<T>: the list of objects to be inserted
     * @param tableName String: the name of the target table in the database
     */
    public final void insertAll(SQLiteDatabase db, ArrayList<T> array_list, String tableName){

        if(array_list != null && array_list.size() >0){

            for (T t : array_list)
                insert(db, t, tableName);
        }
    }

    /**
     * Method that inserts a single object of the type associated with this database in the database table with the given name.
     *
     * @param db SQLiteDatabase: the database used to write the objects in the table, usually this.getWritableDatabase()
     * @param t T: a single object, of the type associated with this database, that needs to be inserted
     * @param tableName String: the name of the target table in the database
     */
    public final void insert(SQLiteDatabase db, T t, String tableName){
        ContentValues contentValues = getContentValues(t);

        db.insert(tableName,null,contentValues);
    }

    /**
     * Method that updates a list of objects of the type associated with this database in the database table with the given name.
     *
     * @param array_list ArrayList<T>: the list of objects to be updated
     * @param tableName String: the name of the target table in the database
     */
    public final void updateAll(ArrayList<T> array_list, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        updateAll(db, array_list, tableName);
        db.close();
    }


    /**
     * Method that updates a list of objects of the type associated with this database in the database table with the given name.
     *
     * @param db SQLiteDatabase: the database used to write the objects in the table, usually this.getWritableDatabase()
     * @param array_list ArrayList<T>: the list of objects to be updated
     * @param tableName String: the name of the target table in the database
     */
    public final void updateAll(SQLiteDatabase db, ArrayList<T> array_list, String tableName){

        if(array_list != null && array_list.size() >0){
            for (T t : array_list)
                update(db, t, tableName);
        }
    }

    /**
     * Method that updates a single object of the type associated with this database in the database table with the given name.
     *
     * @param db SQLiteDatabase: the database used to write the objects in the table, usually this.getWritableDatabase()
     * @param t T: a single object, of the type associated with this database, that needs to be updated
     * @param tableName String: the name of the target table in the database
     */
    public void update(SQLiteDatabase db, T t, String tableName){

        if(!getTableNames().contains(tableName))
            throw new IllegalArgumentException(String.format("Found no table with tableName = %s",tableName));

        ContentValues contentValues = getContentValues(t);
        BatadaseTable table = getTable(tableName);
        BatadaseColumn primary = table.getPrimaryColumn();

        Object value = contentValues.get(primary.getKey());
        String clause;
        if(primary.getType() == BatadaseColumn.DataType.TEXT)
            clause = String.format("%s = '%s'",primary.getKey(), value);
        else
            clause = String.format("%s = %s",primary.getKey(), value);

        db.update(tableName,contentValues, clause, null);

    }

    /**
     * Method that updates a list of objects of the type associated with this database in the database table with the given name
     * and creates a new row if object in the list doesn't exist in the table.
     *
     * @param array_list ArrayList<T>: the list of objects to be updated
     * @param tableName String: the name of the target table in the database
     */
    public final void updateAllSafe(ArrayList<T> array_list, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        updateAllSafe(db, array_list, tableName);
        db.close();
    }


    /**
     * Method that updates a list of objects of the type associated with this database in the database table with the given name
     * and creates a new row if object in the list doesn't exist in the table.
     *
     * @param db SQLiteDatabase: the database used to write the objects in the table, usually this.getWritableDatabase()
     * @param array_list ArrayList<T>: the list of objects to be updated
     * @param tableName String: the name of the target table in the database
     */
    public final void updateAllSafe(SQLiteDatabase db, ArrayList<T> array_list, String tableName){

        if(array_list != null && array_list.size() >0){
            for (T t : array_list)
                updateSafe(db, t, tableName);
        }
    }

    /**
     * Method that updates a single object of the type associated with this database in the database table with the given name
     * and creates a new row if object in the list doesn't exist in the table.
     *
     * @param db SQLiteDatabase: the database used to write the objects in the table, usually this.getWritableDatabase()
     * @param t T: a single object, of the type associated with this database, that needs to be updated
     * @param tableName String: the name of the target table in the database
     */
    public void updateSafe(SQLiteDatabase db, T t, String tableName){

        if(!getTableNames().contains(tableName))
            throw new IllegalArgumentException(String.format("Found no table with tableName = %s",tableName));

        ContentValues contentValues = getContentValues(t);
        BatadaseTable table = getTable(tableName);
        BatadaseColumn primary = table.getPrimaryColumn();

        Object value = contentValues.get(primary.getKey());

        String clause;
        if(primary.getType() == BatadaseColumn.DataType.TEXT)
            clause = String.format("%s = '%s'",primary.getKey(), value);
        else
            clause = String.format("%s = %s",primary.getKey(), value);

        if(getFrom(db, tableName,primary.getKey(),value.toString()) == null){
            insert(db,t,tableName);
        } else {
            db.update(tableName,contentValues, clause, null);
        }
    }


    public void delete(SQLiteDatabase database, T t, String tableName){
        if(!getTableNames().contains(tableName))
            throw new IllegalArgumentException(String.format("Found no table with tableName = %s",tableName));

        final ContentValues contentValues = getContentValues(t);
        final BatadaseTable table = getTable(tableName);
        final BatadaseColumn primary = table.getPrimaryColumn();

        final Object value = contentValues.get(primary.getKey());

        String clause;
        if(primary.getType() == BatadaseColumn.DataType.TEXT)
            clause = String.format("%s = '%s'",primary.getKey(), value);
        else
            clause = String.format("%s = %s",primary.getKey(), value);


        database.delete(tableName, clause,null);
    }


    public void deleteWhere(SQLiteDatabase database, String tableName, String whereClause, String[] whereArgs){
        if(!getTableNames().contains(tableName))
            throw new IllegalArgumentException(String.format("Found no table with tableName = %s",tableName));


        database.delete(tableName, whereClause, whereArgs);
    }

    //Getters

    /**
     * Method that returns an object of the type associated with this database
     *
     * @param tableName String: the name of the target table in the database
     * @param columnName String: the name of the target column in the table
     * @param value String: the specific value associated with the target object
     *
     * @return T: an object of the type associated with this database
     */
    public final T getFrom(String tableName, String columnName, String value) {
        SQLiteDatabase db = getReadableDatabase();
        T t = getFrom(db, tableName, columnName, value);
        db.close();
        return t;
    }

    /**
     * Method that returns an object of the type associated with this database
     *
     * @param db SQLiteDatabase: the database used to read the table, usually this.getReadableDatabase()
     * @param tableName String: the name of the target table in the database
     * @param columnName String: the name of the target column in the table
     * @param value String: the specific value associated with the target object
     *
     * @return T: an object of the type associated with this database
     */
    public final T getFrom(SQLiteDatabase db, String tableName, String columnName, String value){

        String query = selectFromWhereEquals(tableName, columnName, value);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            T t = fromCursor(cursor);
            cursor.close();
            return t;
        }
        return null;
    }

    /**
     * This methods gets all the objects from the provided table name
     *
     * @param tableName String: the name of the target table
     *
     * @return ArrayList<T>: a list of objects of the type associated with this database
     */
    public final ArrayList<T> getAllFrom(String tableName) {

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<T> array_list = getAllFrom(db, tableName);
        db.close();
        return array_list;
    }

    /**
     * This methods gets all the objects from the provided table names
     *
     * @param tableNames String[]: an array of the names of the target tables
     *
     * @return ArrayList<T>: a list of objects of the type associated with this database
     */
    public final ArrayList<T> getAllFrom(String... tableNames) {

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<T> array_list = new ArrayList<>(tableNames.length);

        for(String tableName : tableNames)
            array_list.addAll(getAllFrom(db, tableName));

        db.close();
        return array_list;
    }

    /**
     * This methods gets all the objects from the provided table names
     *
     * @param db SQLiteDatabase: the database used to read the table, usually this.getReadableDatabase()
     * @param tableNames String[]: an array of the names of the target tables
     *
     * @return ArrayList<T>: a list of objects of the type associated with this database
     */
    public final ArrayList<T> getAllFrom(SQLiteDatabase db, String... tableNames) {

        ArrayList<T> array_list = new ArrayList<>(tableNames.length);

        for(String tableName : tableNames)
                array_list.addAll(getAllFrom(db, tableName));

        return array_list;
    }

    /**
     * This methods gets all the objects from the provided table name
     *
     * @param db SQLiteDatabase: the database used to read the table, usually this.getReadableDatabase()
     * @param tableName String: the name of the target table
     *
     * @return ArrayList<T>: a list of objects of the type associated with this database
     */
    public final ArrayList<T> getAllFrom(SQLiteDatabase db, String tableName){
        final ArrayList<T> array_list = new ArrayList<>();

        String query = selectFrom(tableName);

        Cursor cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){

                T t = fromCursor(cursor);
                array_list.add(t);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return array_list;
    }

    /**
     * This methods gets all the object from all the tables associated with this database
     *
     * @param db SQLiteDatabase: the database used to read the table, usually this.getReadableDatabase()
     * @return ArrayList<T>: a list of objects of the type associated with this database
     */
    public final ArrayList<T> getAll(SQLiteDatabase db){


        ArrayList<T> array_list =  new ArrayList<>();

        for (BatadaseTable table : getTables())
            array_list.addAll(getAllFrom(db, table.getName()));

        return array_list;
    }

    /**
     * This methods gets all the object from all the tables associated with this database
     *
     * @return ArrayList<T>: a list of objects of the type associated with this database
     */
    public final ArrayList<T> getAll(){

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<T> array_list =  getAll(db);

        db.close();

        return array_list;
    }

    //Count

    /**
     * Method if you need to know how many rows are in a specific table
     *
     * @param db SQLiteDatabase: the database used to read the table, usually this.getReadableDatabase()
     * @param tableName String: the name of the target table
     *
     * @return int: the number of rows
     */
    public static int getCount(SQLiteDatabase db, String tableName){

        return (int) DatabaseUtils.queryNumEntries(db,tableName);
    }

    /**
     * Method if you need to know how many rows are in all tables
     *
     * @param db SQLiteDatabase: the database used to read the table, usually this.getReadableDatabase()
     * @return int: the total number of rows
     */

    public final int getCount(SQLiteDatabase db) {
        if(getTables() == null || getTables().length == 0)
            return 0;

        int count = 0;
        for(BatadaseTable table : getTables())
            count += getCount(db,table.getName());

        return count;
    }

    /**
     * Method if you need to know how many rows are in all tables
     *
     * @return int: the total number of rows
     */

    public final int getCount() {
        if(getTables() == null || getTables().length == 0)
            return 0;

        SQLiteDatabase db = this.getReadableDatabase();
        int count = getCount(db);

        db.close();
        return count;
    }

    /**
     * Method if you need to know how many rows are in a specific table
     *
     * @param tableName String: the name of the target table
     *
     * @return int: the number of rows
     */
    public final int getCount(String tableName) {
        if(getTables() == null || getTables().length == 0)
            return 0;

        SQLiteDatabase db = this.getReadableDatabase();
        int count = getCount(db, tableName);

        db.close();
        return count;
    }

    /**
     * Method to get a specific BatadaseTable based on the table's name
     *
     * @param tableName String: the name of the target
     *
     * @return BatadaseTable: the Batadase table with the specified name
     */
    public final BatadaseTable getTable(String tableName){
        for(BatadaseTable table : getTables())
            if(table.getName().equals(tableName))
                return table;

        return null;
    }

    //QUERIES
    protected static String selectFrom(String tableName){
        return String.format("SELECT  * FROM %s",tableName);
    }

    protected static String selectOnlyFrom(String[] columns, String tableName){
        return String.format("SELECT %s FROM %s", buildColumnsString(columns),tableName);
    }

    protected static String selectOnlyFrom(BatadaseColumn[] columns, String tableName){
        return String.format("SELECT %s FROM %s", buildColumnsString(columns),tableName);
    }

    protected static String selectFromWhereEquals(String tableName, String columnName, Object item){
//        return String.format("SELECT  * FROM %s WHERE %s = %s", tableName, columnName, item);
        return String.format("SELECT  * FROM %s WHERE %s = '%s'", tableName, columnName, item);
    }

    protected static String selectFromWhereContains(String tableName, String columnName, Object item){
        return String.format("SELECT  * FROM %s WHERE %s LIKE '%%%s%%'", tableName, columnName, item);
    }

    protected static String selectOnlyFromWhereEquals(String[] columns, String tableName, String columnName, Object item){
        return String.format("SELECT %s FROM %s WHERE %s = '%s'", buildColumnsString(columns), tableName, columnName, item);
    }

    protected static String selectOnlyFromWhereContains(String[] columns, String tableName, String columnName, Object item){
        return String.format("SELECT %s FROM %s WHERE %s LIKE '%%%s%%'", buildColumnsString(columns), tableName, columnName, item);
    }

    protected static String selectOnlyFromWhereEquals(BatadaseColumn[] columns, String tableName, String columnName, Object item){
        return String.format("SELECT %s FROM %s WHERE %s = '%s'", buildColumnsString(columns), tableName, columnName, item);
    }

    protected static String selectOnlyFromWhereContains(BatadaseColumn[] columns, String tableName, String columnName, Object item){
        return String.format("SELECT %s FROM %s WHERE %s LIKE '%%%s%%'", buildColumnsString(columns), tableName, columnName, item);
    }

    protected static String selectFromRandom(String tableName, int amount) {
        return String.format("SELECT * FROM %s ORDER BY RANDOM() LIMIT %s", tableName, amount);
    }

    protected static String selectOnlyFromRandom(String[] columns, String tableName, int amount) {
        return String.format("SELECT %s FROM %s ORDER BY RANDOM() LIMIT %s", buildColumnsString(columns), tableName, amount);
    }

    protected static String selectOnlyFromRandom(BatadaseColumn[] columns, String tableName, int amount) {
        return String.format("SELECT %s FROM %s ORDER BY RANDOM() LIMIT %s", buildColumnsString(columns), tableName, amount);
    }

    //private methods
    private ArrayList<String> getTableNames(){
        if(getTables() == null || getTables().length < 1)
            return null;
        ArrayList<String> tableNames = new ArrayList<>(getTables().length);

        for (BatadaseTable table : getTables())
            tableNames.add(table.getName());

        return tableNames;
    }

    private static String buildColumnsString(String[] columns){
        StringBuilder builder = new StringBuilder(columns[0]);

        for (int i = 1; i < columns.length; i++)
            builder.append(String.format(", %s",columns[i]));

        return builder.toString();
    }

    private static String buildColumnsString(BatadaseColumn[] columns){
        StringBuilder builder = new StringBuilder(columns[0].getKey());

        for (int i = 1; i < columns.length; i++)
            builder.append(String.format(", %s",columns[i].getKey()));

        return builder.toString();
    }



//    public void exportCVS(Activity activity, BatadaseTable table){
//        exportCVS(activity, table,table.getColumns());
//    }
//
//    public void exportCVS(Activity activity, BatadaseTable table, BatadaseColumn... columns) {
//
//        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TestFiles");
//        if (!exportDir.exists()) {
//            exportDir.mkdirs();
//        }
//
//        File file = new File(exportDir, String.format("%s.csv",table.getName()));
//        try {
////            file.mkdir();
//            int permissionCheck = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                if (!file.createNewFile()) {
//                    Log.i("Test", "This file is already exist: " + file.getAbsolutePath());
//                }
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            String query = selectFrom(table.getName());
//
//            Cursor curCSV = db.rawQuery(query,null);
//            csvWrite.writeNext(getColumnNames(columns));
//
//            while(curCSV.moveToNext()) {
//                String arrStr[] = getCVSstringArray(curCSV,columns);
//                csvWrite.writeNext(arrStr);
//            }
//            curCSV.close();
//            csvWrite.close();
//            }
////            file.createNewFile();
//
//
//        }
//        catch(Exception e) {
//            Blogger.logcat(getClass(),e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private String[] getColumnNames(BatadaseColumn[] columns){
//        List<String> list = new ArrayList<>(columns.length);
//        for(BatadaseColumn column : columns){
//            list.add(column.getKey());
//        }
//        return list.toArray(new String[0]);
//    }
//
//    private String[] getCVSstringArray(Cursor cursor ,BatadaseColumn[] columns){
//        List<String> list = new ArrayList<>(columns.length);
//        for(BatadaseColumn column : columns){
//            list.add(getCVSstring(cursor,column));
//        }
//        return list.toArray(new String[0]);
//    }
//
//    private String getCVSstring(Cursor cursor ,BatadaseColumn column){
//        try {
//            switch (column.getType()){
//                case NULL:
//                    return "";
//                case BLOB:
//                    return String.valueOf(cursor.getBlob(cursor.getColumnIndex(column.getKey())));
//                case REAL:
//                    return String.valueOf(cursor.getDouble(cursor.getColumnIndex(column.getKey())));
//                case INTEGER:
//                    return String.valueOf(cursor.getInt(cursor.getColumnIndex(column.getKey())));
//                case TEXT:
//                    return cursor.getString(cursor.getColumnIndex(column.getKey()));
//            }
//        } catch (Exception e) {
//            Blogger.logcat(getClass(),e.getLocalizedMessage());
//        }
//            return null;
//
//    }
}

package mobile.bny.bandroidapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mobile.bny.androidtools.Blogger;
import mobile.bny.batadase.Batadase;
import mobile.bny.bandroidapp.data.User.Keys;
import mobile.bny.batadase.BatadaseColumn;
import mobile.bny.batadase.BatadaseTable;

import static mobile.bny.batadase.BatadaseColumn.DataType;


public class UserDatabase extends Batadase<User> {

    private static final String DATABASE_NAME = "MyUserDB.db";
    public static final String DATABASE_TABLE_NAME = "data_table";
    private static final int DATABASE_VERSION = 1;

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertAllUsers(UserData userData){
        if(userData != null)
            insertAll(userData.getData(), DATABASE_TABLE_NAME);
    }

    public void updateAllUsers(UserData userData){
        if(userData != null)
            updateAll(userData.getData(), DATABASE_TABLE_NAME);
    }

    public User get(int userId){
        return get(Keys.ID, String.valueOf(userId));
    }

    public User get(String columnName, String userSpecificValue){
        return getFrom(DATABASE_TABLE_NAME, columnName, userSpecificValue);
    }

    public User getRandomUser(){

        SQLiteDatabase db = getReadableDatabase();

        String query = selectFromRandom(DATABASE_TABLE_NAME, 1);

        Blogger.debug(getClass(),"String query = %s", query);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();

            if (cursor.getCount() > 0) {

                User user = fromCursor(cursor);
                cursor.close();
                db.close();
                return user;
            }
            cursor.close();
        }

        db.close();

        return null;
    }

    public ArrayList<String> getAllMalesNames(){
        final ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = selectOnlyFromWhereEquals(
                new String[]{ Keys.FIRST_NAME, Keys.LAST_NAME },    //Select only these columns
                DATABASE_TABLE_NAME,                                //from this table
                Keys.GENDER,                                        //where this column
                "Male"                                              //equals this
        );

        Blogger.debug(getClass(),"String query = %s", query);

        Cursor cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){

                array_list.add(
                        String.format("%s %s",
                                cursor.getString(cursor.getColumnIndex(Keys.FIRST_NAME)),
                                cursor.getString(cursor.getColumnIndex(Keys.LAST_NAME)))
                );

                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();

        return array_list;

    }

    //Need tablename(s) and their columnstructure(s)
    @Override
    public BatadaseTable[] getTables() {
        return new BatadaseTable[]{
                new BatadaseTable(
                        DATABASE_TABLE_NAME,
                        new BatadaseColumn(Keys.ID, DataType.INTEGER, true), //true = primary key (make sure you have at least 1 primary key)
                        new BatadaseColumn(Keys.FIRST_NAME, DataType.TEXT),
                        new BatadaseColumn(Keys.LAST_NAME, DataType.TEXT),
                        new BatadaseColumn(Keys.EMAIL, DataType.TEXT),
                        new BatadaseColumn(Keys.GENDER, DataType.TEXT),
                        new BatadaseColumn(Keys.AVATAR, DataType.TEXT)
                )
        };
    }

    //Need to know how to translate a Cursor into User
    @Override
    protected User fromCursor(Cursor cursor) {
        if (cursor.getCount() > 0) {
            User user = new User();

            user.setId(cursor.getInt(cursor.getColumnIndex(Keys.ID)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(Keys.FIRST_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(Keys.LAST_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(Keys.EMAIL)));
            user.setGender(cursor.getString(cursor.getColumnIndex(Keys.GENDER)));
            user.setAvatar(cursor.getString(cursor.getColumnIndex(Keys.AVATAR)));

            return user;
        }
        return null;
    }

    //Need to know how to store User
    @Override
    protected ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        if(user != null){

            contentValues.put(Keys.ID, user.getId());
            contentValues.put(Keys.FIRST_NAME, user.getFirstName());
            contentValues.put(Keys.LAST_NAME, user.getLastName());
            contentValues.put(Keys.EMAIL, user.getEmail());
            contentValues.put(Keys.GENDER, user.getGender());
            contentValues.put(Keys.AVATAR, user.getAvatar());

        }
        return contentValues;
    }
}

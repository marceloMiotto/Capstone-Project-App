package udacitynano.com.br.cafelegal.data;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class DatabaseProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private   DatabaseHelper mOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;

    static final int PESSOA = 100;

    private static final SQLiteQueryBuilder sPessoasQueryBuilder;

    static{
        sPessoasQueryBuilder = new SQLiteQueryBuilder();

        sPessoasQueryBuilder.setTables(DatabaseContract.PessoaEntry.TABLE_NAME);

    }


    private Cursor getPessoas(String[] projection, String selection,
                              String[] selectionArgs,String sortOrder) {



        Cursor pessoaCursor = sPessoasQueryBuilder.query(sqLiteDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        Log.e("Debug","Debug10 first result "+ pessoaCursor.getCount());

        return pessoaCursor;
    }


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, DatabaseContract.PATH_PESSOA, PESSOA);
        return matcher;
    }

    public DatabaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if ( null == selection ) selection = "1";
        switch (match) {
            case PESSOA:
                rowsDeleted = sqLiteDatabase.delete(
                        DatabaseContract.PessoaEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case PESSOA:
                return DatabaseContract.PessoaEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {

            case PESSOA: {
                //normalizeDate(values);
                long _id = sqLiteDatabase.insert(DatabaseContract.PessoaEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = DatabaseContract.PessoaEntry.buildUserUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        if(sqLiteDatabase == null) {
            sqLiteDatabase = mOpenHelper.getWritableDatabase();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case PESSOA: {
                Log.e("Debug","Debug07 "+projection.toString());

                retCursor = getPessoas(projection, selection, selectionArgs, sortOrder);
                Log.e("Debug","Debug08 "+retCursor.getCount());
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {

            case PESSOA:
                //normalizeDate(values);
                rowsUpdated = sqLiteDatabase.update(DatabaseContract.PessoaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {


            case PESSOA:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        //normalizeDate(value);
                        long _id = sqLiteDatabase.insert(DatabaseContract.PessoaEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    /*
    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(MeasureContract.MeasureEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(WeatherContract.WeatherEntry.COLUMN_DATE);
            values.put(WeatherContract.WeatherEntry.COLUMN_DATE, WeatherContract.normalizeDate(dateValue));
        }
    }
    */
}
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

    static final int PESSOA  = 100;
    static final int CONVITE = 101;

    private static final SQLiteQueryBuilder sPessoasQueryBuilder;
    private static final SQLiteQueryBuilder sConvitesQueryBuilder;

    static{
        sPessoasQueryBuilder = new SQLiteQueryBuilder();

        sPessoasQueryBuilder.setTables(DatabaseContract.PessoaEntry.TABLE_NAME);

        sConvitesQueryBuilder = new SQLiteQueryBuilder();

        sConvitesQueryBuilder.setTables(DatabaseContract.ConviteEntry.TABLE_NAME);

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

        return pessoaCursor;
    }


    private Cursor getConvites(String[] projection, String selection,
                              String[] selectionArgs,String sortOrder) {



        Cursor conviteCursor = sConvitesQueryBuilder.query(sqLiteDatabase,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return conviteCursor;
    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, DatabaseContract.PATH_PESSOA, PESSOA);
        matcher.addURI(authority, DatabaseContract.PATH_CONVITE, CONVITE);
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

            case CONVITE:
                rowsDeleted = sqLiteDatabase.delete(
                        DatabaseContract.ConviteEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Delete Unknown uri: " + uri);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case PESSOA:
                return DatabaseContract.PessoaEntry.CONTENT_TYPE;

            case CONVITE:
                return DatabaseContract.ConviteEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("getType Unknown uri: " + uri);
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

            case CONVITE: {
                //normalizeDate(values);
                long _id = sqLiteDatabase.insert(DatabaseContract.ConviteEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = DatabaseContract.ConviteEntry.buildUserUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Insert Unknown uri: " + uri);
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
                retCursor = getPessoas(projection, selection, selectionArgs, sortOrder);
                break;
            }

            case CONVITE: {
                retCursor = getConvites(projection, selection, selectionArgs, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Query Unknown uri: " + uri);
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

            case CONVITE:
                //normalizeDate(values);
                rowsUpdated = sqLiteDatabase.update(DatabaseContract.ConviteEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Update Unknown uri: " + uri);
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

            case CONVITE:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {
                        long _id = sqLiteDatabase.insert(DatabaseContract.ConviteEntry.TABLE_NAME, null, value);
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

}
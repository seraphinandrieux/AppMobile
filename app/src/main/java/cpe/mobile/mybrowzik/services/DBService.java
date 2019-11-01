package cpe.mobile.mybrowzik.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;

import cpe.mobile.mybrowzik.models.DbConstants;
import cpe.mobile.mybrowzik.models.DbRequestType;

public class DBService {

    private SQLiteDatabase db;


    public DBService(SQLiteDatabase db){

        this.db = db;
    }


    public void executeRequest(DbRequestType requestType, String myTable, String whereClause,String[] myArgs){

        ContentValues contentValues = new ContentValues();
        try {
            try {
                switch (myTable) {

                    case DbConstants.MUSIC_TABLE:

                        contentValues.put(DbConstants.KEY_COL_TITLE, myArgs[0]);
                        contentValues.put(DbConstants.KEY_COL_ARTIST_ID, Integer.parseInt(myArgs[1]));
                        contentValues.put(DbConstants.KEY_COL_ALBUM_ID, Integer.parseInt(myArgs[2]));
                        contentValues.put(DbConstants.KEY_COL_GENRE_ID, Integer.parseInt(myArgs[3]));


                        break;

                    case DbConstants.ALBUM_TABLE:
                        contentValues.put(DbConstants.KEY_COL_ALBUM, myArgs[0]);
                        contentValues.put(DbConstants.KEY_COL_IMAGE_URL, myArgs[1]);
                        contentValues.put(DbConstants.KEY_COL_YEAR, Integer.parseInt(myArgs[2]));

                        break;

                    case DbConstants.ARTIST_TABLE:
                        contentValues.put(DbConstants.KEY_COL_ARTIST, myArgs[0]);

                        break;

                    case DbConstants.GENRE_TABLE:
                        contentValues.put(DbConstants.KEY_COL_GENRE, myArgs[0]);

                        break;


                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e);
            }
        }catch(NullPointerException e){
            System.out.println(e);
        }

        switch(requestType) {

            case INSERT:

                db.insert(myTable, null, contentValues);
                break;

            case DELETE:

                db.delete(myTable,whereClause,myArgs);
                break;

            case UPDATE:


                db.update(myTable, contentValues, whereClause, myArgs);
                break;
            default:
                // code block
        }


    }

    public int getIDfromTable(String myTable,String whereClause,String[] whereArgs){


        String[] projections=new String[] { DbConstants.KEY_COL_ID };

        // And then store the column index answered by the request (we present an other way to retireve data)
        final int cursorIdColNumber=0;



        Cursor cursor=db.query(myTable,projections ,whereClause, whereArgs,null,null,null);

        ArrayList<Object> returnList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            // The elements to retrieve
            Object colResult;

            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(DbConstants.KEY_COL_ID );


            do {
                colResult = cursor.getInt(indexId);
                returnList.add(colResult);

            } while (cursor.moveToNext());

        } else {
            System.out.println("No elements found");
        }
        try {
            return (Integer) returnList.get(0);
        }catch(NullPointerException e){
            return -1;
        }
    }



    // return all projections wanted TODO Optimize it to have each rows for one row in the table and not horizontal row

    public ArrayList<Object> getMultiListfromTable(String myTable,String whereClause,String[] whereArgs,String[] projections){


        Cursor cursor=db.query(myTable,projections ,whereClause, whereArgs,null,null,null);
        return getMultiResultsCursor(cursor,projections);

    }

    public ArrayList<Object> getListfromTable(String myTable,String whereClause,String[] whereArgs,String column){


        String[] projections=new String[] { column };


        Cursor cursor ;

        /*
        try {
            System.out.println("My table :" + myTable + "| where clause : " + whereClause + "| where args : " + whereArgs[0] + " column to check : " + column);
        }catch(NullPointerException e){
            System.out.println(e);
        }

         */
        cursor=db.query(myTable,projections ,whereClause + "=?", whereArgs,null,null,null);
        return getResultsCursor(cursor,column);

    }

    public ArrayList<Object> getMultiResultsCursor(Cursor cursor,String[] projections){
        ArrayList<Object> returnList = new ArrayList<>();

        Integer len = projections.length;

        for (Integer i=0;i<len;i++) {
            ArrayList<Object> projectionsCursor = new ArrayList<>();
            if (cursor.moveToFirst()) {
                // The elements to retrieve
                Object colResult;

                // The associated index within the cursor
                int indexId = cursor.getColumnIndex(projections[i]);

                do {
                    // colResult = cursor.getInt(indexId);

                    colResult = cursor.getString(indexId);

                    projectionsCursor.add(colResult);

                } while (cursor.moveToNext());

            } else {
                System.out.println("No elements found");
            }
            returnList.add(projectionsCursor.clone());
        }
        cursor.close();

        return returnList;

    }


    public ArrayList<Object> getResultsCursor(Cursor cursor,String columnName){
            ArrayList<Object> returnList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                // The elements to retrieve
                Object colResult;

                // The associated index within the cursor
                int indexId = cursor.getColumnIndex(columnName);

                do {
                   // colResult = cursor.getInt(indexId);

                    colResult = cursor.getString(indexId);

                    returnList.add(colResult);

                } while (cursor.moveToNext());

            } else {
                System.out.println("No elements found");
            }
            cursor.close();

            return returnList;

    }

    public boolean checkIfExist(String myTable,String[] valueToCheck, String columnToCheck){
        boolean lreturn = false;


        if(!getListfromTable(myTable,columnToCheck , valueToCheck,columnToCheck).isEmpty()){
            lreturn =true;
        }

        return lreturn;
    }






}

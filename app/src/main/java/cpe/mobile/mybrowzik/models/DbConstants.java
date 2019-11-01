package cpe.mobile.mybrowzik.models;

import android.provider.BaseColumns;

public class DbConstants implements BaseColumns{


        // The database name
        public static final String DATABASE_NAME = "myBrowzikData.db";

        // The database version
        public static final int DATABASE_VERSION = 1;

        //-------------------------------Table Music-------------------------------------------

        // The table Name
        public static final String MUSIC_TABLE = "Music";

        // ## Column name ##
        // My Column ID and the associated explanation for end-users
        public static final String KEY_COL_ID = "_id";// Mandatory

        public static final String KEY_COL_TITLE = "title";

        public static final String KEY_COL_ARTIST_ID = "artistID";

        public static final String KEY_COL_ALBUM_ID = "albumID";

        public static final String KEY_COL_GENRE_ID = "genreID";


        // Indexes des colonnes
        // The index of the column ID
        public static final int ID_COLUMN = 1;

        public static final int TITLE_COLUMN = 2;

        public static final int ARTIST_ID_COLUMN = 3;

        public static final int ALBUM_ID_COLUMN = 4;

        public static final int GENRE_ID_COLUMN = 5;


        //-------------------------------Table Artist-------------------------------------------

        // The table Name
        public static final String ARTIST_TABLE = "Artist";

        // ## Column name ##
        // My Column ID and the associated explanation for end-users

        public static final String KEY_COL_ARTIST = "artist";

        // Indexes des colonnes
        // The index of the column ID

        public static final int ARTIST_COLUMN = 2;


        //-------------------------------Table Album-------------------------------------------

        // The table Name
        public static final String ALBUM_TABLE = "Album";

        // ## Column name ##
        // My Column ID and the associated explanation for end-users

        public static final String KEY_COL_ALBUM = "album";

        public static final String KEY_COL_YEAR = "year";

        public static final String KEY_COL_IMAGE_URL = "imageUrl";

        // Indexes des colonnes
        // The index of the column ID

        public static final int ALBUM_COLUMN = 2;

        public static final int YEAR_COLUMN = 3;

        public static final int IMAGE_URL_COLUMN = 4;

        //-------------------------------Table Genre-------------------------------------------

        // The table Name
        public static final String GENRE_TABLE = "Genre";

        // ## Column name ##
        // My Column ID and the associated explanation for end-users

        public static final String KEY_COL_GENRE = "genre";



        // Indexes des colonnes
        // The index of the column ID

        public static final int ALBUM_GENRE= 2;


    }

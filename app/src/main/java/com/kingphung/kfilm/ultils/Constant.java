package com.kingphung.kfilm.ultils;

public class Constant {
    public static String IMDB = "IMDB: ";
    public static String DIRECTOR = "Director: ";
    public static String YEAR = "Year: ";
    public static String DESCRIPTION = "Desctiption: ";

    //database column
    public static final String DB_NAME = "KFILM.db";
    public static final String DB_TABLE_NAME = "MOVIES";
    public static final int DB_VERSION = 1;
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_IMDB = "IMDB";
    public static final String COLUMN_DIRECTOR = "DIRECTOR";
    public static final String COLUMN_PRODUCTION_YEAR = "PRODUCTION_YEAR";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

    //type of file extension
    public static final String MP4_EXTENSION = ".mp4";
    public static final String JPEG_EXTENSION = ".jpg";
    public static final String SRT_EXTENSION = ".srt";

    //flag put through intent to play movie
    public static final boolean ONLINE = true;
    public static final boolean OFFLINE = false;
}

package com.example.cyl.alltest;


public class DbContent {

    //数据库名称
    public static final String DATABASE_NAME = "name.db";

    //数据库版本
    public static final int DATABASE_VERSION = 0;

    public static class NameTable {
        public static final String ID = "_ID";
        //表名
        public static final String TABLE_NAME = "";
        //成员变量
        public static final String COLOUM = "";

        //建表语句
        public static final String CREATE_NAME_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " ("
                + " _ID INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + COLOUM + " INTEGER, "
                + COLOUM1 + " VARCHAR(255),"
                + COLOUM2 + " TEXT, "
                + ")";
        //删表语句
        public static final String DROP_NAME_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}

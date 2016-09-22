package com.smartlab.drivetrain.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 15/12/9.
 * SQlHelper
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context, Params.testDb,null,Params.testDB_version);
    }
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i("------开始创建数据库-------");
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(Params.TableName);
        builder.append("(");
        builder.append("`km` int(11) NOT NULL,");
        builder.append("`stxh` bigint(11) NOT NULL,");
        builder.append("`stsx` varchar(5) DEFAULT NULL,");
        builder.append("`sttx` varchar(5) DEFAULT NULL,");
        builder.append("`stnr` varchar(255) DEFAULT NULL,");
        builder.append("`stdaA` varchar(45) DEFAULT NULL,");
        builder.append("`stdaB` varchar(45) DEFAULT NULL,");
        builder.append("`stdaC` varchar(45) DEFAULT NULL,");
        builder.append("`stdaD` varchar(45) DEFAULT NULL,");
        builder.append("`stda` varchar(45) DEFAULT NULL,");
        builder.append("PRIMARY KEY (`stxh`)");
        builder.append(")");
        db.execSQL(builder.toString());
        LogUtils.i("-----数据库创建成功-----");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Params.TableName);
        onCreate(db);
    }


}

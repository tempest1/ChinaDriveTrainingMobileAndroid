package com.smartlab.drivetrain.test;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.test.AndroidTestCase;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.database.DbHelper;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 15/11/30.
 * 测试
 */
public class LoginTest extends AndroidTestCase {

    public void testDb () throws Exception{
        DbHelper helper = new DbHelper(this.getContext(), Params.testDb,null,Params.testDB_version);
        final SQLiteDatabase db = helper.getReadableDatabase();
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                String [] columns = new String[]{"stxh","stsx","sttx","stnr","stdaA","stdaB","stdaC","stdaD","stda"};
                return db.query(Params.TableName,columns, null,null,null,null,"stxh");}

            @Override
            protected void onPostExecute(Cursor cursor) {
                if (cursor != null){
                    if (cursor.moveToFirst()){
                        while (cursor.moveToNext()){
                            String [] error_exam = cursor.getColumnNames();
                            for (String a : error_exam) {
                                LogUtils.e(a);
                            }
                        }
                    }
                }
            }
        }.execute();
    }
}

package com.smartlab.drivetrain.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.model.Question;
import com.smartlab.drivetrain.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 16/1/22
 * 考试题目.
 */
public class ExamServer {

    private DbHelper helper;
    private SQLiteDatabase db;
    public ExamServer(Context context){
        this.helper = new DbHelper(context);
    }

    /**
     *
     * @param km 科目一 和 科目四
     * @param question {@link Question Question}
     */
    public void insertExam(String km,Question question){
        db = helper.getWritableDatabase();
        if (db == null) return;
        ContentValues values = new ContentValues();
        values.put("km",km);
        values.put("stxh",question.getStxh());
        values.put("stsx",question.getStsx());
        values.put("sttx",question.getSttx());
        values.put("stnr",question.getStnr());
        values.put("stdaA",question.getStdaA());
        values.put("stdaB",question.getStdaB());
        values.put("stdaC",question.getStdaC());
        values.put("stdaD",question.getStdaD());
        values.put("stda", question.getStda());
        db.insert(Params.TableName, null, values);
    }

    public List<Question> queryQuestion(int km){
        String [] columns = new String[]{"stxh","stsx","sttx","stnr","stdaA","stdaB","stdaC","stdaD","stda"};
        String statement  = "SELECT * FROM exercise where km = " + km;
        db = helper.getReadableDatabase();
        if (db == null) {
            LogUtils.e("获取数据库对象失败，请检查磁盘空间是否充足");
            return null;
        }
        Cursor cursor = db.rawQuery(statement, null);
        List<Question> error_question = null;
        if (cursor != null){
            if (cursor.moveToFirst()){
                error_question = new ArrayList<>();
                int i = 1;
                do{
                    Question q = new Question();
                    q.setStxh(Integer.valueOf(cursor.getString(cursor.getColumnIndex(columns[0]))));
                    q.setStsx(Integer.valueOf(cursor.getString(cursor.getColumnIndex(columns[1]))));
                    q.setSttx(Integer.valueOf(cursor.getString(cursor.getColumnIndex(columns[2]))));
                    q.setStnr(cursor.getString(cursor.getColumnIndex(columns[3])));
                    q.setStdaA(cursor.getString(cursor.getColumnIndex(columns[4])));
                    q.setStdaB(cursor.getString(cursor.getColumnIndex(columns[5])));
                    q.setStdaC(cursor.getString(cursor.getColumnIndex(columns[6])));
                    q.setStdaD(cursor.getString(cursor.getColumnIndex(columns[7])));
                    q.setStda(cursor.getString(cursor.getColumnIndex(columns[8])));
                    q.setIndex(i);
                    error_question.add(q);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null) cursor.close();
        return error_question;
    }

    public void close(){
        if (db != null){
            db.close();
        }
    }
}

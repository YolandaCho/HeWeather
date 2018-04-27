package com.heweather.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Administrator on 2018/3/29.
 * read database file (city.db)
 */
public class DatabaseHandle {
    private Context context;
    /** database's path */
    private String filePath = "/data/data/com.heweather/databases/";
    private String fileName = "city.db";
    private SQLiteDatabase db;

    public DatabaseHandle(Context context) {
        this.context = context;
        db = getDatabase();
    }

    public SQLiteDatabase getDatabase() {
        File direction = new File(filePath);
        if (!direction.exists()) {
            /** if the path not exists. create new path follow the define path  */
            direction.mkdirs();
        }

        File dbFile = new File(filePath + fileName);
        if (!dbFile.exists()) {
            InputStream in;
            try {
                /** read city.db */
                in = context.getAssets().open("city.db");
                OutputStream out = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.flush();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("异常");
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    /**
     * get all province
     * @return province list
     */
    public List<String> getAllProvinces() {
        Cursor cursor = db
                .query("province", null, null, null, null, null, null);
        List<String> list = new ArrayList<String>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(1));
        }
        cursor.close();

        return list;
    }

    /**
     * get province's id by province string
     * @param provinceName
     * @return province's id
     */
    public int getProvinceIdByName(String provinceName) {
        Cursor cursor = db.query("province", new String[] { "provinceId" },
                "provinceName=?", new String[] { provinceName }, null, null,
                null);
        if (cursor.moveToNext()) {
            int i = cursor.getInt(0);
            cursor.close();
            return i;
        }
        cursor.close();
        return 0;
    }

    /**
     * get city by province's name
     * @param provinceName
     * @return city's list
     */
    public List<String> getCityByProvinceName(String provinceName) {
        int provinceId = getProvinceIdByName(provinceName);
        Cursor cursor = db.query("city", new String[] { "cityName" },
                "provinceId=?", new String[] { "" + provinceId }, null, null,
                null);
        List<String> list = new ArrayList<String>();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        cursor.close();
        return list;
    }
}

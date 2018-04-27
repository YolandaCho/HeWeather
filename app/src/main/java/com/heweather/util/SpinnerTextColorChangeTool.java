package com.heweather.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class SpinnerTextColorChangeTool extends ArrayAdapter{
    private Context mContext;
    private List<String> list;

    public SpinnerTextColorChangeTool(Context context, List<String> list) {
        super(context, android.R.layout.simple_spinner_item, list);
        mContext = context;
        this.list=list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(list.get(position));
        tv.setTextSize(12f);
        tv.setTextColor(Color.BLACK);

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(list.get(position));
        tv.setTextSize(12f);
        tv.setTextColor(Color.BLACK);
        return convertView;
    }
}

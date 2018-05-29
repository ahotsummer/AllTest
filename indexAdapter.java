package com.example.cyl.alltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cyl on 2018/5/23.
 */

public class indexAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public indexAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View = LayoutInflater.from(context).inflate(R.layout., null, false);
            convertView = LayoutInflater.from(context).inflate(R.layout., null, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.root = convertView.findViewById(R.id.root);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    private class ViewHolder {
        public TextView text;
        public ImageView img;
        public View root;
    }
}

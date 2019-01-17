package com.example.secha.histoybookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by secha on 2019-01-15.
 */

public class Bookmark_Adapter extends BaseAdapter {
    private ArrayList<BookMarkModel> arrayList;
    private Context context;

    public Bookmark_Adapter(Context context, ArrayList<BookMarkModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView= inflater.inflate(R.layout.custom_view, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.bookmar_title);
            TextView url = (TextView) convertView.findViewById(R.id.bookmark_url);

            title.setText(arrayList.get(position).getBookmarkTitle());
            url.setText(arrayList.get(position).getBookmarkUrl());

        }
        return convertView;
    }


}

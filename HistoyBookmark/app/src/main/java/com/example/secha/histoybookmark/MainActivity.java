package com.example.secha.histoybookmark;

import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static ListView listView;
    private static ArrayList<BookMarkModel> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        arrayList = new ArrayList<BookMarkModel>();
        listView = (ListView) findViewById(R.id.bookmark_list);

        // Cursor to get Bookmark information
        Cursor cursor = getContentResolver().query(
                Browser.BOOKMARKS_URI,
                new String[] { Browser.BookmarkColumns.TITLE,
                        Browser.BookmarkColumns.URL }, null, null, null);

        // Note : " Browser.BookmarkColumns.BOOKMARK " - this will return 0 or
        // 1. '1' indicates a bookmark and '0' indicates history item.

        try {
            // Now loop to all items using cursor
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    // Add Bookmark title and Bookmark Url
                    arrayList.add(new BookMarkModel(cursor.getString(0), cursor
                            .getString(1)));

                } while (cursor.moveToNext()); // Move to next
            }
        } finally {

            // Close the cursor after use
            cursor.close();
        }

        Bookmark_Adapter adapter = new Bookmark_Adapter(MainActivity.this,
                arrayList);
        listView.setAdapter(adapter);// Set adapter
    }
}

package com.example.secha.history;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> titles;
    private ArrayList<String> urls;
    private ArrayList<Bitmap> bitmaps;
    private ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createLists();
    }

    protected void onResume(){
        super.onResume();
        getBH();
        showHistoryBookmarks();

    }

    public void createLists(){
        titles=new ArrayList<String>();
        urls=new ArrayList<String>();
        bitmaps=new ArrayList<Bitmap>();

    }

    public void getBH(){
        Bitmap icon;
        cr=getContentResolver();
        String order= Browser.BookmarkColumns.DATE+" DESC";
        String[] projection={Browser.BookmarkColumns.TITLE,Browser.BookmarkColumns.URL,Browser.BookmarkColumns.FAVICON};
        //String selection=projection[0]+"=?";
        //String args[]={"Google"};
        Cursor rows=cr.query(Browser.BOOKMARKS_URI,projection, null,null,order);
        if(rows.getCount()>0){
            while(rows.moveToNext()) {
                //read title
                String title=rows.getString(rows.getColumnIndex(projection[0]));
                //read url
                String url=rows.getString(rows.getColumnIndex(projection[1]));
                //read icon
                byte[] bicon=rows.getBlob(rows.getColumnIndex(projection[2]));
                if(bicon!=null){
                    //convert blob image data to Bitmap
                    icon= BitmapFactory.decodeByteArray(bicon,0,bicon.length);

                }

                else{
                    //default icon for history and bookmarks that do not icons
                    icon=BitmapFactory.decodeResource(getResources(),R.drawable.noicon);
                }
                //add to lists
                addToList(title,url,icon);
            }
            //close the cursor
            rows.close();
        }
    }

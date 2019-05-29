package com.example.momsee.EnglishGame;  //토플, 텝스, 공무원 심화 단어 공부

import com.example.momsee.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rudgn on 2019-01-10.
 */

public class FileTable4 {

    InputStream fi;

    FileSplit0 word;

    // 단어공부 파일 읽어오기
    public void loadFile(int num) {

        fi = StudyView5.mContext.getResources().openRawResource(R.raw.toefl01 + num-1);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word = new FileSplit0(s);

        } catch (IOException e) {
        }

    }

}
package com.example.android0211.UserInfo;

import java.util.ArrayList;

public class UserInfoData {

    private ArrayList<UserInfo> UserInfo;

    public ArrayList<UserInfo> GetUserInfo(){
        return UserInfo;
    }

    public void setUserInfo(ArrayList<UserInfo> UserInfo){
        this.UserInfo=UserInfo;
    }
}

package com.example.android0211.UserInfo;
//계정정보를 저장하는 클래스
public class UserInfo   {

    private  String saveid; //인덱스
    private  String saveunique_id; //아이디
    private   String savename; //이름
    private String saveemail; //이메일
    private  String saveencrypted_password; //암호화된 패스워드
    private  String savesalt; //ㅋ
    private  String savecreated_at; //날짜1
    private String saveupdated_at; //날짜2

    //////////////////////////////////////////////////////////////////////////////////생성자
    public UserInfo(String saveid,String saveunique_id,String savename,String saveemail,String saveencrypted_password,String savesalt,String savecreated_at,String saveupdated_at){
        this.saveid=saveid;
        this.saveunique_id=saveunique_id;
        this.savename=savename;
        this.saveemail=saveemail;
        this.saveencrypted_password=saveencrypted_password;
        this.savesalt=savesalt;
        this.savecreated_at=savecreated_at;
        this.saveupdated_at=saveupdated_at;
    }
    ///////////////////////////////////////////////////////////////////////////////////////get
    public String GetsaveId(){
        return saveid;
    }
    public String Getsaveunique_id(){
        return saveunique_id;
    }
    public String Getsavename(){
        return saveemail;
    }
    public String Getsaveencrypted_password(){
        return saveencrypted_password;
    }
    public String Getsavesalt(){
        return savesalt;
    }
    public String Getsavecreated_at(){
        return savecreated_at;
    }
    public String Getsaveupdated_at(){
        return saveupdated_at;
    }

    ////////////////////////////////////////////////////////////////////////////////////////set

    public void Setsaveid(String saveid){
       this.saveid=saveid;
    }
    public void Setsaveunique_id(String saveunique_id){
        this.saveunique_id=saveunique_id;
    }
    public void Setsavename(String savename){
        this.savename=savename;
    }
    public void Setsaveemail(String saveemail){
        this.saveemail=saveemail;
    }
    public void Setsaveencrypted_password(String saveencrypted_password){
        this.saveencrypted_password=saveencrypted_password;
    }
    public void Setsavesalt(String savesalt){
        this.savesalt=savesalt;
    }
    public void Setsavecreated_at(String savecreated_at){
        this.savecreated_at=savecreated_at;
    }
    public void Setsaveupdated_at(String saveupdated_at){
        this.saveupdated_at=saveupdated_at;
    }






}

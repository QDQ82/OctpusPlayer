package com.CNSI.OctopusPlayer.ui.CustomListview;

import java.io.Serializable;

public class ListViewItem implements Serializable {

    //private Drawable icon;
    private String title ;
    private String url ;
    private String id;
    private String pw;
    private String rtspport;
    private String webport;
    private String profile;
    private String cameratype;


    //public void setIcon(Drawable _icon){
     //   this.icon = _icon;
    //}
    public void setTitle(String _title) {
        this.title = _title ;
    }
    public void setUrl(String _url) {
        this.url = _url ;
    }
    public void setID(String _id){
        this.id = _id;
    }
    public void setPW(String _pw){
        this.pw = _pw;
    }
    public void setRTSPPort(String _rtsp){
        this.rtspport = _rtsp;
    }
    public void setWebport(String _web){
        this.webport = _web;
    }
    public void setProfile(String _profile){
        this.profile = _profile;
    }
    public void setCameratype(String _cameratype){
        this.cameratype = _cameratype;
    }


    //public  Drawable getIcon(){
     //   return this.icon;
   // }
    public String getTitle() {
        return this.title ;
    }
    public String getUrl() {
        return this.url ;
    }
    public String getID(){
        return this.id;
    }
    public String getPW(){
        return this.pw;
    }
    public String getRtspport(){
        return this.rtspport;
    }
    public String getWebport(){
        return this.webport;
    }
    public String getProfile(){
        return this.profile;
    }
    public String getCameratype() {return this.cameratype;}




}

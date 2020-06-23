package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Track {

    private  JSONObject object;
    private  String name;
    private  String time;
    private  String preview;



    public Track(JSONObject obj) throws JSONException {
        this.object = obj;
        this.name = this.object.getString("title");
        this.time=this.object.getString("duration");
        this.preview=this.object.getString("preview");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getTime() {
        return Integer.parseInt(time);
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getPreview(){
        return preview;
    }
    public void setPreview(String preview){
        this.preview=preview;
    }

}

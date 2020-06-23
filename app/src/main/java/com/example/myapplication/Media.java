package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Media implements Parcelable {
    // Store the id of the  movie poster
    private JSONObject object;
    private  String Poster;
    private  String Title;
    private  String Descrip;

    public Media(JSONObject obj) throws JSONException {
        this.object = obj;
        this.Poster = this.object.getJSONObject("album").getString("cover_big");
        this.Title=this.object.getJSONObject("album").getString("title");
        this.Descrip=this.object.getJSONObject("artist").getString("name");
//        this.mRelease = mRelease;
    }

    protected Media(Parcel in) {
        Poster = in.readString();
        Title = in.readString();
        Descrip = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Poster);
        dest.writeString(Title);
        dest.writeString(Descrip);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public String getmImageDrawable() {
        return Poster;
    }

    public void setmImageDrawable(String mImageDrawable) {
        this.Poster = mImageDrawable;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String mName) {
        this.Title = mName;
    }
    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String mName) {
        this.Descrip = mName;
    }
//
//    public String getmRelease() {
//        return mRelease;
//    }
//
//    public void setmRelease(String mRelease) {
//        this.mRelease = mRelease;
//    }
}

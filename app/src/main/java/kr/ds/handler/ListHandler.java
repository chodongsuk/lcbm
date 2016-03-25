package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-03-21.
 */
public class ListHandler  implements Parcelable{
    private String gd_uid;
    private String cd_code;
    private String gd_name;
    private String gd_tema;
    private String gd_address;
    private String gd_tell;
    private String gd_time;
    private String gd_holiday;
    private String gd_image;
    private String gd_lat;
    private String gd_lon;
    private String gd_content;
    private String gd_regdate;
    public ListHandler() {

    }


    public ListHandler(Parcel src) {
        // TODO Auto-generated constructor stub
        this.gd_uid = src.readString();
        this.cd_code = src.readString();
        this.gd_name = src.readString();
        this.gd_tema = src.readString();
        this.gd_address = src.readString();
        this.gd_tell = src.readString();
        this.gd_time = src.readString();
        this.gd_holiday = src.readString();
        this.gd_image = src.readString();
        this.gd_lat = src.readString();
        this.gd_lon = src.readString();
        this.gd_content = src.readString();
        this.gd_regdate = src.readString();
    }


    public String getGd_tema() {
        return gd_tema;
    }

    public void setGd_tema(String gd_tema) {
        this.gd_tema = gd_tema;
    }

    public String getGd_uid() {
        return gd_uid;
    }

    public void setGd_uid(String gd_uid) {
        this.gd_uid = gd_uid;
    }

    public String getCd_code() {
        return cd_code;
    }

    public void setCd_code(String cd_code) {
        this.cd_code = cd_code;
    }

    public String getGd_name() {
        return gd_name;
    }

    public void setGd_name(String gd_name) {
        this.gd_name = gd_name;
    }

    public String getGd_lat() {
        return gd_lat;
    }

    public void setGd_lat(String gd_lat) {
        this.gd_lat = gd_lat;
    }

    public String getGd_lon() {
        return gd_lon;
    }

    public void setGd_lon(String gd_lon) {
        this.gd_lon = gd_lon;
    }

    public String getGd_content() {
        return gd_content;
    }

    public void setGd_content(String gd_content) {
        this.gd_content = gd_content;
    }

    public String getGd_regdate() {
        return gd_regdate;
    }

    public void setGd_regdate(String gd_regdate) {
        this.gd_regdate = gd_regdate;
    }

    public String getGd_address() {
        return gd_address;
    }

    public void setGd_address(String gd_address) {
        this.gd_address = gd_address;
    }

    public String getGd_tell() {
        return gd_tell;
    }

    public void setGd_tell(String gd_tell) {
        this.gd_tell = gd_tell;
    }

    public String getGd_time() {
        return gd_time;
    }

    public void setGd_time(String gd_time) {
        this.gd_time = gd_time;
    }

    public String getGd_holiday() {
        return gd_holiday;
    }

    public void setGd_holiday(String gd_holiday) {
        this.gd_holiday = gd_holiday;
    }

    public String getGd_image() {
        return gd_image;
    }

    public void setGd_image(String gd_image) {
        this.gd_image = gd_image;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(this.gd_uid);
        dest.writeString(this.cd_code);
        dest.writeString(this.gd_name);
        dest.writeString(this.gd_tema);
        dest.writeString(this.gd_address);
        dest.writeString(this.gd_tell);
        dest.writeString(this.gd_time);
        dest.writeString(this.gd_holiday);
        dest.writeString(this.gd_image);
        dest.writeString(this.gd_lat);
        dest.writeString(this.gd_lon);
        dest.writeString(this.gd_content);
        dest.writeString(this.gd_regdate);
    }

    public static final Parcelable.Creator CREATOR = new Creator() { //데이터 가져오기

        @Override
        public Object createFromParcel(Parcel in) {
            // TODO Auto-generated method stub
            return new ListHandler(in);
        }
        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ListHandler[size];
        }
    };
}

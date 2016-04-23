package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

public class EventHandler implements Parcelable {

	private String ed_uid;
	private String ed_name;
	private String ed_subject;
	private String ed_image;
	private String ed_content;
	private String ed_regdate;
	private String sub_images;

	public EventHandler() {
		// TODO Auto-generated constructor stub

	}
	public EventHandler(Parcel src) {
		// TODO Auto-generated constructor stub
		this.ed_uid = src.readString();
		this.ed_name = src.readString();
		this.ed_subject = src.readString();
		this.ed_image = src.readString();
		this.ed_content = src.readString();
		this.ed_regdate = src.readString();
		this.sub_images = src.readString();

	}

	public String getEd_uid() {
		return ed_uid;
	}

	public void setEd_uid(String ed_uid) {
		this.ed_uid = ed_uid;
	}

	public String getEd_name() {
		return ed_name;
	}

	public void setEd_name(String ed_name) {
		this.ed_name = ed_name;
	}

	public String getEd_subject() {
		return ed_subject;
	}

	public void setEd_subject(String ed_subject) {
		this.ed_subject = ed_subject;
	}

	public String getEd_image() {
		return ed_image;
	}

	public void setEd_image(String ed_image) {
		this.ed_image = ed_image;
	}

	public String getEd_content() {
		return ed_content;
	}

	public void setEd_content(String ed_content) {
		this.ed_content = ed_content;
	}

	public String getEd_regdate() {
		return ed_regdate;
	}

	public void setEd_regdate(String ed_regdate) {
		this.ed_regdate = ed_regdate;
	}

	public String getSub_images() {
		return sub_images;
	}

	public void setSub_images(String sub_images) {
		this.sub_images = sub_images;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.ed_uid);
		dest.writeString(this.ed_name);
		dest.writeString(this.ed_subject);
		dest.writeString(this.ed_image);
		dest.writeString(this.ed_content);
		dest.writeString(this.ed_regdate);
		dest.writeString(this.sub_images);

	}

	public static final Creator CREATOR = new Creator() { //데이터 가져오기

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new EventHandler(in);
		}
		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new EventHandler[size];
		}
	};
}

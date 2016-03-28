package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryHandler implements Parcelable {
	
	private String name;
	private String code;
	private String image;
	private String type;
	private String homepage;
	private Boolean isBg;

	public CategoryHandler() {
		// TODO Auto-generated constructor stub

	}
	public CategoryHandler(Parcel src) {
		// TODO Auto-generated constructor stub
		this.name = src.readString();
		this.code = src.readString();
		this.image = src.readString();
		this.type = src.readString();
		this.homepage = src.readString();

	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Boolean getIsBg() {
		return isBg;
	}

	public void setIsBg(Boolean isBg) {
		this.isBg = isBg;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.name);
		dest.writeString(this.code);
		dest.writeString(this.image);
		dest.writeString(this.type);
		dest.writeString(this.homepage);

	}

	public static final Parcelable.Creator CREATOR = new Creator() { //데이터 가져오기

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new CategoryHandler(in);
		}
		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CategoryHandler[size];
		}
	};
}

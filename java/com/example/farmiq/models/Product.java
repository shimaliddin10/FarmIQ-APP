package com.example.farmiq.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String type;
    private double price;
    private String address;
    private int imageResId;

    public Product(String name, String type, double price, String address, int imageResId) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.address = address;
        this.imageResId = imageResId;
    }

    protected Product(Parcel in) {
        name = in.readString();
        type = in.readString();
        price = in.readDouble();
        address = in.readString();
        imageResId = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeDouble(price);
        dest.writeString(address);
        dest.writeInt(imageResId);
    }
}

package com.example.greenscape;

import android.os.Parcel;
import android.os.Parcelable;

public class PlantStoreModel implements Parcelable {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int imageResId; // Поле для зображення має бути типу int

    public PlantStoreModel(String name, String description, double price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
        this.imageResId = imageResId;
    }

    protected PlantStoreModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        imageResId = in.readInt();
    }

    public static final Creator<PlantStoreModel> CREATOR = new Creator<PlantStoreModel>() {
        @Override
        public PlantStoreModel createFromParcel(Parcel in) {
            return new PlantStoreModel(in);
        }

        @Override
        public PlantStoreModel[] newArray(int size) {
            return new PlantStoreModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeDouble(price);
        parcel.writeInt(quantity);
        parcel.writeInt(imageResId);
    }
}

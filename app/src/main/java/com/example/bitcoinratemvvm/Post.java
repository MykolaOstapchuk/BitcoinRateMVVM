package com.example.bitcoinratemvvm;

import com.google.gson.annotations.SerializedName;

public class Post {

    private String asset_id_base;

    @SerializedName("asset_id_quote")
    private String name;

    @SerializedName("rate")
    private double price;

    public Post(String asset_id_base, String name, double price) {
        this.asset_id_base = asset_id_base;
        this.name = name;
        this.price = price;
    }

    public String getAsset_id_base() {
        return asset_id_base;
    }

    public void setAsset_id_base(String asset_id_base) {
        this.asset_id_base = asset_id_base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

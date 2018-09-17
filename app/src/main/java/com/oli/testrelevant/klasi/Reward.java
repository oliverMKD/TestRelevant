package com.oli.testrelevant.klasi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reward implements Serializable {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("expiryDate")
    @Expose
    public String expiryDate;

    public Reward(String name, String expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public Reward() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

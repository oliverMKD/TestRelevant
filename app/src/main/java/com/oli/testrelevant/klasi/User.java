package com.oli.testrelevant.klasi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("email")
    @Expose
    public String email ;
    @SerializedName("register_type")
    @Expose
    public Integer register_type;
    @SerializedName("register_device_type")
    @Expose
    public String register_device_type;
    @SerializedName("auth_token")
    @Expose
    public String auth_token;

    public User(String email, Integer register_type, String register_device_type, String auth_token) {
        this.email = email;
        this.register_type = register_type;
        this.register_device_type = register_device_type;
        this.auth_token = auth_token;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRegister_type() {
        return register_type;
    }

    public void setRegister_type(Integer register_type) {
        this.register_type = register_type;
    }

    public String getRegister_device_type() {
        return register_device_type;
    }

    public void setRegister_device_type(String register_device_type) {
        this.register_device_type = register_device_type;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}

package com.example.instagramlike.network;

public interface ResponseCallback {
    public String onSuccess(String res);
    public String onFailure(String res);
}

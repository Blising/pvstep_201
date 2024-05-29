package com.example.greenscape;

public interface TranslateCallback {
    void onSuccess(String translatedText);
    void onFailure(String error);
}

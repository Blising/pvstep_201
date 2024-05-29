package com.example.greenscape;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TranslationResponse {
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("translations")
        public List<Translation> translations;
    }

    public static class Translation {
        @SerializedName("translatedText")
        public String translatedText;
    }
}

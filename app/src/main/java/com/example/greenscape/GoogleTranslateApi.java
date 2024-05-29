package com.example.greenscape;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleTranslateApi {
    @GET("/language/translate/v2")
    Call<TranslationResponse> translate(
            @Query("key") String apiKey,
            @Query("q") String text,
            @Query("source") String sourceLang,
            @Query("target") String targetLang
    );
}

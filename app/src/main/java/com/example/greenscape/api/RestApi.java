package com.example.greenscape.api;

import com.example.greenscape.entity.Plants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @GET("/getAllPlants")
    Call<List<Plants>> getAllPlant();

    @POST("/newPlants")
    Call<Plants>savePlants(@Body Plants plants);

}

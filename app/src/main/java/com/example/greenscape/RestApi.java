package com.example.greenscape;


import com.example.greenscape.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @GET("/employees")
    Call<List<Employee>> getAllEmployees();

    @POST("/employees")
    Call<Employee>saveEmployee(@Body Employee employee);
}

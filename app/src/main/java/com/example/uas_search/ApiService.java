package com.example.uas_search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("products/search")
    Call<ResponseData> searchItems(@Query("q") String query);
}

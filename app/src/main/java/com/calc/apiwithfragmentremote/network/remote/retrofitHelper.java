package com.calc.apiwithfragmentremote.network.remote;

import com.calc.apiwithfragmentremote.models.bookModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface retrofitHelper
{
    @GET("books/v1/volumes")
  Call<bookModel>  getBooks(
          @Query("q") String query
    );


}

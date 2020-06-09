package com.calc.apiwithfragmentremote.network.remote;

import com.calc.apiwithfragmentremote.models.bookModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class retrofitClint
{
    //singleton

    // 3-
    private static retrofitHelper retrofitHelper;
//2-
    private static retrofitClint retrofitClint;
    // 1 -
    private retrofitClint ()
    {
  //5 - ta3ref el retrofit last thing we will do here inside this method
        Retrofit retrofit= new Retrofit.Builder()
                // its HTTPS we will not need to do clearTraffic at mainfest
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitHelper = retrofit.create(retrofitHelper.class);
    }
// 4-
    public static retrofitClint getInstance()
    {
        if ( retrofitClint == null)
        {
            retrofitClint = new retrofitClint();
        }
        return retrofitClint;
    }

    // 6 -
    public static Call<bookModel> getBooks(String q)
    {
        return retrofitHelper.getBooks(q);
    }
}

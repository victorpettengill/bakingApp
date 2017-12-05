package br.com.victorpettengill.bakingapp.network;

import br.com.victorpettengill.bakingapp.constants.ApiConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by appimagetech on 29/11/17.
 */

public class NetworkUtils {

    public static Retrofit getRequest() {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(ApiConstants.API_BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

}
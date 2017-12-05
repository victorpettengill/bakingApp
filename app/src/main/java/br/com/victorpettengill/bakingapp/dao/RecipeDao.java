package br.com.victorpettengill.bakingapp.dao;

import android.util.Log;

import java.util.List;

import br.com.victorpettengill.bakingapp.beans.Recipe;
import br.com.victorpettengill.bakingapp.helper.DaoHelper;
import br.com.victorpettengill.bakingapp.network.NetworkUtils;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by appimagetech on 28/11/17.
 */

public class RecipeDao {

//    baking.json

    private static RecipeDao instance;

    public static RecipeDao getInstance() {

        if(instance == null) {
            instance = new RecipeDao();
        }

        return instance;
    }

    public void getRecipes(final DaoHelper helper) {

        RecipeService request = NetworkUtils.getRequest().create(RecipeService.class);
        request.getRecipes().enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                helper.onSuccess(response.body());


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                helper.onError(t.getLocalizedMessage());

            }

        });


    }

    interface RecipeService {

        @Headers("Content-Type: application/json")
        @GET("/android-baking-app-json")
        Call<List<Recipe>> getRecipes();

    }

}
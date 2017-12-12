package br.com.victorpettengill.bakingapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appimagetech on 27/11/17.
 */

public class Recipe implements Parcelable{

    private long id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private long servings;
    private String image;

    protected Recipe(Parcel in) {

        Gson gson = new Gson();

        Recipe aux = gson.fromJson(in.readString(), Recipe.class);

        id = aux.getId();
        name = aux.getName();
        ingredients = aux.getIngredients();
        steps = aux.getSteps();
        servings = aux.getServings();
        image = aux.getImage();

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public long getServings() {
        return servings;
    }

    public void setServings(long servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        Gson gson = new Gson();
        parcel.writeString(gson.toJson(this));

    }
}
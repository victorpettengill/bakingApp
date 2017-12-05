package br.com.victorpettengill.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by appimagetech on 05/12/17.
 */

public class RecipeAdapter {

    private class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name) TextView recipeName;
        @BindView(R.id.servings) TextView servings;
        @BindView(R.id.image) ImageView image;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {

            recipeName.setText(recipe.getName());
            servings.setText(String.format(itemView.getContext().getString(R.string.number_servings), recipe.getServings()));

            if(recipe.getImage() != null) {

            }

        }

    }

}
package br.com.victorpettengill.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by appimagetech on 05/12/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private RecipeClickListener listener;
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes, RecipeClickListener listener) {
        this.listener = listener;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(View.inflate(parent.getContext(), R.layout.recipe_item, null), listener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        holder.bind(recipes.get(position));

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name) TextView recipeName;
        @BindView(R.id.servings) TextView servings;
        @BindView(R.id.image) ImageView image;

        private RecipeClickListener listener;

        public RecipeViewHolder(View itemView, final RecipeClickListener listener) {
            super(itemView);

            this.listener = listener;
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Recipe recipe) {

            recipeName.setText(recipe.getName());
            servings.setText(String.format(itemView.getContext().getString(R.string.number_servings), recipe.getServings()));

            if(recipe.getImage() != null) {

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onRecipeClicked(recipe);

                }
            });

        }

    }

    public interface RecipeClickListener {
        void onRecipeClicked(Recipe recipe);
    }

}
package br.com.victorpettengill.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Recipe;
import br.com.victorpettengill.bakingapp.beans.Step;
import butterknife.ButterKnife;

/**
 * Created by appimagetech on 08/12/17.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder>{

    private Recipe recipe;
    private RecipeDetailListener listener;

    public RecipeDetailAdapter(Recipe recipe, RecipeDetailListener listener) {
        this.recipe = recipe;
        this.listener = listener;
    }

    @Override
    public RecipeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeDetailViewHolder(
                View.inflate(
                        parent.getContext(),
                        R.layout.recipeitem_list_content,
                        null),
                listener);
    }

    @Override
    public void onBindViewHolder(RecipeDetailViewHolder holder, int position) {

        switch (position) {

            case 0:
                holder.bindIngredient(recipe);
                break;

            default:
                holder.bindRecipeStep(recipe.getSteps().get(position-1));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size() + 1;
    }

    class RecipeDetailViewHolder extends RecyclerView.ViewHolder {

        private RecipeDetailListener listener;

        RecipeDetailViewHolder(View itemView, RecipeDetailListener listener) {
            super(itemView);

            this.listener = listener;

            ButterKnife.bind(this, itemView);

        }

        void bindIngredient(Recipe recipe) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onIngredientsClicked();

                }
            });

        }

        void bindRecipeStep(final Step step) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onStepClicked(step);

                }
            });

        }

    }

    public interface RecipeDetailListener {

        void onIngredientsClicked();
        void onStepClicked(Step recipeStep);

    }

}

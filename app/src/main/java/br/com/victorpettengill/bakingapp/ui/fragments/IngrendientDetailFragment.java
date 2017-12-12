package br.com.victorpettengill.bakingapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Ingredient;
import br.com.victorpettengill.bakingapp.ui.RecipeItemDetailActivity;
import br.com.victorpettengill.bakingapp.ui.RecipeItemListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A fragment representing a single RecipeItem detail screen.
 * This fragment is either contained in a {@link RecipeItemListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeItemDetailActivity}
 * on handsets.
 */
public class IngrendientDetailFragment extends Fragment {

    public static final String INGREDIENT_ARG = "ingredients";
    private ArrayList<Ingredient> ingredients;

    @BindView(R.id.ingredient_detail) TextView ingredientsDetail;

    public IngrendientDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(INGREDIENT_ARG)) {

            ingredients = getArguments().getParcelableArrayList(INGREDIENT_ARG);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredient_detail, container, false);

        ButterKnife.bind(this, rootView);

        if(ingredients != null) {

            StringBuilder builder = new StringBuilder();

            for (Ingredient ingredient: ingredients) {

                builder.append("• ");
                builder.append(ingredient.getQuantity());
                builder.append(" ");
                builder.append(ingredient.getMeasure());
                builder.append(" of ");
                builder.append(ingredient.getIngredient());

                builder.append("\n");

            }

            ingredientsDetail.setText(builder.toString());

        }

        return rootView;
    }
}

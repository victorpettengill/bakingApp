package br.com.victorpettengill.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.adapters.RecipeDetailAdapter;
import br.com.victorpettengill.bakingapp.beans.Ingredient;
import br.com.victorpettengill.bakingapp.beans.Recipe;
import br.com.victorpettengill.bakingapp.beans.Step;
import br.com.victorpettengill.bakingapp.ui.fragments.IngrendientDetailFragment;
import br.com.victorpettengill.bakingapp.ui.fragments.StepDetailFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recipeitem_list) RecyclerView recyclerView;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeitem_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(getTitle());

        recipe = getIntent().getExtras().getParcelable("recipe");

        if (findViewById(R.id.recipeitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecipeDetailAdapter(recipe, new RecipeDetailAdapter.RecipeDetailListener() {

            @Override
            public void onIngredientsClicked() {

                ArrayList<Ingredient> ingredients = recipe.getIngredients();

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelableArrayList(IngrendientDetailFragment.INGREDIENT_ARG, ingredients);
                    IngrendientDetailFragment fragment = new IngrendientDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipeitem_detail_container, fragment)
                            .commit();
                } else {

                    Intent intent = new Intent(RecipeItemListActivity.this, RecipeItemDetailActivity.class);
                    intent.putExtra(IngrendientDetailFragment.INGREDIENT_ARG, ingredients);

                    startActivity(intent);
                }

            }

            @Override
            public void onStepClicked(Step recipeStep) {

                if (mTwoPane) {

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.recipeitem_detail_container,
                                    StepDetailFragment.newInstance(
                                            recipeStep))
                            .commit();

                } else {

                    Intent intent = new Intent(RecipeItemListActivity.this, RecipeItemDetailActivity.class);
                    intent.putExtra(StepDetailFragment.STEP_ARG, recipeStep);

                    startActivity(intent);
                }

            }
        }));

//        DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
//        if (mTwoPane) {
//            Bundle arguments = new Bundle();
//            arguments.putString(IngrendientDetailFragment.INGREDIENT_ARG, item.id);
//            IngrendientDetailFragment fragment = new IngrendientDetailFragment();
//            fragment.setArguments(arguments);
//            mParentActivity.getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.recipeitem_detail_container, fragment)
//                    .commit();
//        } else {
//            Context context = view.getContext();
//            Intent intent = new Intent(context, RecipeItemDetailActivity.class);
//            intent.putExtra(IngrendientDetailFragment.INGREDIENT_ARG, item.id);
//
//            context.startActivity(intent);
//        }

    }

}

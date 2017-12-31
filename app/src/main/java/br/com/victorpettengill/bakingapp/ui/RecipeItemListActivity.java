package br.com.victorpettengill.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

            mTwoPane = true;
        }

        setupRecyclerView(savedInstanceState, recyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setupRecyclerView(Bundle savedInstanceState, RecyclerView recyclerView) {

        if(mTwoPane) {

            if(savedInstanceState == null) {

                ArrayList<Ingredient> ingredients = recipe.getIngredients();

                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList(IngrendientDetailFragment.INGREDIENT_ARG, ingredients);
                IngrendientDetailFragment fragment = new IngrendientDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeitem_detail_container, fragment)
                        .commit();

            }

        }

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
                    intent.putExtra(RecipeItemDetailActivity.RECIPE_ARG, recipe);
                    intent.putExtra(RecipeItemDetailActivity.STEP_POSITION, 0);
                    startActivity(intent);

                }

            }

            @Override
            public void onStepClicked(Step recipeStep) {

                if (mTwoPane) {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipeitem_detail_container,
                                    StepDetailFragment.newInstance(
                                            recipeStep))
                            .commit();

                } else {

                    Intent intent = new Intent(RecipeItemListActivity.this, RecipeItemDetailActivity.class);
                    intent.putExtra(RecipeItemDetailActivity.RECIPE_ARG, recipe);
                    intent.putExtra(RecipeItemDetailActivity.STEP_POSITION, recipe.getSteps().indexOf(recipeStep)+1);
                    startActivity(intent);

                }

            }
        }));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_recipe_view, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.save_ingredients) {

            recipe.setCurrentRecipe(this);

        } else {

            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("finished", RecipeItemListActivity.class.getName());

    }

}

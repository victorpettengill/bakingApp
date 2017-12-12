package br.com.victorpettengill.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Step;
import br.com.victorpettengill.bakingapp.ui.fragments.IngrendientDetailFragment;
import br.com.victorpettengill.bakingapp.ui.fragments.StepDetailFragment;

/**
 * An activity representing a single RecipeItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeItemListActivity}.
 */
public class RecipeItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeitem_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();

            if(getIntent().hasExtra(IngrendientDetailFragment.INGREDIENT_ARG)) {

                arguments.putParcelableArrayList(IngrendientDetailFragment.INGREDIENT_ARG,
                        getIntent().getParcelableArrayListExtra(IngrendientDetailFragment.INGREDIENT_ARG));

                IngrendientDetailFragment fragment = new IngrendientDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipeitem_detail_container, fragment)
                        .commit();

                getSupportActionBar().setTitle(R.string.ingredients);

            } else if(getIntent().hasExtra(StepDetailFragment.STEP_ARG)) {

                Step recipeStep = (Step) getIntent().getParcelableExtra(StepDetailFragment.STEP_ARG);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipeitem_detail_container,
                                StepDetailFragment.newInstance(
                                        recipeStep))
                        .commit();

                getSupportActionBar().setTitle(recipeStep.getShortDescription());

            }

//            if(getIntent().hasExtra(IngrendientDetailFragment.STEP_ARG)) {
//
//                arguments.putParcelable(IngrendientDetailFragment.STEP_ARG,
//                        getIntent().getParcelableExtra(IngrendientDetailFragment.STEP_ARG));
//
//            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo(new Intent(this, RecipeItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

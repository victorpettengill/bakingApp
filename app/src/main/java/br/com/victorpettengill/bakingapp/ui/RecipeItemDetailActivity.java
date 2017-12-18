package br.com.victorpettengill.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
public class RecipeItemDetailActivity extends AppCompatActivity implements StepDetailFragment.FragmentStepComm{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_recipeitem_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {

            Log.i("state","null");

            Bundle arguments = new Bundle();

            if(getIntent().hasExtra(IngrendientDetailFragment.INGREDIENT_ARG)) {

                arguments.putParcelableArrayList(IngrendientDetailFragment.INGREDIENT_ARG,
                        getIntent().getParcelableArrayListExtra(IngrendientDetailFragment.INGREDIENT_ARG));

                IngrendientDetailFragment fragment = new IngrendientDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeitem_detail_container, fragment)
                        .commit();

                getSupportActionBar().setTitle(R.string.ingredients);

            } else if(getIntent().hasExtra(StepDetailFragment.STEP_ARG)) {

                Step recipeStep = (Step) getIntent().getParcelableExtra(StepDetailFragment.STEP_ARG);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeitem_detail_container,
                                StepDetailFragment.newInstance(
                                        recipeStep))
                        .commit();

                getSupportActionBar().setTitle(recipeStep.getShortDescription());

            }

        } else {

            Log.i("state","not null");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


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

    @Override
    public void onLandscape() {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        View decorView = getWindow().getDecorView();
        // Hide Status Bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onPortrait() {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().show();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);


    }
}

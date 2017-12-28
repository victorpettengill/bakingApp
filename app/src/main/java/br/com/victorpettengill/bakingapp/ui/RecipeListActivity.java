package br.com.victorpettengill.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import br.com.victorpettengill.bakingapp.IdlingResource.SimpleIdlingResource;
import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.adapters.RecipeAdapter;
import br.com.victorpettengill.bakingapp.beans.Recipe;
import br.com.victorpettengill.bakingapp.dao.RecipeDao;
import br.com.victorpettengill.bakingapp.helper.DaoHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.swipe) SwipeRefreshLayout refresh;
    @BindView(R.id.recycler) RecyclerView recycler;

    private GridLayoutManager layoutManager;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        getIdlingResource();

        setSupportActionBar(toolbar);

        layoutManager =  new GridLayoutManager(this, getResources().getInteger(R.integer.recipe_collums));
        recycler.setLayoutManager(layoutManager);

        refresh.post(new Runnable() {
            @Override
            public void run() {

                refresh.setRefreshing(true);

            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getRecipes();

            }
        });

        getRecipes();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

    }

    private void getRecipes() {

        RecipeDao.getInstance().getRecipes(new DaoHelper() {

            @Override
            public void onSuccess(Object object) {

                refresh.setRefreshing(false);

                List<Recipe> list = (List<Recipe>) object;
                recycler.setAdapter(new RecipeAdapter(list, new RecipeAdapter.RecipeClickListener() {
                    @Override
                    public void onRecipeClicked(Recipe recipe) {

                        Intent i = new Intent(RecipeListActivity.this, RecipeItemListActivity.class);
                        i.putExtra("recipe", recipe);
                        startActivity(i);

                    }
                }));

                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }

            }

            @Override
            public void onError(String error) {

                refresh.setRefreshing(false);

            }

        });

    }

}

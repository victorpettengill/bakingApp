package br.com.victorpettengill.bakingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

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

    private void getRecipes() {


        RecipeDao.getInstance().getRecipes(new DaoHelper() {

            @Override
            public void onSuccess(Object object) {

                refresh.setRefreshing(false);

                List<Recipe> list = (List<Recipe>) object;
                recycler.setAdapter(new RecipeAdapter(list, new RecipeAdapter.RecipeClickListener() {
                    @Override
                    public void onRecipeClicked(Recipe recipe) {

                    }
                }));

            }

            @Override
            public void onError(String error) {

                refresh.setRefreshing(false);

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

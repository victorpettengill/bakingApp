package br.com.victorpettengill.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.victorpettengill.bakingapp.adapters.RecipeDetailAdapter;
import br.com.victorpettengill.bakingapp.beans.Recipe;
import br.com.victorpettengill.bakingapp.beans.Step;
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
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.recipeitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new RecipeDetailAdapter(recipe, new RecipeDetailAdapter.RecipeDetailListener() {
            @Override
            public void onIngredientsClicked() {

            }

            @Override
            public void onStepClicked(Step recipeStep) {

            }
        }));

//        DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
//        if (mTwoPane) {
//            Bundle arguments = new Bundle();
//            arguments.putString(RecipeItemDetailFragment.ARG_ITEM_ID, item.id);
//            RecipeItemDetailFragment fragment = new RecipeItemDetailFragment();
//            fragment.setArguments(arguments);
//            mParentActivity.getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.recipeitem_detail_container, fragment)
//                    .commit();
//        } else {
//            Context context = view.getContext();
//            Intent intent = new Intent(context, RecipeItemDetailActivity.class);
//            intent.putExtra(RecipeItemDetailFragment.ARG_ITEM_ID, item.id);
//
//            context.startActivity(intent);
//        }

    }

}

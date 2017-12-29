package br.com.victorpettengill.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Ingredient;
import br.com.victorpettengill.bakingapp.beans.Recipe;

/**
 * Created by appimagetech on 21/12/17.
 */

public class IngredientWidgetProvider implements RemoteViewsService.RemoteViewsFactory {

    private Recipe recipe;
    private Context context;

    public IngredientWidgetProvider(Context context) {
        this.context = context;

        recipe = Recipe.getCurrentRecipe(context);

        Log.i("Provider", "onCreate");

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        Log.i("Provider", "count: "+recipe.getIngredients().size());

        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.ingredient_widget_item);

        Ingredient ingredient = recipe.getIngredients().get(i);

        StringBuilder builder = new StringBuilder();
        builder.append("• ");
        builder.append(ingredient.getQuantity());
        builder.append(" ");
        builder.append(ingredient.getMeasure());
        builder.append(" of ");
        builder.append(ingredient.getIngredient());

        remoteView.setTextViewText(R.id.ingredient, builder.toString());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

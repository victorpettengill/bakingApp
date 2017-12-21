package br.com.victorpettengill.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Ingredient;
import br.com.victorpettengill.bakingapp.beans.Recipe;

/**
 * Created by appimagetech on 21/12/17.
 */

public class IngredientWidgetProvider implements RemoteViewsService.RemoteViewsFactory {

    private Recipe recipe;
    private Context context;

    public IngredientWidgetProvider(Context context, Recipe recipe) {
        this.recipe = recipe;
        this.context = context;
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
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.ingredient_widget_item);
        remoteView.setTextViewText(R.id.ingredient, String.format("• %s", recipe.getIngredients().get(i).getIngredient()));

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
        return false;
    }
}

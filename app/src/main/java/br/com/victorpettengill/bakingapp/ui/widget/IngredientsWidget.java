package br.com.victorpettengill.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

import br.com.victorpettengill.bakingapp.R;
import br.com.victorpettengill.bakingapp.beans.Ingredient;
import br.com.victorpettengill.bakingapp.beans.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.i("IngredientsWidget", "update");

        Recipe recipe = Recipe.getCurrentRecipe(context);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        views.setTextViewText(R.id.title_widget, recipe.getName()+" - "+context.getString(R.string.ingredients));

        Intent intent = new Intent(context, IngredientsWidgetService.class);
//        intent.putExtra(IngredientsWidgetService.RECIPE_ARG, recipe);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.putExtra("random", new Date().getTime());

        views.setRemoteAdapter(R.id.appwidget_list, intent);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_list);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}


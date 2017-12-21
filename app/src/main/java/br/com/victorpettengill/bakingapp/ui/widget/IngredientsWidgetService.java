package br.com.victorpettengill.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import br.com.victorpettengill.bakingapp.beans.Recipe;

/**
 * Created by appimagetech on 21/12/17.
 */

public class IngredientsWidgetService extends RemoteViewsService {

    public static final String RECIPE_ARG = "recipe";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.i("factory", "getViewFactory");

        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        Recipe recipe = intent.getParcelableExtra(RECIPE_ARG);

        return (new IngredientWidgetProvider(this.getApplicationContext(), recipe));
    }

}

package udacitynano.com.br.cafelegal.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONException;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class CafeWidget extends AppWidgetProvider {

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.cafe_widget);
            Intent intent = new Intent(context, CafeWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_convite_button, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String resultMsg;
        ConviteService conviteService = new ConviteService(context, null);
        try {
            conviteService.sendConvite(UserType.getUserId(context),"",false);
            resultMsg = context.getString(R.string.convite_result_enviado_sucesso);

        } catch (JSONException e) {
            resultMsg = context.getString(R.string.convite_result_erro_enviar);
            e.printStackTrace();
        }

        Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();

    }

} /*extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
    int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.cafe_widget);

            Intent intent = new Intent(context, CafeWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_convite_button, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

}
        */
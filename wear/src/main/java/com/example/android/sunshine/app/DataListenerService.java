package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class DataListenerService extends WearableListenerService {

    public final String TAG = DataListenerService.class.getSimpleName();
    private static final String DATA_SYNC_TAG = "DATA_SYNC";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(DATA_SYNC_TAG, TAG + ": onDataChanged(): " + dataEvents);

        String friendlyDate;

        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/today-forecast")) {
                    friendlyDate = dataMap.getString("date");
                    Log.d(DATA_SYNC_TAG, TAG + ": date is " + friendlyDate);

                    Log.d(DATA_SYNC_TAG, TAG + ": sending broadcast 'watch-face-data'");

                    Intent intent = new Intent("watch-face-data");
                    intent.putExtra("date", friendlyDate);
                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent);
                }
            }
        }

    }
}

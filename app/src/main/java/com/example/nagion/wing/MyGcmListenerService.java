package com.example.nagion.wing;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String delimiter = "||";
        String msgToken[];

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        try {
            msgToken = message.split("||");
            String cmd = msgToken[0];
            if (cmd.equals("WING")) {
                //윙
                Log.w("FROM", "-----------------------------------------" + msgToken[1]);
                Log.w("NAME", "-----------------------------------------" + msgToken[2]);
                Log.w("CNT", "-----------------------------------------" + msgToken[3]);
                Log.w("TIME", "-----------------------------------------" + msgToken[4]);
                sendWingNotification(msgToken[1], msgToken[2], msgToken[3]);
            } else if (cmd.equals("CUSTOMWING")) {
                //커스텀윙
                Log.w("FROM", "-----------------------------------------" + msgToken[1]);
                Log.w("NAME", "-----------------------------------------" + msgToken[2]);
                Log.w("PATTERN", "-----------------------------------------" + msgToken[3]);
                Log.w("TIME", "-----------------------------------------" + msgToken[4]);
                sendCustomWingNotification(msgToken[1], msgToken[2], msgToken[3]);
            } else if (cmd.equals("FR")) {
                //친구요청
                Log.w("FROM", "-----------------------------------------" + msgToken[1]);
                Log.w("MESSAGE", "-----------------------------------------" + msgToken[2]);
                Log.w("TIME", "-----------------------------------------" + msgToken[3]);
                sendRequestNotification(msgToken[1], msgToken[2]);
            }
        }
        catch (Exception e){
            /* before code
            e.printStackTrace();
            */
            Log.e("e","error occured");
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        //TODO 앱 실행중일땐(아니면 WingActivity 보고있을때) 화면 갱신(윙컴포넌트 생성) 아닐땐 푸시알림 - 푸시알림 누르면 Wing화면으로 이동
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     *
     */
    private void sendWingNotification(String from, String name, String cnt) {
        Intent intent = new Intent(this, WingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        long[] ptrn = {0, 1000};

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Wing")
                .setContentText(name+"으로부터 위-ㅇ")
                .setVibrate(ptrn)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
/*
        Vibrator vibe;
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(pattern, -1);*/

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendCustomWingNotification(String from, String name, String pattern) {
        Intent intent = new Intent(this, WingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



        String[] ptrnToken = pattern.split("^");
        long ptrn[] = new long[ptrnToken.length];

        for(int i=0;i<ptrnToken.length;i++){
            ptrn[i] = Long.parseLong(ptrnToken[i]);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Wing")
                .setContentText(name+"으로부터 커스텀 위-ㅇ")
                .setVibrate(ptrn)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
/*
        Vibrator vibe;
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(pattern, -1);*/

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendRequestNotification(String from, String name) {
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        long[] ptrn = {0, 1000};
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Wing")
                .setContentText(name+"으로부터 친구 요청")
                .setAutoCancel(true)
                .setVibrate(ptrn)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
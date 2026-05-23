package com.example.splitscreen;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.graphics.Path;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.content.Context;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class SplitService
    extends AccessibilityService {

    @Override
    protected void onServiceConnected() {

        new Handler().postDelayed(() -> {

            performGlobalAction(
                GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN
            );

            new Handler().postDelayed(() -> {

                Intent waze =
                    getPackageManager()
                    .getLaunchIntentForPackage(
                        "com.waze"
                    );

                if (waze != null) {

                    waze.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                    );

                    startActivity(waze);
                }

            }, 1500);

            new Handler().postDelayed(() -> {

                Path path = new Path();

                path.moveTo(960, 600);
                path.lineTo(1280, 600);

                GestureDescription.Builder builder =
                    new GestureDescription.Builder();

                builder.addStroke(
                    new GestureDescription.StrokeDescription(
                        path,
                        0,
                        500
                    )
                );

                dispatchGesture(
                    builder.build(),
                    null,
                    null
                );

            }, 5000);

            new Handler().postDelayed(() -> {

                WifiManager wifi =
                    (WifiManager)getApplicationContext()
                    .getSystemService(
                        Context.WIFI_SERVICE
                    );

                wifi.setWifiEnabled(false);

                new Handler().postDelayed(() -> {

                    wifi.setWifiEnabled(true);

                }, 3000);

            }, 7000);

            // TAP SCREEN
            new Handler().postDelayed(() -> {

                Path tapPath = new Path();

                // CHANGE THESE COORDINATES
                tapPath.moveTo(500, 500);

                GestureDescription.Builder tapBuilder =
                    new GestureDescription.Builder();

                tapBuilder.addStroke(
                    new GestureDescription.StrokeDescription(
                        tapPath,
                        0,
                        100
                    )
                );

                dispatchGesture(
                    tapBuilder.build(),
                    null,
                    null
                );

            }, 14000);

            // PLAY MUSIC
            new Handler().postDelayed(() -> {

                AudioManager audioManager =
                    (AudioManager)getSystemService(
                        Context.AUDIO_SERVICE
                    );

                audioManager.dispatchMediaKeyEvent(
                    new KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_MEDIA_PLAY
                    )
                );

                audioManager.dispatchMediaKeyEvent(
                    new KeyEvent(
                        KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_MEDIA_PLAY
                    )
                );

            }, 17000);

        }, 1000);
    }

    @Override
    public void onAccessibilityEvent(
        AccessibilityEvent event
    ) {

    }

    @Override
    public void onInterrupt() {

    }
}

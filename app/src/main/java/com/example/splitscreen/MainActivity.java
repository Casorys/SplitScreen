package com.example.splitscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent music =
            getPackageManager()
            .getLaunchIntentForPackage(
                "com.apple.android.music"
            );

        if (music != null) {
            startActivity(music);
        }

        new Handler().postDelayed(() -> {

            Intent service =
                new Intent(
                    this,
                    SplitService.class
                );

            startService(service);

        }, 3000);

        finish();
    }
}

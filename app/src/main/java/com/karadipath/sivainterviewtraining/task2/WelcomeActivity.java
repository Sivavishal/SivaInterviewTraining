package com.karadipath.sivainterviewtraining.task2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.karadipath.sivainterviewtraining.R;
import com.karadipath.sivainterviewtraining.home.HomeActivity;
import com.karadipath.sivainterviewtraining.home.UtilFunctions;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Are you want to Logout?", Snackbar.LENGTH_LONG)
                .setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UtilFunctions.setPreference(WelcomeActivity.this, UtilFunctions.PREFS_USEREMAIL, "");
                        UtilFunctions.setPreference(WelcomeActivity.this, UtilFunctions.PREFS_USER_PIN, "");
                       finish();
                    }
                }).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

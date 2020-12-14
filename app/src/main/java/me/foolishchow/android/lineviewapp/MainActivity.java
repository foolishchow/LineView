package me.foolishchow.android.lineviewapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSS = findViewById(R.id.ss);
        //mSS.postDelayed(mUpdater, 200L);
    }

    private int index = 1;
    private Runnable mUpdater = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            if (mSS != null) {
                mSS.setText("[ " + index + " -- " + Double.valueOf(Math.random()).floatValue() * 1000 + " ]");
                index ++;
                mSS.postDelayed(mUpdater, 200L);
            }
        }
    };

}
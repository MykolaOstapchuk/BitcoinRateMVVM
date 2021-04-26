package com.example.bitcoinratemvvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment cuFragment = new MainPageFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_page, cuFragment)
                .addToBackStack("MainPageFragment")
                .commit();
    }
}
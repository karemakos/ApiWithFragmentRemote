package com.calc.apiwithfragmentremote.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.calc.apiwithfragmentremote.R;
import com.calc.apiwithfragmentremote.fragments.bookMainFragment;
import com.calc.apiwithfragmentremote.models.bookModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadFragment();
    }

    private void LoadFragment()
    {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new bookMainFragment())
                .commit();

    }
}

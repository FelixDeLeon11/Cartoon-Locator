package com.example.cartoonlocator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cartoonlocator.Fragments.CartoonListActivity;

public class MainActivity extends AppCompatActivity implements NavigationHost {
    Fragment fragmentTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CartoonListActivity())
                    .commit();
        }
    }

    public void GOTO(Fragment fragment){
        fragmentTO = fragment;
    };

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()    //.hide(fragment);
                        .replace(R.id.container, fragment);

//        transaction.add(R.id.container, fragmentTO);
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

}
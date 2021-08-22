package com.udem.homeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction transition;
    Fragment f_home, f_test1, f_test2;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);

        f_test1 = new ViewNoteFragment();
        f_test2 = new ViewRemindFragment();
        f_home = new ViewHomeFragment();

        navigationView = findViewById(R.id.androidNav);
        navigationView.setSelectedItemId(R.id.navHome);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transition = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navHome:
                        transition.replace(R.id.contenedor, f_home);
                        transition.addToBackStack(null);
                        break;
                    case R.id.navSearch:
                        Toast.makeText(getApplicationContext(),"Search Option",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navNote:
                        transition.replace(R.id.contenedor, f_test1);
                        transition.addToBackStack(null);
                        break;
                    case R.id.navremind:
                        transition.replace(R.id.contenedor, f_test2);
                        transition.addToBackStack(null);
                        break;
                }
                transition.commit();
                return true;
            }
        });

    }

        /*
    public void OnClick(View view){
        transition = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.btnf1:
                transition.replace(R.id.contenedor, f_test1);
                transition.addToBackStack(null);
                break;
            case R.id.btnf2:
                transition.replace(R.id.contenedor, f_test2);
                transition.addToBackStack(null);
                break;
        }
        transition.commit();
    }

         */
}
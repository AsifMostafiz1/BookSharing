package com.example.firebaseauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String currentUserID;

    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    goDashbord();
                    return true;


                case R.id.navigation_add:
                    mAuth.signOut();

                   startActivity(new Intent(MainActivity.this,LoginActivity.class));
                   return true;

                case R.id.navigation_profile:

                    goUserProfile();
                    return true;




            }
            return false;
        }
    };

    private void goUserProfile()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.conteiner,new UserProfileFragment());
        ft.commit();
    }


    private void goDashbord() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.conteiner,new UserHomeFragment());
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.main_page_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seller");

        drawerLayout = findViewById(R.id.drawer_layout);



        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        navigationView = findViewById(R.id.navigationViewID);

        View view = navigationView.inflateHeaderView(R.layout.nevagation_header);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser==null)
        {
            sendUserLoginActivity();
        }

    }

    private void sendUserLoginActivity()

    {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)
        ) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
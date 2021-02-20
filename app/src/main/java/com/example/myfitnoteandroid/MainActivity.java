package com.example.myfitnoteandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.myfitnoteandroid.data.SessionManager;
import com.example.myfitnoteandroid.ui.ViewProfile;
import com.example.myfitnoteandroid.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView nome, navMail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nome = headerView.findViewById(R.id.nav_nome_cognome);
         navMail = (TextView) headerView.findViewById(R.id.menuMail);
        SessionManager sessionManager = new SessionManager(this);

        navMail.setText(sessionManager.getMail());
        nome.setText(sessionManager.getName() + " " + sessionManager.getSurname());

        nome.setOnClickListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_foods, R.id.nav_esercizi,
                R.id.nav_schede, R.id.nav_mis_battito,
                R.id.sheet_details, R.id.nav_obbiettivi,
                R.id.nav_kcal, R.id.nav_bmi, R.id.nav_scheda)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            this.finish();
            startActivity(intent);
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.removeSession();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == nome.getId() || v.getId() == navMail.getId()  ){

            Intent intent = new Intent(this, ViewProfile.class);
            startActivity(intent);

        }



    }
}

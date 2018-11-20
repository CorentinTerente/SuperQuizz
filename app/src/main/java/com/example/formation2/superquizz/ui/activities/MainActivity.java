package com.example.formation2.superquizz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.model.Question;
import com.example.formation2.superquizz.ui.fragments.PlayFragment;
import com.example.formation2.superquizz.ui.fragments.QuestionListFragment;
import com.example.formation2.superquizz.ui.fragments.ScoreFragment;
import com.example.formation2.superquizz.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestionListFragment.OnListFragmentInteractionListener,ScoreFragment.OnFragmentInteractionListener,SettingsFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            QuestionListFragment fragment= new QuestionListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.farmelayout_fragment_container, fragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



         if (id == R.id.nav_info) {

             Intent intent = new Intent(MainActivity.this,InfoActivity.class);
             startActivity(intent);
        } else if (id == R.id.nav_play){

             QuestionListFragment fragment = new QuestionListFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.farmelayout_fragment_container, fragment)
                     .commit();
         } else if(id == R.id.nav_score){

             ScoreFragment fragment = new ScoreFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.farmelayout_fragment_container, fragment)
                     .commit();
         } else if (id == R.id.nav_settings) {

             SettingsFragment fragment = new SettingsFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.farmelayout_fragment_container, fragment)
                     .commit();
         }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //fix later
    @Override
    public void onQuestionListSelected(Question item) {
        Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
        startActivity(intent);
    }
}

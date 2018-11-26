package com.example.formation2.superquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.formation2.superquizz.R;
import com.example.formation2.superquizz.api.APIClient;
import com.example.formation2.superquizz.database.QuestionsDatabaseHelper;
import com.example.formation2.superquizz.model.Question;
import com.example.formation2.superquizz.ui.fragments.CreateFragment;
import com.example.formation2.superquizz.ui.fragments.QuestionListFragment;
import com.example.formation2.superquizz.ui.fragments.ScoreFragment;
import com.example.formation2.superquizz.ui.fragments.SettingsFragment;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("CanBeFinal")
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestionListFragment.OnListFragmentInteractionListener, CreateFragment.OnCreateListener {

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

        //default fragment is QuestionListFragment
        if (savedInstanceState == null) {

            Toast loadToast = Toast.makeText(this,"Can't load questions from server",Toast.LENGTH_SHORT);
            APIClient client = APIClient.getInstance();

            client.getQuestions(new APIClient.APIResult<List<Question>>() {
                @Override
                public void onFailure(IOException e) {
                    loadToast.show();
                }

                @Override
                public void OnSuccess(List<Question> object) throws IOException {
                    QuestionsDatabaseHelper dbHelper= QuestionsDatabaseHelper.getInstance(getApplicationContext());
                    dbHelper.synchroniseDatabaseQuestions(object);
                }
            });

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            QuestionListFragment fragment= new QuestionListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_fragment_container, fragment)
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


         if (id == R.id.nav_info) {

             Intent intent = new Intent(MainActivity.this,InfoActivity.class);
             startActivity(intent);
        } else if (id == R.id.nav_play){

             QuestionListFragment fragment = new QuestionListFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.frame_layout_fragment_container, fragment)
                     .commit();
         } else if(id == R.id.nav_score){

             ScoreFragment fragment = new ScoreFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.frame_layout_fragment_container, fragment)
                     .commit();
         } else if (id == R.id.nav_settings) {

             SettingsFragment fragment = new SettingsFragment();
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.frame_layout_fragment_container, fragment)
                     .commit();
         } else if (id == R.id.nav_add) {

             CreateFragment fragment = new CreateFragment();
             fragment.listener = this;
             getSupportFragmentManager().beginTransaction()
                     .replace(R.id.frame_layout_fragment_container,fragment)
                     .commit();
         }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onQuestionListSelected(Question item) {
        Intent intent = new Intent(MainActivity.this,QuestionActivity.class);
        intent.putExtra("question",item);
        startActivity(intent);
    }

    @Override
    public void OnQuestionLongPressed(Question mItem) {
        APIClient client = APIClient.getInstance();
        QuestionsDatabaseHelper dbHelper = QuestionsDatabaseHelper.getInstance(this);

        client.deleteQuestion(new APIClient.APIResult<Question>(){
        @Override
        public void onFailure(IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(),"Can't delete quesiton",Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public void OnSuccess(Question object) throws IOException {
            dbHelper.deleteQuestion(mItem);
        }
    },mItem);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_fragment_container,QuestionListFragment.newInstance(1))
                .commit();
    }

    @Override
    public void questionCreated(Question q) {
        Toast toastFailCreate = Toast.makeText(getApplicationContext(),"Can't add question to database", Toast.LENGTH_SHORT);
        APIClient.getInstance().createQuestion(new APIClient.APIResult<Question>(){
            @Override
            public void onFailure(IOException e) {
                toastFailCreate.show();
            }

            @Override
            public void OnSuccess(Question object) throws IOException {
                QuestionsDatabaseHelper questionDb = QuestionsDatabaseHelper.getInstance(getApplicationContext());
                try {
                    questionDb.addQuestion(object);
                } catch (Exception e){
                    Log.e("ERROR","Can't insert");
                }
            }
        },q);




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_fragment_container,QuestionListFragment.newInstance(1))
                .commit();

    }
}

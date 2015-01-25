package com.example.matthewdarke.navagatingaround1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


/**
 * Created by matthewdarke on 1/25/15.
 */
public class DetailActivity extends ActionBarActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // If no state bundle exists, this is the first launch.
        // Add your fragments just this one time.

        if (savedInstanceState == null){

            DetailFragment detFrag = new DetailFragment();

            getFragmentManager().beginTransaction().

                    add(R.id.detail_activity, detFrag).commit();



        }

//creates/Nav bar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
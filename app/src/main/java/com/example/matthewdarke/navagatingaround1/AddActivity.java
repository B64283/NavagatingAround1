package com.example.matthewdarke.navagatingaround1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by matthewdarke on 1/23/15.
 */
public class AddActivity extends ActionBarActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (savedInstanceState == null){
            AddFragment f1 = new AddFragment();
            getFragmentManager()
                    .beginTransaction()


                    .add(R.id.add_activity_id, f1)
                    .commit();


        }


        //actionBar for navagation back to main



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Add_Contacts:
                // Implement code to execute for the settings
              AddFragment.saveContact();

                break;
            case android.R.id.home:
                // Implement code to execute for the search
                finish();
            default:
                // Implement default behavior here
                break;
        }
        return true;
    }

}

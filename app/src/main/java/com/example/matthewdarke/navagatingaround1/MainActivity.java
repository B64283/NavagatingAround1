package com.example.matthewdarke.navagatingaround1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements Serializable {

    private static final long serialVersionUID = 8736847634070552888L;
    //set up arrayList and array adapter with model data
    public static ArrayList<Contacts> contactsArray = new ArrayList<>();
    public static ArrayAdapter<Contacts> adapter;


    //requestCode - the identifier from the launching intent
    public static final int REQUEST_CODE = 2;

    public static Contacts mConData;
    public static int deleteIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If no state bundle exists, this is the first launch.
        // Add your fragments just this one time.
        if (savedInstanceState == null) {

            MainFragment fragment = new MainFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.main_activity_id, fragment).commit();
        }



    }

//When an Activity launches an Intent with the desire to get data back
// from the launched component, it will need to incorporate the onActivityResult()
// method. This method accepts three parameters:

    //* int requestCode - the identifier from the launching intent
    //* int resultCode - a integer constant informing the launching activity of a successful or
    // failed data return.
    //* Intent data - the intent returned by the launched component

    //update the contents of the list
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        MainFragment mainFragment = (MainFragment) getFragmentManager()
                .findFragmentById(R.id.main_activity_id);

        super.onActivityResult(requestCode, resultCode, data);

        // Indicate state of results
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<Contacts> first = (ArrayList<Contacts>) data.getSerializableExtra("conData");

            if (contactsArray != null) {
                cacheData();
                contactsArray.addAll(first);

                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsArray);

                mainFragment.setListAdapter(adapter);

            } else {
                cacheData();
                contactsArray = first;

            }


        }

    }

    //saves data
    public void cacheData() {


        try {
            FileOutputStream outputStream = openFileOutput("contactData", Context.MODE_PRIVATE);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            //add data items to array
            for (Contacts aContactsArray : contactsArray) {

                mConData = aContactsArray;

                //write object
                objectOutputStream.writeObject(mConData);
            }

            objectOutputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }




    //reads saved data
    public void loadCacheData() {

        MainFragment mainFragment = (MainFragment)
                getFragmentManager().findFragmentById(R.id.main_activity_id);


        try{

            FileInputStream inputStream = openFileInput("contactData");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            //needs while loop

            while (inputStream.available()!=0){

                //cast data
                mConData = (Contacts) objectInputStream.readObject();

                contactsArray.add(mConData);
                //close input stream
            }

            //close input stream

            objectInputStream.close();
//add objects to array adapter
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsArray);

            mainFragment.setListAdapter(adapter);
        }catch (Exception e) {

            e.printStackTrace();
        }
    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Add_Contacts) {
            Intent addTo = new Intent(this,AddActivity.class);
            startActivityForResult(addTo, REQUEST_CODE);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.matthewdarke.navagatingaround1;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by matthewdarke on 1/23/15.
 */
public class MainFragment extends ListFragment {

    private int mPersonSelected = -1;
    private ActionMode mActionMode;
    Button bnImpli;

    View v;
    private Contacts mContactDetails = new Contacts();
    public static ArrayList<Contacts> mContactsArray = new ArrayList<>();

    private int mContactSelect =  -1;

    //public static final int REQUEST_CODE = 2001;
    public MainFragment(){

    }

    //use save instance state with bundles

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.main_fragment, container, false);
        bnImpli = (Button) v.findViewById(R.id.b3);


        ListView contactList = (ListView) v.findViewById(android.R.id.list);

        contactList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(mActionMode != null) {
                    return false;


                }
                MainActivity.deleteIndex = position;
                //mPersonSelected = position;
                mActionMode = getActivity().startActionMode(mActionModeCallback);

                return true;

            }



        });

        return v;

    }


    //restore your fragment state in onActivityCreated()
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // provides your activity with a Bundle object that can be used to store
        // out any data needed so that the activity can be created exactly as it
        // was before it was destroyed

        super.onActivityCreated(savedInstanceState);

        // we can check if that bundle is null and, if not, use it to restore our state
//Load cached Data from main activity
        if (getActivity() != null) {
            ((MainActivity)getActivity()).loadCacheData();
        }


    }



    //CONTEXTUAL ACTION CALLBACK
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contact, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }


        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.peopleDelete:
                    int deleteIndex = MainActivity.deleteIndex;

                    //delete at index from contacts
                    MainActivity.contactsArray.remove(deleteIndex);

                    //tell adapter of dataset change
                    MainActivity.adapter.notifyDataSetChanged();

                    //updatedatacache

                    //DetailFragment.updateCache();


                    //DetailFragment.updateCache();
                    mode.finish();
                    Toast.makeText(getActivity(), getString(R.string.delete), Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {

            mActionMode = null;
        }
    };




    //set up onListItemClick
    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);

        MainActivity.deleteIndex = position;


        Contacts contacts = (Contacts) l.getItemAtPosition(position);

        Bundle bundle = new Bundle();
        bundle.putString("mName", contacts.getmName());
        bundle.putString("mAddress", contacts.getmAddress());
        bundle.putString("mNumber", contacts.getmNumber());

        // requesting the start of a new Activity (DetailActivity) within The application.
        Intent pdIntent = new Intent(view.getContext(),DetailActivity.class);

        //passing data to detail using putExtra in the intent
        pdIntent.putExtra("contactsArray", ((MainActivity)getActivity()).contactsArray);
        pdIntent.putExtras(bundle);

        //Using startActivity Android will process the intent (pdIntent), and launch the requested component from the Intent.
        startActivity(pdIntent);


    }










}
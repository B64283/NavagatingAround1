package com.example.matthewdarke.navagatingaround1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by matthewdarke on 1/25/15.
 */
public class DetailFragment extends Fragment {

    public ArrayList<Contacts> mContactsArrayList = new ArrayList<>();
    public String mCon;
    private TextView mConName;
    private TextView mConAddress;
    private TextView mConNumber;
    Button bnImpli;
    Button bnDelete;



    public DetailFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view;
        view = inflater.inflate(R.layout.detail_fragment, container, false);

        bnImpli = (Button) view.findViewById(R.id.b3);
        bnDelete = (Button) view.findViewById(R.id.bnDelete);

        mConName = (TextView) view.findViewById(R.id.textView_Make);
        mConAddress = (TextView) view.findViewById(R.id.textView_model);
        mConNumber = (TextView) view.findViewById(R.id.textView_rate);



        Intent intent = getActivity().getIntent();


        mContactsArrayList = (ArrayList<Contacts>) intent.getSerializableExtra("contactsArray");


        //bundles data and getsExtras above
        Bundle data = intent.getExtras();


        if (data != null) {
///gets key value & set text



            String firstName = data.getString("mName");
            mCon = data.get("mName").toString();

            String firstAddress = data.getString("mAddress");
            String firstNumber = data.getString("mNumber");

            mConName.setText(firstName);
            mConAddress.setText(firstAddress);
            mConNumber.setText(firstNumber);
        }


        //accsesss the activity instance using the get Activity method

        bnImpli.setOnClickListener(new View.OnClickListener() {

/*
If you're sending text, you'd set your mime to be "text/plain" or "text/xml"
depending on what type of text it was. You could also set your data to be an image
with "image/png" or "image/jpeg". If you're sending text, which is the most common,
the Intent class defines an extra you can set that will hold the text you want to send.

Intent sendIntent = new Intent(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this text!");
sendIntent.setType("text/plain");
startActivity(Intent.createChooser(sendIntent, "Share data with..."));



You might've noticed that after we set our action, text, and type, we started a chooser
 using Intent.createChooser(). A chooser will allow the user to decide which app handles the intent
  that you're trying to send. If you're attempting to launch an ACTION_SEND intent, the system will
   populate the chooser with all activities that can handle an ACTION_SEND intent with your type of data.
    If only one app exists that can open this data, that app will open without prompting the user first.

 */

            @Override
            public void onClick(View view) {
                // TDOD Auto-generated method stub

                Intent implicit = new Intent(Intent.ACTION_SEND);

                implicit.putExtra(Intent.EXTRA_TEXT, "Sharing this text from my app I made!");
                implicit.setType("text/plain");
                startActivity(implicit);

            }
        });

        bnDelete.setOnClickListener(new View.OnClickListener() {

            /*
            Viewing Data
            The view intent, otherwise referred to as ACTION_VIEW, allows applications to launch external viewing apps.
             So if you wanted to view an image, you could launch the system gallery. If you wanted to view a web link,
             you could open the Chrome browser. As with send intents, the app that is opened is determined by the type of
              data you're trying to view.

            When declaring an ACTION_VIEW intent, you would set the data and/or type of your intent. The data for an intent is
             a Uri that points to the data you wish to view. This could be a file or web Uri. If you're setting a web Uri, you only
              need to set the data and the intent will set the type for you. However, if you're setting a file Uri, you'll need to set
               the type as well so that the system knows what the contents of the file are underneath.

            // Open web Uri.
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            viewIntent.setData(Uri.parse("http://www.google.com"));
            startActivity(Intent.createChooser(viewIntent, "Open with..."));
            // Open file Uri.
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            File file = new File("/some_directory/video.mp4");
            viewIntent.setDataAndType(Uri.fromFile(file), "video/*");
            startActivity(Intent.createChooser(viewIntent, "Open with..."));

             */
//
            // Delete contact removes @ index and notifies the adapter of dataset change and update the cache
            @Override
            public void onClick(View view) {
                int deleteIndex = MainActivity.deleteIndex;

                //delete at index from contacts
                MainActivity.contactsArray.remove(deleteIndex);

                //tell adapter of dataset change
                MainActivity.adapter.notifyDataSetChanged();

                //updatedatacache

                getActivity().
                        finish();
                updateCache();

                Toast.makeText(getActivity(), getString(R.string.delete), Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }


    // Updates the cache when user delete a contact it updates And
    // rewrites the data to the contactData file
    public void updateCache() {
        try{


            FileOutputStream outputStream = getActivity().openFileOutput("contactData", Context.MODE_PRIVATE);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            //iterate through Array
            for(int i = 0; i < MainActivity.contactsArray.size(); i++){

                MainActivity.mConData = MainActivity.contactsArray.get(i);


                objectOutputStream.writeObject(MainActivity.mConData);
            }
            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();

        }


    }


/*
There are several apps baked into the Android OS that can handle different functions. The Chrome browser can open web links, the gallery can open/edit images, and the Hangouts app can send text and web message. As mentioned in a previous lesson, you can access these functions by launching an implicit intent. However, what if your app has the ability to view data? Would you want that functionality accessible to other apps? If you do, you can offer that functionality to other apps by setting up one of your activities to handle implicit intents.

Up until now, we've mostly ignored the <intent-filter> tag inside the manifest file. By default, this tag specifies a MAIN action and a LAUNCHER category. What this intent filter does is tell the system that this activity is the main activity for the app (the app's starting point) and it should be shown in the system's app launcher. This should only ever appear in one of your app's activities as you would typically have only one starting point for a given app.

The MAIN/LAUNCHER intent filter is just one of many intent filters that you apply to your activity. For instance, if you want your app to be able to accept ACTION_VIEW intents, you could change your intent filter to accept the VIEW action instead of the MAIN action. Below we'll walk through an example of how to convert your intent filter to accept a VIEW intent that can be used to view web links.

EXAMPLE

We'll start this example off by creating a new application and we'll name our generated activity, MainActivity. In our manifest, this generates the following activity tag.

<activity
	android:name=".MainActivity"
	android:label="@string/app_name" >
	<intent-filter>
		<action android:name="android.intent.action.MAIN" />
		<category android:name="android.intent.category.LAUNCHER" />
	</intent-filter>
</activity>

As mentioned previously, the above intent filter is used to signify that this is the main activity for our app. Instead of being the main activity for the app, we'll change this to accept an ACTION_VIEW intent for viewing web data. To do this, we need to change a couple things. The first thing we change is the action tag. Instead of a MAIN action, we need a VIEW action. Then, we'd delete the LAUNCHER category as this activity won't appear in the app's launcher drawer. Instead, we'll add two new categories, DEFAULT and BROWSABLE. The DEFAULT category signifies to the system that this app can be used as a default viewer for data. This is important to set because the Chrome web browser is already set to be the system default. If you don't signal your activity to be a default, the system will just ignore your app and open Chrome. The BROWSABLE category then signals that this app can handle browsable links, such as web links. The last thing to do here is add a data tag that specifies a Uri scheme. This scheme refers to the beginning section of the Uri type you want to support. We're supporting web links, so we'll specify the "http" scheme.

<activity
	android:name=".MainActivity"
	android:label="@string/app_name" >
	<intent-filter>
		<action android:name="android.intent.action.VIEW" />
		<category android:name="android.intent.category.BROWSABLE" />
		<category android:name="android.intent.category.DEFAULT" />
		<data android:scheme="http" />
	</intent-filter>
</activity>

That's it! At this point, your activity will no longer show up as an app in the launcher drawer, but your app will instead show up in the chooser dialog when opening a web link. We can use the demo app for the lesson on handling implicit attempts (also linked in this lesson) to launch an ACTION_VIEW intent which will show our app in the chooser dialog.

Now that are app is showing up in the chooser, we should probably make it actually handle and use the intent. Since we're handling web links, let's show those links in a WebView. In our layout, we'll define a simple LinearLayout that holds a WebView.

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" >
    <WebView android:id="@+id/web_view"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />
</LinearLayout>

Then, in our MainActivity class, we'll set this layout to be our view and get a reference to our WebView. The WebView widget requires that you set a web client to the view or else it'll just open all links in the default browser. So we'll set our web client to just be a default WebViewClient object. Now we can get the intent that opened this activity using getIntent() (like we would for data flow and intent extras), and get the data that was set. The data is a Uri so we convert that to a string using the toString() method and load that string, which should be our web link, in the WebView. Keep in mind that WebView requires the INTERNET permission to load http Uris, so be sure to declare that in the manifest.

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		Uri data = intent.getData();
		WebView wv = (WebView)findViewById(R.id.web_view);
		wv.setWebViewClient(new WebViewClient());
		wv.loadUrl(data.toString());
	}
}

At this point our app is fully capable of accepting ACTION_VIEW intents that contain web links. To do this with other intent actions, you would follow a very similar framework. You'd update the intent filter with actions and categories, set the type of data your activity accepts, and handle the intent in the activity. Try doing creating an intent filter that accepts an ACTION_SEND intent using what you know about intent filters and implicit intents.
 */


}

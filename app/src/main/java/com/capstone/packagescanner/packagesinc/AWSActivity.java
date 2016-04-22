package com.capstone.packagescanner.packagesinc;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author austi
 * @author camwhe
 * @since 6 April 2016
 *
 * @see AppCompatActivity
 * @see Package
 */
public class AWSActivity extends AppCompatActivity {


    /**
     * Parent calling class.
     */
    private Class parentActivity = MainPage.class;

    /**
     * Name value to use when ordering this Activity to return to an Activity that is not the
     * MainPage activity.
     */
    public static final String INTENT_PARENT_ACTIVITY
            = "com.capstone.packagescanner.packagesinc.parentactivity";


    /**
     * Package ID of scanned package.
     */
    private String packageID = "UNKNOWN_PACKAGE_ID";

    /**
     * Name value to use when sending a package ID to this Activity through its intent.
     */
    public static final String INTENT_PACKAGE_ID
            = "com.capstone.packagescanner.packagesinc.packageid";


    /**
     * Date object that holds the time the package with the ID above was scanned.
     */
    private Date utc = new Date(0);

    /**
     * Name value to use when sending a Date object containing the UTC to this Activity
     * through its intent.
     */
    public static final String INTENT_PACKAGE_UTC
            = "com.capstone.packagescanner.packagesinc.packageutc";


    /**
     * String representation of a JSON list containing all of a package's extra attributes.
     */
    private String attributesJSONString = "{}";

    /**
     * Name value to use when sending a JSON string of attributes to this Activity through its
     * intent.
     */
    public static final String INTENT_PACKAGE_ATTRIBUTES
            = "com.capstone.packagescanner.packagesinc.packageattributes";

    /**
     * String List of AWS Credentials.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.content);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        layout.addView(textView);

        Intent intent = getIntent();
        this.processIntent(intent);

        AWSTask task = new AWSTask(getApplicationContext());
        task.addPackage(
                new Package()
                        .setPackageID(this.packageID)
                        .setUTC(this.utc)
                        .setAttributesJSON(this.attributesJSONString)
        );

        /*List<String> creds = new ArrayList<String>();
        creds.add("us-east-1:cfa64042-b279-4ccb-8c6c-d8bbc98209b7");
        creds.add("US_EAST_1");
        task.setCredentials(creds);
        */

        task.execute();

        textView.setText("Sending Package ID: " + packageID);

        // TODO: Could implement a response from server message here.
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
    }


    /**
     * Method to call after this Activity has been started so that its intent can be interpreted.
     *
     * @param intent Intent of this Activity.
     */
    private void processIntent(Intent intent)
    {
        if (intent.hasExtra(AWSActivity.INTENT_PARENT_ACTIVITY)) {
            Serializable s = intent.getSerializableExtra(AWSActivity.INTENT_PARENT_ACTIVITY);
            // TODO: Deserialization
        }

        if (intent.hasExtra(AWSActivity.INTENT_PACKAGE_ID)) {
            String packID = intent.getStringExtra(AWSActivity.INTENT_PACKAGE_ID);
            if (null != packID) this.packageID = packID;
        }

        if (intent.hasExtra(AWSActivity.INTENT_PACKAGE_UTC)) {
            Long l = intent.getLongExtra(AWSActivity.INTENT_PACKAGE_UTC, 0);
            this.utc.setTime(l);
        }

        if (intent.hasExtra(AWSActivity.INTENT_PACKAGE_ATTRIBUTES)) {
            String jsonAttributes = intent.getStringExtra(AWSActivity.INTENT_PACKAGE_ATTRIBUTES);
            if (null != jsonAttributes) this.attributesJSONString = jsonAttributes;
        }
    }


    public void returnToMain(View view)
    {
        Intent intent = new Intent(this, parentActivity);
        startActivity(intent);
        this.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return (id == R.id.action_settings) || super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_display_message,
                    container, false);
            return rootView;
        }
    }
}
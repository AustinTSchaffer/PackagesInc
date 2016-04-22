package com.capstone.packagescanner.packagesinc;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * This AppCompatActivity will be used to display fields to the user. These fields will be populated
 * based on user configurations.
 *
 * @author austi
 * @author camwhe
 * @since 20 February 2016
 *
 * @see AppCompatActivity
 * @see Intent
 */
public class MainPage extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.capstone.packagescanner.MESSAGE";
    public final static String SCANNED_BARCODE = "com.capstone.packagescanner.BARCODE_SCANNED";
    public final static String SCAN_BARCODE = "com.capstone.packagescanner.SCAN_BARCODE";
    public final static String ATTRIBUTE_STRING = "com.capstone.packagescanner.ATTRIBUTE_STRING";
    public final static String ATTRIBUTE_DATA = "com.capstone.packagescanner.ATTRIBUTE_DATA";
    public final static String ID_POOL = "IDENTITY_POOL";
    public final static String REGION = "REGION";
    private static final String TAG = "MainPage";
    private static final int RETREIVE_ATTRIBUTES = 1;

    private Intent intent;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    private ArrayList<String> myCreds = new ArrayList<>(), attributeData = new ArrayList<>();


    private SharedPreferences myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPreference= PreferenceManager.getDefaultSharedPreferences(this);

        this.digentIntent();

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                Log.d(TAG, "Preference key =" + key);
                if (key.equals(ID_POOL) || key.equals(REGION)) {
                    // handle credential changes
                    setCredentials();

                }
            }
        };
        myPreference.registerOnSharedPreferenceChangeListener(listener);
//
        setCredentials();


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openCamera(view);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(this, activityPreference.class);
                startActivity(myIntent);
            default:
                break;
        }
        return true;
    }

    /**
     * Populates fields according to the Activity's intent.
     *
     * @see Intent
     */
    private void digentIntent() {
        this.intent = getIntent();

        if (this.intent.hasExtra(MainPage.SCANNED_BARCODE)) {
            EditText editText = (EditText) findViewById(R.id.edit_message);
            editText.setText(this.intent.getStringExtra(MainPage.SCANNED_BARCODE));
        }

        if (this.intent.hasExtra(ATTRIBUTE_DATA)) {
            TextView attributeText = (TextView) findViewById(R.id.attribute_text);
            attributeData = this.intent.getStringArrayListExtra(ATTRIBUTE_DATA);
            String attributeString = "";
            for (String attribute: attributeData) {
                attributeString += attribute + "\n";
            }
            attributeText.setText(attributeString);
        }

    }

    private void setCredentials() {
        String region = myPreference.getString(REGION, "");
        TextView regionText = (TextView) findViewById(R.id.region_text);
        regionText.setText(region);
    }

    /**
     * Starts the BarcodeScanner activity.
     *
     * @param view
     * @see BarcodeScanner
     */
    public void openCamera(View view) {
        Intent intent = new Intent(this, BarcodeScanner.class);
        intent.putExtra(SCAN_BARCODE, "");
        startActivity(intent);
    }

    /**
     * Starts an AWSActivity activity.
     *
     * @param view
     * @see AWSActivity
     */
    public void sendMessage(View view) {

        ConnectivityCheck myCheck = new ConnectivityCheck(this);
        if (myCheck.isNetworkReachableAlertUserIfNot()) {
            Intent AWSIntent = new Intent(this, AWSActivity.class);

            EditText packageIDTextBox = (EditText) findViewById(R.id.edit_message);
            String packageID = packageIDTextBox.getText().toString();


            AWSIntent.putExtra(AWSActivity.INTENT_PARENT_ACTIVITY, MainPage.class);
            AWSIntent.putExtra(AWSActivity.INTENT_PACKAGE_ID, packageID);
            AWSIntent.putExtra(AWSActivity.INTENT_PACKAGE_UTC, Calendar.getInstance().getTime().getTime());
            AWSIntent.putExtra(AWSActivity.INTENT_PACKAGE_ATTRIBUTES, "{\"NERD\" : \"Laura Miller\"}");

            startActivity(AWSIntent);
        }
    }
    public void inputAttributes(View view) {

        Intent attributeIntent = new Intent(this, AttributePage.class);
        if (!attributeData.isEmpty()) {
            attributeIntent.putExtra(ATTRIBUTE_DATA, attributeData);
        }
        startActivityForResult(attributeIntent, RETREIVE_ATTRIBUTES);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETREIVE_ATTRIBUTES) {
            if (resultCode == RESULT_OK) {
                TextView attributeText = (TextView) findViewById(R.id.attribute_text);
                attributeData = data.getStringArrayListExtra(ATTRIBUTE_DATA);
                this.intent.putExtra(MainPage.ATTRIBUTE_DATA, attributeData);
                String attributeString = "";
                for (String attribute: attributeData) {
                    attributeString += attribute + "\n";
                }
                attributeText.setText(attributeString);
            }
        }
    }
}
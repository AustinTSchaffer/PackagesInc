package com.capstone.packagescanner.packagesinc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import java.util.Calendar;

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
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        this.digentIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openCamera(view);
            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts the BarcodeScanner activity.
     *
     * @param view
     * @see BarcodeScanner
     */
    public void openCamera(View view) {
        Intent intent = new Intent(this, BarcodeScanner.class);
        intent.putExtra(this.SCAN_BARCODE, "");
        startActivity(intent);
    }

    /**
     * Starts an AWSActivity activity.
     *
     * @param view
     * @see AWSActivity
     */
    public void sendMessage(View view) {

        Intent intent = new Intent(this, AWSActivity.class);

        EditText packageIDTextBox = (EditText) findViewById(R.id.edit_message);
        String packageID = packageIDTextBox.getText().toString();

        intent.putExtra(AWSActivity.INTENT_PARENT_ACTIVITY, MainPage.class);
        intent.putExtra(AWSActivity.INTENT_PACKAGE_ID, packageID);
        intent.putExtra(AWSActivity.INTENT_PACKAGE_UTC, Calendar.getInstance().getTime().getTime());
        intent.putExtra(AWSActivity.INTENT_PACKAGE_ATTRIBUTES, "{\"NERD\" : \"Laura Miller\"}");

        startActivity(intent);
    }

}
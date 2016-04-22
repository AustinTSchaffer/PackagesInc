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

import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to display the response from the AWS server.
 *
 * @author austi
 * @author camwhe
 * @since 6 April 2016
 *
 * @see AppCompatActivity
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


    private ArrayList<HashMap<String, String>> putItemRequests;


    public static final String INTENT_SERIALIZED_REQUEST_MAP
            = "com.capstone.packagescanner.packagesinc.requestmap";

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

        this.processIntent(getIntent());

        AWSTask task = new AWSTask(getApplicationContext());

        for (Map<String, String> m : putItemRequests) {
            task.execute(m);
        }

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
        if (null == this.putItemRequests) {
            this.putItemRequests = new ArrayList<HashMap<String, String>>();
        }

        int i = 0;
        while (intent.hasExtra(AWSActivity.INTENT_SERIALIZED_REQUEST_MAP + ((i == 0)? "" : i))) {
            this.putItemRequests.add(
                    (HashMap<String, String>)
                            intent.getSerializableExtra(
                                    AWSActivity.INTENT_SERIALIZED_REQUEST_MAP + ((i == 0)? "" : i))
            );
            i += 1;
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
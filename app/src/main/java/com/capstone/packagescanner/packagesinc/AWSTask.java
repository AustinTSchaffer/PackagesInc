package com.capstone.packagescanner.packagesinc;

/**
 * Created by camwhe on 2016-03-23.
 */
import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import java.util.Calendar;

public class AWSTask extends AsyncTask<String, Void, Void> {

    private Context mContext;
    public AWSTask (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        // send DDB mapping set

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                mContext,    /* get the context for the application */
                "us-east-1:cfa64042-b279-4ccb-8c6c-d8bbc98209b7",    /* Identity Pool ID */
                Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

        Package item = new Package();
        item.setBarcode(params[0]);
        Calendar c = Calendar.getInstance();
        item.setUID(c.getTime().toString());

        mapper.save(item);

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onCancelled(java.lang.Object)
     */
    @Override
    protected void onCancelled() {
        //  Auto-generated method stub
        super.onCancelled();
    }

    /**
     * Used to package data for sending data to AWS server.
     * Currently a restrictive implementation, allowing only 2 strings to be sent.
     */
    @DynamoDBTable(tableName = "Basic_Test")
    public class Package {

        /**
         * Default no-args constructor.
         */
        public Package() {
            this.barcode = "";
            this.uid = "";
        }

        /**
         * Value returned from the barcode scanner.
         */
        private String barcode;

        /**
         * Unique identifier for the next table entry.
         */
        private String uid;

        @DynamoDBRangeKey(attributeName = "Barcode")
        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        @DynamoDBHashKey(attributeName = "UID")
        public String getUID() {

            // A.B.C. its as easy as 1.2.3.
            String test = "fuck you git.";
            test = test.toUpperCase();
            System.out.println(test);

            return uid;
        }

        public void setUID(String uid) {
            this.uid = uid;
        }
    }



};
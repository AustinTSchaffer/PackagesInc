package com.capstone.packagescanner.packagesinc;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import java.util.Calendar;

/**
 * AWSTask is an AsyncTask used to transmit data to an AWS DynamoDB.
 *
 * @author camwhe
 * @since 23 March 2016
 *
 * @see AmazonDynamoDBClient
 * @see AsyncTask
 * @see CognitoCachingCredentialsProvider
 * @see DynamoDBMapper
 */
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
        item.setUID(Calendar.getInstance().getTime().toString());
        mapper.save(item);

        return null;
    }

    @Override
    protected void onCancelled() {
        //  Auto-generated method stub
        super.onCancelled();
    }

    /**
     * Used to package data for sending data to a AWS DynamoDB. Class will be processed by
     * a DynamoDBMapper.
     *
     * @see DynamoDBMapper
     */
    @DynamoDBTable(tableName = "Basic_Test")
    public class Package {

        /**
         * Default no-args constructor.
         */
        public Package() {
            this.barcode = "DEFAULT_BARCODE";
            this.uid = "DEFAULT_UID";
        }

        private String barcode;
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
            return uid;
        }

        public void setUID(String uid) {
            this.uid = uid;
        }
    }



};
package com.capstone.packagescanner.packagesinc;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class AWSTask extends AsyncTask<Map<String, String>, Void, List<PutItemResult>> {

    private Context mContext;

    public AWSTask (Context context) {
        mContext = context;
    }

    public static final String PACKAGE_ID_ATT_NAME = "PackageID";
    public static final String UTC_ATT_NAME = "UTC";

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected List<PutItemResult> doInBackground(Map<String, String>... params) {
        // send DDB mapping set

        CognitoCachingCredentialsProvider credentialsProvider =
            new CognitoCachingCredentialsProvider(
                mContext, /* get the context for the application */
                "us-east-1:cfa64042-b279-4ccb-8c6c-d8bbc98209b7", /* Identity Pool ID */
                Regions.US_EAST_1  /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
            );

        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);


        Map<String, AttributeValue> dbPutItemRequest = new HashMap<String, AttributeValue>();
        List<PutItemResult> dbPutItemResults = new ArrayList<PutItemResult>();

        for (Map<String, String> inputSSMap : params) {

            for (String key : inputSSMap.keySet()) {
                dbPutItemRequest.put(key, new AttributeValue(inputSSMap.get(key)));
            }

            if (this.verify(dbPutItemRequest)) {
                dbPutItemResults.add(ddbClient.putItem("Package_Table", dbPutItemRequest));
            }

            dbPutItemRequest.clear();
        }

        return dbPutItemResults;
    }


    /**
     * Verifies that the request contains all of the required keys.
     *
     * @param dbPutItemRequest Put item request to verify.
     * @return True if the request contains a Package ID Attribute, a UTC, and a Local Date Time.
     */
    private boolean verify(Map<String, AttributeValue> dbPutItemRequest) {
        return (null != dbPutItemRequest)
                && dbPutItemRequest.containsKey(AWSTask.PACKAGE_ID_ATT_NAME)
                && dbPutItemRequest.containsKey(AWSTask.UTC_ATT_NAME);
    }

    @Override
    protected void onCancelled() {
        //  Auto-generated method stub
        super.onCancelled();
    }

};
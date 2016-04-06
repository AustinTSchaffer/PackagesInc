package com.capstone.packagescanner.packagesinc;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import java.util.ArrayList;
import java.util.List;


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

    private List<Package> packages;

    public AWSTask (Context context) {
        mContext = context;
        this.packages = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... params) {
        // send DDB mapping set

        CognitoCachingCredentialsProvider credentialsProvider =
            new CognitoCachingCredentialsProvider(
                mContext, /* get the context for the application */
                "us-east-1:cfa64042-b279-4ccb-8c6c-d8bbc98209b7", /* Identity Pool ID */
                Regions.US_EAST_1  /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
            );

        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

        for (Package p : packages) mapper.save(p);
        packages.clear();

        return null;
    }

    @Override
    protected void onCancelled() {
        //  Auto-generated method stub
        super.onCancelled();
    }


    /**
     * Adds a package to the list of packages to save to AWS DynamoDB.
     *
     * @param awsPackage Package object to save.
     */
    public void addPackage(Package awsPackage) {
        this.packages.add(awsPackage);
    }

};
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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import org.w3c.dom.Attr;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Package item = new Package()
            .setPackageID(params[0])
            .setDate(Calendar.getInstance().getTime())
            .addAttribute("DSU")
            .addAttribute("Recieved")
            .addAttribute("Austin Schaffer");

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

    @DynamoDBTable(tableName = "Package_Table")
    public class Package {

        Package() {
            this.attributes = new HashSet<>();
        }

        private String packageID;
        private Date utc;
        private Set<String> attributes;


        @DynamoDBHashKey(attributeName = "PackageID")
        public String getPackageID() {
            return this.packageID;
        }

        @DynamoDBRangeKey(attributeName = "UTC")
        public Long getUTC() {
            return utc.getTime();
        }

        @DynamoDBAttribute(attributeName = "DateTime")
        public String getLocalTimeString() {
            return utc.toString();
        }

        @DynamoDBAttribute(attributeName = "Attributes")
        public Set<String> getAttributes() {
            return this.attributes;
        }


        public Package setPackageID(String packageID) {
            this.packageID = packageID;
            return this;
        }

        public Package setDate(Date utc) {
            this.utc = utc;
            return this;
        }

        public Package addAttribute(String attribute) {
            this.attributes.add(attribute);
            return this;
        }
    }



};
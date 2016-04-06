package com.capstone.packagescanner.packagesinc;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Object used to send information to an AWS DynamoDB. Implemented to work with a DynamoDBMapper.
 *
 * @author austi
 * @author camwhe
 * @since 5 March 2016
 *
 * @see DynamoDBTable
 * @see com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
 */
@DynamoDBTable(tableName = "Package_Table")
public class Package {

    Package() { }

    private String packageID = "";
    private Date utc = new Date(0);
    private String attributesJSON = "{}";

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
    public String getAttributes() {
        return this.attributesJSON;
    }


    public Package setPackageID(String packageID) {
        this.packageID = packageID;
        return this;
    }

    public Package setUTC(Date utc) {
        this.utc.setTime(utc.getTime());
        return this;
    }

    public Package setAttributesJSON(String attribute) {
        this.attributesJSON = attribute;
        return this;
    }
}
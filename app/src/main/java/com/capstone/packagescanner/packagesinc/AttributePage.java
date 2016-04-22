package com.capstone.packagescanner.packagesinc;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Cameron on 4/22/2016.
 */
public class AttributePage extends ListActivity {

    CustomAdapter myAdapter;
    private SharedPreferences myPreference;
    private ArrayList<String> attributeData;
    private String templateString;
    private ArrayList<ResourceItem> resourceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_page);

        myAdapter = null;
        myPreference= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (this.getIntent().hasExtra(MainPage.ATTRIBUTE_DATA)) {
            attributeData = this.getIntent().getStringArrayListExtra(MainPage.ATTRIBUTE_DATA);
            for (String attribute: attributeData) {
                String[] attributeArray = attribute.split(": ");
                resourceList.add(new ResourceItem(attributeArray[0], attributeArray[1]));
            }
        }
        else {

            templateString = myPreference.getString("ATTRIBUTE_DEFAULTS", "User Description");

            for (String title : templateString.split(" ")) {
                resourceList.add(new ResourceItem(title));
            }
        }

        //  create custom adapter (myAdapter) and pass in your collection of JSON objects for it to draw from for display
        myAdapter = new CustomAdapter(this, AttributePage.this, R.layout.listview_row_layout, resourceList);
        // bind data adapter to this listview,  setListAdapter(myAdapter);
        this.setListAdapter(myAdapter);

        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myAdapter.add(new ResourceItem());
                myAdapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton subFab = (FloatingActionButton) findViewById(R.id.subFab);
        subFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (myAdapter.getCount() > 1) {
                    myAdapter.remove(myAdapter.getItem(myAdapter.getCount() - 1));
                }
            }
        });

        FloatingActionButton saveFab = (FloatingActionButton) findViewById(R.id.saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                attributeData = new ArrayList<String>();
                String attributeDefaults = "";
                for (int i = 0; i < myAdapter.getCount(); i++) {
                    ResourceItem item = myAdapter.getItem(i);
                    attributeData.add(item.toString());
                    if (item.isChecked()) {
                        if(i != 0) {
                            attributeDefaults += " ";
                        }
                        attributeDefaults += item.getResourceTitle();
                    }
                }

                if (attributeDefaults.length() > 0) {
                    myPreference.edit().putString("ATTRIBUTE_DEFAULTS", attributeDefaults).commit();
                }

                Intent intent=new Intent();
                intent.putExtra(MainPage.ATTRIBUTE_DATA, attributeData);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}

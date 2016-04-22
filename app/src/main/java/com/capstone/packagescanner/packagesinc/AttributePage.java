package com.capstone.packagescanner.packagesinc;

import android.app.ListActivity;
import android.os.Bundle;
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
    private ArrayList<String> templateList;
    private String templateString;
    private ArrayList<ResourceItem> resourceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_page);

        myAdapter = null;

        templateList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.default_no_attributes)));
        templateString = "User Description";

        for (String title : templateString.split(" ")) {
            resourceList.add(new ResourceItem(title));
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
                if (myAdapter.getCount() > 1 ) {
                    myAdapter.remove(myAdapter.getItem(myAdapter.getCount() - 1));
                }
            }
        });

        FloatingActionButton saveFab = (FloatingActionButton) findViewById(R.id.saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

}

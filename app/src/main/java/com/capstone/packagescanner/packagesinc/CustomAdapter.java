package com.capstone.packagescanner.packagesinc;

/**
 * Created by Cameron on 4/21/2016.
 */

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.ExecutionException;
        import java.util.zip.Inflater;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

class CustomAdapter extends ArrayAdapter<ResourceItem> {

    private static final String TAG = "CustomAdapter";
    private List<ResourceItem> data;
    Context context;
    private LayoutInflater inflater;
    private Activity activity;

    /**
     * @author lynn
     * class that holds pointers to all the views in listview_row_layout.xml
     * you can hold additional data as well, for instance I held a copy of the
     * the images pictureID (its filename) so that I could match the picture
     * to the bike when sorting so
     */
    private static class ViewHolder {
        CheckBox toSave;
        EditText Title;
        EditText Value;
    }

    // define your custom adapter, pass in your collection of bikedata
    public CustomAdapter(Activity activity, Context context, int textViewResourceId,
                         List<ResourceItem> data) {
        super(context, textViewResourceId, data );

        this.activity = activity;
        // notice again with the getSystemService, its used in a lot of Android
        // APIs
        if (activity != null)
            this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //yes thats a reference to same object
        //but I dont want to allocate too much memory
        this.data = data;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // fill this out, use both Viewholder and convertview

        final ViewHolder holder;

        if( convertView == null) {
            convertView = inflater.inflate((R.layout.listview_row_layout), parent, false);
        }
        holder = new ViewHolder();
        //holder.imageView1 = (ImageView)convertView.findViewById(R.id.imageView1);
        holder.toSave = (CheckBox)convertView.findViewById(R.id.Send);
        holder.Title = (EditText)convertView.findViewById(R.id.Title);
        holder.Value = (EditText)convertView.findViewById(R.id.Value);
        //holder.Description = (TextView)convertView.findViewById(R.id.Description);
        //holder.pictureID = data.get(position).getPicture();

        holder.toSave.setChecked(true);
        data.get(position).setChecked(true);
        holder.Title.setText(data.get(position).getResourceTitle());
        holder.Value.setText(data.get(position).getResourceValue());

        holder.Title.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (position < data.size()) {
                        data.get(position).setResourceTitle(((EditText) v).getText().toString());
                    }
                }

            }
        });
        holder.Value.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(position < data.size()) {
                        data.get(position).setResourceValue(((EditText) v).getText().toString());
                    }
                }

            }
        });
        holder.toSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data.get(position).setChecked(isChecked);
            }
        });

        //give it to listview for display
        return convertView;
    }

    public void setData(List<ResourceItem> aData) {
        // set data to new loaded
        data = aData;
    }

    public ArrayList<ResourceItem> getData() {
        return new ArrayList<ResourceItem>(data);
    }
    public boolean isChecked(int position) {
        return data.get(position).isChecked();
    }

    public String stringOf(int position) {
        return data.get(position).toString();
    }

    public String valueOf(int position) {
        return data.get(position).getResourceValue();
    }
}
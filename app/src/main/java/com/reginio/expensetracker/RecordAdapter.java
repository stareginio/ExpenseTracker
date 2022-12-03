package com.reginio.expensetracker;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.*;

public class RecordAdapter extends BaseAdapter implements OnEditRecordSpnrSelect {

    private OnEditRecordSpnrSelect oe;
    private ArrayList<HashMap<String,String>> recordsList;
    private Context context;
    private String modify;
    private LayoutInflater layoutInflater;

    String LOG_TAG = "Debugging";

    public RecordAdapter(Context context, ArrayList<HashMap<String,String>> data,
                         OnEditRecordSpnrSelect listener) {
        this.context = context;
        recordsList = data;
        this.oe = listener;

        layoutInflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return recordsList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_record, null);

            viewHolder.recordIconIv = (ImageView) convertView.findViewById(R.id.ivRecordIcon);
            viewHolder.recordCategoryTv = (TextView) convertView.findViewById(R.id.tvRecordCategory);
            viewHolder.recordNameTv = (TextView) convertView.findViewById(R.id.tvRecordName);
            viewHolder.recordAmountTv = (TextView) convertView.findViewById(R.id.tvRecordAmount);
            viewHolder.recordSpnr = (Spinner) convertView.findViewById(R.id.spnrRecord);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // NTS: set image based on category
        viewHolder.recordCategoryTv.setText((recordsList.get(position)).get("category"));
        viewHolder.recordNameTv.setText((recordsList.get(position)).get("name"));
        viewHolder.recordAmountTv.setText((recordsList.get(position)).get("amount"));

        // populate the spinner
        populateRecordSpinner(viewHolder.recordSpnr);

        // spinner listener
        ViewHolder finalViewHolder = viewHolder;
        (viewHolder.recordSpnr).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int positionInSpinner, long id) {

                modify = finalViewHolder.recordSpnr.getSelectedItem().toString();
                oe.onItemSelectedListener(modify, position);

                Log.d(LOG_TAG, "RecordAdapter =========================");
                Log.d(LOG_TAG, "modify: " + modify);
                Log.d(LOG_TAG, "position: " + position);

                // hide selection text
                ((TextView)view).setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return convertView;
    }

    @Override
    public void onItemSelectedListener(String modify, int id) {}

    private static class ViewHolder {
        public ImageView recordIconIv;
        public TextView recordCategoryTv, recordNameTv, recordAmountTv;
        public Spinner recordSpnr;
    }

    private void populateRecordSpinner(Spinner spnr) {
        // create a list of items for the spinner
        String[] items = new String[]{"" ,"Edit", "Delete"};

        // create an adapter to describe how the items are displayed
        ArrayAdapter<String> spnrAdapter = new ArrayAdapter<>(
                context,
                R.layout.spinner,
                items
        );

        // set the spinner adapter to the previously created one
        spnr.setAdapter(spnrAdapter);
    }
}

/*
References
Customized Adapter (1): https://stackoverflow.com/questions/27248695/get-the-position-in-a-listview-of-a-spinner-and-get-its-selected-value
Customized Adapter (2): https://stackoverflow.com/questions/39342086/update-listview-based-on-spinner-selection-in-android
Customized Adapter (3): https://www.digitalocean.com/community/tutorials/android-listview-with-custom-adapter-example-tutorial
Customized Adapter (4): https://www.geeksforgeeks.org/custom-arrayadapter-with-listview-in-android/
Hide spinner selection text: https://stackoverflow.com/questions/4931186/hide-text-of-android-spinner
Send string from custom Adapter to Activity (1): https://stackoverflow.com/questions/48048977/send-string-from-custom-adapter-to-activity
Send string from custom Adapter to Activity (2): https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities
Send string from custom Adapter to Activity (3): https://stackoverflow.com/questions/17417839/how-to-pass-values-from-adapter-to-activity
*/
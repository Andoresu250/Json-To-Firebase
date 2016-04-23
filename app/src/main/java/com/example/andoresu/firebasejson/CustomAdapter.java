package com.example.andoresu.firebasejson;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.List;

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    private final Context context;
    private List<DataEntry> listEntries;
    private List<String> ids;
    private Firebase rootRef;

    public CustomAdapter(Context context, List<DataEntry> listEntries, List<String> ids, Firebase rootRef) {
        this.context = context;
        this.listEntries = listEntries;
        this.ids = ids;
        this.rootRef = rootRef;
    }

    @Override
    public int getCount() {
        return listEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return listEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DataEntry entry = listEntries.get(position);

        final String key = ids.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, null);
        }

        TextView f1 = (TextView) convertView.findViewById(R.id.tvField1);
        TextView f2 = (TextView) convertView.findViewById(R.id.tvField2);

        f1.setText(String.valueOf(entry.getFistName()));
        f2.setText(String.valueOf(entry.getLastName()));

        ImageButton deleteBtn = (ImageButton) convertView.findViewById(R.id.btnRemove);
        deleteBtn.setFocusableInTouchMode(false);
        deleteBtn.setFocusable(false);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootRef.child(key).removeValue();
            }
        });
        ImageButton detailsBtn = (ImageButton) convertView.findViewById(R.id.btnDetails);
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("data", entry);
                context.startActivity(i);
            }
        });

        convertView.setTag(entry);

        return convertView;
    }

    @Override
    public void onClick(View v) {
    }
}

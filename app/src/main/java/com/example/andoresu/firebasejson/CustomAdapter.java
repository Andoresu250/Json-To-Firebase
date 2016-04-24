package com.example.andoresu.firebasejson;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        f1.setText(String.valueOf(entry.getFirstName()));
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

        ImageButton editBtn = (ImageButton) convertView.findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog editUser = new Dialog(context);
                editUser.setContentView(R.layout.edit_dialog);
                editUser.setTitle("Editar Usuario");
                editUser.show();

                final EditText name = (EditText) editUser.findViewById(R.id.editName);
                name.setText(entry.getFirstName());
                final EditText lastName = (EditText) editUser.findViewById(R.id.editLastName);
                lastName.setText(entry.getLastName());

                final Spinner genders = (Spinner) editUser.findViewById(R.id.editGender);

                List<String> gendersArray = new ArrayList<String>();
                gendersArray.add("male");
                gendersArray.add("female");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, gendersArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genders.setAdapter(adapter);

                genders.setSelection(adapter.getPosition(entry.getGender()));

                Button cancelBtn = (Button) editUser.findViewById(R.id.cancelBtn);
                Button savelBtn = (Button) editUser.findViewById(R.id.saveUserBtn);

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editUser.dismiss();
                    }
                });

                savelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newName = name.getText().toString();
                        String newLastName = lastName.getText().toString();
                        String newGender = genders.getSelectedItem().toString();

                        Firebase myUser = rootRef.child(key);
                        Map<String, Object> user = new HashMap<String, Object>();
                        user.put("firstName",newName);
                        user.put("gender",newGender);
                        user.put("lastName",newLastName);
                        myUser.updateChildren(user);
                        editUser.dismiss();
                    }
                });

            }
        });

        convertView.setTag(entry);

        return convertView;
    }

    @Override
    public void onClick(View v) {
    }
}

package com.example.projetmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    private Context context;

    public PersonAdapter(Context context, List<Person> data) {
        super(context, 0, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Person person = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_row, parent, false);
        }

        TextView textViewPersonName = convertView.findViewById(R.id.person_name);
        TextView textViewPersonPhone = convertView.findViewById(R.id.person_phone);

        textViewPersonName.setText(person.getName());
        textViewPersonPhone.setText(person.getPhone());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleItemClick(person);
            }
        });

        return convertView;
    }

    private void handleItemClick(Person person) {
        Intent intent = new Intent(context, CallActivity.class);
        intent.putExtra("personName", person.getName());
        intent.putExtra("personPhone", person.getPhone());
        context.startActivity(intent);
    }
}




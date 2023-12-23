package com.example.projetmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Person> personsList;
    private MyDatabaseHelper db;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDatabaseHelper(MainActivity.this);
        personsList = new ArrayList<>();

        ImageButton ajouter = findViewById(R.id.imageButton);
        ListView liste = findViewById(R.id.listePersonnes);
        liste.setAdapter(adapter);
        EditText Nom = findViewById(R.id.nominput);
        EditText tel = findViewById(R.id.telinput);

        // Initialize the adapter
        adapter = new PersonAdapter(this, personsList);

        // Set the adapter to the ListView
        liste.setAdapter(adapter);

        // Load data from the database
        DisplayData();

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Nom.getText().toString().trim();
                String phone = tel.getText().toString().trim();
                db.addPerson(name, phone);
                personsList.add(new Person(name, phone));
                adapter.notifyDataSetChanged();
                Nom.setText("");
                tel.setText("");
            }
        });
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person selectedPerson = personsList.get(position);

                handleListItemClick(selectedPerson);
            }
        });
    }



    void DisplayData() {
        personsList.clear();
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "There is no data...", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                personsList.add(new Person(name, phone));
            }
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }
    private void handleListItemClick(Person selectedPerson) {
        // Create an Intent to start the CallActivity
        Intent intent = new Intent(this, CallActivity.class);
        // Put the person's data in the Intent
        intent.putExtra("personName", selectedPerson.getName());
        intent.putExtra("personPhone", selectedPerson.getPhone());
        // Start the CallActivity
        startActivity(intent);
    }
}


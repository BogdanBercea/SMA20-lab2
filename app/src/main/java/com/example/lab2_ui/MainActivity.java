package com.example.lab2_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String SHARE_TAG = "SHARE";
    EditText eText;

    Button bClick;
    Button bShare;
    Button bSearch;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eText = (EditText) findViewById(R.id.eName);
        bClick = (Button) findViewById(R.id.bClick);
        bShare = (Button) findViewById(R.id.bShare);
        bSearch = (Button) findViewById(R.id.bSearch);
        spinner = (Spinner) findViewById(R.id.color_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        bClick.setTextColor(Color.RED);
                        break;

                    case 1:
                        bClick.setTextColor(Color.BLUE);
                        break;

                    case 2:
                        bClick.setTextColor(Color.GREEN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = eText.getText().toString();

                if(text.equals("")){
                    Toast.makeText(MainActivity.this, "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(SHARE_TAG, text);
                    intent.setType("text/plain");

                    Intent chooser = Intent.createChooser(intent, "SHARE");

                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(chooser);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No Apps", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = eText.getText().toString();

                if(text.equals("")){
                    Toast.makeText(MainActivity.this, "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else{
                    Uri uri = Uri.parse("http://www.google.com/search?q=" + text);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

            }
        });

        bClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = eText.getText().toString();
                AlertDialog alertDialog = popup(text);
                alertDialog.show();
            }
        });
    }

    public AlertDialog popup(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("This is the title");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
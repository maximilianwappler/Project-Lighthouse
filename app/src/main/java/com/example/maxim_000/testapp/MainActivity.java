package com.example.maxim_000.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    boolean checked;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sb = new StringBuilder();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Diese Funktion ist noch nicht verfügbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //open the Settings activity
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the SettingsActivity button */
    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks at one of the checkboxes */
    public void onCheckboxClicked(View view) {

        // Check for savedata and load it
        loadSavedata();

        // Perform action for clicked Checkbox
        performAction(view);
    }

    public void loadSavedata() {
        // Check for savedata and load it
        FileInputStream in = null;
        try {
            in = openFileInput("project_lighthouse_savedata");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sb = new StringBuilder();
        String line;
        if (in != null){
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void performAction(View view){
        // Is the view now checked?
        checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked and perform action
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked) {
                    ((TextView) findViewById(R.id.textView2)).setText("Steckdose 1 an. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdose1an", sb.toString());
                }else {
                    ((TextView) findViewById(R.id.textView2)).setText("Steckdose 1 aus. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdose1aus", sb.toString());
                    ((CheckBox) findViewById(R.id.checkBox4)).setChecked(false);
                }break;
            case R.id.checkBox2:
                if (checked) {
                    ((TextView) findViewById(R.id.textView2)).setText("Steckdose 2 an. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdose2an", sb.toString());
                }else {
                    ((TextView) findViewById(R.id.textView2)).setText("Steckdose 2 aus. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdose2aus", sb.toString());
                    ((CheckBox) findViewById(R.id.checkBox4)).setChecked(false);
                }break;
            case R.id.checkBox3:
                if (checked) {
                    ((TextView) findViewById(R.id.textView2)).setText("Steckdose 3 an. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdose3an", sb.toString());
                }else {
                    ((TextView) findViewById(R.id.textView2)).setText("Steckdose 3 aus. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdose3aus", sb.toString());
                    ((CheckBox) findViewById(R.id.checkBox4)).setChecked(false);
                }break;
            case R.id.checkBox4:
                if (checked) {
                    ((TextView) findViewById(R.id.textView2)).setText("Alle Steckdosen an. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdoseAan", sb.toString());
                    ((CheckBox) findViewById(R.id.checkBox1)).setChecked(true);
                    ((CheckBox) findViewById(R.id.checkBox2)).setChecked(true);
                    ((CheckBox) findViewById(R.id.checkBox3)).setChecked(true);
                }else {
                    ((TextView) findViewById(R.id.textView2)).setText("Alle Steckdosen aus. IP: " + sb.toString());
                    ConnectToRaspberryPi raspi = new ConnectToRaspberryPi();
                    raspi.execute("steckdoseAaus", sb.toString());
                    ((CheckBox) findViewById(R.id.checkBox1)).setChecked(false);
                    ((CheckBox) findViewById(R.id.checkBox2)).setChecked(false);
                    ((CheckBox) findViewById(R.id.checkBox3)).setChecked(false);
                }break;
        }
        if (((CheckBox) findViewById(R.id.checkBox1)).isChecked() && ((CheckBox) findViewById(R.id.checkBox2)).isChecked() && ((CheckBox) findViewById(R.id.checkBox3)).isChecked()){
            ((CheckBox) findViewById(R.id.checkBox4)).setChecked(true);
        }
    }
}

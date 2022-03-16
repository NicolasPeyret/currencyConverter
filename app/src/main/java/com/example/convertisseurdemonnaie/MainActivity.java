package com.example.convertisseurdemonnaie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    int spinnerPosition1;
    int spinnerPosition2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemInSpinners();

        ImageView imgView = findViewById(R.id.imageView1);
        registerForContextMenu(imgView);

        //-------------------------------------------- BDD -----------------------------------------


        MonnaieBDD monnaieBdd = new MonnaieBDD(this);
        Monnaie monnaie = new Monnaie("Euros", 1.358);
        monnaieBdd.open();
        monnaieBdd.insertMonnaie(monnaie);
        monnaieBdd.close();

    }

        //------------------------------------------------------------------------------------------

    private void addItemInSpinners() {


        String[] spinnerAdapter = {"Livre", "Euro", "Dirham", "Yen", "Francs CFA", "Dollars US"};
        Spinner spinnerDeviseEntree = findViewById(R.id.spinner1);
        Spinner spinnerDeviseSortie = findViewById(R.id.spinner2);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerAdapter);
        spinnerDeviseEntree.setAdapter(adapter);
        spinnerDeviseSortie.setAdapter(adapter);

        Integer position1 = getPreferences(MODE_PRIVATE).getInt("spinner1", 0);
        Integer position2 = getPreferences(MODE_PRIVATE).getInt("spinner2", 0);

        spinnerDeviseEntree.setSelection(position1);
        spinnerDeviseSortie.setSelection(position2);

    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public void click_convert(View v) {

        Spinner spinnerDeviseEntree = findViewById(R.id.spinner1);
        Spinner spinnerDeviseSortie = findViewById(R.id.spinner2);

        spinnerPosition1 = spinnerDeviseEntree.getSelectedItemPosition();
        spinnerPosition2 = spinnerDeviseSortie.getSelectedItemPosition();

        EditText convertValue = findViewById(R.id.editText1);

        String msg = null;
        if (spinnerDeviseEntree.getSelectedItem().equals(spinnerDeviseSortie.getSelectedItem())) {
            Log.i("MainActivity", "R.string.same_devise");
            Toast.makeText(getBaseContext(), R.string.same_devise
                    ,
                    Toast.LENGTH_LONG).show();
        } else if (convertValue.getText().toString().equals("") || convertValue.getText().toString().equals(".")) {
            Log.i("MainActivity", "R.string.value_empty");
            Toast.makeText(getBaseContext(), R.string.value_empty
                    ,
                    Toast.LENGTH_LONG).show();
        } else {

            //recupérer les valeurs
            String deviseEntree = spinnerDeviseEntree.getSelectedItem().toString();
            String deviseSortie = spinnerDeviseSortie.getSelectedItem().toString();

            double moneyValue = Double.parseDouble(convertValue.getText().toString());


            //utilisation de la class Convert
            Convert convert = new Convert();
            double resultConvert = convert.convertir(deviseEntree, deviseSortie, moneyValue);


            DecimalFormat df = new DecimalFormat("#.##");

            String newIn = df.format(resultConvert).replace(",", ".");
            String newsOut = df.format(resultConvert).replace(",", ".");

            msg = newIn + " " + deviseEntree + " = " + newsOut + " " + deviseSortie;

            Intent intent = new Intent(this, page_2.class);
            intent.putExtra("strOfConvertion", msg);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("spinner1", spinnerPosition1);
        editor.putInt("spinner2", spinnerPosition2);
        editor.apply();

    }

    public void click_invert(View v) {

        Spinner spinnerDeviseEntree = findViewById(R.id.spinner1);
        Spinner spinnerDeviseSortie = findViewById(R.id.spinner2);


        if (spinnerDeviseEntree.getSelectedItem().equals(spinnerDeviseSortie.getSelectedItem())) {

            Log.i("MainActivity", "R.string.same_devise");
            Toast.makeText(getBaseContext(), R.string.same_devise
                    ,
                    Toast.LENGTH_LONG).show();
        } else {

            int spinner1Index = spinnerDeviseEntree.getSelectedItemPosition();
            spinnerDeviseEntree.setSelection(spinnerDeviseSortie.getSelectedItemPosition());
            spinnerDeviseSortie.setSelection(spinner1Index);


            Toast.makeText(getBaseContext(), spinnerDeviseEntree.getSelectedItem() + " <-> " + spinnerDeviseSortie.getSelectedItem()
                    ,
                    Toast.LENGTH_LONG).show();
        }


    }


    public void click_quit(View v) {

        finish();
        System.exit(0);

    }


    @Override
    protected void onStart() {
        super.onStart();
        // L’activité est en cours de lancement. Ensuite le système
        // appelle la méthode onResume() ou onStop().
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // L’activité est en cours de lancement après avoir été
        // stoppée. Ensuite le système appelle la méthode onStart().
    }

    @Override
    protected void onResume() {
        super.onResume();
        // L’activité est lancée (elle est active et a le focus).
        // Ensuite le système appelle la méthode onPause().
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Une autre activité a pris le focus. L’activité est en
        // pause, mais elle reste néanmoins visible (au moins
        // partiellement). Attention : l’activité peut être tuée par
        // le système en cas de manque de mémoire. Ensuite il appelle
        // la méthode onResume() ou onStop().
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Une autre activité a pris le focus. L’activité est stoppée
        // et n’est pas visible. Attention : l’activité peut être tuée
        // par le système en cas de manque de mémoire. Ensuite, le
        // système appelle la méthode onRestart() ou onDestroy().
    }

    @Override
    protected void onDestroy() {
        super.onDestroy(); // L’activité est en cours de suppression.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu2langue:
                Intent changerLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerLangue);
                return true;

            case R.id.menu2param:
                Intent changerParametres = new Intent(Settings.ACTION_SETTINGS);
                startActivity(changerParametres);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choisissez votre option");
        getMenuInflater().inflate(R.menu.menu1, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1langue:
                Intent changerLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerLangue);
                return true;

            case R.id.menu1param:
                Intent changerParametres = new Intent(Settings.ACTION_SETTINGS);
                startActivity(changerParametres);

            case R.id.menu1convert:
                click_convert(item.getActionView());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
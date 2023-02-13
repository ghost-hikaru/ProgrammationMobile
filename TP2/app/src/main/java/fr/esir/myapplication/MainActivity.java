package fr.esir.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    Map<String, String> pop = new HashMap<>();

    //ArrayList<String> departementsQ6 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_departement);

        /*
        departementsQ6.add("Côtes-d'Armor - Bretagne");
        departementsQ6.add("Finistère - Bretagne");
        departementsQ6.add("Ille-et-Vilaine - Bretagne");
        departementsQ6.add("Morbihan - Bretagne");*/

        pop.put("Côtes-d'Armor - Bretagne", "598 814");
        pop.put("Finistère - Bretagne", "909 028");
        pop.put("Ille-et-Vilaine - Bretagne", "1 060 199");
        pop.put("Morbihan - Bretagne", "750 863");

        List<String> departementsName = new ArrayList<String>(pop.keySet());
        ArrayList<String> departementsList = new ArrayList<String>(pop.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, departementsList);

        ListView listView = (ListView) findViewById(R.id.liste);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String departement = departementsName.get(position);
                String population = pop.get(departementsList.get(position));
                //Toast.makeText(getApplicationContext(), departement, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(departement);
                builder.setMessage("Le departement "+departement+" contient "+population+" d'habitants.");
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}

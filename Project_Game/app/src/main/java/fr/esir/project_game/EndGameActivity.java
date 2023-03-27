package fr.esir.project_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EndGameActivity extends Activity {
    Map<String, String> pop = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_endgame);
        pop.put("Côtes-d'Armor - Bretagne", "598 814");
        pop.put("Finistère - Bretagne", "909 028");
        pop.put("Ille-et-Vilaine - Bretagne", "1 060 199");
        pop.put("Morbihan - Bretagne", "750 863");

        List<String> departementsName = new ArrayList<String>(pop.keySet());
        ArrayList<String> departementsList = new ArrayList<String>(pop.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, departementsList);

        ListView listView = (ListView) findViewById(R.id.leaderbord_listview_endgame);
        listView.setAdapter(adapter);

    }
}

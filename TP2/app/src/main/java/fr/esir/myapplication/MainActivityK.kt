package fr.esir.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView


class MainActivityK : Activity() {
    val pop = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste_departement)

        pop["Côtes-d'Armor"] = "598 814"
        pop["Finistère"] = "909 028"
        pop["Ille-et-Vilaine"] = "1 060 199"
        pop["Morbihan"] = "750 863"

        val departementsName = ArrayList(pop.keys)
        val departementsList = ArrayList(pop.keys)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, departementsList)

        val listView = findViewById<ListView>(R.id.liste)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val departement = departementsName[position]
            val population = pop[departementsList[position]]
            val builder = AlertDialog.Builder(this)
            builder.setTitle(departement)
            builder.setMessage("Le departement $departement contient $population d'habitants.")
            builder.setNegativeButton("Cancel") { _, _ -> }

            val dialog = builder.create()
            dialog.show()
        }
    }
}

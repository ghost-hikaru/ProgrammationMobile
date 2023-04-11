import 'package:flutter/material.dart';
import 'package:flutter_map/plugin_api.dart';
import 'package:latlong2/latlong.dart';
import 'package:url_launcher/url_launcher.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ESIR Mobility',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;

  static final List<Widget> _widgetOptions = <Widget>[
    Home(),
    Map(),
    ListHelp()
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: _widgetOptions.elementAt(_selectedIndex),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.map),
            label: 'Map',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.list),
            label: 'List',
          ),
        ],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
      ),
    );
  }
}

class Home extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        child: Column(
      children: [
        Container(
          margin: EdgeInsets.only(top: 20),
          child: Image.asset('assets/logoG.png', width: 200, height: 200),
        ),
        Text(
          'Find the informations you want and get the right contacts !',
          style: TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
          ),
          textAlign: TextAlign.center,
        ),
        SizedBox(height: 20),
        Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10),
            gradient: LinearGradient(
              colors: [Color(0xFF312E81), Color(0xFF701A75)],
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
            ),
          ),
          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 20),
          margin: EdgeInsets.symmetric(horizontal: 16, vertical: 10),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'An english project',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              SizedBox(height: 15),
              Text(
                'Made by students of the ESIR engineering school, the goal of this project is to help students of the ESIR engineering school to find a destination abroad for their internationals mobilities. The project is still in development, so it is not yet complete. We hope that this project will help you in your search for an internship abroad.',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ],
          ),
        ),
        SizedBox(height: 16),
        Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10),
            gradient: LinearGradient(
              colors: [Color(0xFF312E81), Color(0xFF701A75)],
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
            ),
          ),
          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 20),
          margin: EdgeInsets.only(top: 16, left: 16, right: 16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'A lack of informations about internship and international contacts',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              SizedBox(height: 15),
              Text(
                'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ],
          ),
        ),
        SizedBox(height: 16),
        Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10),
            gradient: LinearGradient(
              colors: [Color(0xFF312E81), Color(0xFF701A75)],
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
            ),
          ),
          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 20),
          margin: EdgeInsets.only(top: 16, left: 16, right: 16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Interractive Map',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              SizedBox(height: 15),
              Text(
                'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ],
          ),
        ),
        SizedBox(height: 16),
        Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10),
            gradient: LinearGradient(
              colors: [Color(0xFF312E81), Color(0xFF701A75)],
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
            ),
          ),
          padding: EdgeInsets.symmetric(horizontal: 16, vertical: 20),
          margin: EdgeInsets.only(top: 16, left: 16, right: 16, bottom: 5),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'By research',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              SizedBox(height: 15),
              Text(
                'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ],
          ),
        ),
      ],
    ));
  }
}

class Map extends StatefulWidget {
  @override
  _MapState createState() => _MapState();
}

class _MapState extends State<Map> {
  final mapController = MapController();

  @override
  Widget build(BuildContext context) {
    var marker = <Marker>[];

    marker = [
      Marker(
        point: LatLng(48.117266, -1.6777926),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://google.com');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.red,
          ),
        ),
      ),
      Marker(
        point: LatLng(40.630514, -8.657516),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://www.ua.pt');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.blue,
          ),
        ),
      ),
      Marker(
        point: LatLng(40.284355, -3.819315),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://www.urjc.es');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.blue,
          ),
        ),
      ),
      Marker(
        point: LatLng(41.389268, 2.115710),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://www.upc.edu/es');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.blue,
          ),
        ),
      ),
      Marker(
        point: LatLng(45.477982, 9.227309),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://www.polimi.it');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.green,
          ),
        ),
      ),
      Marker(
        point: LatLng(43.116302, 12.386801),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://www.unipg.it');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.green,
          ),
        ),
      ),
      Marker(
        point: LatLng(39.221905, 9.112723),
        builder: (ctx) => GestureDetector(
          onTap: () {
            launch('https://www.unica.it/unica/');
          },
          child: Icon(
            Icons.pin_drop,
            color: Colors.green,
          ),
        ),
      ),
    ];
    return Scaffold(
      body: Center(
          child: Column(
        children: [
          Flexible(
              child: FlutterMap(
            options: MapOptions(
              center: LatLng(48.117266, -1.6777926),
              zoom: 5,
            ),
            children: [
              TileLayer(
                urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
                userAgentPackageName: 'com.example.app',
              ),
              MarkerLayer(markers: marker),
            ],
          ))
        ],
      )),
    );
  }
}

class HelpItem {
  final String title;
  final String description;
  final Image imageUrl;
  final String speciality;
  final List<String> budget;
  final String monnaie;
  final List<String> doc;
  final String lvlLangue;
  final String bourse;
  final List<String> culturel;
  final List<String> air;
  final String sport;
  final String temoignage;
  final List<Color> couleur;

  HelpItem({
    required this.title,
    required this.description,
    required this.imageUrl,
    required this.speciality,
    required this.budget,
    required this.monnaie,
    required this.doc,
    required this.lvlLangue,
    required this.bourse,
    required this.culturel,
    required this.air,
    required this.sport,
    required this.temoignage,
    required this.couleur,
  });
}

class ListHelp extends StatelessWidget {
  final List<HelpItem> helpItems = [
    HelpItem(
      title: 'Universidade de Aveiro',
      description: 'Porto - Portugal',
      imageUrl:
          Image.asset('assets/Flag_of_Portugal.png', width: 50, height: 50),
      speciality: '',
      budget: [],
      monnaie: '',
      doc: [],
      lvlLangue: '',
      bourse: '',
      culturel: [],
      air: [],
      sport: '',
      temoignage: '',
      couleur: [],
    ),
    HelpItem(
      title: 'Universidad Juan Carlos',
      description: 'Madrid - Espagna ',
      imageUrl: Image.asset('assets/Flag_of_Spain.png', width: 50, height: 50),
      speciality: '',
      budget: [],
      monnaie: '',
      doc: [],
      lvlLangue: '',
      bourse: '',
      culturel: [],
      air: [],
      sport: '',
      temoignage: '',
      couleur: [],
    ),
    HelpItem(
      title: 'Universidad Politecnica de Cataluña',
      description: 'Barcelona - Espagna',
      imageUrl: Image.asset('assets/Flag_of_Spain.png', width: 50, height: 50),
      speciality: '',
      budget: [],
      monnaie: '',
      doc: [],
      lvlLangue: '',
      bourse: '',
      culturel: [],
      air: [],
      sport: '',
      temoignage: '',
      couleur: [],
    ),
    HelpItem(
      title: 'Politecnico di Milano',
      description: 'Milano - Italy',
      imageUrl: Image.asset('assets/Flag_of_Italy.png', width: 50, height: 50),
      speciality: 'Matériaux, Informatique, Santé',
      budget: [
        "Logement : 600-900 € par mois pour un studio ou une colocation",
        "Transport : Ticket 24h à 4€",
        "Nourriture : environ 120€ par mois"
      ],
      monnaie: 'euro (€)',
      doc: [
        "Carte européenne d'assurance maladie (CEAM)",
        "Carte d'identité ou passeport",
        "Attestation de responsabilité civile et assurance rapatriement"
      ],
      lvlLangue: 'niveau B2 anglais recommandé',
      bourse: 'bourse ERASMUS',
      culturel: [
        "Scala de Milano",
        "Duomo plaza",
        "Villes environnantes : Pavie, Bergame"
      ],
      air: ["Théâtres", "Musées de nuit", "Parc Sempione"],
      sport: "Football, course à pied, tennis",
      temoignage:
          "En plus de faire des soirées et des voyages incroyables avec l'ESN (Erasmus Student Network) j'ai aussi eu l'occasion de travailler en groupe avec d'autres étudiants étrangers qui m'ont énormément apporté d'un point de vu personnel et professionnel. J'ai pu améliorer mon anglais écrit et surtout gagner en confiance à l'oral. Je garde les meilleurs souvenirs de ma vie étudiante là-bas, je suis très reconnaissante d'avoir eu cette expérience à la Politecnico di Milano, une université où on se sent bien rapidement. »",
      couleur: [
        Color.fromRGBO(255, 237, 213, 1),
        Color.fromRGBO(212, 142, 125, 1)
      ],
    ),
    HelpItem(
      title: 'Università degli Studi di Perugiaa',
      description: 'Peruugiaa - Italy',
      imageUrl: Image.asset('assets/Flag_of_Italy.png', width: 50, height: 50),
      speciality: 'Matériaux, Informatique, Santé',
      budget: [
        "Logement : 300-400 € par mois pour un studio ou une colocation",
        "Transport : Ticket 24h à 5,4€",
        "Nourriture : environ 110€ par mois"
      ],
      monnaie: 'euro (€)',
      doc: [
        "Carte européenne d'assurance maladie (CEAM)",
        "Carte d'identité ou passeport",
        "Attestation de responsabilité civile et assurance rapatriement"
      ],
      lvlLangue: 'niveau B2 anglais recommandé',
      bourse: 'bourse ERASMUS',
      culturel: [
        "Le centre historique",
        "Palazzo dei Priori",
        "Piazza IV Novembre & fontaine Maggiore"
      ],
      air: [
        "Piazza IV Novembre & fontaine Maggiore",
        "Piazza IV Novembre & fontaine Maggiore"
      ],
      sport: "Football, course à pied, tennis",
      temoignage: 'Pas encore de témoignage',
      couleur: [
        Color.fromRGBO(255, 237, 213, 1),
        Color.fromRGBO(148, 151, 143, 1)
      ],
    ),
    HelpItem(
        title: 'Università degli Studi di Cagliari',
        description: 'Cagliari - Sardaigne',
        imageUrl:
            Image.asset('assets/Flag_of_Italy.png', width: 50, height: 50),
        speciality: 'Santé',
        budget: [
          "Logement : 400-500 € par mois pour un studio ou une colocation",
          "Transport :abonnement mensuel à 35€/mois",
          "Nourriture : environ 110€ par mois"
        ],
        monnaie: 'euro (€)',
        doc: [
          "Carte européenne d'assurance maladie (CEAM)",
          "Carte d'identité ou passeport",
          "Attestation de responsabilité civile et assurance rapatriement"
        ],
        lvlLangue: 'niveau B2 anglais recommandé',
        bourse: 'bourse ERASMUS',
        culturel: [
          "Bastione San Remy",
          "Torre Dell'elefante",
          "Il Castello (vieux centre)"
        ],
        air: ["La Marina", "Parco di Monte Urpinu"],
        sport: 'Football, course à pied, tennis',
        temoignage: 'Pas encore de témoignage',
        couleur: [
          Color.fromRGBO(255, 237, 213, 1),
          Color.fromRGBO(226, 217, 204, 1)
        ]),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('List of university'),
      ),
      body: ListView.builder(
        itemCount: helpItems.length,
        itemBuilder: (BuildContext context, int index) {
          final HelpItem helpItem = helpItems[index];

          return ListTile(
            /*leading: CircleAvatar(
              child: helpItem.imageUrl,
            ),*/
            leading: helpItem.imageUrl,
            title: Text(helpItem.title),
            subtitle: Text(helpItem.description),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => HelpItemDetails(helpItem: helpItem),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

class HelpItemDetails extends StatelessWidget {
  final HelpItem helpItem;

  HelpItemDetails({required this.helpItem});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(helpItem.title),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Container(
              padding: EdgeInsets.all(16.0),
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.grey.shade300,
                  width: 1.0,
                ),
                borderRadius: BorderRadius.circular(8.0),
              ),
              child: Column(
                children: [
                  Text(
                    "Quelques infos...",
                    style: TextStyle(
                      fontSize: 20.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 16.0),
                  Text(
                    "Spécialités concernées",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    this.helpItem.speciality,
                    style: TextStyle(fontSize: 16.0),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Budget",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 8.0),
                  ListView.builder(
                    shrinkWrap: true,
                    itemCount: this.helpItem.budget.length,
                    itemBuilder: (BuildContext context, int index) {
                      return Text(
                        "• " + this.helpItem.budget[index],
                        style: TextStyle(fontSize: 16.0),
                      );
                    },
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Monnaie",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    this.helpItem.monnaie,
                    style: TextStyle(fontSize: 16.0),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Documents nécessaires",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 8.0),
                  ListView.builder(
                    shrinkWrap: true,
                    itemCount: this.helpItem.doc.length,
                    itemBuilder: (BuildContext context, int index) {
                      return Text(
                        "• " + this.helpItem.doc[index],
                        style: TextStyle(fontSize: 16.0),
                      );
                    },
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Niveau de langue",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    this.helpItem.lvlLangue,
                    style: TextStyle(fontSize: 16.0),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Bourse disponible",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    this.helpItem.bourse,
                    style: TextStyle(fontSize: 16.0),
                  ),
                ],
              ),
            ),
            SizedBox(height: 16.0),
            Container(
              padding: EdgeInsets.all(16.0),
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.grey.shade300,
                  width: 1.0,
                ),
                borderRadius: BorderRadius.circular(8.0),
              ),
              child: Column(
                children: [
                  Text(
                    "Activité",
                    style: TextStyle(
                      fontSize: 20.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 16.0),
                  Text(
                    "Culturelle",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  ListView.builder(
                    shrinkWrap: true,
                    itemCount: this.helpItem.culturel.length,
                    itemBuilder: (BuildContext context, int index) {
                      return Text(
                        "• " + this.helpItem.culturel[index],
                        style: TextStyle(fontSize: 16.0),
                      );
                    },
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Plein air",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 8.0),
                  ListView.builder(
                    shrinkWrap: true,
                    itemCount: this.helpItem.air.length,
                    itemBuilder: (BuildContext context, int index) {
                      return Text(
                        "• " + this.helpItem.air[index],
                        style: TextStyle(fontSize: 16.0),
                      );
                    },
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Sport",
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Text(
                    this.helpItem.sport,
                    style: TextStyle(
                      fontSize: 16.0,
                    ),
                  ),
                ],
              ),
            ),
            SizedBox(height: 16.0),
            Container(
              padding: EdgeInsets.all(16.0),
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.grey.shade300,
                  width: 1.0,
                ),
                borderRadius: BorderRadius.circular(8.0),
              ),
              child: Column(
                children: [
                  Text(
                    "Témoignage",
                    style: TextStyle(
                      fontSize: 20.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 16.0),
                  Text(
                    this.helpItem.temoignage,
                    style: TextStyle(
                      fontSize: 16.0,
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

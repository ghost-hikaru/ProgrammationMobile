import 'dart:ffi';

import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:csv/csv.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class MyAppState extends ChangeNotifier {
  Future<String> getText() async {
    final directory = await getApplicationDocumentsDirectory();
    if (!await Directory('${directory.path}/challenges').exists()) {
      await Directory('${directory.path}/challenges').create(recursive: true);
    }

    final File file = File('${directory.path}/challenges/challenges.csv');
    notifyListeners();
    print("contenue du fichier : " + await file.readAsString());
    return await file.readAsString();
  }
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  Widget build(BuildContext context) {
    String? _fileContent;

    Future<void> _loadFileContent() async {
      final appState = context.read<MyAppState>();
      final content = await appState.getText();
      setState(() {
        _fileContent = content;
      });
    }

    @override
    void initState() {
      super.initState();
      _loadFileContent();
    }

    return Scaffold(
      body: Stack(
        children: [
          Positioned(
            bottom: 16,
            left: 16,
            child: Text(
              _fileContent ?? 'Fichier vide',
              style: const TextStyle(fontSize: 16),
            ),
          ),
          Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                const Text(
                  'Jeux Pair à Pair',
                  style: TextStyle(fontSize: 40, fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: 25),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        // Action when 2 joueurs button pressed
                      },
                      child: const Text('2 joueurs'),
                    ),
                    const SizedBox(width: 75),
                    ElevatedButton(
                      onPressed: () {
                        // Action when 1 joueur button pressed
                      },
                      child: const Text('1 joueur'),
                    ),
                  ],
                ),
                const SizedBox(height: 25),
                ElevatedButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => AddChallengeScreen()),
                    );
                  },
                  child: const Text('Ajouter des défis'),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class AddChallengeScreen extends StatelessWidget {
  final TextEditingController categoryController = TextEditingController();
  final TextEditingController questionController = TextEditingController();
  final TextEditingController answerController = TextEditingController();

  void addChallenge() async {
    final directory = await getApplicationDocumentsDirectory();
    if (!await Directory('${directory.path}/challenges').exists()) {
      await Directory('${directory.path}/challenges').create(recursive: true);
    }
    final File file = File('${directory.path}/challenges/challenges.csv');
    List<List<dynamic>> csvFileContent = [];

    // Vérifie si le fichier existe déjà, sinon crée l'en-tête du fichier
    if (await file.exists()) {
      csvFileContent =
          const CsvToListConverter().convert(await file.readAsString());
    } else {
      csvFileContent.add(['Catégorie', 'Question', 'Réponse']);
    }

    // Ajoute le nouveau défi
    csvFileContent.add([
      categoryController.text,
      questionController.text,
      answerController.text,
    ]);

    // Écrit le contenu du fichier CSV
    String csvData = const ListToCsvConverter().convert(csvFileContent);
    await file.writeAsString(csvData);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const Text(
            "Ajout d'un défi",
            style: TextStyle(fontSize: 40, fontWeight: FontWeight.bold),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 20),
          TextField(
            controller: categoryController,
            decoration: InputDecoration(hintText: 'Catégorie'),
          ),
          const SizedBox(height: 10),
          const Text(
            'Concernant la catégorie, veuillez remplir avec un des champs suivants:\n-Capteurs\n-Mouvements\n-Questions',
            style: TextStyle(fontSize: 14),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 10),
          TextField(
            controller: questionController,
            decoration: InputDecoration(hintText: 'Question'),
          ),
          const SizedBox(height: 10),
          TextField(
            controller: answerController,
            decoration: InputDecoration(hintText: 'Réponse'),
          ),
          const SizedBox(height: 20),
          ElevatedButton(
            onPressed: () async {
              addChallenge();
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => HomeScreen()),
              );
            },
            child: Text('Ajouter le défi'),
          ),
        ],
      ),
    );
  }
}

# TP3 sur le data storage
## Introduction
Durant ce tp, nous allons apprendre à manipuler les fichiers avec AndoidStudio. Le but est de pouvoir créer ainsi que de manipuler des fichiers et de les stockers dans la mémoire interne du téléphone. 
Tout au long de ce TP, nous allons utilisé l'API File fourni par Java. Cette API, nous servira afin de manipuler les différents fichiers.

## Création de fichier

```java
public void createCustomFile(String filenameCustom){
    File file = new File(this.getFilesDir(),filenameCustom+".txt");
    try {
        file.createNewFile();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

## Ecriture dans un fichier

```java
public void write(String message,String filename){
    try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
        fos.write(message.getBytes());
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
```

## Lecture d'un fichier et affichage dans un Layout

```java
public String showContent(String filename){
    try {
        FileInputStream fis = openFileInput(filename);
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = fis.read()) != -1) {
            sb.append((char) ch);
        }
        fis.close();
        return sb.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "Fichier vide";
}
```

## Mini-application

```java

```

## Application de manipulation de fichier

## Conclusion 


## 🙇 Author
#### Mathurin Melvin
- Github: [@Melvin](https://github.com/ghost-hikaru)
- Adresse mail : [melvin.mathurin@etudiant.univ-rennes.f](melvin.mathurin@etudiant.univ-rennes.fr)
#### Voisin Enzo
- Github: [@Enzo](https://github.com/Slonev0)
- Adresse mail : [enzo.voisin@etudiant.univ-rennes.fr](enzo.voisin@etudiant.univ-rennes.fr)
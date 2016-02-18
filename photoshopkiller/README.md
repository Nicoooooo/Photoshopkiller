# Photoshopkiller
Trois commandes: 
- **interest <nom de fichier> :**

Enregistre le graph d'intérrêt de l'image dans le fichier "<nom du fichier>_interest.pgm"
Exemple : "java -jar photoshopkiller.jar interest ex1"

- **rmcols <nom de fichier> <nombre de colonnes> :**

Suprimme N colonnes et enregistre le résultat dans le fichier "<nom du fichier>_m<nombre de colonnes>"
Exemple : "java -jar photoshopkiller.jar rmcols ex1 50"

- **highlight <nom de fichier> <nombre de colonnes> :**

"Surligne" N colonnes et enregistre le résultat dans le fichier "<nom du fichier>_h<nombre de colonnes>"
Exemple : "java -jar photoshopkiller.jar highlight ex1 50"


#Le travail a été réparti de la façon suivante :

writepgm et dijkstra par Yann Bernard
interest, toGraph et le logiciel par Nicolas Gauville

# Photoshopkiller
Trois commandes: 
- **interest <nom de fichier> :**

Enregistre le graph d'intÃ©rrÃªt de l'image dans le fichier "<nom du fichier>_interest.pgm"
Exemple : "java -jar photoshopkiller.jar interest ex1"

- **rmcols <nom de fichier> <nombre de colonnes> :**

Suprimme N colonnes et enregistre le rÃ©sultat dans le fichier "<nom du fichier>_rmcols-<nombre de colonnes>"
Exemple : "java -jar photoshopkiller.jar rmcols ex1 50"

- **addcols <nom de fichier> <nombre de colonnes> :**

Ajoute N colonnes et enregistre le rÃ©sultat dans le fichier "<nom du fichier>_addcols-<nombre de colonnes>"
Exemple : "java -jar photoshopkiller.jar addcols ex1 50"

#Le travail à été réparti de la façon suivante :

writepgm, dijkstra, rm2cols par Yann
interest, toGraph, addcols et le logiciel par Nicolas

Morgan GRT   
Jean-Baptiste MCU   

****************************************

# Aide à la décision

Le projet ne dispose pas d'interface graphique   

## Compilation :

Pour compiler vous pouvez utilisez:
- Ant : ant
- Gradle : gradle build
- Maven : mvn install

## Lancement :

Le projet est composé de trois parties distinctes :
- La représentation des objets et contraintes (avec Backtrack)
- La plannification
- L'extraction de connaissances   

L'éxecution est par défaut "La représentation des objets et contraintes (avec Backtrack)"   

Au lancement il est possible de choisir la partie, certaines requierts des arguments additionnels :
- en premier, choix de la partie:
	-- 0 : utilisation de l'extraction
	-- 1 : utilisation du planning
	-- 2 : utilisation du backtrack
- en second choix:
	-- une fréquence (int) si utilisation de extraction
- en troisième choix:
	-- une confiance (float) si utilisation de extraction
- en quatrième choix:
	-- le nom d'une base de données (ex : exemple_db) pris par défaut en .csv et stockée dans extraction/database 
	-- ou 0 pour utiliser l'exemple du cours (A B C D E)

Pour lancer selon la méthode de compilation
- Ant : java -jar dist/aide-decision-1.0.jar
- Gradle : java -jar build/libs/aide-decision-1.0.jar
- Maven : java -jar target/aide-decision-1.0.jar

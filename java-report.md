# Project report

## tech stack:
  UDP: Pour le contact discovery on a utilisé UDP qui permet de faire du broadcast et ne nécessite pas d'établissement de connexion.
  TCP: Employé pour les échanges de messages entre les utilisateurs, TCP garantit la fiabilité et l’ordre des messages.
  SQLite : Cet outil est très important pour concevoir une base de données embarquée. Elle est très facile à utiliser. Nous avons créé différentes tables avec le langage sql.
  Java Swing : Nous avons utilisé Swing pour faire l’interface graphique. C’est un outil très simple à utiliser.

## testing policy:
  On a testé les méthodes de la classe User.
  Pour le contact discovery, les tests ont porté sur la détection de nouveaux utilisateurs et vérifier s'ils ne sont plus actifs.
  Pour l'échange de message nous avons vérifié si les messages étaient corrects.
  Nous avons fait les tests après avoir implémenté les fonctions.
  L'intégration continue a été mise en place vers la fin du projet. (je ne pense pas qu’il faut le dire même si on n’a pas bien appliqué cette partie, cela pourrait nous porter préjudice)
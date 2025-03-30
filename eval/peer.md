---
Evaluated team:
 - FORNARO
 - ZOUGBA
Evaluators: 
 - DOGANIS
 - CLEREMBAUX
...

# Functionalities
  
## discovery
<!-- Connection and contact discovery phase -->
grade: B
comments: Lorsque 2 utilisateurs se connectent en même temps, contact discovery échoue. Période de 10 secondes : un peu long, sinon le reste marche

## Presentation of contacts/error 
<!-- How readable and user friendly is the presented output. -->
grade: B
comments: presentation: compréhensible mais pourrait avoir des améliorations.



# Quality

## README
<!-- Presence and completeness of the README -->
grade: A
comments: RAS


## maven
<!-- Does the project compile and run based on the `pom.xml` file only. -->
grade: B
comments: ça a marché après avoir ajouté une ligne liée à la version de maven dans le pom.xml


## tests
<!-- Proportion of the code covered by the tests. Are the tests sensible, correct and well organized -->
grade: D
comments: n'oubliez pas les tests


## repository
<!-- Structure of the git repository (directories, gitignore, presence of undesired files) -->
grade: A
comments: RAS


## structure
<!-- Structure of the code into sensible and independent packages -->
grade: B
comments: Ok pour les classes, mais mettez les dans des packages


## reliability
<!-- Thread safety and error handling -->
grade: C
comments: Mettez des méthodes synchronized + propagez et gérez les erreurs en plus de les afficher.


## style
<!-- Variable naming, indentation, comments, ... -->
grade: B
comments: mettez des commentaires, sinon le reste c'est très bien.
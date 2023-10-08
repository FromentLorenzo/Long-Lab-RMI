# Long-Lab-RMI
Project RMI S7 vote


## Introduction
Pour notre projet de simulation de vote basé sur la technologie RMI (Remote Method Invocation), nous avons dû prendre des décisions de conception importantes pour atteindre nos objectifs. Dans ce rapport, nous allons expliquer ces choix et notre démarche.

## Structure du Projet
Nous avons naturellement opté pour la séparation du client et du serveur en deux projets distincts. Chacun de ces projets possède son propre espace, mais ils partagent un "package contrat" contenant des classes, des interfaces et des méthodes communes. Cette approche permet de maintenir la cohérence des méthodes tout en préservant la confidentialité des données sensibles, notamment les votes individuels, qui doivent demeurer anonymes. Nous avons choisis de définir la fin du vote au moment où tout le monde a voté, peu importe le temps mis. Cela permet à chaque votant de donner son avis. Bien sûr pour cela nous partons du principe que chaque votant joue le jeu et vote à un moment

## Package Contrat
Le "package contrat" joue un rôle essentiel dans notre architecture globale. Il regroupe les classes et les interfaces nécessaires pour faciliter la communication entre le client et le serveur. Cette stratégie assure la cohérence des méthodes tout en garantissant la confidentialité des données. De plus, il nous a permis de modéliser les entités essentielles du système, à savoir les électeurs et les candidats.

## Utilisation du Diagramme de Séquences
Pour la réalisation de notre projet, nous avons principalement tiré parti du diagramme de séquences qui nous avait été fourni. Nous avons donc procédé à l'aide d'un VoteMateriel au travers duquel nous transmettons un One Time Password ainsi qu'un stub private permettant au votant d'accéder au serveur privé qui permet de voter. Pour faire ceci, nous avons fait une classe VoteMaterial pour pouvoir créer cet objet.

## Difficultés
Durant le développement nous avons rencontré quelques soucis comme l'oubli de throw exception dans les interfaces nous bloquant sur des erreurs mais aussi le choix de l'architecture permettant de séparer le côté public du côté privé

## Mode d'emploi
Pour utiliser notre application vous devez lancer le main du server, puis lancer le main du client.
Après cela, vous aurez le choix en tant que voter:

-ca : afficher les candidats

-au : vous authentifier

-vo : voter (seulement après authentification)

-re : voir les résultats finaux (seulement quand tout le monde a voté)


### Elements à savoir:

-Vous pouvez revoter

-Pour finir le vote rapidement nous avons mis en commentaire tous les étudiants sauf 2 (student Number: 1 pw:A et StudentNumber: 2 pw:B)


Bon vote!

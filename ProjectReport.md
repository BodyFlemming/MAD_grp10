# MyMons

<title>MyMons</title>

| App project 10 |
| -------------- |

| Navn                    | Studienummer |
| ----------------------- | ------------ |
| Lasse Borring Petersen  | 202208165    |
| Benjamin Harboe Strunge | 202209864    |
| Esben Inglev            | 202210050    |

## App Vision

The app allows users to collect and view rare Pokémon, which you can boast about and show off to your friends.
Users can catch random Pokémon through the app, store them in their collection, and see where each Pokémon was caught on a map. The app combines fun with technology by integrating Firebase Authentication for secure user accounts, Firestore for storing user and Pokémon data, and PokéAPI for fetching Pokémon information such as names, images, and types.

Each user has a personal profile where they can view account details and manage their collection. The app aims to create a light, gamified experience where users can feel a sense of progression and ownership over their Pokémon while exploring real-world locations through GPS.

<div style="page-break-before: always;"></div>

## User-stories

- Auth:
  - As a user, I want to be able to navigate to a sign-up page, and create an account with a username and an icon.
  - As a user, I would like to be able to navigate to a login page, and enter my email and password to login.
- Catch:
  - As a user, I want to navigate to the "Catch mon" page and obtain a randomly generated mon
- Collection:
  - As a user, I want to view all pokémon that I have caught in an overview.
  - As a user, I want to be able to click on a specific pokémon, to view details about it.
  - As a user, I want to view on a map where a specific pokémon was obtained.
- Profile:
  - As a user I want to navigate to the "My profile" page where I can view all the details about my account

### Use Case Diagrams

<div style="page-break-before: always;"></div>

## UI Diagrams

### Profile page and collection page

<img src="img/projectreport/UserDashboard.png" alt="Dashboard" width="200"/>
<img src="img/projectreport/MyMons.png" alt="Pokemons" width="200"/>

### Pokemon catch page

<img src="img/projectreport/CathMon.png" alt="Catch" width="200"/>
<img src="img/projectreport/CathcedMon.png" alt="Catch done" width="200"/>

### Pokemon details and catch location

<img src="img/projectreport/MonDetails.png" alt="Details" width="200"/>
<img src="img/projectreport/CaughtLocation.png" alt="Map" width="200"/>

<!-- <div style="page-break-before: always;"></div>
<div style="page-break-before: always;"></div> -->

## Component Diagrams

## Conclusion

The app has fullfilled all of the user stories. A user can signup, login, catch a mon and view their mons. The app UI in itself is pretty simple, but the software architecture of the app has been the challenge. With multiple external dependencies the team has obtained significant knowledge on how to connect a kotlin app to these sources. This has been done via configurable files such as the gradle files, .toml file and the manifest. This has been done for dependencies such as Firestore, Authentication and OpenStreetMap. For external REST based APIs the team has learned about Ktor  

has obtained both significant insight in the projects configurable files (Gradle, toml, manifest) and how to requests calls   


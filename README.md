# Doctor Appointment Scheduling App – Frontend (Kotlin Android)

Cette application Android permet aux patients et aux médecins de gérer de façon intuitive les rendez-vous médicaux, prescriptions et rappels, tout en assurant une expérience hors-ligne fluide.

## Fonctionnalités clés

- **Inscription & connexion** : via email/mot de passe ou Google.
- **Recherche de médecins** : par spécialité, nom, ou localisation (Google Maps intégré).
- **Gestion des rendez-vous** : prise, modification, annulation et réception de notifications.
- **Check-in QR code** : scan du QR code pour valider l’arrivée du patient au cabinet.
- **Gestion des prescriptions** : visualisation, téléchargement PDF, historique complet.
- **Mode hors-ligne** : accès aux rendez-vous et prescriptions même sans connexion, synchronisation automatique à la reconnexion.
- **Notifications push** : rappels pour les rendez-vous, disponibilité des prescriptions, etc.

## Prérequis

- Android Studio (Giraffe+ recommandé)
- Kotlin 1.8+
- Connexion à l’API backend Django (voir [README backend](../doctor-appointment-backend/README.md))

## Installation

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/<votre_organisation>/doctor-appointment-android.git
   cd doctor-appointment-android
   ```

2. Ouvrez le projet dans Android Studio.

3. Renseignez l’URL de l’API backend dans les fichiers de configuration (`app/src/main/res/values/strings.xml` ou fichier de config dédié).

4. Générez et lancez l’application sur un émulateur ou appareil Android.

## Technologies principales

- Kotlin, Jetpack Compose/UI XML
- Room (stockage local)
- Retrofit (requêtes HTTP)
- Firebase Cloud Messaging (notifications)
- ZXing (scan QR code)

## Contribution

Suggestions et améliorations sont bienvenues via issues ou pull requests.

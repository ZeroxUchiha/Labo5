# ImageViewer Application - LOG121 Lab #5

## Description
Application Java Swing pour l'affichage d'images avec perspectives multiples. Implémente plusieurs patrons de conception dans une architecture MVC.

## Fonctionnalités
- **Affichage d'images** avec zoom et translation
- **Perspectives multiples** sur une même image
- **Opérations annulables** (undo/redo)
- **Sauvegarde/Chargement** d'état complet
- **Interface intuitive** avec menus et contrôles souris

## Patrons de conception implémentés

### Obligatoires
- **Observer** : Synchronisation modèle-vue
- **Command** : Gestion des opérations annulables
- **Singleton** : Gestionnaire de commandes unique

### Optionnels (Bonus)
- **Strategy** : Stratégies de copier-coller
- **Mediator** : Coordination copier-coller
- **Memento** : Sauvegarde d'état pour redo

## Architecture MVC
```
Modèle
├── ImageModel (gestion images)
└── PerspectiveModel (gestion perspectives)

Vue  
├── ThumbnailView (vignette)
├── PerspectiveView (vue principale)
└── MenuView (interface menus)

Contrôleur
└── PerspectiveController (gestion interactions)
```

## Structure du projet
```
src/
├── ImageViewerApp.java     # Point d'entrée principal
├── ImageModel.java         # Modèle image
├── PerspectiveModel.java   # Modèle perspective  
├── ThumbnailView.java      # Vue vignette
├── PerspectiveView.java    # Vue perspective
├── Observable.java         # Pattern Observer
├── Observer.java           # Interface Observer
├── Serializer.java         # Gestion sérialisation
└── PerspectiveController.java # Contrôleur

docs/
└── class_diagram.puml      # Diagramme de classes PlantUML
```

## Compilation et exécution

### Prérequis
- Java 21+ (JDK)
- IDE compatible ou ligne de commande

### Instructions
```bash
# Compilation
javac -cp . src/*.java

# Exécution
java -cp src ImageViewerApp
```

## Utilisation
1. **Ouvrir une image** : Menu Fichier → Ouvrir image
2. **Zoom** : Molette souris ou menus
3. **Translation** : Glisser-déposer souris
4. **Nouvelle perspective** : Menu Perspective → Nouvelle perspective
5. **Sauvegarder** : Menu Fichier → Sauvegarder
6. **Annuler** : Menu Edition → Défaire

## État du développement

### ✅ Fonctionnel
- Application démarre
- Interface de base
- Structure MVC

### 🚧 En développement  
- Pattern Observer complet
- Gestion souris (zoom/translation)
- Commandes annulables
- Sérialisation

### 📋 À implémenter
- Fonctionnalités bonus
- Tests unitaires
- Documentation complète

## Équipe
- Développement en équipe (LOG121 - ÉTS)
- Architecture collaborative avec patterns

---
*Application développée dans le cadre du cours LOG121 - École de technologie supérieure*
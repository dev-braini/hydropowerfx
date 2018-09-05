# OOP2 Programmierprojekt 2Ibb/2iCbb FS18

## Bearbeitet von
 - Markus Winter

## Zusatzfeatures
 - Überarbeitung des UI
 - Styling der gesamten Applikation via CSS
 - Willkommens-Screen
 - Enabling/Disabling aller Buttons und Views
 - Undo/Redo auf "Speichern-Level"
 - bewährte Programmstruktur -> klare Trennung und unabhängige UI-Elemente
 - Custom Controls aus der CUIE-Klasse
 - Zusätzliche Views
    - "Map": Anzeige des Standorts auf Google Maps
    - "Grouped": Gruppierte Ansicht nach Gewässer und Leistungsumfang
    - "Time": Animierte Ansicht eines Zeitstrahls der Kraftwerke (beta)
 - Animation beim Wechsel einer View
 - Rückfrage-System beim Löschen eines Kraftwerks und beim Verlassen des Programms ohne zu speichern
 - Anzeige des Bildes im Kraftwerk-Header
 - Anzeige des Kantonskürzels mittels Kantonswappen
 - Editierbare Tabelle
 - Input-Validierung bei Double und Int Feldern (falsche Eingabe wird unterbunden)
 - Eigenes Applikations-Icon
 - Minimale JUnit Tests
 - Keyboard-Shortcuts
    - Save         = Ctrl + S
    - Add          = Ctrl + +
    - Remove       = Ctrl + -
    - Undo         = Ctrl + Z
    - Redo         = Ctrl + Y
    - View Text    = Ctrl + 1
    - View Map     = Ctrl + 2
    - View Grouped = Ctrl + 3
    - View Time    = Ctrl + 4

## Custom Controls (x2)
 - **WaterQuantity** von
    - Valentina Giampa ( valentina.giampa@students.fhnw.ch )
    - Alessandro Calcagno  ( alessandro.calcagno@students.fhnw.ch )
 - **SwissLocation** von
    - Benjamin Denzler (benjamin.denzler@students.fhnw.ch)
    - ACHTUNG: Benötigt "heap size" von mind. 2048MB

## Abgabe
- Mittwoch, 6.6.2018, 18:00 Uhr
- Die Abgabe erfolgt durch ein "Push" auf den Master-Branch in Ihrem GitHub-Repository.

## Aufgabe: HydroPowerFX

Implementieren Sie eine Applikation auf Basis JavaFX gemäss der Aufgabenbeschreibung auf dem AD. 

Die dort vorhandene Beschreibung ist massgebend, hier die wichtigsten Punkte:
 - Benutzen Sie zur Umsetzung die vorgegebene Struktur des "Application-Template".
 - Die Verwendung von SceneBuilder und FXML ist nicht erlaubt.
 - Für eine 4.0 müssen folgende Basis-Features implementiert sein
   - Einlesen der Daten
   - Abspeichern der Änderungen
   - Darstellen aller Wasserkraftwerke in Tabelle/Liste 
   - Editor-Bereich
     - Editor-Bereich arbeitet stets auf dem in der Tabelle selektierten Wasserkraftwerk
     - Änderungen führen zu *unmittelbarem* Update der Tabelle und des Headers
     - Änderung von ‘Leistung (MW)’ sowie 'Kanton' führt zu einem Update des Footer-Bereichs
   - Header-Bereich 
     - Darstellung Name, Standort, Kanton, maximaler Leistung, Jahr der Inbetriebnahme
     - einfaches Styling via CSS
   - Footer-Bereich
     - Informationen zu den einzelnen Kantonen (Name des Kantons, Gesamtleistung aller Kraftwerke, Anzahl Kraftwerke)
     - diese Informationen werden immer aktuell gehalten
   - Layout mit SplitPane
   - sinnvolles Resizing-Verhalten
   - Anlegen eines neuen Wasserkraftwerks mit Update des Footer-Bereichs
   - Löschen bestehender Einträge mit Update des Footer-Bereichs
   - Programmstruktur
     - zwei Layer für Presentation-Model und View 

# 🏥 NHPlus - Schulprojekt

## Übersicht:
Die Gesundheitsverwaltungssoftware ist eine Anwendung, die Ärzten und Pflegekräften dabei hilft, Patienten und deren Behandlungen effizient zu verwalten. Die Software bietet verschiedene Funktionen, darunter die Anzeige und Bearbeitung von Patientendaten, die Zuweisung von Pflegern zu Behandlungen, die Sperrung von Patientendaten und automatische Datenbereinigung nach Datenschutzrichtlinien.

## Installation:
Um die Gesundheitsverwaltungssoftware zu verwenden, folgen Sie bitte diesen Schritten:
1. Laden Sie die Software von Github herunter.
2. Entpacken Sie die heruntergeladene Datei in einen Ordner Ihrer Wahl.
3. Öffnen Sie die Anwendung durch Doppelklick auf die ausführbare Datei.

## Anleitung:
1. Beim Start der Anwendung werden Sie zur Anmeldung aufgefordert. Geben Sie Ihren Benutzernamen und Ihr Passwort ein.
2. Nach erfolgreicher Anmeldung können Sie auf die verschiedenen Funktionen der Software zugreifen, je nach Ihren Zugriffsrechten.
3. Verwenden Sie die Benutzeroberfläche, um Patientendaten anzuzeigen, Behandlungen zu erstellen, Pfleger zuzuweisen und weitere Aktionen durchzuführen.

> ###  🚧 Technische Daten
> Benutzername: ``NHPlus``
> Passwort: ``Password12345!``

---

## Testfälle:

### ✅ 1. Erfolgreiche Anmeldung mit Benutzername und Passwort
- **Vorbedingung:** Die Anmeldeseite der Anwendung ist geöffnet.
- **Schritte:**
> 1. Geben Sie Ihren Benutzernamen und Ihr Passwort in die entsprechenden Felder ein.
> 2. Klicken Sie auf den Anmelde-Button.

🎯 **Erwartetes Ergebnis:** Die Anmeldung ist erfolgreich, und die Standardseite wird angezeigt.

---

### ❌ 2. Fehlschlagende Anmeldung mit Benutzername und Passwort
- **Vorbedingung:** Die Anmeldeseite der Anwendung ist geöffnet.
- **Schritte:**
> 1. Geben Sie Ihren Benutzernamen und ein falsches Passwort in die entsprechenden Felder ein.
> 2. Klicken Sie auf den Anmelde-Button.

🎯 **Erwartetes Ergebnis:** Die Anmeldung ist nicht erfolgreich, und das Anmeldefeld wird weiterhin angezeigt.

---

### 👩🏻‍⚕️ 3. Pfleger eintragen / bearbeiten oder löschen
- **Vorbedingung:** Die Standardseite wird angezeigt.
- **Schritte:**
> 1. Klicke auf den Pfleger/innen-Button an der Seite. 
> 2. Gib die Pflegerdaten in die drei Felder Vorname, Nachname und Telefonnummer ein und klicke anschließend auf "Hinzufügen". 
> 3. Doppelklicke auf eines der Felder: Vorname, Nachname oder Telefon, um diese entsprechend anzupassen.
> 4. Wähle einen Pfleger aus, indem du auf diesen klickst, und klicke auf "Löschen", um diesen zu entfernen.

🎯 **Erwartetes Ergebnis:** Der Pfleger wird erfolgreich hinzugefügt, bearbeitet oder gelöscht, und die entsprechenden Änderungen werden auf der Seite angezeigt.

---

### ✏️ 4. Entfernen des Labels "Vermögensstand" aus den Patientendaten
- **Vorbedingung:** Die Patientendaten sind in der Ansicht geöffnet.
- **Schritte:**
> 1. Überprüfen Sie, ob das Label "Vermögensstand" in den Patientendaten vorhanden ist.

🎯 **Erwartetes Ergebnis:** Das Label "Vermögensstand" wurde aus den Patientendaten entfernt.

---

### 📝 5. Exportieren von Patientendaten als PDF
# ``🚨 NICHT GEMACHT``
- **Vorbedingung:** Die Patientendaten sind in der Anwendung geöffnet.
- **Schritte:**
> 1. Klicken Sie auf den Export-Button, um den Export der Patientendaten als PDF zu starten.

🎯 **Erwartetes Ergebnis:** Die Patientendaten werden erfolgreich als PDF exportiert.

---

### 📝 6. Extra Aufgabe: Dark / White Modus
# ``🚨 NICHT GEMACHT``
- **Vorbedingung:** Die Standardseite wird angezeigt.
- **Schritte:**
> 1. Klicken Sie auf den Theme-Switch-Button, um das Design von schwarz auf weiß oder umgekehrt umzustellen.

🎯 **Erwartetes Ergebnis:** Die Farbpalette der Benutzeroberfläche ändert sich in Schwarz oder Weiß.

---

### 📅 7. Extra Aufgabe: Termin Kalender
- **Vorbedingung:** Die Standardseite wird angezeigt und du muss mit einem Pfleger Account angemeldet sein.
- **Schritte:**
> 1. Klicken Sie auf den Terminkalender-Button, um die Ansicht der Behandlungen für den heutigen Tag zu öffnen.

🎯 **Erwartetes Ergebnis:** Termine werden dem Datum entsprechend angezeigt.

---

## 🫨 Gruppen Aufteilung
> Einzel Bewertung!


|           Aufgaben            |     Can      |  Shawkat   |
|:-----------------------------:|:------------:|:----------:|
|         Pfleger Modul         |      ✅       |            |
|   Authentifizierung System    |      ✅       |            |
| Entfernung des Vermögensstand |      ✅       |            |
|        Löschanpassung         |              |     ✅      |
|          PDF-Export           |              |     ❌      |
|       Dark White Modus        |              |     ❌      |
|        Termin Kalender        |      ✅       |            |
|    Merge Konflikt Behebung    |      ✅       |            |
|    Minor Updates / Fixing     |      ✅       |            |
|      Zeilen geschrieben       | +2224 / -511 | +250~/ -30 |

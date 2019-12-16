#Example Menu
Dieses Spigot Plugin zeigt beispielhaft wie ein Menü umgesetzt werden kann.

Ich möchte direkt vorweg nehmen, dass vieles verbessert werden kann! __Bitte erstellt Issues oder Merge requests und weist auf Fehler und Verbesserungsvorschläge hin.__
Ich habe wenig Erfahrung in der Entwicklung von Minecraft Plugins und in Java generell.

Es gibt noch ein paar Bugs und vermutlich ist die Nutzung der Fileconfiguration nicht optimal.
Das Updaten und Laden erscheint mir auch noch nicht wirklich richtig.
Die Befehlssyntax ist etwas seltsam. Ich habe dies Plugin in ein paar Stunden, ohne vorher ein klares Design zu haben, geschrieben und werde es evtl. in Zukunft als Lehrobjekt nutzen und Stück für Stück weiter verbessern und Strukturieren.

Dieses Plugin entstand _nebenher_, als ich ein anderes Plugin auf Spigots API Version 1.14 aktualisieren wollte.
 Ich habe mich schon öfters über die Art, wie Menüs identifiziert wurden, gewundert und war mir sicher das es eine bessere Lösung dafür gibt.
```java
public void onInventoryClick(InventoryClickEvent e) {
  [...]
  if(e.getView().getTitle().equalsIgnoreCase("§6Menuname"))){
    [...]
  }
}
```
oder 
```java
if (inventory.getName().equals(myInventory.getName())) { // The inventory is our custom Inventory
```
Diese Identifikation an einem Attribut fest zu machen, das von Spielern geändert werden kann wollte ich nicht akzeptieren.

Nach ein wenig Recherche in JavaDocs _(RTFM!)_ bin ich auf den `InventoryHolder` gekommen. Irgendwann bin ich auf dieses kleine Tutorial gefunden: https://www.spigotmc.org/wiki/creating-a-gui-inventory/

Beim erstellen eines `Invetory`:
```java
Inventory inv = Bukkit.createInventory(<Owner>,<Size>,<Title>);
```
kann einfach an der Stelle `<Owner>` ein `InventoryHolder` angegeben werden.
Danach trifft folgender Check zu:
```java
if(e.getInventory().getHolder() instanceof <Unsere Klasse die InventoryHolder implementiert>))
```
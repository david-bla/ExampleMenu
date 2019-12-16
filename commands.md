#Commands

#### `/menu [<menuId>]`
Öffnet das Menü mit der `menuId` oder wenn nichts angegeben wird mit der `menuId 0`. Dieses ist bereits in der `menu.yml` als Beispiel hinterlegt.

---
#### `/menu new`
Erstellt ein neues Menü. Die `meniId` wird automatisch erstellt indem die nächste freie benutzt wird.
- Berechtigung erforderlich: `menu.new`
---

#### `/menu <menuId> add <slodId>`
Fügt einen neuen Menupunkt an <slotId> hinzu. Beginnend ab 0, der Slot muss frei sein.

---

#### `/menu <menuId> del [<slotId>}`
Löscht ein ganzes Menü, oder bei angabe eines Menüounkts nur den Punkt.
- evtl. Confirm Befehl nachrüsten.

---

>Um ein Menü zu ändern ist die Berechtgung `menu.edit.<menuId>` notwendig.
>

#### `/menu <menuId> set title <title>`
Setzt den Titel eines Menüs. Es können Farbcodes mit `&` genutzt werden.

---
#### `/menu <menuId> set size <size>`
Setzt die Größe eines Menüs. Die Größe eines Menüs muss ein vielfaches von 9 sein. Der Wert muss zwischen 9 und 54 liegen.

---
#### `/menu <menuId> set viewPermission <Berechtigung>`
Ändert die Berechtigungen die notwenig sind um ein Menü zu öffnen.

---
#### `/menu <menuId> set editPermission <Berechtigung>`
Ändert die Berechtigungen die notwenig sind um ein Menü zu ändern.
 
---
#### `/menu <menuId> set <slotNr> title <title>`
Setzt den Namen des Items in <slotNr>

---
#### `/menu <menuId> set <slotNr> subtitle <zeile> <title>`
Setzt den Subtitel (Lore) des Items in <slotNr>. Es muss jeweils die Zeile (von 0 bis 2) angegeben werden.

---
#### `/menu <menuId> set <slotNr> permission <zeile> <Berechtigung>`
Die Berechtigung damit ein Menüpunkt benutzt werden darf.

---
#### `/menu <menuId> set <slotNr> action <MenuAction>`
Setzt die Aktion welche beim Klick auf einen Menüpunkt ausgeführt wird.
Die Aktionen habe ich in `MenuAction` definiert.
- `Log` _ist nur zum test - sendet eine Nachricht auf der Konsole_
- `Teleport` _speichert die Position des Spielers beim Setzen und teleportiert bei Klick dann an diese Stelle.
- `Item` _hinterlegt das Item in der Hand und gibt Spielern beim Klick ein Item
- `SendPlayerMessage <nachricht>` _sendet eine Nachricht an den Spieler der auf den Button klickt_
- `BroadcastMessage <nachricht>` _sendet eine Nachricht an alle Spieler wenn jemand auf den MenuPunkt klickt._
- `PlayerCommand <command>` _lässt den Spieler der auf den Button drückt den Command ausführen_ 
- `ConsoleCommand <command>` _führt bei Klick ein in der Console aus, %p wird mit dem Spielernamen ersetzt_

---
#### `/menu <menuId> set <slotId> material <material>`
Setzt das Item eines Menüpunkts

---
#### `/menu <menuId> set <slotId> slot <newSlotId>`
Verschiebt einen Menüpunkt, ist der Zielslot belegt werden die Positionen getauscht.
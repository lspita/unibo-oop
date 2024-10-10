# Principio di incapsulamento

* Analizzare la classe `oop.lab03.encapsulation.Student`
* È una versione modificata della classe vista la settimana scorsa
    - I campi sono ora privati: un client della classe non sarà mai influenzato dalla modifica di meri aspetti implementativi
    - Sono stati introdotti dei selettori per ottenere le proprietà dell'oggetto: `getName()`, `getSurname()`, ...
* Si modifichino similmente le classi `Calculator` e `Smartphone` contenute nel package `oop.lab03.encapsulation`:
    - Si rendano privati tutti i campi
    - Si introducano selettori opportuni per recuperare o modificare le proprietà di interesse

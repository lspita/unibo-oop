# Esercizio sugli scope (ambiti di visibilità) di variabili all'interno di metodi e classi

Si prega di *non modificare*:

* I nomi dei metodi
* Il numero ed il tipo di parametri in ingresso ai metodi
* Il tipo di ritorno dei metodi

## FASE 1

1. Analizzare il sorgente `Scopes.java`
2. Prima di compilare e lanciare il programma riflettere sul comportamento dei metodi della classe e provare a prevedere l'output di ogni singola stampa, scrivendo su un foglio il risultato previsto.

## FASE 2

1. Si compili programma
  * NOTA: la classe `TestScopesMain` usa (ovvero dipende da) la classe `Scopes`.
    Dunque, durante la compilazione di `TestScopesMain`, il compilatore effettua 
    controlli per verificare che la classe `Scopes` sia usata in modo corretto.
    A questo fine, il compilatore deve controllare il bytecode di `Scopes`.
  * NOTA: Essendo presenti due file sorgenti,
    il compilatore `javac` andrebbe concettualmente invocato due volte,
    una volta per ciascun file (ALTERNATIVA 1).
  * Alternativamente,
    è possibile invocare `javac` passando entrambi i file come parametro (ALTERNATIVA 2),
    oppure utilizzare la wildcard `*` seguita dall'estensione del file (ovvero `*.java`) per compilare tutti i file in una sola passata (ALTERNATIVA 3).
  * Si provi a compilare usando tutte e tre le metodologie descritte.
    Fra una compilazione e la successiva si eliminino i file con estensione `.class` generati dal compilatore.
  * NOTA: versioni recenti di `javac` sono in grado di risolvere automaticamente le
    dipendenze in un sorgente qualora questi e tali dipendenze si trovino nella stessa cartella 
    dove si è eseguito `javac`. In questo caso particolare, può essere sufficiente compilare direttamente e solo `TestScopesMain` per avere successo (ed ottenere anche il file `.class` risultante dalla compilazione di `Scopes.java`). Tuttavia, questa risoluzione delle dipendenze NON funziona in generale. Si provi a compilare dalla cartella padre per verificare la cosa (avendo l'accortezza di eliminare eventuali file `.class` precedentemente ottenuti), 
    oppure a compilare fornendo opzione `-sourcepath ..`.
2. Si esegua il programma, e se ne confronti l'output con quanto previsto al punto (2):
  se l'output è differente, si cerchi di capirne il motivo (si contatti il docente o il tutor in caso di dubbi).
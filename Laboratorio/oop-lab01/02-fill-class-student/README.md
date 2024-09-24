# Completamento di una classe mediante aggiunta/implementazione di membri (campi, metodi)

Si prega di *non modificare*:

* I nomi dei metodi
* Il tipo di ritorno dei metodi

## FASE 1

1. Si completi la classe `Student` aggiungendo:
  * Campi:
    - `String name`
    - `String surname`
    - `int id` (rappresentante la matricola)
    - `int matriculationYear`
  * Metodi:
    - `void build(String, String, int, int)`:
      inizializza l'oggetto di tipo `Student` con i parametri forniti in input. Essi sono, in ordine:
      - il nome,
      - il cognome,
      - la matricola, e
      - l'anno di immatricolazione
    - `void printStudentInfo()`:
      stampa in standard output le informazioni relative allo studente, ossia:
      - il suo nome e cognome,
      - il numero di matricola, e
      - l'anno di immatricolazione)
2. Si compili la classe `Student` verificando che non vi siano problemi di compilazione

## FASE 2

1. Si modifichi il metodo `main` della classe `TestStudent` come segue:
  - Creare dei nuovi oggetti relativi agli studenti:
      - Nome: Alex, Cognome: Balducci, id: 1015, anno di immatricolazione: 2019
      - Nome: Angel, Cognome: Bianchi, id: 1016, anno di immatricolazione: 2018
      - Nome: Andrea, Cognome: Bracci, id: 1017, anno di immatricolazione: 2017
2. Si stampino a video le informazioni relative a ciascuno studente usando l'apposito metodo.
3. Si compilino i sorgenti, e si esegua `TestStudent`

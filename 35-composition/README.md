# Composizione

* Si osservi il banale sistema software contenente un sistema per la gestione di appelli d'esame universitari. Gli studenti possono vedere gli esami esistenti, registrarsi, visualizzare i voti degli esami conclusi, visualizzare le statistiche relative ai propri esami. I docenti possono creare nuovi appelli, inserire voti per gli appelli passati, visualizzare statistiche relative agli appelli.
* Si osservi l'interfaccia `User`, che definisce un generico utente del sistema, e la classe `Student` che la implementa.
* Si implementi la classe `Professor`, che implementa `User` e modella un generico docente
    - Campi:
        * `int id`
        * `String name`
        * `String surname`
        * `String password` (nota, in realtà le password non dovrebbero mai esser tenute in chiaro)
        * `String[] courses`
    - Un solo costruttore che richieda i dati necessari ad inizializzare la classe
    - Metodi:
        * `public void replaceCourse(String, int)`: sostituisce il corso all'indice `index` con quello fornito in input
        * `public void replaceAllCourses(String[])`: sostituisce tutti i corsi tenuti dal docente
* Si implementi la classe `Exam`, che modella un generico appello d'esame.
    - Campi:
        * `int id`
        * `int maxStudents`
        * `int registeredStudents`
        * `String courseName`
        * `Professor professor`
        * `ExamRoom room` (fornita nei sorgenti)
        * `Student[] students`
    - Un solo costruttore che richieda i dati necessari ad inizializzare la classe. Inizialmente ci dovranno essere zero studenti registrati.
    - Metodi:
        * Opportuni selettori per le proprietà dell'esame
        * `public void registerStudent(Student student)`: iscrive lo studente all'appello. Qualora si sia raggiunto il limite massimo di studenti per l'appello, non fa nulla.
        * Si implementi `toString()` in modo opportuno, utilizzando la funzione di utilità `Arrays.toString()` per ottenere una rappresentazione formato `String` di un array
    - Nota: è possibile creare l'array di studenti fissandone la dimensione al numero massimo di studenti, ed usando il numero di studenti registrati come indice per sapere in che posizione registrare nuovi studenti.
* Si seguano le linee guida in `Testing` per effettuare il test delle classi `Professor` ed `Exam`

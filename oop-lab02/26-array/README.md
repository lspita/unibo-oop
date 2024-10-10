# Esercizio con array

# FASE 1 - Arrays, le basi

1. Si analizzi il metodo `countOccurencies(int[]. int)` della classe `WorkWithArrays`,
aiutandosi con l'utilità di test `boolean testCountOccurrencies()`
2. Si compili ed esegua la classe
3. Si aggiunga un nuovo test al metodo `testCountOccurrencies()`
4. Si compili e si esegua nuovamente
5. Si completino i seguenti metodi statici nella classe `WorkWithArrays`:
   - `static int[] evenElements(int[] array)`,
   che restituisce un array con i soli elementi in posizione (indice) *pari* dell'array fornito in input,
   ad esempio: `evenElems([1, 2, 3, 4]) => [1, 3]`
   - `static int[] oddElements(int[] array)`,
   che restituisce un array con i soli elementi in posizione (indice) *dispari*
   dell'array fornito in input, ad esempio: `oddElems([1, 2, 3, 4] => [2,4]`
   - `static int mostRecurringElement(int[] array)`,
   che restituisce l'elemento che viene ripetuto più volte all'interno dell'`array`,
   - ad esempio:
     - `mostRecurringElement([1, 2, 1, 3, 4] => 1`,
     - `mostRecurringElement([7,1,5,7,7,9]) => 7`

#### FASE 2 - Riconoscitore di sequenze

1. La classe `SeqRecognizer` implementa un riconoscitore di varie sequenze di caratteri.
Si analizzi il primo esempio già implementato `boolean checkSeq1(int[] array)` che riconosce la sequenza {1}{2|3},
ossia, una qualunque sequenza (anche vuota) di 1 seguita da una qualunque sequenza (anche vuota) di 2 o 3.
Strategia risolutiva in linguaggio naturale:
    1. Finché trovo una sequenza di uno, vado avanti nell’array
    2. Finché trovo dei due o dei tre, vado avanti nell’array
    3. Verifico di essere arrivato in fondo
2. Si scriva il metodo `static boolean checkSeq2(int[] array)`,
che riconosce la sequenza 1{2}3, ossia una occorrenza di 1, 
seguita da una qualunque sequenza (anche vuota) di 2, seguita da un 3.
3. Si scriva il metodo `static boolean checkSeq3(int[] array)`,
che riconosce la sequenza 1{2}3{4}[5],
ossia una occorrenza di 1 , seguita da una qualunque sequenza (anche vuota) di 2, seguita da un 3,
seguita da una qualunque sequenza (anche vuota) di 4,
seguita opzionalmente da un 5
4. Si scriva il metodo `static boolean checkSeq4(int[] array)`,
che riconosce la sequenza [2|3]{4}5

#### FASE 3 - Inversione e duplicazione

Implementare il metodo `static int[] revertUpTo(int[] array, int element)` in `WorkWithArrays`,
che inverte gli elementi di `array` dall'inizio fino alla prima occorrenza dell'elemento `element` *inclusa*.
Gli elementi successivi alla suddetta occorrenza devono rimanere invariati, per esempio `revertUpTo([1,2,3,4,5,6,7], 5) => [5,4,3,2,1,6,7]`

#### FASE 4 - Array di oggetti

Completare la classe `PlayWithSmartphoneArrays`, che lavora sulla classe `Smartphone` fornita,
implementando i seguenti metodi che restituiscono il numero di smartphone che rispettano le preferenze fornite dagli utenti:

1. `static int search(Smartphone[] array, String brand)`
2. `static int search(Smartphone[] array, boolean hasNFC, boolean hasGPS)`
3. `static int search(Smartphone[] array, int nCPUs, int sdSize, boolean has3G)`

#### FASE 5 - Algoritmica su array

1. Implementare `static int[] sortArray(int[] array, boolean isDescending)` all'interno di `WorkWithArrays`,
che ordina l'array fornito in input
(ordinamento crescente/descrescente a seconda del valore del parametro `isDescending`.
    - Si può far riferimento all'algoritmo bubble sort
    - è proibito l'uso del metodo di libreria `Arrays.sort()` :)
2. Implementare `static double computeVariance(double[] array)`,
che calcola la varianza degli elementi di un array

#### FASE 6 - Array circolare

Completare la classe `MyCircularArray` che implementa un array circolare, contenente:
    - Un campo `int[] array`;
    - Costruttore `MyCircularArray(int size)` che crea un array circolare di dimensione `size`;
    - Costruttore `MyCircularArray()` che crea un array circolare di 10 elementi;
    - metodo `void add(int element)`, che aggiunge `element` all'interno di `array` in modo che,
    una volta raggiunta la dimensione massima dell'array,
    i nuovi valori vadano a sovrascrivere quelli precedentemente memorizzati;
    - metodo `void reset()`, che reinizializza a zero tutti gli elementi di `array`.

*Nota*: è possibile aggiungere campi e metodi a piacimento al fine di realizzare quanto richiesto.

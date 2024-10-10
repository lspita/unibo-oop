# Implementare un nuovo sistema da capo

## Shape e circle

* Si definisca *da zero* una nuova interfaccia `it.unibo.shapes.api.Shape` che modella una qualunque figura geometrica bidimensionale, che consenta il calcolo di area e perimetro.
* Si implementi la classe `it.unibo.shapes.impl.Circle`, che implementa `Shape`, e che modella il cerchio. Si faccia particolare attenzione alla scelta dei campi, scegliendo quelli strettamente necessari.
* Si ricorda che la classe `java.lang.Math` ha un campo statico pubblico `PI` che contiene il valore di π. Si ricorda inoltre che il perimetro di un cerchio di raggio r può esser calcolato come 2πr, e che l'area del cerchio è calcolabile come πr².

## Poligoni

* Si crei una nuova interfaccia `it.unibo.shapes.api.Polygon` che estende `Shape`, modellando l'idea di figure geometriche con un numero finito di lati (poligoni). In particolare, l'interfaccia abbia un metodo `int getEdgeCount()` che restituisce il suddetto numero.
* Si implementino le classi `it.unibo.shapes.impl.Square`, `it.unibo.shapes.impl.Rectangle`, e `it.unibo.shapes.impl.Triangle`. Tutte implementano `Polygon`.
* Una volta terminata l'implementazione, si scriva un test `it.unibo.shapes.test.WorkWithShapes` nel quale si creano varie figure geometriche (almeno una per tipo) e se ne stampa l'area ed il perimetro.

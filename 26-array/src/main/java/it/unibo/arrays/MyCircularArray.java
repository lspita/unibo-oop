package it.unibo.arrays;

class MyCircularArray {

    static final int DEF_SIZE = 10;

    int[] array;
    int currentIndex;

    MyCircularArray(int size) {
        this.array = new int[size];
        this.resetIndex();
    }

    MyCircularArray() {
        this(DEF_SIZE);
    }

    void resetIndex() {
        this.currentIndex = 0;
    }

    void add(final int elem) {
        this.array[this.currentIndex] = elem;
        this.currentIndex++;
        if (this.currentIndex == this.array.length) {
            this.resetIndex();
        }
    }

    void reset() {
        for (int i = 0; i < this.array.length; i++) {
            this.array[i] = 0;
        }

        this.resetIndex();
    }

    void printArray() {
        System.out.print("[");
        for (int i = 0; i < this.array.length - 1; i++) {
            System.out.print(this.array[i] + ",");
        }
        System.out.println(this.array[this.array.length - 1] + "]");
    }

    public static void main(final String[] args) {

        // 1) Creare un array circolare di dieci elementi

        // 2) Aggiungere gli elementi da 1 a 10 e stampare il contenuto
        // dell'array circolare

        // 3) Aggiungere gli elementi da 11 a 15 e stampare il contenuto
        // dell'array circolare

        // 4) Invocare il metodo reset

        // 5) Stampare il contenuto dell'array circolare

        // 6) Aggiungere altri elementi a piacere e stampare il contenuto
        // dell'array circolare

        MyCircularArray array = new MyCircularArray();

        for (int i = 1; i <= 10; i++) {
            array.add(i);
        }
        array.printArray();

        for (int i = 11; i <= 15; i++) {
            array.add(i);
        }
        array.printArray();

        array.reset();
        array.printArray();


        for (int i = 16; i <= 30; i++) {
            array.add(i);
        }
        array.printArray();
    }
}

class ComplexNum {
    /*
     * Inserire qui la dichiarazione dei campi della classe
     */

    double re;
    double im;

    void build(double re, double im) {
        /*
         * Inserire qui l'inizializzazione dei campi con i parametri forniti in input
         */

        this.re = re;
        this.im = im;
    }

    boolean equal(ComplexNum num) {
        /*
         * Implementare il metodo in modo che restituisca true se e solo se il
         * numero complesso è uguale al parametro num passato in input
         */

        return this.re == num.re && this.im == num.im;
    }

    void add(ComplexNum num) {
        /*
         * Implementare il metodo in modo che venga aggiunto il numero complesso
         * passato in input - Rif. Appendice A1 (slide)
         */

        this.re += num.re;
        this.im += num.im;
    }

    String toStringRep() {
        /*
         * Implementare il metodo in modo che restituisca una rappresentazione
         * testuale del numero complesso
         */

        String imRep = "";
        if (this.im > 0) {
            imRep = String.format("+%.3fi", this.im);
        } else if (this.im < 0) {
            imRep = String.format("%.3fi", this.im);
        }

        return String.format("%.3f%s", this.re, imRep);
    }
}
class Student {

    // È buona pratica mettere i campi in testa alla classe

    String name;
    String surname;
    int id;
    int matriculationYear;

    void build(String name, String surname, int id, int matriculationYear) {
        /*
         * Completare il corpo del metodo
         */

        this.name = name;
        this.surname = surname;
        this.id = id;
        this.matriculationYear = matriculationYear;
    }

    void printStudentInfo() {
        /*
         * Completare il corpo del metodo
         */

        System.out.println(this.name + " " + this.surname
                + "(id: " + this.id
                + ", matriculation year: " + this.matriculationYear + ")");
    }
}

class TestComplexNumCalculator {
  public static void main(String[] args) {
    /*
     * 1) Testare la classe ComplexNumCalculator con le seguenti operazioni
     * tra numeri complessi:
     *
     * - add(1+2i, 2+3i) = 3+5i
     *
     * - sub(4+5i, 6+7i) = -2-2i
     *
     * - mul(8+2i, 3-i) = 26 - 2i
     *
     * - ... altre a piacere
     *
     * 2) Verificare il corretto valore dei campi nOpDone, lastRes
     *
     * 3) Fare altre prove con operazioni a piacere
     */

    ComplexNumCalculator calculator = new ComplexNumCalculator();
    calculator.build();

    ComplexNum x = new ComplexNum();
    ComplexNum y = new ComplexNum();
    ComplexNum result = null;

    x.build(1, 2);
    y.build(2, 3);
    result = calculator.add(x, y);
    System.out.println(result.toStringRep());

    x.build(4, 5);
    y.build(6, 7);
    result = calculator.sub(x, y);
    System.out.println(result.toStringRep());

    x.build(8, 2);
    y.build(3, -1);
    result = calculator.mul(x, y);
    System.out.println(result.toStringRep());

    x.build(8, 2);
    y.build(3, -1);
    result = calculator.div(x, y);
    System.out.println(result.toStringRep());
  }
}

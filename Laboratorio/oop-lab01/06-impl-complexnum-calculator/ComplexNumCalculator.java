public class ComplexNumCalculator {
    int nOpDone;
    ComplexNum lastRes;

    void build() {
        this.nOpDone = 0;
        this.lastRes = null;
    }

    private ComplexNum saveResult(ComplexNum result) {
        this.lastRes = result;
        return result;
    }

    ComplexNum add(ComplexNum x, ComplexNum y) {
        ComplexNum result = new ComplexNum();
        result.build(x.re + y.re, x.im + y.im);

        return saveResult(result);
    }

    ComplexNum sub(ComplexNum x, ComplexNum y) {
        ComplexNum result = new ComplexNum();
        result.build(x.re - y.re, x.im - y.im);

        return saveResult(result);
    }

    ComplexNum mul(ComplexNum x, ComplexNum y) {
        ComplexNum result = new ComplexNum();
        result.build((x.re * y.re) - (x.im * y.im), (x.im * y.re) + (x.re * y.im));

        return saveResult(result);
    }

    ComplexNum div(ComplexNum x, ComplexNum y) {
        ComplexNum result = new ComplexNum();
        double squareSum = (y.re * y.re) + (y.im * y.im);

        result.build(((x.re * y.re) + (x.im * y.im)) / squareSum,
                ((x.im * y.re) - (x.re * y.im)) / squareSum);

        return saveResult(result);
    }
}

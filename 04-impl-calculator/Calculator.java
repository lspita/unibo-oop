public class Calculator {
    int nOpDone;
    double lastRes;

    void build() {
        this.nOpDone = 0;
        this.lastRes = 0.0;
    }

    private double saveResult(double result) {
        this.lastRes = result;
        return result;
    }

    double add(double x, double y) {
        return this.saveResult(x + y);
    }

    double sub(double x, double y) {
        return this.saveResult(x - y);
    }

    double mul(double x, double y) {
        return this.saveResult(x * y);
    }

    double div(double x, double y) {
        return this.saveResult(x / y);
    }
}

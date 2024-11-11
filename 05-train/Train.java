public class Train {
    int nTotSeats;
    int nFirstClassSeats;
    int nSecondClassSeats;
    int nFirstClassReservedSeats;
    int nSecondClassReservedSeats;

    void build(int nFirstClassSeats, int nSecondClassSeats) {
        this.nFirstClassSeats = nFirstClassSeats;
        this.nSecondClassSeats = nSecondClassSeats;

        this.nTotSeats = this.nFirstClassSeats + this.nSecondClassSeats;
        this.deleteAllReservations();
    }

    void reserveFirstClassSeats(int nSeats) {
        this.nFirstClassReservedSeats += nSeats;
    }

    void reserveSecondClassSeats(int nSeats) {
        this.nSecondClassReservedSeats += nSeats;
    }

    double getTotOccupancyRatio() {
        int totReservedSeats = this.nFirstClassReservedSeats + this.nSecondClassReservedSeats;

        return ((double) totReservedSeats / (double) this.nTotSeats) * 100;
    }

    double getFirstClassOccupancyRatio() {
        return ((double) this.nFirstClassReservedSeats / (double) this.nFirstClassSeats) * 100;
    }

    double getSecondClassOccupancyRatio() {
        return ((double) this.nSecondClassReservedSeats / (double) this.nSecondClassSeats) * 100;
    }

    void deleteAllReservations() {
        this.nFirstClassReservedSeats = 0;
        this.nSecondClassReservedSeats = 0;
    }

    String toStringRepr() {
        return "Total reserved seats: " + this.getTotOccupancyRatio() + "%"
                + "\nFirst class reserved seats: " + this.getFirstClassOccupancyRatio() + "%"
                + "\nSecond class reserved seats: " + this.getSecondClassOccupancyRatio() + "%";
    }
}

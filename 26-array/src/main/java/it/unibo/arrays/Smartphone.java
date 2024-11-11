package it.unibo.arrays;

class Smartphone {

    static final boolean DEF_HAS_GPS = true;
    static final boolean DEF_HAS_3G = true;
    static final boolean DEF_HAS_NFC = true;
    static final int DEF_SD_SIZE = 8192;
    static final int DEF_RAM_SIZE = 1024;
    static final int DEF_N_CPU = 2;

    final int nCPU;
    final int ram;
    final int sdSize;
    final String brand;
    final String model;
    final boolean hasGPS;
    final boolean has3G;
    final boolean hasNFC;

    Smartphone(final int nCPU, final int ram, final int sdSize, final String brand, final String model,
            final boolean hasGPS, final boolean has3G, final boolean hasNFC) {
        this.nCPU = nCPU;
        this.ram = ram;
        this.sdSize = sdSize;
        this.brand = brand;
        this.model = model;
        this.hasGPS = hasGPS;
        this.has3G = has3G;
        this.hasNFC = hasNFC;
    }

    Smartphone(final String brand, final String model) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, DEF_HAS_GPS, DEF_HAS_3G, DEF_HAS_NFC);
    }

    Smartphone(final String brand, final String model, final boolean hasNFC) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, DEF_HAS_GPS, DEF_HAS_3G, hasNFC);
    }

    Smartphone(final String brand, final String model, final boolean hasGPS, final boolean has3G) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, hasGPS, has3G, DEF_HAS_NFC);
    }

    Smartphone(final String brand, final String model, final int sdSize) {
        this(DEF_N_CPU, DEF_RAM_SIZE, sdSize, brand, model, DEF_HAS_GPS, DEF_HAS_3G, DEF_HAS_NFC);
    }

    int getCPUs() {
        return this.nCPU;
    }

    int getRam() {
        return this.ram;
    }

    int getSdSize() {
        return this.sdSize;
    }

    String getBrand() {
        return this.brand;
    }

    String getModel() {
        return this.model;
    }

    boolean hasGPS() {
        return this.hasGPS;
    }

    boolean has3G() {
        return this.has3G;
    }

    boolean hasNFC() {
        return this.hasNFC;
    }

    void printStringRep() {
        System.out.println("Smartphone info:");
        System.out.println("-n CPU(s): " + this.nCPU);
        System.out.println("-RAM amount: " + this.ram);
        System.out.println("-SD size: " + this.sdSize);
        System.out.println("-brand: " + this.brand);
        System.out.println("-model: " + this.model);
        System.out.println("-hasGPS: " + this.hasGPS);
        System.out.println("-has3G: " + this.has3G);
        System.out.println("-hasNFC: " + this.hasNFC + "\n");
    }
}

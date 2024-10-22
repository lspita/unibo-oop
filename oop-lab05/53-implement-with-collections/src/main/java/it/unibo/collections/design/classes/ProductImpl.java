package it.unibo.collections.design.classes;

import it.unibo.collections.design.api.Product;

public class ProductImpl implements Product {

    private static final double DEFAULT_QUANTITY = 1.0;

    private final String name;
    private final double quantity;

    public ProductImpl(final String name, final double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ProductImpl(final String name) {
        this(name, DEFAULT_QUANTITY);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getQuantity() {
        return this.quantity;
    }
}

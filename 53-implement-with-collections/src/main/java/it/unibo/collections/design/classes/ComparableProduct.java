package it.unibo.collections.design.classes;

import it.unibo.collections.design.api.Product;

public class ComparableProduct extends ProductImpl implements Comparable<Product> {

    public ComparableProduct(String name) {
        super(name);
    }

    public ComparableProduct(String name, double quantity) {
        super(name, quantity);
    }

    @Override
    public int compareTo(Product o) {
        return this.getName().compareTo(o.getName());
    }
}

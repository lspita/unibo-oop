package it.unibo.inner.test.impl;

import it.unibo.inner.test.api.Product;


/**
 * Product implementation.
 * 
 */
public class ProductImpl implements Product {

    private final String name;
    private final double quantity;

    /**
     * @param n
     *            the product name
     * @param q
     *            the product quantity
     */
    public ProductImpl(final String n, final double q) {
        super();
        this.name = n;
        this.quantity = q;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getQuantity() {
        return quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return name.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        return obj == this
            || obj instanceof ProductImpl p
            && p.getClass().equals(this.getClass()) // Breaks the symmetry otherwise
            && p.name.equals(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Product[name=" + name + ", quantity=" + quantity + "]";
    }
}

package it.unibo.collections.design.classes;

import java.util.Set;
import java.util.LinkedHashSet;

import it.unibo.collections.design.api.Product;
import it.unibo.collections.design.api.Warehouse;

public class WarehouseImpl implements Warehouse {

    private final Set<Product> products = new LinkedHashSet<>();

    @Override
    public void addProduct(Product p) {
        products.add(p);
    }

    @Override
    public Set<String> allNames() {
        Set<String> names = new LinkedHashSet<>();
        for (Product p : this.allProducts()) {
            names.add(p.getName());
        }

        return names;
    }

    @Override
    public Set<Product> allProducts() {
        Set<Product> productsClone = new LinkedHashSet<>(this.products);
        return productsClone;
    }

    @Override
    public boolean containsProduct(Product p) {
        return this.products.contains(p);
    }

    @Override
    public double getQuantity(String name) {
        double count = 0.0;
        for (Product p : this.products) {
            if (p.getName().equals(name)) {
                count += p.getQuantity();
            }
        }
        return count;
    }

}

package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShoppingCart {
    private Map<String, Integer> userCart;

    public ShoppingCart() {
        this.userCart = new HashMap<>();
    }

    public void addProduct(String productId) {
        userCart.put(productId, userCart.getOrDefault(productId, 0) + 1);
    }

    public void changeProductQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            return;
        }
        userCart.put(productId, quantity);
    }

    public Set<String> getProductIds() {
        return userCart.keySet();
    }

    public int getProductQuantity(String productId) {
        return userCart.getOrDefault(productId, 0);
    }
}

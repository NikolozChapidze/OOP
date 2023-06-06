package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCatalog {
    public final Map<String, Product> catalog;
    public final List<Product> products;

    public ProductCatalog(List<Product> products) {
        this.catalog = new HashMap<>();
        this.products= products;
        products.forEach((product)->catalog.put(product.getProductid(),product));
    }

    public Product getProduct(String productid){
        return catalog.get(productid);
    }

    public List<Product> getAllProducts(){
        return products;
    }
}

import models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection implements StudentStoreDao {

    private Connection conn;

    public DBConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT productid, name, imagefile, price FROM products;");
            while (rs.next()) {
                products.add(new Product(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

import models.ProductCatalog;
import models.ShoppingCart;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class initListener implements ServletContextListener, HttpSessionListener {

    public initListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing context");
        try {
            Class.forName("com.mysql.cj.jdbc.MysqlDataSource");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/freeuni", "root", "admin");
            StudentStoreDao dao = new DBConnection(conn);
            ProductCatalog productCatalog = new ProductCatalog(dao.getProducts());
            sce.getServletContext().setAttribute("products", productCatalog.getAllProducts());
            sce.getServletContext().setAttribute(ProductCatalog.class.getSimpleName(), productCatalog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        se.getSession().setAttribute(ShoppingCart.class.getSimpleName(), new ShoppingCart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        se.getSession().removeAttribute(ShoppingCart.class.getSimpleName());
    }
}

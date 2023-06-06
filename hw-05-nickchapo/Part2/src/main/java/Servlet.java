import models.Product;
import models.ProductCatalog;
import models.ShoppingCart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(ShoppingCart.class.getSimpleName());
        ProductCatalog productCatalog = (ProductCatalog) request.getServletContext().getAttribute(ProductCatalog.class.getSimpleName());
        if (request.getParameter("productID") != null) {
            shoppingCart.addProduct(request.getParameter("productID"));
        } else {
            ShoppingCart temp = new ShoppingCart();
            for (String prod : shoppingCart.getProductIds()) {
                temp.changeProductQuantity(prod, Integer.parseInt(request.getParameter("quantityOf" + prod)));
            }
            shoppingCart = temp;
            request.getSession().setAttribute(ShoppingCart.class.getSimpleName(),shoppingCart);
        }

        double sumPrice = 0;
        List<Product> products = new ArrayList<>();
        for (final String prod : shoppingCart.getProductIds()) {
            products.add(productCatalog.getProduct(prod));
            sumPrice += productCatalog.getProduct(prod).getPrice() * shoppingCart.getProductQuantity(prod);
        }
        request.setAttribute("userProducts", products);
        request.setAttribute("sumPrice", sumPrice);
        request.getRequestDispatcher("cartPage.jsp").forward(request, response);
    }
}

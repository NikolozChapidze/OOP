package Servlets;

import manager.AccountManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateAccountServlet", value = "/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        AccountManager accountManager = (AccountManager) servletContext.getAttribute(AccountManager.class.getSimpleName());
        String nextPage = "userExists.jsp";
        if(accountManager.createAccount(request.getParameter("userName"),request.getParameter("userPassword"))){
            nextPage = "logedIn.jsp";
        }
        request.getRequestDispatcher(nextPage).forward(request,response);
    }
}

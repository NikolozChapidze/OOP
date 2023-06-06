package Listeners;

import manager.AccountManager;

import javax.servlet.*;
import javax.servlet.annotation.*;

@WebListener
public class ContextListener implements ServletContextListener{

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        AccountManager accountManager = new AccountManager();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(AccountManager.class.getSimpleName(),accountManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }
}

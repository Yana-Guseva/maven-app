package servlets.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("MyServletContextListener initialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("MyServletContextListener destroyed");
    }
}

package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int number = Integer.valueOf(req.getParameter("number"));
            System.out.println("Request number " + number);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

public class HelloServlet extends HttpServlet {
    private static final String PATH = "http://localhost:8080/hello?number=";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int number = Integer.valueOf(req.getParameter("number"));
        try {
            System.out.println("Servlet " + number + " called.");
            URL url = new URL(PATH + (number + 1));
            Object content = url.getContent();
            resp.getWriter().write("content: " + content);
        } catch (Throwable e) {
            e.printStackTrace();
        }

//        try {
//            int number = Integer.valueOf(req.getParameter("number"));
//            System.out.println("Request number " + number);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
    }
}
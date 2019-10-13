package main.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Resume</title>");
        out.println("</head>");

        out.println("<body bgcolor=\"white\">");

        out.println("<table border=\"11\">");
        out.println("<tr>");
        out.println("<td>строка ${name} ячейка1</td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
    }
}

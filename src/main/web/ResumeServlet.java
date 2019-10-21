package main.web;

import main.Config;
import main.model.Resume;
import main.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.write("<html>");
        out.write("<head>");
        out.write("<title>Resume</title>");
        out.write("</head>");

        out.write("<body>");

        out.write("<table border=\"11\">");
        out.write("<tr>");
        out.write("<td>Имя</td>");
        out.write("<td>fullName</td>");
        out.write("</tr>");
        for (Resume resume : storage.getAllSorted()) {
            out.write("<tr>");
            out.write("<td>" + resume.getUuid() + "</td>");
            out.write("<td>" + resume.getFullName() + "</td>");
            out.write("</tr>");
        }
        out.write("</table>");

        out.write("</body>");
        out.write("</html>");
    }
}

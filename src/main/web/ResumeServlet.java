package main.web;

import main.Config;
import main.model.*;
import main.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean createNew = false;

        if (uuid == null) {
            createNew = true;
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (createNew) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {//TODO all
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (!createNew) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));//TODO
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        r.addSection(type, new ListSection(value));//TODO
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> org = new ArrayList<>();
                        String title = request.getParameter(type.name() + "title");
                        String url = request.getParameter(type.name() + "url");
                        String dateS = request.getParameter(type.name() + "start");
                        String dateE = request.getParameter(type.name() + "end");
                        String name = request.getParameter(type.name() + "name");
                        String des = request.getParameter(type.name() + "des");
                        org.add(new Organization(url, title, new Organization.Place(LocalDate.of(2010, 10, 10), LocalDate.of(2010, 10, 10), name, des)));

                        r.addSection(type, new OrganizationSection(org));
                        break;
                }
            }
        }
        if (createNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r;
        switch (action) {
            case "add":
                getRequestDispatcher(request, response, new Resume(), "/WEB-INF/jsp/add.jsp");
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = r.getSection(sectionType);
                    switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                r.addSection(sectionType, new TextSection(""));
                            } 
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                List list = new ArrayList();
                                list.add("empty list");
                                r.addSection(sectionType, new ListSection(list));
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            List<Organization.Place> place = new ArrayList<>();
                            place.add(new Organization.Place(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 1), "empty", "empty"));
                            r.addSection(sectionType, new OrganizationSection(new Organization(new Organization.Link("empty", "empty"), place)));
                            break;
                    }
                    storage.update(r);
                    break;
                }
                getRequestDispatcher(request, response, r, ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"));
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }

    private void getRequestDispatcher(HttpServletRequest request, HttpServletResponse response, Resume r, String action) throws ServletException, IOException {
        request.setAttribute("resume", r);
        request.getRequestDispatcher(action).forward(request, response);
    }
}

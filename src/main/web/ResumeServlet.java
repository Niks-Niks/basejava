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
        String action = request.getParameter("back");
        Resume r;
        boolean createNew = false;

        if (uuid == null) {
            createNew = true;
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        if (action != null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        } else {
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                if (isEmpty(value)) {
                    r.getContacts().remove(type);
                } else {
                    r.addContact(type, value);
                }
            }
            for (SectionType type : SectionType.values()) {//TODO all
                String value = request.getParameter(type.name());
                if (isEmpty(value)) {
                    r.getSections().remove(type);
                } else {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            r.addSection(type, new TextSection(value));
                            break;
                        case QUALIFICATIONS:
                        case ACHIEVEMENT:
                            r.addSection(type, new ListSection(value));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            List<Organization> org = new ArrayList<>();
                            String title = isEmpty(type.name() + "title", r, type, request);
                            String url = isEmpty(type.name() + "url", r, type, request);

                            String name = isEmpty(type.name() + "name", r, type, request);
                            String des = isEmpty(type.name() + "des", r, type, request);
                            String dateS = isEmpty(type.name() + "start", r, type, request);
                            String dateE = isEmpty(type.name() + "end", r, type, request);
                            LocalDate start;
                            LocalDate end;
                            try {
                                start = LocalDate.parse(dateS);
                                end = LocalDate.parse(dateE);
                            } catch (Exception e) {
                                start = LocalDate.now();
                                end = LocalDate.now();
                            }
                            org.add(new Organization(url, title, new Organization.Place(start, end, name, des)));

                            r.addSection(type, new OrganizationSection(org));
                    }
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
                for (SectionType type : SectionType.values()) {//TODO all
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = new TextSection("");
                            }
                            break;
                        case QUALIFICATIONS:
                        case ACHIEVEMENT:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            if (organizationSection != null) {

                                List<Organization> org = new ArrayList<>();
                                String title = isEmpty(type.name() + "title", r, type, request);
                                String url = isEmpty(type.name() + "url", r, type, request);

                                String name = isEmpty(type.name() + "name", r, type, request);
                                String des = isEmpty(type.name() + "des", r, type, request);
                                String dateS = isEmpty(type.name() + "start", r, type, request);
                                String dateE = isEmpty(type.name() + "end", r, type, request);
                                LocalDate start;
                                LocalDate end;

                                try {
                                    start = LocalDate.parse(dateS);
                                    end = LocalDate.parse(dateE);
                                } catch (Exception e) {
                                    start = LocalDate.now();
                                    end = LocalDate.now();
                                }
                                org.add(new Organization(url, title, new Organization.Place(start, end, name, des)));

                                section = new OrganizationSection(org);
                            } else {
                                section = new OrganizationSection(new Organization("", "", new Organization.Place(LocalDate.now(), LocalDate.now(), "", "")));
                            }

                    }
                    r.addSection(type, section);
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

    private String isEmpty(String check, Resume r, SectionType type, HttpServletRequest request) {
        return request.getParameter(isEmpty(check) ? r.getSections().remove(type).toString() : check);
    }

    private boolean isEmpty(String check) {
        return check == null || check == "" || check.trim().length() == 0;
    }

}


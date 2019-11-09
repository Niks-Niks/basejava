package main.web;

import main.Config;
import main.model.*;
import main.storage.Storage;
import main.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
            if (isEmpty(value)) {
                r.getContacts().remove(type);
            } else {
                r.addContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {//TODO all
            String value = request.getParameter(type.name());
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (!isEmpty(value)) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENT:
                    if (!isEmpty(value)) {
                        r.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    if (isEmptyArray(new String[]{type.name() + "title", type.name() + "url", type.name() + "start", type.name() + "end", type.name() + "name", type.name() + "des"})) {// true == all array-> empty
                        List<Organization> org = new ArrayList<>();

                        String title = request.getParameter(type.name() + "title");
                        String url = request.getParameter(type.name() + "url");

                        LocalDate dateS = DateUtil.parse(request.getParameter(type.name() + "start"));
                        LocalDate dateE = DateUtil.parse(request.getParameter(type.name() + "end"));
                        String name = request.getParameter(type.name() + "name");
                        String des = request.getParameter(type.name() + "des");

                        org.add(new Organization(url, title, new Organization.Place(dateS, dateE, name, des)));

                        r.addSection(type, new OrganizationSection(org));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
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
                r = storage.get(uuid);
                getRequestDispatcher(request, response, r, "/WEB-INF/jsp/view.jsp");
                break;
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
                            List<Organization> orgList = new ArrayList<>();
                            if (organizationSection != null) {
                                for (Organization org : organizationSection.getList()) {
                                    for (Organization.Place place : org.getList()) {
                                        orgList.add(new Organization(org.getHomePage().getHomePage(), org.getHomePage().getLink(),
                                                new Organization.Place(place.getDateStart(), place.getDateEnd(), place.getTitle(), place.getDescription())));
                                    }
                                }
                            } else {
                                List<Organization.Place> place = new ArrayList<>();
                                place.add(new Organization.Place(LocalDate.now(), LocalDate.now(), "", ""));
                                orgList.add(new Organization(new Organization.Link(), place));
                            }

                            section = new OrganizationSection(orgList);

                            break;
                    }
                    r.addSection(type, section);
                }
                getRequestDispatcher(request, response, r, "/WEB-INF/jsp/edit.jsp");
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }

    private void getRequestDispatcher(HttpServletRequest request, HttpServletResponse response, Resume r, String action) throws ServletException, IOException {
        request.setAttribute("resume", r);
        request.getRequestDispatcher(action).forward(request, response);
    }

    private boolean isEmptyArray(String[] check) {
        boolean isEmpty = true;
        for (String q : check) {
            isEmpty = isEmpty(q);
            if (!isEmpty) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    private boolean isEmpty(String check) {
        return check == null || check.equals("") || check.trim().length() == 0;
    }

}


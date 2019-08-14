package main.model;

import java.util.List;

public class OrganizationSection extends Section {
    private List<String> list;

    private OrganizationSection(List<String> list) {
        this.list = list;
    }

    public List getList(){
        return list;
    }
}

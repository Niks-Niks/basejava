package main.model;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {
    private List<Organization> list;

    private static final long serialVersionUID = 1L;

    public OrganizationSection() {

    }

    public OrganizationSection(Organization list){
        this(Arrays.asList(list));
    }

    public OrganizationSection(List<Organization> list) {
        this.list = list;
    }
    
    public List<Organization> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}

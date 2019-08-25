package main.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection implements Serializable {
    private List<Organization> list;

//    private static final long serialVersionUID = 1L;

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

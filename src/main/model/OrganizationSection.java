package main.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<String> list;

    private OrganizationSection(List<String> list) {
        this.list = list;
    }

    public List getList(){
        return list;
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

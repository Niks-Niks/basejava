package main.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public ListSection(String list) {
        this(Arrays.asList(list));
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}

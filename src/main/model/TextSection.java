package main.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

    private String text;

    public TextSection(String title) {
        this.text = text;
    }

    public String getSection() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}

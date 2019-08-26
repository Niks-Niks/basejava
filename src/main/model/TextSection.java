package main.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection(String title) {
        this.text = title;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "text='" + text + '\'' +
                '}';
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

package domain;

import java.util.Objects;

public class Sorts {
    private final Integer id;
    private String name;

    public Sorts(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sorts(String name) {
        this(null, name);
    }

    public String getSortName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sorts sorts = (Sorts) o;
        return Objects.equals(id, sorts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

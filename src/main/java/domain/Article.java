package domain;

import java.util.Objects;

public class Article {
    private final Integer id;
    private String name;
    private Account seller;
    private Sorts sort;

    public Article(Integer id, String name, Account seller, Sorts sort) {
        this.id = id;
        this.name = name;
        this.seller = seller;
        this.sort = sort;
    }

    public Article(String name, Account seller, Sorts sort) {
        this(null, name, seller, sort);
    }

    public Account getSellter() {
        return this.seller;
    }

    public Sorts getSort() {
        return this.sort;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(Sorts sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

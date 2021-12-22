package domain;

import java.util.Objects;

public class Article {
    private final Integer id;
    private String name;
    private double preis;
    private String description;
    private final Account seller;
    private final Sorts sort;

    public Article(Integer id, String name, double preis, String description, Account seller, Sorts sort) {
        this.id = id;
        this.name = name;
        this.preis = preis;
        this.description = description;
        this.seller = seller;
        this.sort = sort;
    }

    public Article(String name, double preis, String description, Account seller, Sorts sort) {
        this(null, name, preis, description, seller, sort);
    }

    public Integer getId() {
        return id;
    }

    public double getPreis() {
        return preis;
    }

    public String getDescription() {
        return description;
    }

    public Account getSeller() {
        return seller;
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

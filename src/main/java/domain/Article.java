package domain;

import java.util.Objects;

import java.util.Comparator;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Article {
    @Getter
    @Setter
    private final Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private final SellerAccount seller;
    @Getter
    @Setter
    private final Sorts sort;

    public Article(Integer id, String name, double preis, String description, SellerAccount seller, Sorts sort) {
        this.id = id;
        this.name = name;
        this.price = preis;
        this.description = description;
        this.seller = seller;
        this.sort = sort;
    }

    public Article(String name, double preis, String description, SellerAccount seller, Sorts sort) {
        this(null, name, preis, description, seller, sort);
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

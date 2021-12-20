package domain;

import java.util.Objects;

public class Order {
    private final Integer id;
    private final CartArticle article;
    private String address;

    public Order(Integer id, CartArticle article, String address) {
        this.id = id;
        this.article = article;
        this.address = address;
    }

    public Order(CartArticle article, String address) {
        this(null,article,address);
    }

    public Integer getId() {
        return id;
    }

    public CartArticle getArticle() {
        return article;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

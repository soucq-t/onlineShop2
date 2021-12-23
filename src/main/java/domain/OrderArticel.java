package domain;

import java.util.Comparator;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
public class OrderArticel implements Comparable {
    @Setter
    @Getter
    private final Integer id;
    @Setter
    @Getter
    private final Article article;
    @Setter
    @Getter
    private final Order order;

    public OrderArticel(Integer id, Article article, Order order) {
        this.id = id;
        this.article = article;
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderArticel{" +
                "id=" + id +
                ", article=" + article +
                ", order=" + order +
                '}';
    }

    public OrderArticel(Article article, Order order) {
        this(null,article,order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderArticel that = (OrderArticel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Object o) {
        return Comparator.comparing(OrderArticel::getId).thenComparing(OrderArticel::getArticle).compare(this, (OrderArticel) o);
    }
}

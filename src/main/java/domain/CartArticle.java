package domain;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
public class CartArticle {
    @Setter
    @Getter
    private final Integer id;
    @Setter
    @Getter
    private final Article article;
    @Setter
    @Getter
    private final BuyerAccount buyer;

    public CartArticle(Integer id, Article article, BuyerAccount buyer) {
        this.id = id;
        this.article = article;
        this.buyer = buyer;
    }

    public CartArticle(Article article, BuyerAccount buyer) {
        this(null,article,buyer);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartArticle that = (CartArticle) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

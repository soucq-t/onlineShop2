package domain;

import java.util.Objects;

public class InCartArticle {
    private final Integer id;
    private final Article article;
    private final Account buyer;

    public InCartArticle(Integer id, Article article, Account buyer) {
        this.id = id;
        this.article = article;
        this.buyer = buyer;
    }

    public InCartArticle(Article article, Account buyer) {
        this(null,article,buyer);
    }

    public Integer getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public Account getBuyer() {
        return buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InCartArticle that = (InCartArticle) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

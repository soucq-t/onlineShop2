package domain;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
public class Order {
    @Setter
    @Getter
    private final Integer id;
    @Setter
    @Getter
    private final BuyerAccount account;

    public Order(Integer id, BuyerAccount buyerAccount) {
        this.id = id;
        this.account = buyerAccount;
    }

    public Order(BuyerAccount account) {
        this(null, account);
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BestellungID: " +
                "id=" + id ;
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

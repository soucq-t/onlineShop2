package domain;

import java.util.Objects;

public class Order {
    private final Integer id;
    private final Account account;

    public Order(Integer id, Account buyerAccount) {
        this.id = id;
        this.account = buyerAccount;
    }

    public Order(Account account) {
        this(null, account);
    }

    public Integer getId() {
        return id;
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

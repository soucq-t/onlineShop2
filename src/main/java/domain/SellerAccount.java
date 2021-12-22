package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class SellerAccount  {
    @Setter
    @Getter
    private final Integer id;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String kennwort;

    public SellerAccount(Integer id, String username, String kennwort) {
        this.id = id;
        this.username = username;
        this.kennwort = kennwort;
    }

    public SellerAccount(String username, String kennwort) {
        this(null, username, kennwort);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerAccount that = (SellerAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

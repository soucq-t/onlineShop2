package domain;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
public class BuyerAccount  {
    @Setter
    @Getter
    private final Integer id;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String kennwort;

    public BuyerAccount(Integer id, String username, String kennwort) {
        this.id = id;
        if (username.isEmpty() || kennwort.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.username = username;
        this.kennwort = kennwort;
    }


    public BuyerAccount(String username, String kennwort) {
        this(null,username,kennwort);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyerAccount that = (BuyerAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BuyerAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", kennwort='" + kennwort + '\'' +
                '}';
    }
}

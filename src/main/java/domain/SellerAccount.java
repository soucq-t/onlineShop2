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
    @Getter
    @Setter
    private  String sitz;

    public SellerAccount(Integer id, String username, String kennwort, String sitz) {
        this.id = id;
        this.username = username;
        this.kennwort = kennwort;
        this.sitz = sitz;
    }

    public SellerAccount(String username, String kennwort, String sitz) {
        this(null, username, kennwort, sitz);
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

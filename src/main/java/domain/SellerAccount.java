package domain;

import java.util.Objects;

public class SellerAccount implements Account {
    private final Integer id;
    private String username;
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
    public Integer getId() {
        return null;
    }


    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public String getKennwort() {
        return this.kennwort;
    }

    @Override
    public void getUserName(String username) {
        this.username = username;
    }

    @Override
    public void setKennwort(String Kennwort) {
        this.kennwort = kennwort;
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

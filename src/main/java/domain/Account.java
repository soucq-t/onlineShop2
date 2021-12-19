package domain;

public interface Account {
    public Integer getId();
    public String getUserName();
    public String getKennwort();
    public void getUserName(String username);
    public void setKennwort(String Kennwort);
}

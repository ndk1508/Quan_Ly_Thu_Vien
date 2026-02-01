package DTO; // Phải có dòng này ở đầu

public class TaiKhoanDTO {
    private int matk;
    private String username;
    private String password;
    private int quyen;

    public TaiKhoanDTO() {}

    public TaiKhoanDTO(int matk, String username, String password, int quyen) {
        this.matk = matk;
        this.username = username;
        this.password = password;
        this.quyen = quyen;
    }

    // Phải có đầy đủ Getter và Setter
    public int getMatk() { return matk; }
    public void setMatk(int matk) { this.matk = matk; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getQuyen() { return quyen; }
    public void setQuyen(int quyen) { this.quyen = quyen; }
}
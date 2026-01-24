package DTO;

public class TaiKhoanDTO {

    private int matk;
    private String username;
    private String password;
    private int quyen;

    public TaiKhoanDTO() {
    }

    public TaiKhoanDTO(int matk, String username, String password, String quyen) {
        this.matk = matk;
        this.username = username;
        this.password = password;
    }
    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    public int getMatk() {
        return matk;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getQuyen() {
        return quyen;
    }
}

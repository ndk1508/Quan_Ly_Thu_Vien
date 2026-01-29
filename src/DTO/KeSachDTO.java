package DTO;

public class KeSachDTO {
    private int maKe;
    private String tenKe; // Trong CSDL cột này tên là ViTri

    public KeSachDTO() {}
    public KeSachDTO(int maKe, String tenKe) {
        this.maKe = maKe;
        this.tenKe = tenKe;
    }

    public int getMaKe() { return maKe; }
    public void setMaKe(int maKe) { this.maKe = maKe; }
    public String getTenKe() { return tenKe; }
    public void setTenKe(String tenKe) { this.tenKe = tenKe; }
}
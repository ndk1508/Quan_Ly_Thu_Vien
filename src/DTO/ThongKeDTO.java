package DTO;

public class ThongKeDTO {
    private String ten;
    private int soLuong;
    private double tyLe; // % (dùng cho biểu đồ)

    public ThongKeDTO(String ten, int soLuong) {
        this.ten = ten;
        this.soLuong = soLuong;
    }

    public ThongKeDTO(String ten, int soLuong, double tyLe) {
        this.ten = ten;
        this.soLuong = soLuong;
        this.tyLe = tyLe;
    }

    public String getTen() {
        return ten;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getTyLe() {
        return tyLe;
    }
}

package DTO;

public class SachDTO {
    private int maSach;
    private String tenSach;
    private int maLoai;
    private int maNXB;
    private int maTacGia;
    private int namXB;
    private int soLuong;
    private int make; // Tên cột trong DB là 'Make'
    private String hinhAnh;

    public SachDTO() {}

    public SachDTO(int maSach, String tenSach, int maLoai, int maNXB, int maTacGia, int namXB, int soLuong, int make, String hinhAnh) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maLoai = maLoai;
        this.maNXB = maNXB;
        this.maTacGia = maTacGia;
        this.namXB = namXB;
        this.soLuong = soLuong;
        this.make = make;
        this.hinhAnh = hinhAnh;
    }

    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }

    public int getMaNXB() { return maNXB; }
    public void setMaNXB(int maNXB) { this.maNXB = maNXB; }

    public int getMaTacGia() { return maTacGia; }
    public void setMaTacGia(int maTacGia) { this.maTacGia = maTacGia; }

    public int getNamXB() { return namXB; }
    public void setNamXB(int namXB) { this.namXB = namXB; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public int getMake() { return make; }
    public void setMake(int make) { this.make = make; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}
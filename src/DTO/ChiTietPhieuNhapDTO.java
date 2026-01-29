package DTO;

public class ChiTietPhieuNhapDTO {
    private int maCTPN;
    private int maPN;
    private int maSach;
    private int soLuong;
    private double donGia;
    
    // Thuộc tính phụ để hiển thị Thành tiền (không lưu trong DB)
    private double thanhTien;

    public ChiTietPhieuNhapDTO() {}

    public ChiTietPhieuNhapDTO(int maCTPN, int maPN, int maSach, int soLuong, double donGia) {
        this.maCTPN = maCTPN;
        this.maPN = maPN;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia;
    }

    public int getMaCTPN() { return maCTPN; }
    public void setMaCTPN(int maCTPN) { this.maCTPN = maCTPN; }
    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }
    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { 
        this.soLuong = soLuong; 
        this.thanhTien = this.soLuong * this.donGia; // Cập nhật thành tiền
    }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { 
        this.donGia = donGia; 
        this.thanhTien = this.soLuong * this.donGia; // Cập nhật thành tiền
    }
    public double getThanhTien() { return soLuong * donGia; }
}
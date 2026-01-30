package DTO;

import java.util.Date;

public class ChiTietPhieuMuonDTO {
    private int maCTPM;
    private int maPM;
    private int maSach;
    private Date ngayTra;
    private String ghiChu;
    private int trangThai; // 0: Chưa trả, 1: Đã trả

    // Constructor không tham số
    public ChiTietPhieuMuonDTO() {
    }

    // Constructor đầy đủ tham số
    public ChiTietPhieuMuonDTO(int maCTPM, int maPM, int maSach, Date ngayTra, String ghiChu, int trangThai) {
        this.maCTPM = maCTPM;
        this.maPM = maPM;
        this.maSach = maSach;
        this.ngayTra = ngayTra;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public int getMaCTPM() {
        return maCTPM;
    }

    public void setMaCTPM(int maCTPM) {
        this.maCTPM = maCTPM;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    // Ghi đè phương thức toString để hỗ trợ debug nếu cần
    @Override
    public String toString() {
        return "ChiTietPhieuMuonDTO{" +
                "maCTPM=" + maCTPM +
                ", maPM=" + maPM +
                ", maSach=" + maSach +
                ", ngayTra=" + ngayTra +
                ", trangThai=" + trangThai +
                '}';
    }
}
package DTO;
import java.sql.Date; // Dùng sql.Date để tương thích DB

public class PhieuNhapDTO {
    private int maPN;
    private int maNCC;
    private int maNV;
    private Date ngayNhap;

    public PhieuNhapDTO() {}

    public PhieuNhapDTO(int maPN, int maNCC, int maNV, Date ngayNhap) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
    }

    // Getter & Setter
    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }
    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }
}
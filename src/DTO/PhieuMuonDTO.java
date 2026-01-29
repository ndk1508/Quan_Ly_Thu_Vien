package DTO;

import java.sql.Date;

public class PhieuMuonDTO {

    private int maPM;
    private int maNV;
    private int maDocGia;
    private Date ngayMuon;
    private String tinhTrang;
    private int trangThai;

    public PhieuMuonDTO(int maPM, int maNV, int maDocGia,
                        Date ngayMuon, String tinhTrang, int trangThai) {
        this.maPM = maPM;
        this.maNV = maNV;
        this.maDocGia = maDocGia;
        this.ngayMuon = ngayMuon;
        this.tinhTrang = tinhTrang;
        this.trangThai = trangThai;
    }

    public int getMaPM() { return maPM; }
    public int getMaNV() { return maNV; }
    public int getMaDocGia() { return maDocGia; }
    public Date getNgayMuon() { return ngayMuon; }
    public String getTinhTrang() { return tinhTrang; }
    public int getTrangThai() { return trangThai; }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}

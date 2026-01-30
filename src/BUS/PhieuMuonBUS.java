package BUS;

import DAL.PhieuMuonDAL;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonBUS {

    private PhieuMuonDAL dal = new PhieuMuonDAL();

    public ArrayList<Object[]> getAllView() {
        return dal.getAllView();
    }

    public boolean lapPhieu(int maNV, int maDG, Date ngayMuon) {
        java.sql.Date sqlDate = new java.sql.Date(ngayMuon.getTime());
        return dal.insert(maNV, maDG, sqlDate);
    }
 // Thêm vào trong class PhieuMuonBUS

    public boolean suaPhieu(int maPM, int maNV, int maDG, Date ngayMuon, int trangThai) {
        java.sql.Date sqlDate = new java.sql.Date(ngayMuon.getTime());
        return dal.update(maPM, maNV, maDG, sqlDate, trangThai);
    }

    public boolean xoaPhieu(int maPM) {
        return dal.delete(maPM);
    }
    public boolean danhDauDaTra(int maPM) {
        return dal.capNhatDaTra(maPM);
    }
    public ArrayList<Object[]> timKiem(String keyword) {
        return dal.search(keyword);
    }
}

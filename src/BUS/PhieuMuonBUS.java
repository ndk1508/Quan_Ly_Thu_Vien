package BUS;

import DAL.PhieuMuonDAL;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonBUS {

    private PhieuMuonDAL dal = new PhieuMuonDAL();

    // ================= XỬ LÝ PHIẾU MƯỢN (MASTER) =================
    
    // Lấy toàn bộ danh sách hiển thị lên bảng chính
    public ArrayList<Object[]> getAllView() {
        return dal.getAllView();
    }

    // Tìm kiếm phiếu mượn theo từ khóa
    public ArrayList<Object[]> timKiem(String keyword) {
        return dal.search(keyword);
    }

    // Xóa phiếu mượn (Lưu ý: DAL phải xử lý xóa Chi tiết trước hoặc dùng Cascade)
    public boolean xoaPhieu(int maPM) {
        return dal.delete(maPM);
    }

    // Sửa thông tin cơ bản của phiếu mượn
    public boolean suaPhieu(int maPM, int maNV, int maDG, Date ngayMuon, int trangThai) {
        java.sql.Date sqlDate = new java.sql.Date(ngayMuon.getTime());
        return dal.update(maPM, maNV, maDG, sqlDate, trangThai);
    }

    // Đánh dấu toàn bộ phiếu đã trả
    public boolean danhDauDaTra(int maPM) {
        return dal.capNhatDaTra(maPM);
    }

    // ================= XỬ LÝ CHI TIẾT PHIẾU MƯỢN (DETAIL) =================

    // Lấy danh sách sách thuộc một phiếu mượn cụ thể
    public ArrayList<Object[]> getChiTiet(int maPM) {
        return dal.getChiTietByMaPM(maPM);
    }

    /**
     * Lập phiếu mượn mới kèm theo danh sách nhiều cuốn sách
     * @param maNV Mã nhân viên lập
     * @param maDG Mã độc giả mượn
     * @param ngayMuon Ngày mượn
     * @param dsSach Danh sách các Object[] chứa thông tin sách (ít nhất phải có MaSach ở index 0)
     */
    public boolean lapPhieuFull(int maNV, int maDG, Date ngayMuon, ArrayList<Object[]> dsSach) {
        // Kiểm tra logic: Phiếu mượn phải có ít nhất 1 cuốn sách
        if (dsSach == null || dsSach.isEmpty()) {
            return false; 
        }
        
        java.sql.Date sqlDate = new java.sql.Date(ngayMuon.getTime());
        return dal.insertFull(maNV, maDG, sqlDate, dsSach);
    }

    // Phương thức lập phiếu cũ (chỉ 1 dòng, nếu bạn vẫn muốn giữ để tương thích code cũ)
    public boolean lapPhieu(int maNV, int maDG, Date ngayMuon) {
        java.sql.Date sqlDate = new java.sql.Date(ngayMuon.getTime());
        return dal.insert(maNV, maDG, sqlDate);
    }
    public boolean lapPhieuFull(int maNV, int maDG, Date ngayMuon, Date ngayTra, ArrayList<Object[]> dsSach) {
        if (dsSach == null || dsSach.isEmpty()) return false;
        
        java.sql.Date sqlNgayMuon = new java.sql.Date(ngayMuon.getTime());
        java.sql.Date sqlNgayTra = new java.sql.Date(ngayTra.getTime()); // Chuyển đổi ngày trả
        
        return dal.insertFull(maNV, maDG, sqlNgayMuon, sqlNgayTra, dsSach);
    }
}
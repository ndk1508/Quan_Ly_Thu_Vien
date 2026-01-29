package DAL;

import DTO.ChiTietPhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;

public class ChiTietPhieuNhapDAL {

    // 1. Lấy chi tiết
    public ArrayList<ChiTietPhieuNhapDTO> getChiTietByMaPN(int maPN) {
        ArrayList<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        // Lưu ý: Cột mã phiếu nhập là MaPN
        String sql = "SELECT * FROM chitietphieunhap WHERE MaPN = ?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maPN);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO();
                ct.setMaCTPN(rs.getInt("MaCTPN"));
                ct.setMaPN(rs.getInt("MaPN")); 
                ct.setMaSach(rs.getInt("MaSach"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("Gia")); 
                // Không cần lấy cột ThanhTien từ DB vì DTO tự tính toán khi hiển thị
                list.add(ct);
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm chi tiết (FIX LỖI: Thêm cột ThanhTien)
    public boolean themChiTiet(ChiTietPhieuNhapDTO ct) {
        // Tính thành tiền = Số lượng * Giá
        double thanhTien = ct.getSoLuong() * ct.getDonGia();

        // SỬA: Thêm cột ThanhTien vào câu lệnh INSERT
        String sql = "INSERT INTO chitietphieunhap(MaPN, MaSach, SoLuong, Gia, ThanhTien) VALUES(?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ct.getMaPN());
            ps.setInt(2, ct.getMaSach());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());
            ps.setDouble(5, thanhTien); // Truyền giá trị Thành Tiền vào
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
            System.out.println("Lỗi thêm chi tiết: " + e.getMessage());
        }
        return false;
    }

    // 3. Xóa chi tiết
    public boolean xoaChiTiet(int maCTPN) {
        String sql = "DELETE FROM chitietphieunhap WHERE MaCTPN=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maCTPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
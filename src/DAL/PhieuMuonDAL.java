package DAL;

import java.sql.*;
import java.util.ArrayList;

public class PhieuMuonDAL {

    Connection conn = DBConnect.getConnection();

    // ================= LẤY DANH SÁCH HIỂN THỊ =================
    public ArrayList<Object[]> getAllView() {
        ArrayList<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                pm.MaPM,
                nv.TenNV AS TenNhanVien,
                dg.tendocgia AS TenDocGia,
                pm.NgayMuon,
                CASE 
                    WHEN pm.TrangThai = 0 THEN 'Đang mượn'
                    WHEN pm.TrangThai = 1 THEN 'Đã trả'
                END AS TinhTrang
            FROM phieumuon pm
            JOIN nhanvien nv ON pm.MaNV = nv.MaNV
            JOIN docgia dg ON pm.MaDocGia = dg.madocgia
        """;

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("MaPM"),
                    rs.getString("TenNhanVien"),
                    rs.getString("TenDocGia"),
                    rs.getDate("NgayMuon"),
                    rs.getString("TinhTrang")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // ================= LẬP PHIẾU MƯỢN =================
    public boolean insert(int maNV, int maDG, java.sql.Date ngayMuon) {

        String sql = """
            INSERT INTO PhieuMuon
            (MaNV, MaDocGia, NgayMuon, TinhTrang, TrangThai)
            VALUES (?, ?, ?, ?, ?)
        """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maNV);
            ps.setInt(2, maDG);
            ps.setDate(3, ngayMuon);

            // ✔ ĐÚNG KIỂU
            ps.setString(4, "Đang mượn"); // VARCHAR
            ps.setInt(5, 0);              // INT (0 = đang mượn)

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // ================= ĐÁNH DẤU ĐÃ TRẢ =================
    public boolean capNhatDaTra(int maPM) {

        String sql = """
            UPDATE PhieuMuon
            SET TinhTrang = ?,
                TrangThai = ?
            WHERE MaPM = ?
        """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Đã trả"); // VARCHAR
            ps.setInt(2, 1);          // INT (1 = đã trả)
            ps.setInt(3, maPM);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

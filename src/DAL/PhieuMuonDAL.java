package DAL;

import java.sql.*;
import java.util.ArrayList;

public class PhieuMuonDAL {
	// Lấy danh sách sách của 1 phiếu mượn
	public ArrayList<Object[]> getChiTietByMaPM(int maPM) {
	    ArrayList<Object[]> list = new ArrayList<>();
	    String sql = "SELECT ct.MaSach, s.tensach, ct.NgayTra, ct.GhiChu, " +
	                 "CASE WHEN ct.TrangThai = 0 THEN 'Chưa trả' ELSE 'Đã trả' END " +
	                 "FROM chitietphieumuon ct JOIN sach s ON ct.MaSach = s.masach WHERE ct.MaPM = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, maPM);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getString(5)});
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}

	// Lưu phiếu mượn kèm danh sách sách (Transaction)
	public boolean insertFull(int maNV, int maDG, java.sql.Date ngayMuon, ArrayList<Object[]> dsSach) {
	    try {
	        conn.setAutoCommit(false);
	        // 1. Chèn vào PhieuMuon
	        String sqlPM = "INSERT INTO PhieuMuon(MaNV, MaDocGia, NgayMuon, TrangThai, TinhTrang) VALUES(?,?,?,0,N'Đang mượn')";
	        PreparedStatement psPM = conn.prepareStatement(sqlPM, Statement.RETURN_GENERATED_KEYS);
	        psPM.setInt(1, maNV);
	        psPM.setInt(2, maDG);
	        psPM.setDate(3, ngayMuon);
	        psPM.executeUpdate();
	        
	        ResultSet rs = psPM.getGeneratedKeys();
	        if (rs.next()) {
	            int maPMNew = rs.getInt(1);
	            // 2. Chèn vào ChiTietPhieuMuon
	            String sqlCT = "INSERT INTO ChiTietPhieuMuon(MaPM, MaSach, TrangThai) VALUES(?,?,0)";
	            PreparedStatement psCT = conn.prepareStatement(sqlCT);
	            for (Object[] row : dsSach) {
	                psCT.setInt(1, maPMNew);
	                psCT.setInt(2, (int)row[0]); // MaSach
	                psCT.addBatch();
	            }
	            psCT.executeBatch();
	        }
	        conn.commit();
	        return true;
	    } catch (Exception e) {
	        try { conn.rollback(); } catch (SQLException ex) {}
	        e.printStackTrace();
	    }
	    return false;
	}
    Connection conn = DBConnect.getConnection();
    public ArrayList<Object[]> search(String keyword) {
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
            WHERE pm.MaPM LIKE ? OR nv.TenNV LIKE ? OR dg.tendocgia LIKE ?
        """;

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            String val = "%" + keyword + "%";
            ps.setString(1, val);
            ps.setString(2, val);
            ps.setString(3, val);
            
            ResultSet rs = ps.executeQuery();
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
 // Thêm vào trong class PhieuMuonDAL

 // ================= CẬP NHẬT PHIẾU MƯỢN =================
 public boolean update(int maPM, int maNV, int maDG, java.sql.Date ngayMuon, int trangThai) {
     String tinhTrang = (trangThai == 0) ? "Đang mượn" : "Đã trả";
     String sql = """
         UPDATE PhieuMuon 
         SET MaNV = ?, MaDocGia = ?, NgayMuon = ?, TrangThai = ?, TinhTrang = ?
         WHERE MaPM = ?
     """;

     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setInt(1, maNV);
         ps.setInt(2, maDG);
         ps.setDate(3, ngayMuon);
         ps.setInt(4, trangThai);
         ps.setString(5, tinhTrang);
         ps.setInt(6, maPM);

         return ps.executeUpdate() > 0;
     } catch (Exception e) {
         e.printStackTrace();
     }
     return false;
 }

 // ================= XÓA PHIẾU MƯỢN =================
 public boolean delete(int maPM) {
     String sql = "DELETE FROM PhieuMuon WHERE MaPM = ?";
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setInt(1, maPM);
         return ps.executeUpdate() > 0;
     } catch (Exception e) {
         // Lưu ý: Nếu có bảng ChiTietPhieuMuon tham chiếu đến MaPM, 
         // bạn cần xóa chi tiết trước hoặc thiết lập ON DELETE CASCADE trong DB.
         e.printStackTrace();
     }
     return false;
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
    public boolean insertFull(int maNV, int maDG, java.sql.Date ngayMuon, java.sql.Date ngayTra, ArrayList<Object[]> dsSach) {
        // 1. Cập nhật câu lệnh SQL để có thêm cột GhiChu
        String sqlPM = "INSERT INTO PhieuMuon(MaNV, MaDocGia, NgayMuon, TrangThai, TinhTrang) VALUES(?,?,?,?,?)";
        String sqlCT = "INSERT INTO ChiTietPhieuMuon(MaPM, MaSach, TrangThai, NgayTra, GhiChu) VALUES(?,?,?,?,?)";

        try {
            conn.setAutoCommit(false);

            // Chèn phiếu mượn (Master)
            PreparedStatement psPM = conn.prepareStatement(sqlPM, Statement.RETURN_GENERATED_KEYS);
            psPM.setInt(1, maNV);
            psPM.setInt(2, maDG);
            psPM.setDate(3, ngayMuon);
            psPM.setInt(4, 0); 
            psPM.setString(5, "Đang mượn");
            psPM.executeUpdate();

            ResultSet rs = psPM.getGeneratedKeys();
            if (rs.next()) {
                int maPMNew = rs.getInt(1);

                // 2. Chèn chi tiết (Detail)
                PreparedStatement psCT = conn.prepareStatement(sqlCT);
                for (Object[] row : dsSach) {
                    psCT.setInt(1, maPMNew);
                    psCT.setInt(2, (int) row[0]); // MaSach
                    psCT.setInt(3, 0);            // TrangThai (Chưa trả)
                    psCT.setDate(4, ngayTra);     // NgayTra (Hạn trả nhập từ GUI)
                    
                    // GIẢI PHÁP: Truyền chuỗi rỗng vào cột GhiChu để tránh lỗi NOT NULL
                    // Nếu row có chứa ghi chú từ bảng GUI, bạn có thể thay "" bằng (String)row[index]
                    psCT.setString(5, ""); 
                    
                    psCT.addBatch();
                }
                psCT.executeBatch();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}

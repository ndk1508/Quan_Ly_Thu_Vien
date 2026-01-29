package DAL;

import DTO.PhieuMuonDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuMuonDAL {

    public ArrayList<PhieuMuonDTO> getAll() {
        ArrayList<PhieuMuonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM phieumuon";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PhieuMuonDTO(
                        rs.getInt("MaPM"),
                        rs.getInt("MaNV"),
                        rs.getInt("MaDocGia"),
                        rs.getDate("NgayMuon"),
                        rs.getString("TinhTrang"),
                        rs.getInt("TrangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(PhieuMuonDTO pm) {
        String sql = """
            INSERT INTO phieumuon(MaPM, MaNV, MaDocGia, NgayMuon, TinhTrang, TrangThai)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pm.getMaPM());
            ps.setInt(2, pm.getMaNV());
            ps.setInt(3, pm.getMaDocGia());
            ps.setDate(4, pm.getNgayMuon());
            ps.setString(5, pm.getTinhTrang());
            ps.setInt(6, pm.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTinhTrang(int maPM, String tinhTrang) {
        String sql = "UPDATE phieumuon SET TinhTrang=? WHERE MaPM=?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tinhTrang);
            ps.setInt(2, maPM);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

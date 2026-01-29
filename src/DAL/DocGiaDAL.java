package DAL;

import DTO.DocGiaDTO;
import java.sql.*;
import java.util.ArrayList;

public class DocGiaDAL {

    public ArrayList<DocGiaDTO> getAll() {
        ArrayList<DocGiaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM docgia";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DocGiaDTO dg = new DocGiaDTO(
                        rs.getInt("madocgia"),
                        rs.getString("tendocgia"),
                        rs.getString("gioitinh"),
                        rs.getString("diachi"),
                        rs.getString("sdt"),
                        rs.getInt("TrangThai")
                );
                list.add(dg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(DocGiaDTO dg) {
        String sql = """
            INSERT INTO docgia(madocgia, tendocgia, gioitinh, diachi, sdt, TrangThai)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dg.getMaDocGia());
            ps.setString(2, dg.getTenDocGia());
            ps.setString(3, dg.getGioiTinh());
            ps.setString(4, dg.getDiaChi());
            ps.setString(5, dg.getSdt());
            ps.setInt(6, dg.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(DocGiaDTO dg) {
        String sql = """
            UPDATE docgia
            SET tendocgia=?, gioitinh=?, diachi=?, sdt=?, TrangThai=?
            WHERE madocgia=?
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dg.getTenDocGia());
            ps.setString(2, dg.getGioiTinh());
            ps.setString(3, dg.getDiaChi());
            ps.setString(4, dg.getSdt());
            ps.setInt(5, dg.getTrangThai());
            ps.setInt(6, dg.getMaDocGia());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❗ KHÔNG XÓA – CHỈ KIỂM TRA
    public void checkDelete(int maDocGia) throws Exception {
        String sql = "SELECT COUNT(*) FROM phieumuon WHERE madocgia = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDocGia);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new Exception("Độc giả đang có phiếu mượn, không thể xoá!");
            }
        }
    }
}

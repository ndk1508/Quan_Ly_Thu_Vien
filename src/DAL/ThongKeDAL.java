package DAL;

import DTO.ThongKeDTO;
import java.sql.*;
import java.util.ArrayList;

public class ThongKeDAL {

    // 1. Thống kê phiếu mượn theo tình trạng
    public ArrayList<ThongKeDTO> thongKeTheoTinhTrang() {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        String sql = """
            SELECT TinhTrang, COUNT(*) AS SoLuong
            FROM phieumuon
            GROUP BY TinhTrang
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ThongKeDTO(
                        rs.getString("TinhTrang"),
                        rs.getInt("SoLuong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thống kê độc giả mượn nhiều nhất
    public ArrayList<ThongKeDTO> thongKeDocGiaMuonNhieu() {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        String sql = """
            SELECT dg.tendocgia, COUNT(pm.MaPM) AS SoLuong
            FROM phieumuon pm
            JOIN docgia dg ON pm.MaDocGia = dg.madocgia
            GROUP BY dg.tendocgia
            ORDER BY SoLuong DESC
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ThongKeDTO(
                        rs.getString("tendocgia"),
                        rs.getInt("SoLuong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ThongKeDTO> thongKeTinhTrangChiTiet() {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        String sql = """
            SELECT TinhTrang, COUNT(*) AS SoLuong,
                   COUNT(*) * 100.0 / (SELECT COUNT(*) FROM phieumuon) AS TyLe
            FROM phieumuon
            GROUP BY TinhTrang
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ThongKeDTO(
                        rs.getString("TinhTrang"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("TyLe")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Thống kê phiếu mượn theo tháng
    public ArrayList<ThongKeDTO> thongKeTheoThang() {
        ArrayList<ThongKeDTO> list = new ArrayList<>();
        String sql = """
            SELECT MONTH(NgayMuon) AS Thang, COUNT(*) AS SoLuong
            FROM phieumuon
            GROUP BY MONTH(NgayMuon)
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ThongKeDTO(
                        "Tháng " + rs.getInt("Thang"),
                        rs.getInt("SoLuong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

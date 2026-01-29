package DAL;

import DTO.TacGiaDTO;
import java.sql.*;
import java.util.ArrayList;

public class TacGiaDAL {

    public ArrayList<TacGiaDTO> getAllTacGia() {
        ArrayList<TacGiaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tacgia";
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                TacGiaDTO tg = new TacGiaDTO();
                tg.setMaTacGia(rs.getInt("MaTacGia"));
                tg.setTenTacGia(rs.getString("TenTacGia"));
                tg.setNamSinh(rs.getInt("NamSinh"));
                tg.setQueQuan(rs.getString("QueQuan"));
                list.add(tg);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themTacGia(TacGiaDTO tg) {
        
        String sql = "INSERT INTO tacgia(TenTacGia, NamSinh, QueQuan, TrangThai) VALUES(?, ?, ?, 1)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tg.getTenTacGia());
            ps.setInt(2, tg.getNamSinh());
            ps.setString(3, tg.getQueQuan());           
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaTacGia(TacGiaDTO tg) {
        String sql = "UPDATE tacgia SET TenTacGia=?, NamSinh=?, QueQuan=? WHERE MaTacGia=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tg.getTenTacGia());
            ps.setInt(2, tg.getNamSinh());
            ps.setString(3, tg.getQueQuan());
            ps.setInt(4, tg.getMaTacGia());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaTacGia(int maTacGia) {
        String sql = "DELETE FROM tacgia WHERE MaTacGia=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maTacGia);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<TacGiaDTO> timKiemTacGia(String tuKhoa) {
        ArrayList<TacGiaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tacgia WHERE TenTacGia LIKE ?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TacGiaDTO tg = new TacGiaDTO();
                tg.setMaTacGia(rs.getInt("MaTacGia"));
                tg.setTenTacGia(rs.getString("TenTacGia"));
                tg.setNamSinh(rs.getInt("NamSinh"));
                tg.setQueQuan(rs.getString("QueQuan"));
                list.add(tg);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
package DAL;

import DTO.SachDTO;
import java.sql.*;
import java.util.ArrayList;

public class SachDAL {
    
    // 1. Lấy danh sách sách
    public ArrayList<SachDTO> getAllSach() {
        ArrayList<SachDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM sach";
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                SachDTO s = new SachDTO();
                s.setMaSach(rs.getInt("MaSach"));
                s.setTenSach(rs.getString("TenSach"));
                s.setMaLoai(rs.getInt("MaLoai"));
                s.setMaNXB(rs.getInt("MaNXB"));
                s.setMaTacGia(rs.getInt("MaTacGia"));
                s.setNamXB(rs.getInt("NamXB"));
                s.setSoLuong(rs.getInt("SoLuong"));
                s.setMake(rs.getInt("Make"));
                s.setHinhAnh(rs.getString("HinhAnh"));
                list.add(s);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Tìm kiếm sách
    public ArrayList<SachDTO> timKiemSach(String tuKhoa) {
        ArrayList<SachDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM sach WHERE TenSach LIKE ?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SachDTO s = new SachDTO();
                s.setMaSach(rs.getInt("MaSach"));
                s.setTenSach(rs.getString("TenSach"));
                s.setMaLoai(rs.getInt("MaLoai"));
                s.setMaNXB(rs.getInt("MaNXB"));
                s.setMaTacGia(rs.getInt("MaTacGia"));
                s.setNamXB(rs.getInt("NamXB"));
                s.setSoLuong(rs.getInt("SoLuong"));
                s.setMake(rs.getInt("Make"));
                s.setHinhAnh(rs.getString("HinhAnh"));
                list.add(s);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Thêm sách
    public boolean themSach(SachDTO s) {
        String sql = "INSERT INTO sach(TenSach, MaLoai, MaNXB, MaTacGia, NamXB, SoLuong, Make, HinhAnh) VALUES(?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getTenSach());
            ps.setInt(2, s.getMaLoai());
            ps.setInt(3, s.getMaNXB());
            ps.setInt(4, s.getMaTacGia());
            ps.setInt(5, s.getNamXB());
            ps.setInt(6, s.getSoLuong());
            ps.setInt(7, s.getMake());
            ps.setString(8, s.getHinhAnh());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Sửa sách
    public boolean suaSach(SachDTO s) {
        String sql = "UPDATE sach SET TenSach=?, MaLoai=?, MaNXB=?, MaTacGia=?, NamXB=?, SoLuong=?, Make=?, HinhAnh=? WHERE MaSach=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getTenSach());
            ps.setInt(2, s.getMaLoai());
            ps.setInt(3, s.getMaNXB());
            ps.setInt(4, s.getMaTacGia());
            ps.setInt(5, s.getNamXB());
            ps.setInt(6, s.getSoLuong());
            ps.setInt(7, s.getMake());
            ps.setString(8, s.getHinhAnh());
            ps.setInt(9, s.getMaSach());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa sách
    public boolean xoaSach(int maSach) {
        String sql = "DELETE FROM sach WHERE MaSach=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
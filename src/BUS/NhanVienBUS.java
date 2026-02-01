package BUS;

import DAL.NhanVienDAL;
import DAL.TaiKhoanDAL;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;

public class NhanVienBUS {
    private NhanVienDAL dal = new NhanVienDAL();

    public ArrayList<NhanVienDTO> getAll() {
        return dal.getAll();
    }

    public boolean themNhanVienVaTaiKhoan(NhanVienDTO nv, TaiKhoanDTO tk) {
        TaiKhoanDAL tkDal = new TaiKhoanDAL();
        // 1. Thêm tài khoản trước
        if (tkDal.insert(tk)) {
            // 2. Sau đó thêm nhân viên
            return dal.insert(nv);
        }
        return false;
    }
    
    public boolean sua(NhanVienDTO nv) {
        return dal.update(nv);
    }

    public boolean xoa(String maNV) {
        return dal.delete(maNV);
    }
    public ArrayList<NhanVienDTO> timKiem(String tuKhoa) {
        return dal.search(tuKhoa);
    }
}

package BUS;

import DAL.NhanVienDAL;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBUS {
    private NhanVienDAL dal = new NhanVienDAL();

    public ArrayList<NhanVienDTO> getAll() {
        return dal.getAll();
    }

    public boolean them(NhanVienDTO nv) {
        return dal.insert(nv);
    }

    public boolean sua(NhanVienDTO nv) {
        return dal.update(nv);
    }

    public boolean xoa(String maNV) {
        return dal.delete(maNV);
    }
}

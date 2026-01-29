package BUS;

import DAL.SachDAL;
import DTO.SachDTO;
import java.util.ArrayList;

public class SachBUS {
    SachDAL sachDAL = new SachDAL();

    public ArrayList<SachDTO> getDanhSachSach() {
        return sachDAL.getAllSach();
    }

    public ArrayList<SachDTO> timKiemSach(String tuKhoa) {
        return sachDAL.timKiemSach(tuKhoa);
    }

    public boolean themSach(SachDTO s) {
        return sachDAL.themSach(s);
    }

    public boolean suaSach(SachDTO s) {
        return sachDAL.suaSach(s);
    }

    public boolean xoaSach(int maSach) {
        return sachDAL.xoaSach(maSach);
    }
}
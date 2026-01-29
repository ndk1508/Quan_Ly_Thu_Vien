package BUS;

import DAL.NhaXuatBanDAL;
import DTO.NhaXuatBanDTO;
import java.util.ArrayList;

public class NhaXuatBanBUS {
    NhaXuatBanDAL nxbDAL = new NhaXuatBanDAL();

    public ArrayList<NhaXuatBanDTO> getDanhSachNXB() {
        return nxbDAL.getAllNhaXuatBan();
    }

    public ArrayList<NhaXuatBanDTO> timKiemNXB(String tuKhoa) {
        return nxbDAL.timKiemNhaXuatBan(tuKhoa);
    }

    public boolean themNXB(NhaXuatBanDTO nxb) {
        return nxbDAL.themNhaXuatBan(nxb);
    }

    public boolean suaNXB(NhaXuatBanDTO nxb) {
        return nxbDAL.suaNhaXuatBan(nxb);
    }

    public boolean xoaNXB(int maNXB) {
        return nxbDAL.xoaNhaXuatBan(maNXB);
    }
}
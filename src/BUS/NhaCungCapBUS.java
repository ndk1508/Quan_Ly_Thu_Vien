package BUS;

import DAL.NhaCungCapDAL;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBUS {
    NhaCungCapDAL nccDAL = new NhaCungCapDAL();

    public ArrayList<NhaCungCapDTO> getDanhSachNCC() {
        return nccDAL.getAllNCC();
    }

    public ArrayList<NhaCungCapDTO> timKiemNCC(String tuKhoa) {
        return nccDAL.timKiemNCC(tuKhoa);
    }

    public boolean themNCC(NhaCungCapDTO ncc) {
        return nccDAL.themNCC(ncc);
    }

    public boolean suaNCC(NhaCungCapDTO ncc) {
        return nccDAL.suaNCC(ncc);
    }

    public boolean xoaNCC(int maNCC) {
        return nccDAL.xoaNCC(maNCC);
    }
}
package BUS;

import DAL.PhieuMuonDAL;
import DTO.PhieuMuonDTO;
import java.util.ArrayList;

public class PhieuMuonBUS {

    private PhieuMuonDAL dal = new PhieuMuonDAL();

    public ArrayList<PhieuMuonDTO> getAll() {
        return dal.getAll();
    }

    public boolean them(PhieuMuonDTO pm) {
        if (pm.getMaPM() <= 0) return false;
        return dal.insert(pm);
    }

    public boolean capNhatTinhTrang(int maPM, String tinhTrang) {
        return dal.updateTinhTrang(maPM, tinhTrang);
    }
}

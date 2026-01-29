package BUS;

import DAL.PhieuMuonDAL;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonBUS {

    private PhieuMuonDAL dal = new PhieuMuonDAL();

    public ArrayList<Object[]> getAllView() {
        return dal.getAllView();
    }

    public boolean lapPhieu(int maNV, int maDG, Date ngayMuon) {
        java.sql.Date sqlDate = new java.sql.Date(ngayMuon.getTime());
        return dal.insert(maNV, maDG, sqlDate);
    }

    public boolean danhDauDaTra(int maPM) {
        return dal.capNhatDaTra(maPM);
    }
}

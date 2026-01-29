package BUS;

import DAL.ThongKeDAL;
import DTO.ThongKeDTO;
import java.util.ArrayList;

public class ThongKeBUS {

    private ThongKeDAL dal = new ThongKeDAL();

    public ArrayList<ThongKeDTO> thongKeTheoTinhTrang() {
        return dal.thongKeTheoTinhTrang();
    }

    public ArrayList<ThongKeDTO> thongKeDocGiaMuonNhieu() {
        return dal.thongKeDocGiaMuonNhieu();
    }

    public ArrayList<ThongKeDTO> thongKeTheoThang() {
        return dal.thongKeTheoThang();
    }
    public ArrayList<ThongKeDTO> thongKeTinhTrangChiTiet() {
        return dal.thongKeTinhTrangChiTiet();
    }

}

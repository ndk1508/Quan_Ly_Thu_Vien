package BUS;

import DAL.TacGiaDAL;
import DTO.TacGiaDTO;
import java.util.ArrayList;

public class TacGiaBUS {
    TacGiaDAL tacGiaDAL = new TacGiaDAL();

    public ArrayList<TacGiaDTO> getDanhSachTacGia() {
        return tacGiaDAL.getAllTacGia();
    }

    public ArrayList<TacGiaDTO> timKiemTacGia(String tuKhoa) {
        return tacGiaDAL.timKiemTacGia(tuKhoa);
    }

    public boolean themTacGia(TacGiaDTO tg) {
        return tacGiaDAL.themTacGia(tg);
    }

    public boolean suaTacGia(TacGiaDTO tg) {
        return tacGiaDAL.suaTacGia(tg);
    }

    public boolean xoaTacGia(int maTacGia) {
        return tacGiaDAL.xoaTacGia(maTacGia);
    }
}
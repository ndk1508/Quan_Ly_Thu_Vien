package BUS;
import DAL.ChiTietPhieuNhapDAL;
import DTO.ChiTietPhieuNhapDTO;
import java.util.ArrayList;

public class ChiTietPhieuNhapBUS {
    ChiTietPhieuNhapDAL ctpnDAL = new ChiTietPhieuNhapDAL();
    public ArrayList<ChiTietPhieuNhapDTO> getChiTietByMaPN(int maPN) { return ctpnDAL.getChiTietByMaPN(maPN); }
    public boolean themChiTiet(ChiTietPhieuNhapDTO ct) { return ctpnDAL.themChiTiet(ct); }
    public boolean xoaChiTiet(int maCTPN) { return ctpnDAL.xoaChiTiet(maCTPN); }
}
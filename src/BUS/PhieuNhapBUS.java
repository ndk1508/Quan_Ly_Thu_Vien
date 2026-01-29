package BUS;
import DAL.PhieuNhapDAL;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;

public class PhieuNhapBUS {
    PhieuNhapDAL pnDAL = new PhieuNhapDAL();
    public ArrayList<PhieuNhapDTO> getAllPhieuNhap() { return pnDAL.getAllPhieuNhap(); }
    public boolean themPhieuNhap(PhieuNhapDTO pn) { return pnDAL.themPhieuNhap(pn); }
    public boolean suaPhieuNhap(PhieuNhapDTO pn) { return pnDAL.suaPhieuNhap(pn); }
    public boolean xoaPhieuNhap(int maPN) { return pnDAL.xoaPhieuNhap(maPN); }
}
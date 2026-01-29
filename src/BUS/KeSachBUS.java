package BUS;
import DAL.KeSachDAL;
import DTO.KeSachDTO;
import java.util.ArrayList;

public class KeSachBUS {
    KeSachDAL keDAL = new KeSachDAL();
    public ArrayList<KeSachDTO> getAllKe() { return keDAL.getAllKe(); }
    public boolean themKe(KeSachDTO ke) { return keDAL.themKe(ke); }
    public boolean suaKe(KeSachDTO ke) { return keDAL.suaKe(ke); }
    public boolean xoaKe(int maKe) { return keDAL.xoaKe(maKe); }
}
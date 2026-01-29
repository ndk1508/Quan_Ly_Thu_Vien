package BUS;
import DAL.LoaiDAL;
import DTO.LoaiDTO;
import java.util.ArrayList;

public class LoaiBUS {
    LoaiDAL loaiDAL = new LoaiDAL();
    public ArrayList<LoaiDTO> getAllLoai() { return loaiDAL.getAllLoai(); }
    public boolean themLoai(LoaiDTO loai) { return loaiDAL.themLoai(loai); }
    public boolean suaLoai(LoaiDTO loai) { return loaiDAL.suaLoai(loai); }
    public boolean xoaLoai(int maLoai) { return loaiDAL.xoaLoai(maLoai); }
}
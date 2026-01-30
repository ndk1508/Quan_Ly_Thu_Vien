package BUS;

import DAL.DocGiaDAL;
import DTO.DocGiaDTO;
import java.util.ArrayList;

public class DocGiaBUS {

    private DocGiaDAL dal = new DocGiaDAL();

    public ArrayList<DocGiaDTO> getAll() {
        return dal.getAll();
    }

    public boolean them(DocGiaDTO dg) {
        return dal.insert(dg);
    }

    public boolean sua(DocGiaDTO dg) {
        return dal.update(dg);
    }
    public ArrayList<DocGiaDTO> timKiem(String tuKhoa) {
        return dal.search(tuKhoa); // Gọi xuống DAL
    }

    public String kiemTraXoa(int maDocGia) {
        try {
            dal.checkDelete(maDocGia);
            return "OK"; // có thể xoá (nếu sau này bạn cho xoá)
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}

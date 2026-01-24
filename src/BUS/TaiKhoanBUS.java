package BUS;

import DAL.TaiKhoanDAL;
import DTO.TaiKhoanDTO;

public class TaiKhoanBUS {

    private TaiKhoanDAL tkDAL = new TaiKhoanDAL();

    public TaiKhoanDTO dangNhap(String user, String pass) {
        if (user.isEmpty() || pass.isEmpty()) {
            return null;
        }
        return tkDAL.dangNhap(user, pass);
    }
}

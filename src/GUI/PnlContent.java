package GUI;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class PnlContent extends JPanel {

    private CardLayout cardLayout;

    public PnlContent() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Add các panel chức năng
        add(new PnlSach(), "SACH");
        add(new PnlDocGia(), "DOCGIA");
        add(new PnlNhanVien(), "NHANVIEN");
        add(new PnlTacGia(), "TACGIA");
        add(new PnlNhaXuatBan(), "NXB");
        add(new PnlPhieuMuon(), "PHIEUMUON");
        add(new PnlPhieuNhap(), "PHIEUNHAP");
        add(new PnlThongKe(), "THONGKE");

        // Mặc định hiển thị Sách
        cardLayout.show(this, "SACH");
    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }
}

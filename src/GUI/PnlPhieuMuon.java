package GUI;

import BUS.PhieuMuonBUS;
import BUS.NhanVienBUS;
import BUS.DocGiaBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PnlPhieuMuon extends JPanel {

    private JTextField txtMaPM, txtNgayMuon;
    private JComboBox<String> cboNhanVien, cboDocGia, cboTinhTrang;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnLapPhieu, btnDaTra, btnLamMoi;

    private PhieuMuonBUS pmBUS = new PhieuMuonBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DocGiaBUS dgBUS = new DocGiaBUS();

    public PnlPhieuMuon() {
        setLayout(new BorderLayout(10, 10));
        initForm();
        initTable();
        initButton();
        loadCombo();
        loadData();
        event();
    }

    private void initForm() {
        JPanel pnl = new JPanel(new GridLayout(3, 4, 10, 10));
        pnl.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));

        txtMaPM = new JTextField();
        txtMaPM.setEnabled(false);

        txtNgayMuon = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        cboNhanVien = new JComboBox<>();
        cboDocGia = new JComboBox<>();
        cboTinhTrang = new JComboBox<>(new String[]{"Chưa trả", "Đã trả"});

        pnl.add(new JLabel("Mã PM"));
        pnl.add(txtMaPM);
        pnl.add(new JLabel("Ngày mượn"));
        pnl.add(txtNgayMuon);

        pnl.add(new JLabel("Nhân viên"));
        pnl.add(cboNhanVien);
        pnl.add(new JLabel("Tình trạng"));
        pnl.add(cboTinhTrang);

        pnl.add(new JLabel("Độc giả"));
        pnl.add(cboDocGia);

        add(pnl, BorderLayout.NORTH);
    }

    private void initTable() {
        model = new DefaultTableModel(
            new String[]{"Mã PM", "Nhân viên", "Độc giả", "Ngày mượn", "Tình trạng"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initButton() {
        JPanel pnl = new JPanel();
        btnLapPhieu = new JButton("Lập phiếu");
        btnDaTra = new JButton("Đánh dấu đã trả");
        btnLamMoi = new JButton("Làm mới");

        pnl.add(btnLapPhieu);
        pnl.add(btnDaTra);
        pnl.add(btnLamMoi);
        add(pnl, BorderLayout.SOUTH);
    }

    private void loadCombo() {
        nvBUS.getAll().forEach(nv -> cboNhanVien.addItem(nv.getMaNV() + " - " + nv.getTenNV()));
        dgBUS.getAll().forEach(dg -> cboDocGia.addItem(dg.getMaDocGia() + " - " + dg.getTenDocGia()));
    }

    private void loadData() {
        model.setRowCount(0);
        for (Object[] row : pmBUS.getAllView()) {
            model.addRow(row);
        }
    }

    private void event() {
        btnLapPhieu.addActionListener(e -> {
            int maNV = Integer.parseInt(cboNhanVien.getSelectedItem().toString().split(" - ")[0]);
            int maDG = Integer.parseInt(cboDocGia.getSelectedItem().toString().split(" - ")[0]);

            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayMuon.getText());
                if (pmBUS.lapPhieu(maNV, maDG, date)) {
                    JOptionPane.showMessageDialog(this, "Lập phiếu thành công");
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ngày không hợp lệ");
            }
        });

        btnDaTra.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int maPM = Integer.parseInt(model.getValueAt(row, 0).toString());
            pmBUS.danhDauDaTra(maPM);
            loadData();
        });

        btnLamMoi.addActionListener(e -> loadData());
    }
}

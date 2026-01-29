package GUI;

import BUS.PhieuMuonBUS;
import DTO.PhieuMuonDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class PnlPhieuMuon extends JPanel {

    private JTextField txtMaPM, txtMaNV, txtMaDocGia;
    private JComboBox<String> cboTinhTrang;
    private JButton btnThem, btnTraSach, btnLamMoi;
    private JTable table;
    private DefaultTableModel model;

    private PhieuMuonBUS bus = new PhieuMuonBUS();

    public PnlPhieuMuon() {
        setLayout(new BorderLayout(10, 10));

        initForm();
        initTable();
        initButton();

        loadData();
        event();
    }

    private void initForm() {
        JPanel form = new JPanel(new GridLayout(2, 6, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Phiếu mượn"));

        txtMaPM = new JTextField();
        txtMaNV = new JTextField();
        txtMaDocGia = new JTextField();

        cboTinhTrang = new JComboBox<>(new String[]{"Chưa trả", "Đã trả"});

        form.add(new JLabel("Mã PM"));
        form.add(txtMaPM);
        form.add(new JLabel("Mã NV"));
        form.add(txtMaNV);
        form.add(new JLabel("Mã Độc Giả"));
        form.add(txtMaDocGia);

        form.add(new JLabel("Tình trạng"));
        form.add(cboTinhTrang);

        add(form, BorderLayout.NORTH);
    }

    private void initTable() {
        model = new DefaultTableModel(
                new String[]{"MaPM", "MaNV", "MaDocGia", "Ngày mượn", "Tình trạng"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initButton() {
        JPanel p = new JPanel();

        btnThem = new JButton("Lập phiếu");
        btnTraSach = new JButton("Đánh dấu đã trả");
        btnLamMoi = new JButton("Làm mới");

        p.add(btnThem);
        p.add(btnTraSach);
        p.add(btnLamMoi);

        add(p, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        for (PhieuMuonDTO pm : bus.getAll()) {
            model.addRow(new Object[]{
                    pm.getMaPM(),
                    pm.getMaNV(),
                    pm.getMaDocGia(),
                    pm.getNgayMuon(),
                    pm.getTinhTrang()
            });
        }
    }

    private void event() {

        btnThem.addActionListener(e -> {
            PhieuMuonDTO pm = new PhieuMuonDTO(
                    Integer.parseInt(txtMaPM.getText()),
                    Integer.parseInt(txtMaNV.getText()),
                    Integer.parseInt(txtMaDocGia.getText()),
                    new Date(System.currentTimeMillis()),
                    "Chưa trả",
                    1
            );

            if (bus.them(pm)) {
                loadData();
                clearForm();
            }
        });

        btnTraSach.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int maPM = Integer.parseInt(model.getValueAt(row, 0).toString());
            if (bus.capNhatTinhTrang(maPM, "Đã trả")) {
                loadData();
            }
        });

        btnLamMoi.addActionListener(e -> clearForm());
    }

    private void clearForm() {
        txtMaPM.setText("");
        txtMaNV.setText("");
        txtMaDocGia.setText("");
        cboTinhTrang.setSelectedIndex(0);
        table.clearSelection();
    }
}

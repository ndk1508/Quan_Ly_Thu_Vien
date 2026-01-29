package GUI;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


public class PnlThongKe extends JPanel {
	private JPanel pnlChart;
    private JComboBox<String> cboLoai;
    private JButton btnThongKe;
    private JTable tblThongKe;
    private DefaultTableModel model;
    private ThongKeBUS bus = new ThongKeBUS();

    public PnlThongKe() {
        setLayout(null);
        pnlChart = new JPanel();
        pnlChart.setBounds(650, 70, 400, 300);
        add(pnlChart);

        cboLoai = new JComboBox<>(new String[]{
            "Phiếu mượn theo tình trạng",
            "Độc giả mượn nhiều nhất",
            "Phiếu mượn theo tháng"
        });
        cboLoai.setBounds(30, 20, 250, 30);
        add(cboLoai);

        btnThongKe = new JButton("Thống kê");
        btnThongKe.setBounds(300, 20, 120, 30);
        add(btnThongKe);

        model = new DefaultTableModel(new String[]{"Tên", "Số lượng"}, 0);
        tblThongKe = new JTable(model);
        JScrollPane sp = new JScrollPane(tblThongKe);
        sp.setBounds(30, 70, 600, 300);
        add(sp);

        btnThongKe.addActionListener(e -> thongKe());
    }
    private void veBieuDoTinhTrang() {
        pnlChart.removeAll();

        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<ThongKeDTO> list = bus.thongKeTinhTrangChiTiet();

        for (ThongKeDTO tk : list) {
            dataset.setValue(
                tk.getTen() + " (" + tk.getSoLuong() + ")",
                tk.getSoLuong()
            );
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "TỶ LỆ PHIẾU MƯỢN THEO TÌNH TRẠNG",
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(pnlChart.getSize());

        pnlChart.setLayout(new BorderLayout());
        pnlChart.add(chartPanel, BorderLayout.CENTER);
        pnlChart.validate();
    }

    private void thongKe() {
        model.setRowCount(0);

        int index = cboLoai.getSelectedIndex();

        if (index == 0) {
            ArrayList<ThongKeDTO> list = bus.thongKeTinhTrangChiTiet();
            for (ThongKeDTO tk : list) {
                model.addRow(new Object[]{
                    tk.getTen(),
                    tk.getSoLuong(),
                    String.format("%.2f%%", tk.getTyLe())
                });
            }
            veBieuDoTinhTrang();
        }
    }

}

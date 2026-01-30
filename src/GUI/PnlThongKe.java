package GUI;

import BUS.ThongKeBUS;
import DTO.ThongKeDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PnlThongKe extends JPanel {
    private JPanel pnlChart, pnlTop;
    private JComboBox<String> cboLoai;
    private JButton btnThongKe;
    private JTable tblThongKe;
    private DefaultTableModel model;
    private ThongKeBUS bus = new ThongKeBUS();

    public PnlThongKe() {
        setLayout(new BorderLayout(10, 10));

        // ----- Vùng phía trên: Thanh điều khiển -----
        pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        cboLoai = new JComboBox<>(new String[]{
            "Phiếu mượn theo tình trạng",
            "Độc giả mượn nhiều nhất",
            "Phiếu mượn theo tháng"
        });
        cboLoai.setPreferredSize(new Dimension(250, 30));
        btnThongKe = new JButton("Thống kê");
        btnThongKe.setPreferredSize(new Dimension(120, 30));
        
        pnlTop.add(new JLabel("Loại thống kê:"));
        pnlTop.add(cboLoai);
        pnlTop.add(btnThongKe);
        add(pnlTop, BorderLayout.NORTH);

        // ----- Vùng bên trái: Bảng dữ liệu -----
        model = new DefaultTableModel();
        tblThongKe = new JTable(model);
        JScrollPane sp = new JScrollPane(tblThongKe);
        sp.setPreferredSize(new Dimension(450, 0));
        add(sp, BorderLayout.WEST);

        // ----- Vùng ở giữa: Biểu đồ -----
        pnlChart = new JPanel(new BorderLayout());
        pnlChart.setBorder(BorderFactory.createTitledBorder("Biểu đồ trực quan"));
        add(pnlChart, BorderLayout.CENTER);

        // Sự kiện nút bấm
        btnThongKe.addActionListener(e -> thongKe());
    }

    private void thongKe() {
        model.setRowCount(0);
        int index = cboLoai.getSelectedIndex();

        if (index == 0) {
            // 1. Thống kê tình trạng (Dùng biểu đồ Tròn)
            model.setColumnIdentifiers(new String[]{"Tình trạng", "Số lượng", "Tỷ lệ"});
            ArrayList<ThongKeDTO> list = bus.thongKeTinhTrangChiTiet();
            for (ThongKeDTO tk : list) {
                model.addRow(new Object[]{tk.getTen(), tk.getSoLuong(), String.format("%.2f%%", tk.getTyLe())});
            }
            veBieuDoTron(list);
        } 
        else if (index == 1) {
            // 2. Thống kê Độc giả (Dùng biểu đồ Cột)
            model.setColumnIdentifiers(new String[]{"Tên độc giả", "Số lượt mượn"});
            ArrayList<ThongKeDTO> list = bus.thongKeDocGiaMuonNhieu();
            for (ThongKeDTO tk : list) {
                model.addRow(new Object[]{tk.getTen(), tk.getSoLuong()});
            }
            veBieuDoCot(list, "TOP ĐỘC GIẢ MƯỢN NHIỀU", "Độc giả", "Lượt mượn");
        }
        else if (index == 2) {
            // 3. Thống kê theo Tháng (Dùng biểu đồ Đường hoặc Cột)
            model.setColumnIdentifiers(new String[]{"Tháng", "Số lượng phiếu"});
            ArrayList<ThongKeDTO> list = bus.thongKeTheoThang();
            for (ThongKeDTO tk : list) {
                model.addRow(new Object[]{tk.getTen(), tk.getSoLuong()});
            }
            veBieuDoCot(list, "SỐ LƯỢNG PHIẾU MƯỢN THEO THÁNG", "Tháng", "Số phiếu");
        }
    }

    // Hàm vẽ biểu đồ tròn (Pie Chart)
    private void veBieuDoTron(ArrayList<ThongKeDTO> list) {
        pnlChart.removeAll();
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ThongKeDTO tk : list) {
            dataset.setValue(tk.getTen() + " (" + tk.getSoLuong() + ")", tk.getSoLuong());
        }
        JFreeChart chart = ChartFactory.createPieChart("TỶ LỆ PHIẾU MƯỢN", dataset, true, true, false);
        pnlChart.add(new ChartPanel(chart), BorderLayout.CENTER);
        refreshUI();
    }

    // Hàm vẽ biểu đồ cột (Bar Chart)
    private void veBieuDoCot(ArrayList<ThongKeDTO> list, String title, String xLabel, String yLabel) {
        pnlChart.removeAll();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ThongKeDTO tk : list) {
            dataset.addValue(tk.getSoLuong(), "Số lượng", tk.getTen());
        }
        JFreeChart chart = ChartFactory.createBarChart(title, xLabel, yLabel, dataset, 
                PlotOrientation.VERTICAL, false, true, false);
        pnlChart.add(new ChartPanel(chart), BorderLayout.CENTER);
        refreshUI();
    }

    private void refreshUI() {
        pnlChart.revalidate();
        pnlChart.repaint();
    }
}
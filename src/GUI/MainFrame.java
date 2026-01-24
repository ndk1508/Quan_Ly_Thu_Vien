package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        // 1. Thiết lập tiêu đề và kích thước cho cửa sổ
        setTitle("Chương trình MainFrame Đơn Giản");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng app khi nhấn X
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình

        // 2. Tạo Layout và các thành phần (Components)
        setLayout(new FlowLayout()); 

        JLabel label = new JLabel("Chào mừng bạn đến với Java Swing!");
        JButton button = new JButton("Nhấn vào đây");

        // 3. Xử lý sự kiện khi nhấn nút
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bạn vừa nhấn nút thành công!");
            }
        });

        // 4. Thêm các thành phần vào Frame
        add(label);
        add(button);
    }

    public static void main(String[] args) {
        // Chạy chương trình trong Event Dispatch Thread để đảm bảo an toàn giao diện
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
package GUI;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import javax.swing.*;

public class FrmDangNhap extends JFrame {

    JTextField txtUser = new JTextField();
    JPasswordField txtPass = new JPasswordField();
    JButton btnLogin = new JButton("Đăng nhập");

    TaiKhoanBUS tkBUS = new TaiKhoanBUS();

    public FrmDangNhap() {
        setTitle("Đăng nhập");
        setSize(300, 200);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lb1 = new JLabel("Username:");
        JLabel lb2 = new JLabel("Password:");

        lb1.setBounds(30, 30, 80, 25);
        txtUser.setBounds(120, 30, 130, 25);

        lb2.setBounds(30, 70, 80, 25);
        txtPass.setBounds(120, 70, 130, 25);

        btnLogin.setBounds(90, 110, 100, 30);

        add(lb1); add(txtUser);
        add(lb2); add(txtPass);
        add(btnLogin);

        btnLogin.addActionListener(e -> dangNhap());
    }

    private void dangNhap() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        TaiKhoanDTO tk = tkBUS.dangNhap(user, pass);

        if (tk == null) {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu");
        } else {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công: " + tk.getQuyen());
            new FrmMain(tk).setVisible(true);
            this.dispose();
        }
    }
    public static void main(String args[]) {
    	SwingUtilities.invokeLater(() -> {
            new FrmDangNhap().setVisible(true);
        });
    }
}

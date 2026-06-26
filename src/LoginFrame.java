import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PlayerService playerService;

    public LoginFrame() {
        playerService = new PlayerService();
        setTitle("Tic-Tac-Toe — Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("TIC-TAC-TOE");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(100, 200, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblUser, gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtUsername, gbc);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPass, gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(new Color(100, 200, 100));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        // Enter key juga bisa login
        txtPassword.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Player player = playerService.login(username, password);
        if (player != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + player.getUsername() + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            MainMenuFrame menuFrame = new MainMenuFrame(player);
            menuFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}

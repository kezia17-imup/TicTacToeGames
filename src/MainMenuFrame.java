import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private Player currentPlayer;
    private JButton btnStartGame, btnStatistics, btnTopScorers, btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;
        setTitle("Main Menu — " + player.getUsername());
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblWelcome = new JLabel("Halo, " + player.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setForeground(new Color(100, 200, 255));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblWelcome, gbc);

        JLabel lblScore = new JLabel("Skor: " + player.getScore() + "  |  Menang: " + player.getWins(), SwingConstants.CENTER);
        lblScore.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        panel.add(lblScore, gbc);

        btnStartGame = createButton("🎮  Mulai Permainan", new Color(70, 130, 180));
        gbc.gridy = 2;
        panel.add(btnStartGame, gbc);

        btnStatistics = createButton("📊  Statistik Saya", new Color(60, 120, 60));
        gbc.gridy = 3;
        panel.add(btnStatistics, gbc);

        btnTopScorers = createButton("🏆  Top 5 Skor", new Color(180, 130, 30));
        gbc.gridy = 4;
        panel.add(btnTopScorers, gbc);

        btnExit = createButton("❌  Keluar", new Color(150, 50, 50));
        gbc.gridy = 5;
        panel.add(btnExit, gbc);

        add(panel);

        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin mau keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(250, 45));
        return btn;
    }
}

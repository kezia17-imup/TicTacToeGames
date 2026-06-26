import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {
    public StatisticsFrame(Player player) {
        setTitle("Statistik — " + player.getUsername());
        setSize(350, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("📊 Statistik Kamu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(100, 200, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        String[][] stats = {
                {"Username", player.getUsername()},
                {"Menang (Win)", String.valueOf(player.getWins())},
                {"Kalah (Lose)", String.valueOf(player.getLosses())},
                {"Seri (Draw)", String.valueOf(player.getDraws())},
                {"Total Skor", String.valueOf(player.getScore())}
        };

        Color[] rowColors = {Color.WHITE, new Color(100,255,100), new Color(255,100,100), new Color(255,200,100), new Color(100,200,255)};

        for (int i = 0; i < stats.length; i++) {
            JLabel key = new JLabel(stats[i][0] + ":");
            key.setForeground(Color.LIGHT_GRAY);
            key.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridx = 0; gbc.gridy = i + 1;
            panel.add(key, gbc);

            JLabel val = new JLabel(stats[i][1]);
            val.setForeground(rowColors[i]);
            val.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = 1; gbc.gridy = i + 1;
            panel.add(val, gbc);
        }

        JButton btnClose = new JButton("Tutup");
        btnClose.setBackground(new Color(100, 100, 150));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = stats.length + 1; gbc.gridwidth = 2;
        panel.add(btnClose, gbc);
        btnClose.addActionListener(e -> this.dispose());

        add(panel);
    }
}

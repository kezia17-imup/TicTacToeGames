import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {
    public TopScorersFrame() {
        setTitle("🏆 Top 5 Skor Tertinggi");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 30, 50));

        JLabel lblTitle = new JLabel("🏆  Top 5 Pemain Terbaik", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(255, 200, 50));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Rank", "Username", "Menang", "Kalah", "Seri", "Skor"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        PlayerService playerService = new PlayerService();
        ArrayList<Player> top5 = playerService.getTopFiveScorers();

        String[] medals = {"🥇", "🥈", "🥉", "4", "5"};
        for (int i = 0; i < top5.size(); i++) {
            Player p = top5.get(i);
            model.addRow(new Object[]{
                    medals[i], p.getUsername(), p.getWins(),
                    p.getLosses(), p.getDraws(), p.getScore()
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setBackground(new Color(50, 50, 80));
        table.setForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(70, 70, 110));
        table.getTableHeader().setForeground(Color.WHITE);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnClose = new JButton("Tutup");
        btnClose.setBackground(new Color(100, 100, 150));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 50));
        btnPanel.add(btnClose);
        btnClose.addActionListener(e -> this.dispose());
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
    }
}

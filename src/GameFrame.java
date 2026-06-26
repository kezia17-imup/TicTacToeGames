import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Player currentPlayer;
    private PlayerService playerService;
    private GameLogic gameLogic;
    private JButton[] buttons;
    private JLabel lblStatus;
    private boolean gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic = new GameLogic();
        this.gameOver = false;

        setTitle("Tic-Tac-Toe — " + player.getUsername() + " vs Komputer");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));

        // Status label
        lblStatus = new JLabel("Giliran kamu (X)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        mainPanel.add(lblStatus, BorderLayout.NORTH);

        // Board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(50, 50, 80));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            final int index = i;
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setBackground(new Color(60, 60, 90));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Bottom: info + back button
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(30, 30, 50));

        JLabel lblPlayer = new JLabel("Kamu: X   |   Komputer: O");
        lblPlayer.setForeground(Color.LIGHT_GRAY);
        bottomPanel.add(lblPlayer);

        JButton btnBack = new JButton("Kembali ke Menu");
        btnBack.setBackground(new Color(100, 100, 150));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            if (!gameOver) {
                int confirm = JOptionPane.showConfirmDialog(this, "Game belum selesai. Yakin kembali ke menu?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
            }
            // Refresh player data dari DB sebelum kembali ke menu
            Player refreshed = playerService.getPlayerById(currentPlayer.getId());
            if (refreshed == null) refreshed = currentPlayer;
            MainMenuFrame menuFrame = new MainMenuFrame(refreshed);
            menuFrame.setVisible(true);
            this.dispose();
        });
        bottomPanel.add(btnBack);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // Coba buat gerakan pemain
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            JOptionPane.showMessageDialog(this, "Sel sudah terisi!", "Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update tampilan tombol
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(100, 200, 255));

        // Cek apakah pemain menang
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Cek draw
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Giliran komputer
        lblStatus.setText("Giliran Komputer...");
        int compIdx = gameLogic.computerMove();
        buttons[compIdx].setText("O");
        buttons[compIdx].setForeground(new Color(255, 150, 100));

        // Cek apakah komputer menang
        if (gameLogic.checkWinner('O')) {
            finishGame("LOSE");
            return;
        }

        // Cek draw setelah komputer
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Giliran kamu (X)");
    }

    private void finishGame(String result) {
        gameOver = true;

        // Nonaktifkan semua tombol
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }

        // Update database
        playerService.updateStatistics(currentPlayer, result);

        // Tampilkan hasil
        String msg;
        if (result.equals("WIN")) {
            msg = "🎉 Kamu MENANG! +10 poin";
            lblStatus.setText("Kamu Menang!");
            lblStatus.setForeground(new Color(100, 255, 100));
        } else if (result.equals("LOSE")) {
            msg = "😢 Kamu KALAH! +0 poin";
            lblStatus.setText("Kamu Kalah!");
            lblStatus.setForeground(new Color(255, 100, 100));
        } else {
            msg = "🤝 SERI! +3 poin";
            lblStatus.setText("Seri!");
            lblStatus.setForeground(new Color(255, 200, 100));
        }

        JOptionPane.showMessageDialog(this, msg, "Hasil Permainan", JOptionPane.INFORMATION_MESSAGE);

        // Kembali ke menu dengan data terbaru
        Player refreshed = playerService.getPlayerById(currentPlayer.getId());
        if (refreshed == null) refreshed = currentPlayer;
        MainMenuFrame menuFrame = new MainMenuFrame(refreshed);
        menuFrame.setVisible(true);
        this.dispose();
    }
}

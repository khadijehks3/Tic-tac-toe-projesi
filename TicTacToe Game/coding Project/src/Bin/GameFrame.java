package Bin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class GameFrame extends JFrame {

    int boardWidth = 600;
    int boardHeight = 650; // 50px for the text panel on top

    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer;
    String aiPlayer = "O"; // AI player symbol

    boolean gameOver = false;
    int turns = 0;
    boolean isPvE;

    public GameFrame(boolean isPvE) {
        this.isPvE = isPvE;

        setTitle("Tic-Tac-Toe ( Made by : Khadijeh safi )");
        setSize(boardWidth, boardHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        add(boardPanel);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new TileActionListener(tile)); 
            }
        }

        JButton restartButton = new JButton("Yeniden başlat");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        
        JButton gameMenuButton = new JButton("Ana menü");
        gameMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current game frame
                dispose(); 

                // Re-open the GameMenu
                new GameMenu(); 
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(restartButton);
        buttonPanel.add(gameMenuButton);

        add(buttonPanel, BorderLayout.SOUTH);
        if (isPvE) {
            currentPlayer = playerX; 
        } else {
            currentPlayer = playerX; 
        }

        setVisible(true); 

        if (isPvE && currentPlayer == aiPlayer) { 
            aiMove(); 
        }
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;
        textLabel.setText("Tic-Tac-Toe");
    }

    void checkWinner() {
        // Check for horizontal wins
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "" ||
                board[r][0].getText() != board[r][1].getText() ||
                board[r][1].getText() != board[r][2].getText()) {
                continue;
            }
            for (int i = 0; i < 3; i++) {
                setWinner(board[r][i]);
            }
            gameOver = true;
            return;
        }

        // Check for vertical wins
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "" ||
                board[0][c].getText() != board[1][c].getText() ||
                board[1][c].getText() != board[2][c].getText()) {
                continue;
            }
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][c]);
            }
            gameOver = true;
            return;
        }

        // Check for diagonal wins
        if (board[0][0].getText() != "" &&
            board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText()) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        if (board[0][2].getText() != "" &&
            board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText()) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " kazandı!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Beraberlik!");
    }

    void aiMove() {
        // Simple AI: Random move
        while (true) {
            int row = (int) (Math.random() * 3);
            int col = (int) (Math.random() * 3);
            if (board[row][col].getText().equals("")) {
                board[row][col].setText(aiPlayer);
                turns++;
                checkWinner();
                currentPlayer = playerX; // Switch back to player
                break;
            }
        }
    }

    private class TileActionListener implements ActionListener {
        private JButton tile;

        public TileActionListener(JButton tile) {
            this.tile = tile;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;
            if (tile.getText() == "") {
                tile.setText(currentPlayer);
                turns++;
                checkWinner(); 
                if (!gameOver) {
                    if (isPvE && currentPlayer == playerX) { 
                        currentPlayer = aiPlayer; 
                    } else {
                        currentPlayer = currentPlayer == playerX ? playerO : playerX; 
                    }
                    textLabel.setText("Oyuncu "+currentPlayer+"'in sırası");

                    if (isPvE && currentPlayer == aiPlayer) { 
                        aiMove(); 
                    }
                }
            }
        }
    }
}
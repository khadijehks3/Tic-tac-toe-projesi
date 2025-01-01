package Bin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameMenu extends JFrame {

    private JButton pvpButton, pveButton;

    public GameMenu() {
        setTitle("Tic-Tac-Toe - Game Mode");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        pvpButton = new JButton("Player vs. Player");
        pveButton = new JButton("Player vs. AI");

        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the menu frame
                new GameFrame(false); // Start PvP game
            }
        });

        pveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the menu frame
                new GameFrame(true); // Start PvE game
            }
        });

        add(pvpButton);
        add(pveButton);

        setVisible(true);
    }
}
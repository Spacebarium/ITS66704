package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialUI extends JPanel{

        public InitialUI(CardLayout cardLayout, JPanel mainPanel, GamePanel gp){
            JButton startButton = new JButton("Start Game");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(mainPanel, "GamePanel");
                    gp.startGameThread();
                }
            });

            add(startButton, BorderLayout.CENTER);
        }
    }


package io.github.zacklukem.trump;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        MarkovChain trump = new MarkovChain();
        File file = new File("trump_revised.txt");
        Scanner scanner = new Scanner(new FileInputStream(file));
        while(scanner.hasNextLine()) {
            trump.train(scanner.nextLine());
        }
        JFrame frame = new JFrame("Trump Analyser");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel label = new JLabel(String.format("<html><div WIDTH=%d>%s</div><html>", 750, trump.generate()));
        JButton button = new JButton("Generate");
        button.addActionListener(e -> {
            label.setText(String.format("<html><div WIDTH=%d>%s</div><html>", 750, trump.generate()));
        });
        panel.add(label);
        panel.add(button);
        frame.add(panel);
        frame.setSize(new Dimension(800, 150));
    }
}

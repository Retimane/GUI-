package main;

import javax.swing.*;
import items.Item;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import containers.BigContainer;
import containers.Container;
import containers.SmallContainer;
import methods.Calculation;

public class GUI {

    public static void main(String[] args) {
        SmallContainer smallContainer = new SmallContainer();
        BigContainer bigContainer = new BigContainer();
        
        // Create a list to store the ordered items
        List<Item> items = new ArrayList<Item>();

        // Create the frame
        JFrame frame = new JFrame("Shipment Calculation App");
        frame.setDefaultCloseOperation();
        frame.setSize(400, 300);
        frame.getContentPane().setBackground(Color.WHITE);

        // Create the panel and set the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        // Create the input labels and text fields
        JLabel item1Label = new JLabel("Laptop Quantity:");
        JTextField item1Field = new JTextField(10);
        JLabel item2Label = new JLabel("Mouse Quantity:");
        JTextField item2Field = new JTextField(10);
        JLabel item3Label = new JLabel("Desktop Quantity:");
        JTextField item3Field = new JTextField(10);
        JLabel item4Label = new JLabel("LCD Quantity:");
        JTextField item4Field = new JTextField(10);

        // Create the calculate button and result label
        JButton calculateButton = new JButton("Calculate");
        JLabel resultLabel = new JLabel();
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(Color.BLUE);

        // Set up the GridBagConstraint for layout control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel
        panel.add(item1Label, gbc);
        gbc.gridx++;
        panel.add(item1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(item2Label, gbc);
        gbc.gridx++;
        panel.add(item2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(item3Label, gbc);
        gbc.gridx++;
        panel.add(item3Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(item4Label, gbc);
        gbc.gridx++;
        panel.add(item4Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(calculateButton, gbc);

        gbc.gridy++;
        panel.add(resultLabel, gbc);

        // Create a scroll pane and add the panel to it
        JScrollPane scrollPane = new JScrollPane(panel);

        // Add the scroll pane to the frame
        frame.getContentPane().add(scrollPane);

        // Action listener for the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse item quantities from text fields
                    int item1Quantity = Integer.parseInt(item1Field.getText());
                    int item2Quantity = Integer.parseInt(item2Field.getText());
                    int item3Quantity = Integer.parseInt(item3Field.getText());
                    int item4Quantity = Integer.parseInt(item4Field.getText());

                    // Clear previous items
                    items.clear();

                    // Add new items with their respective quantities and dimensions
                    items.add(new Item(item1Quantity, 60,

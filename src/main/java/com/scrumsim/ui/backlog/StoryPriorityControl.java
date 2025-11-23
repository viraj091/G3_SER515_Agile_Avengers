package com.scrumsim.ui.backlog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StoryPriorityControl extends JPanel {

    private final JLabel priorityLabel;
    private final JButton upButton;
    private final JButton downButton;

    public StoryPriorityControl(int currentPriority) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setBackground(Color.WHITE);

        // Priority label
        priorityLabel = new JLabel("Priority: " + currentPriority);
        priorityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        priorityLabel.setForeground(new Color(100, 100, 100));

        // Up button
        upButton = new JButton("▲");
        upButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        upButton.setMargin(new Insets(2, 6, 2, 6));
        upButton.setToolTipText("Increase priority");

        // Down button
        downButton = new JButton("▼");
        downButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
        downButton.setMargin(new Insets(2, 6, 2, 6));
        downButton.setToolTipText("Decrease priority");

        // Add components
        add(priorityLabel);
        add(upButton);
        add(downButton);
    }

    public void setPriority(int priority) {
        priorityLabel.setText("Priority: " + priority);
    }
    public void addUpButtonListener(ActionListener listener) {
        upButton.addActionListener(listener);
    }

    public void addDownButtonListener(ActionListener listener) {
        downButton.addActionListener(listener);
    }

    public void setUpButtonEnabled(boolean enabled) {
        upButton.setEnabled(enabled);
    }

 
    public void setDownButtonEnabled(boolean enabled) {
        downButton.setEnabled(enabled);
    }
}

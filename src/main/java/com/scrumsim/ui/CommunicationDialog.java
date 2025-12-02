package com.scrumsim.ui;

import com.scrumsim.model.CommunicationEntry;
import com.scrumsim.service.CommunicationService;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommunicationDialog extends JDialog {

    private final CommunicationService communicationService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CommunicationDialog(Frame parent, CommunicationService communicationService) {
        super(parent, "Stakeholder Communications", true);
        this.communicationService = communicationService;
        initializeUI();
    }

    private void initializeUI() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("All Communications");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<CommunicationEntry> communications = communicationService.getAll();

        if (communications.isEmpty()) {
            listModel.addElement("No communications yet.");
        } else {
            for (CommunicationEntry entry : communications) {
                String formatted = String.format("[%s] %s: %s",
                    entry.getTimestamp().format(FORMATTER),
                    entry.getStakeholderName(),
                    entry.getMessage());
                listModel.addElement(formatted);
            }
        }

        JList<String> communicationList = new JList<>(listModel);
        communicationList.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        communicationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(communicationList);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
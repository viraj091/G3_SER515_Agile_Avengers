package com.scrumsim.ui;

import com.scrumsim.store.Session;
import com.scrumsim.store.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SessionStatusPanel extends JPanel {

    private final SessionManager sessionManager;
    private String currentSessionId;

    private final JLabel sessionIdLabel;
    private final JLabel userIdLabel;
    private final JLabel createdAtLabel;
    private final JLabel expiresAtLabel;
    private final JLabel statusLabel;
    private final JLabel timeRemainingLabel;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public SessionStatusPanel(SessionManager sessionManager, String currentSessionId) {
        if (sessionManager == null) {
            throw new IllegalArgumentException("SessionManager cannot be null");
        }
        if (currentSessionId == null || currentSessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }

        this.sessionManager = sessionManager;
        this.currentSessionId = currentSessionId;

        sessionIdLabel = new JLabel();
        userIdLabel = new JLabel();
        createdAtLabel = new JLabel();
        expiresAtLabel = new JLabel();
        statusLabel = new JLabel();
        timeRemainingLabel = new JLabel();

        setupUI();
        updateSessionInfo();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Session Status"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel infoPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        infoPanel.add(new JLabel("Session ID:"));
        infoPanel.add(sessionIdLabel);

        infoPanel.add(new JLabel("User ID:"));
        infoPanel.add(userIdLabel);

        infoPanel.add(new JLabel("Created At:"));
        infoPanel.add(createdAtLabel);

        infoPanel.add(new JLabel("Expires At:"));
        infoPanel.add(expiresAtLabel);

        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(statusLabel);

        infoPanel.add(new JLabel("Time Remaining:"));
        infoPanel.add(timeRemainingLabel);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateSessionInfo());

        JButton newSessionButton = new JButton("New Session");
        newSessionButton.addActionListener(e -> createNewSession());

        JButton cleanupButton = new JButton("Cleanup Expired");
        cleanupButton.addActionListener(e -> cleanupExpiredSessions());

        buttonPanel.add(refreshButton);
        buttonPanel.add(newSessionButton);
        buttonPanel.add(cleanupButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateSessionInfo() {
        sessionManager.getSession(currentSessionId).ifPresentOrElse(
            session -> displaySessionData(session),
            () -> displayNoSession()
        );
    }

   
    private void displaySessionData(Session session) {

        String shortId = session.getSessionId().substring(0, Math.min(8, session.getSessionId().length())) + "...";
        sessionIdLabel.setText(shortId);
        sessionIdLabel.setToolTipText(session.getSessionId()); // This shows Full ID on hover

        userIdLabel.setText(session.getUserId());
        createdAtLabel.setText(session.getCreatedAt().format(timeFormatter));
        expiresAtLabel.setText(session.getExpiresAt().format(timeFormatter));

        // Determine status and color
        boolean isValid = session.isValid();
        statusLabel.setText(isValid ? "ACTIVE" : "EXPIRED");
        statusLabel.setForeground(isValid ? new Color(0, 128, 0) : Color.RED);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));

        // Calculate time remaining
        if (isValid) {
            Duration remaining = Duration.between(LocalDateTime.now(), session.getExpiresAt());
            long minutes = remaining.toMinutes();
            long seconds = remaining.getSeconds() % 60;
            timeRemainingLabel.setText(String.format("%d min %d sec", minutes, seconds));
            timeRemainingLabel.setForeground(minutes < 5 ? Color.ORANGE : Color.BLACK);
        } else {
            timeRemainingLabel.setText("Expired");
            timeRemainingLabel.setForeground(Color.RED);
        }
    }

    
    private void displayNoSession() {
        sessionIdLabel.setText("N/A");
        userIdLabel.setText("N/A");
        createdAtLabel.setText("N/A");
        expiresAtLabel.setText("N/A");
        statusLabel.setText("NO SESSION");
        statusLabel.setForeground(Color.RED);
        timeRemainingLabel.setText("N/A");
    }

    
    private void createNewSession() {
        sessionManager.invalidate(currentSessionId);

        // Create a new session with 30-minute timeout
        Session newSession = sessionManager.createSession("default-user", 30);
        currentSessionId = newSession.getSessionId();

        // Refresh the display
        updateSessionInfo();

        JOptionPane.showMessageDialog(this,
            "New session created!\nSession ID: " + newSession.getSessionId(),
            "Session Created",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void cleanupExpiredSessions() {
        int removedCount = sessionManager.cleanupExpired();

        JOptionPane.showMessageDialog(this,
            "Expired sessions removed: " + removedCount + "\n" +
            "Total sessions remaining: " + sessionManager.getSessionCount(),
            "Cleanup Complete",
            JOptionPane.INFORMATION_MESSAGE);

        // Refresh display in case current session was removed
        updateSessionInfo();
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }
}

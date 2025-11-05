package com.scrumsim.ui;

import com.scrumsim.model.Member;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Factory for creating team member card UI components.
 * Follows SRP by only handling member card creation.
 */
public class MemberCardFactory {

    /**
     * Create a visual card for a team member.
     * @param member The member to create a card for
     * @return A JPanel representing the member card
     */
    public JPanel createMemberCard(Member member) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        // Avatar with initials
        JLabel avatar = createAvatar(member);

        // Member info
        JPanel info = createMemberInfo(member);

        // Online status indicator
        JLabel statusDot = createStatusIndicator(member);

        // Layout
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(avatar);
        left.add(info);

        card.add(left, BorderLayout.WEST);
        card.add(statusDot, BorderLayout.EAST);
        return card;
    }

    /**
     * Create an avatar label with member initials.
     */
    private JLabel createAvatar(Member member) {
        JLabel avatar = new JLabel(member.getInitials(), SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(new Color(230, 240, 255));
        avatar.setForeground(new Color(50, 80, 200));
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return avatar;
    }

    /**
     * Create the member information panel with name and role.
     */
    private JPanel createMemberInfo(Member member) {
        JLabel name = new JLabel(member.getName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel role = new JLabel(member.getRole());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        role.setForeground(Color.GRAY);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(name);
        info.add(role);
        return info;
    }

    /**
     * Create an online/offline status indicator.
     */
    private JLabel createStatusIndicator(Member member) {
        JLabel statusDot = new JLabel(member.isOnline() ? "\u25CF" : "\u25CB");
        statusDot.setForeground(member.isOnline() ? Color.GREEN : Color.LIGHT_GRAY);
        return statusDot;
    }
}

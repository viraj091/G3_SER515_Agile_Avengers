package com.scrumsim.ui;

import com.scrumsim.model.Member;
import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import com.scrumsim.navigation.Navigator;
import com.scrumsim.service.ProgressCalculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrumSimulationPanel extends JPanel {

    private final Navigator navigator;
    private final ProgressCalculator progressCalculator;
    private final String teamName;

    private final List<Story> stories;
    private final JLabel progressLabel;

    private final StoryCardFactory storyCardFactory;
    private final MemberCardFactory memberCardFactory;

    private static final int SPRINT_GOAL = 30;

    public ScrumSimulationPanel(Navigator navigator, String teamName, ProgressCalculator progressCalculator) {
        this.navigator = navigator;
        this.teamName = teamName;
        this.progressCalculator = progressCalculator;
        this.stories = initializeStories();

        this.storyCardFactory = new StoryCardFactory(this::updateProgress);
        this.memberCardFactory = new MemberCardFactory();

        this.progressLabel = new JLabel("", SwingConstants.CENTER);
        this.progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        this.progressLabel.setForeground(Color.DARK_GRAY);

        initializeUI();
        updateProgress();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 248, 250));

        add(createHeader(), BorderLayout.NORTH);

        add(createContentArea(), BorderLayout.CENTER);

        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Scrum Simulation Tool - " + teamName, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        header.add(title, BorderLayout.NORTH);
        header.add(progressLabel, BorderLayout.SOUTH);

        return header;
    }

    private JSplitPane createContentArea() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);

        splitPane.setLeftComponent(new JScrollPane(createBacklogPanel()));

        splitPane.setRightComponent(new JScrollPane(createTeamMembersPanel()));

        return splitPane;
    }

    private JPanel createBacklogPanel() {
        JPanel backlog = new JPanel();
        backlog.setLayout(new BoxLayout(backlog, BoxLayout.Y_AXIS));
        backlog.setBorder(new EmptyBorder(10, 10, 10, 10));
        backlog.setOpaque(false);

        JLabel backlogHeader = new JLabel("Product Backlog  " + stories.size() + " stories");
        backlogHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backlog.add(backlogHeader);
        backlog.add(Box.createVerticalStrut(10));

        for (Story story : stories) {
            backlog.add(storyCardFactory.createStoryCard(story));
            backlog.add(Box.createVerticalStrut(8));
        }

        return backlog;
    }

    private JPanel createTeamMembersPanel() {
        JPanel membersPanel = new JPanel();
        membersPanel.setLayout(new BoxLayout(membersPanel, BoxLayout.Y_AXIS));
        membersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        membersPanel.setOpaque(false);

        List<Member> members = initializeMembers();

        long onlineCount = members.stream().filter(Member::isOnline).count();
        JLabel memberHeader = new JLabel("Team Members  " + onlineCount + " online  " + members.size() + " total");
        memberHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        membersPanel.add(memberHeader);
        membersPanel.add(Box.createVerticalStrut(10));

        for (Member member : members) {
            membersPanel.add(memberCardFactory.createMemberCard(member));
            membersPanel.add(Box.createVerticalStrut(6));
        }

        return membersPanel;
    }

    private JButton createFooter() {
        JButton backBtn = new JButton(" Back to Team Management");
        backBtn.addActionListener(e -> navigator.showTeamManagement());
        return backBtn;
    }

    private void updateProgress() {
        String progressText = progressCalculator.getProgressMessage(stories, SPRINT_GOAL);
        progressLabel.setText(progressText);
    }

    private List<Story> initializeStories() {
        return new ArrayList<>(Arrays.asList(
                new Story("Implement user authentication system", StoryStatus.IN_PROGRESS, 8, "Sarah Chen, Mike Johnson"),
                new Story("Design dashboard UI components", StoryStatus.TODO, 5, "Emma Wilson"),
                new Story("Setup CI/CD pipeline", StoryStatus.DONE, 13, "Alex Rodriguez, Sarah Chen"),
                new Story("Create API documentation", StoryStatus.NEW, 3, "Mike Johnson")
        ));
    }

    private List<Member> initializeMembers() {
        return Arrays.asList(
                new Member("SC", "Sarah Chen", "Product Owner", true),
                new Member("MJ", "Mike Johnson", "Developer", true),
                new Member("EW", "Emma Wilson", "Designer", false),
                new Member("AR", "Alex Rodriguez", "Developer", true),
                new Member("LA", "Lisa Anderson", "Scrum Master", true)
        );
    }
}

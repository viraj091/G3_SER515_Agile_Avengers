package com.scrumsim.ui;

import com.scrumsim.model.Member;
import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import com.scrumsim.navigation.FrameNavigator;
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

        // Updated to use Consumer<Story> callback for edit functionality
        this.storyCardFactory = new StoryCardFactory(this::onEditStory);
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

        // Create top section with Session button on left and title in center
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        // Session button in top-left
        JButton sessionBtn = new JButton("Session");
        sessionBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sessionBtn.addActionListener(e -> showSessionDialog());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(sessionBtn);

        // Title in center
        JLabel title = new JLabel("Scrum Simulation Tool - " + teamName, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        topSection.add(leftPanel, BorderLayout.WEST);
        topSection.add(title, BorderLayout.CENTER);

        header.add(topSection, BorderLayout.NORTH);
        header.add(progressLabel, BorderLayout.SOUTH);

        return header;
    }

    private void showSessionDialog() {
        // Get session information from navigator
        if (navigator instanceof FrameNavigator) {
            FrameNavigator frameNavigator = (FrameNavigator) navigator;

            // Create dialog to show session status
            JDialog sessionDialog = new JDialog();
            sessionDialog.setTitle("Session Status");
            sessionDialog.setModal(false);
            sessionDialog.setSize(500, 350);
            sessionDialog.setLocationRelativeTo(this);

            // Create session status panel
            SessionStatusPanel sessionPanel = new SessionStatusPanel(
                frameNavigator.getSessionManager(),
                frameNavigator.getCurrentSessionId()
            );

            sessionDialog.add(sessionPanel);
            sessionDialog.setVisible(true);
        }
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

        JLabel backlogHeader = new JLabel("Product Backlog  " + stories.size() + " User Stories");
        backlogHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backlog.add(backlogHeader);
        backlog.add(Box.createVerticalStrut(10));

        for (Story story : stories) {
            backlog.add(storyCardFactory.createStoryCard(story));
            backlog.add(Box.createVerticalStrut(8));
        }

        // Add More button at the bottom-right of the backlog section
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.setOpaque(false);
        JButton addMoreButton = new JButton("Add More");
        addMoreButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addMoreButton.addActionListener(e -> onAddStory());
        addButtonPanel.add(addMoreButton);
        backlog.add(addButtonPanel);

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


    private void onEditStory(Story story) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        UserStoryEditDialog dialog = new UserStoryEditDialog(parentFrame, story);
        dialog.setVisible(true);

        // Refresh the UI if the user clicked Save
        if (dialog.wasSaved()) {
            refreshUI();
        }
    }

    private void onAddStory() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        UserStoryEditDialog dialog = new UserStoryEditDialog(parentFrame);
        dialog.setVisible(true);

        // Add the new story if the user clicked Save
        if (dialog.wasSaved()) {
            stories.add(dialog.getStory());
            refreshUI();
        }
    }

    private void refreshUI() {
        removeAll();

        // Recreate all components
        add(createHeader(), BorderLayout.NORTH);
        add(createContentArea(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        // Update progress with new story data
        updateProgress();

        // Repaint the panel
        revalidate();
        repaint();
    }

    private List<Story> initializeStories() {
        return new ArrayList<>(Arrays.asList(
                new Story("Implement user authentication system", StoryStatus.IN_PROGRESS, 8, "Sairaj Dalvi, Pranav Irlapale"),
                new Story("Design dashboard UI components", StoryStatus.TODO, 5, "Gunjan Purohit"),
                new Story("Setup CI/CD pipeline", StoryStatus.DONE, 13, "Shreyas Revankar, Viraj Rathor"),
                new Story("Create API documentation", StoryStatus.NEW, 3, "Viraj Rathor")
        ));
    }

    private List<Member> initializeMembers() {
        return Arrays.asList(
                new Member("SD", "Sairaj Dalvi", "Product Owner", true),
                new Member("PI", "Pranav Irlapale", "Developer", true),
                new Member("GP", "Gunjan Purohit", "Designer", false),
                new Member("SR", "Shreyas Revankar", "Developer", true),
                new Member("VR", "Viraj Rathor", "Scrum Master", true)
        );
    }
}

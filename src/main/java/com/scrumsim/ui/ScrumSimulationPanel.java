package com.scrumsim.ui;

import com.scrumsim.model.Member;
import com.scrumsim.model.Story;
//import com.scrumsim.model.StoryStatus;
import com.scrumsim.model.User;
import com.scrumsim.navigation.Navigator;
import com.scrumsim.service.ProgressCalculator;
import com.scrumsim.service.BacklogService;
import com.scrumsim.service.DefaultBacklogService;
import com.scrumsim.service.StakeholderInputService;
import com.scrumsim.service.DefaultStakeholderInputService;
import com.scrumsim.service.StakeholderFeedbackService;
import com.scrumsim.service.DefaultStakeholderFeedbackService;
import com.scrumsim.service.BusinessValueService;
import com.scrumsim.service.DefaultBusinessValueService;
import com.scrumsim.service.CommunicationService;
import com.scrumsim.service.DefaultCommunicationService;
import com.scrumsim.service.StoryService;
import com.scrumsim.service.DefaultStoryService;
import com.scrumsim.service.StoryUpdateGuard;
import com.scrumsim.service.DefaultStoryUpdateGuard;
import com.scrumsim.service.StatusRefreshService;
import com.scrumsim.service.DefaultStatusRefreshService;
import com.scrumsim.repository.InMemoryStoryRepository;
import com.scrumsim.repository.StoryRepository;
import com.scrumsim.repository.StakeholderFeedbackRepository;
import com.scrumsim.repository.InMemoryStakeholderFeedbackRepository;
import com.scrumsim.store.InMemoryDataStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrumSimulationPanel extends JPanel {

    private final Navigator navigator;
    private final ProgressCalculator progressCalculator;
    private final String teamName;
    private final User currentUser;

    private List<Story> stories;
    private final JLabel progressLabel;

    private final StoryCardFactory storyCardFactory;
    private final MemberCardFactory memberCardFactory;
    private final RolePermissionManager rolePermissionManager;
    private final BacklogService backlogService;
    private final BusinessValueService businessValueService;
    private final StoryService storyService;
    private final StatusRefreshService refreshService;
    private final StorySelectionManager selectionManager;
    private boolean multiSelectMode;

    private static final int SPRINT_GOAL = 30;
    private static final StoryRepository sharedStoryRepository = new InMemoryStoryRepository();
    private static final InMemoryDataStore<String, String> sharedDataStore = new InMemoryDataStore<>();

    public ScrumSimulationPanel(Navigator navigator, String teamName, ProgressCalculator progressCalculator, User currentUser) {
        this.navigator = navigator;
        this.teamName = teamName;
        this.progressCalculator = progressCalculator;
        this.currentUser = currentUser;
        this.stories = initializeStories();

        this.storyCardFactory = new StoryCardFactory(this::onEditStory);
        this.memberCardFactory = new MemberCardFactory();
        this.rolePermissionManager = new RolePermissionManager();
        this.selectionManager = new StorySelectionManager();
        this.multiSelectMode = false;

        this.backlogService = new DefaultBacklogService(sharedStoryRepository);
        this.businessValueService = new DefaultBusinessValueService(sharedStoryRepository);
        StoryUpdateGuard guard = new DefaultStoryUpdateGuard();
        this.storyService = new DefaultStoryService(sharedStoryRepository, guard);

        this.refreshService = new DefaultStatusRefreshService();
        this.refreshService.addListener(this::handleStatusRefresh);
        this.refreshService.start();

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

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);

        JButton backlogBtn = new JButton("Backlog");
        backlogBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        backlogBtn.addActionListener(e -> showBacklogDialog());
        leftPanel.add(backlogBtn);

        JButton multiSelectBtn = new JButton("Multi-Select");
        multiSelectBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        multiSelectBtn.addActionListener(e -> toggleMultiSelectMode(multiSelectBtn));
        leftPanel.add(multiSelectBtn);

        JLabel title = new JLabel("Scrum Simulation Tool - " + teamName, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        JButton myWorkBtn = new JButton("My Work");
        myWorkBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        myWorkBtn.addActionListener(e -> showMyWorkDialog());
        rightPanel.add(myWorkBtn);

        JButton giveInputBtn = new JButton("Give Input");
        giveInputBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        giveInputBtn.addActionListener(e -> showStakeholderInputDialog());
        rightPanel.add(giveInputBtn);

        if (rolePermissionManager.shouldShowButton(currentUser, "Assign Story")) {
            JButton assignStoryBtn = new JButton("Assign Story");
            assignStoryBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            assignStoryBtn.addActionListener(e -> showAssignStoryDialog());
            rolePermissionManager.applyButtonPermission(assignStoryBtn, currentUser, "Assign Story");
            rightPanel.add(assignStoryBtn);
        }

        if (rolePermissionManager.shouldShowButton(currentUser, "Review Business Value")) {
            JButton reviewBVBtn = new JButton("Review Business Value");
            reviewBVBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            reviewBVBtn.addActionListener(e -> showReviewBusinessValueDialog());
            rolePermissionManager.applyButtonPermission(reviewBVBtn, currentUser, "Review Business Value");
            rightPanel.add(reviewBVBtn);
        }

        if (currentUser.isProductOwner()) {
            JButton viewCommBtn = new JButton("View Communication");
            viewCommBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            viewCommBtn.addActionListener(e -> showCommunicationDialog());
            rightPanel.add(viewCommBtn);
        }

        topSection.add(leftPanel, BorderLayout.WEST);
        topSection.add(title, BorderLayout.CENTER);
        topSection.add(rightPanel, BorderLayout.EAST);

        header.add(topSection, BorderLayout.NORTH);
        header.add(progressLabel, BorderLayout.SOUTH);

        return header;
    }

    private void showAssignStoryDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        AssignStoryDialog dialog = new AssignStoryDialog(parentFrame, stories);
        dialog.setVisible(true);
        refreshUI();
    }

    private void showMyWorkDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        MyWorkDialog dialog = new MyWorkDialog(parentFrame, sharedStoryRepository, storyService, currentUser);
        dialog.setVisible(true);
    }

    private void showStakeholderInputDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        StakeholderFeedbackRepository repository = new InMemoryStakeholderFeedbackRepository();
        StakeholderFeedbackService feedbackService = new DefaultStakeholderFeedbackService(repository);
        StakeholderInputService inputService = new DefaultStakeholderInputService(feedbackService);
        CommunicationService commService = new DefaultCommunicationService(sharedDataStore);
        StakeholderInputDialog dialog = new StakeholderInputDialog(parentFrame, inputService, commService, currentUser.getName(), stories);
        dialog.setVisible(true);
    }

    private void showReviewBusinessValueDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        ReviewBusinessValueDialog dialog = new ReviewBusinessValueDialog(parentFrame, stories, businessValueService);
        dialog.setVisible(true);
        refreshUI();
    }

    private void showCommunicationDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        CommunicationService commService = new DefaultCommunicationService(sharedDataStore);
        CommunicationDialog dialog = new CommunicationDialog(parentFrame, commService);
        dialog.setVisible(true);
    }

    private void showBacklogDialog() {
        System.out.println("\n--- SPRINT STORIES (Current Sprint) ---");
        for (Story story : stories) {
            System.out.println("ID: " + story.getId() + " | Title: " + story.getTitle() + " | Points: " + story.getPoints());
        }

        List<Story> backlogStories = backlogService.getBacklogStories(stories);

        System.out.println("\n--- BACKLOG STORIES (Not in Sprint) ---");
        if (backlogStories.isEmpty()) {
            System.out.println("(No backlog stories)");
        } else {
            for (Story story : backlogStories) {
                System.out.println("ID: " + story.getId() + " | Title: " + story.getTitle() + " | Points: " + story.getPoints());
            }
        }

        System.out.println("\n--- SUMMARY ---");
        System.out.println("Total Sprint Stories: " + stories.size());
        System.out.println("Total Backlog Stories: " + backlogStories.size());

        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        BacklogDialog dialog = new BacklogDialog(parentFrame, backlogService, sharedStoryRepository);
        dialog.setVisible(true);
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

        JLabel backlogHeader = new JLabel(stories.size() + " User Stories");
        backlogHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backlog.add(backlogHeader);
        backlog.add(Box.createVerticalStrut(10));

        for (Story story : stories) {
            backlog.add(createStoryCardWithCheckbox(story));
            backlog.add(Box.createVerticalStrut(8));
        }

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.setOpaque(false);

        JButton moveToBacklogBtn = new JButton("Move to Backlog");
        moveToBacklogBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        moveToBacklogBtn.setEnabled(!selectionManager.getSelectedStoryIds().isEmpty());
        moveToBacklogBtn.addActionListener(e -> moveSelectedStoriesToBacklog());
        addButtonPanel.add(moveToBacklogBtn);

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

    private JPanel createFooter() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);

        JButton backBtn = new JButton("Back to Team Management");
        backBtn.addActionListener(e -> navigator.showTeamManagement());
        footerPanel.add(backBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> navigator.showLogin());
        footerPanel.add(logoutBtn);

        return footerPanel;
    }

    private void updateProgress() {
        String progressText = progressCalculator.getProgressMessage(stories, SPRINT_GOAL);
        progressLabel.setText(progressText);
    }


    private void onEditStory(Story story) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        UserStoryEditDialog dialog = new UserStoryEditDialog(parentFrame, story, storyService, currentUser);
        dialog.setVisible(true);

        if (dialog.wasSaved()) {
            refreshUI();
        }
    }

    private void onAddStory() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        UserStoryEditDialog dialog = new UserStoryEditDialog(parentFrame, storyService, currentUser);
        dialog.setVisible(true);

        if (dialog.wasSaved()) {
            stories.add(dialog.getStory());
            refreshUI();
        }
    }

    private JPanel createStoryCardWithCheckbox(Story story) {
        if (!multiSelectMode) {
            return storyCardFactory.createStoryCard(story);
        }

        JPanel wrapper = new JPanel(new BorderLayout(5, 0));
        wrapper.setOpaque(false);

        JCheckBox checkbox = new JCheckBox();
        checkbox.setOpaque(false);
        checkbox.setSelected(selectionManager.isSelected(story.getId()));
        checkbox.addActionListener(e -> selectionManager.toggleSelect(story.getId()));

        wrapper.add(checkbox, BorderLayout.WEST);
        wrapper.add(storyCardFactory.createStoryCard(story), BorderLayout.CENTER);

        return wrapper;
    }

    private void toggleMultiSelectMode(JButton button) {
        multiSelectMode = !multiSelectMode;
        if (multiSelectMode) {
            button.setText("Exit Multi-Select");
        } else {
            button.setText("Multi-Select");
            selectionManager.clearSelection();
        }
        refreshUI();
    }

    private void moveSelectedStoriesToBacklog() {
        List<String> selectedIds = selectionManager.getSelectedStoryIds();
        if (selectedIds.isEmpty()) {
            return;
        }

        backlogService.moveStoriesToBacklog(selectedIds);
        selectionManager.clearSelection();
        multiSelectMode = false;
        refreshUI();
    }

    private void refreshUI() {
        this.stories = sharedStoryRepository.findSprintStories();

        removeAll();

        add(createHeader(), BorderLayout.NORTH);
        add(createContentArea(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        updateProgress();

        revalidate();
        repaint();
    }

    private void handleStatusRefresh() {
        SwingUtilities.invokeLater(this::refreshUI);
    }

    public void stopRefreshService() {
        if (refreshService != null) {
            refreshService.stop();
        }
    }

    private List<Story> initializeStories() {
        return sharedStoryRepository.findSprintStories();
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
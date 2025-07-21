package view;

import model.Employee;
import util.PositionRoleMapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Professional HR Dashboard - Complete HR Management System
 * Provides HR personnel with comprehensive employee management tools
 */
public class HRDashboard extends JFrame {
    private Employee currentEmployee;
    private JLabel statusLabel;
    private JLabel timeLabel;
    private Timer clockTimer;
    
    // Professional HR Color Scheme
    private static final Color HR_PRIMARY = new Color(155, 89, 182);  // Purple
    private static final Color HR_SECONDARY = new Color(142, 68, 173);
    private static final Color HR_ACCENT = new Color(241, 196, 15);   // Gold
    private static final Color HR_SUCCESS = new Color(39, 174, 96);   // Green
    private static final Color HR_WARNING = new Color(230, 126, 34);  // Orange
    private static final Color HR_DANGER = new Color(231, 76, 60);    // Red
    private static final Color HR_INFO = new Color(52, 152, 219);     // Blue
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    
    public HRDashboard(Employee employee) {
        this.currentEmployee = employee;
        initializeHRInterface();
        startClock();
    }
    
    private void initializeHRInterface() {
        setTitle("MotorPH HR Management System - " + currentEmployee.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 700));
        
        // Set professional look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            // Use default if system L&F fails
        }
        
        createMenuBar();
        createMainContent();
        setIconImage(createHRIcon());
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(HR_PRIMARY);
        menuBar.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        // Employee Management Menu
        JMenu employeeMenu = createStyledMenu("👥 Employee Management", "Manage employee records and information");
        employeeMenu.add(createStyledMenuItem("Employee Directory", "View all employees", this::viewEmployeeDirectory));
        employeeMenu.add(createStyledMenuItem("Add New Employee", "Register new employee", this::addNewEmployee));
        employeeMenu.add(createStyledMenuItem("Employee Records", "Manage employee files", this::manageEmployeeRecords));
        employeeMenu.add(createStyledMenuItem("Employee Reports", "Generate employee reports", this::generateEmployeeReports));
        employeeMenu.addSeparator();
        employeeMenu.add(createStyledMenuItem("Organization Chart", "View company structure", this::viewOrgChart));
        employeeMenu.add(createStyledMenuItem("Department Management", "Manage departments", this::manageDepartments));
        
        // Recruitment Menu
        JMenu recruitmentMenu = createStyledMenu("🎯 Recruitment", "Manage hiring and recruitment processes");
        recruitmentMenu.add(createStyledMenuItem("Job Postings", "Create and manage job posts", this::manageJobPostings));
        recruitmentMenu.add(createStyledMenuItem("Applications", "Review job applications", this::reviewApplications));
        recruitmentMenu.add(createStyledMenuItem("Interview Scheduling", "Schedule interviews", this::scheduleInterviews));
        recruitmentMenu.add(createStyledMenuItem("Candidate Pipeline", "Track recruitment pipeline", this::viewCandidatePipeline));
        recruitmentMenu.add(createStyledMenuItem("Onboarding", "New employee onboarding", this::manageOnboarding));
        
        // Leave Management Menu
        JMenu leaveMenu = createStyledMenu("📅 Leave Management", "Handle leave requests and policies");
        leaveMenu.add(createStyledMenuItem("Pending Requests", "Review pending leave requests", this::reviewLeaveRequests));
        leaveMenu.add(createStyledMenuItem("Leave Calendar", "View team leave schedule", this::viewLeaveCalendar));
        leaveMenu.add(createStyledMenuItem("Leave Policies", "Manage leave policies", this::manageLeavePolicies));
        leaveMenu.add(createStyledMenuItem("Leave Analytics", "Analyze leave patterns", this::analyzeLeavePatterns));
        leaveMenu.add(createStyledMenuItem("Holiday Management", "Manage company holidays", this::manageHolidays));
        
        // Performance Menu
        JMenu performanceMenu = createStyledMenu("📊 Performance", "Employee performance and development");
        performanceMenu.add(createStyledMenuItem("Performance Reviews", "Manage performance evaluations", this::managePerformanceReviews));
        performanceMenu.add(createStyledMenuItem("Goal Setting", "Set and track employee goals", this::manageGoals));
        performanceMenu.add(createStyledMenuItem("Training Programs", "Manage training and development", this::manageTraining));
        performanceMenu.add(createStyledMenuItem("Skills Assessment", "Assess employee skills", this::assessSkills));
        performanceMenu.add(createStyledMenuItem("Career Planning", "Plan career development", this::planCareerDevelopment));
        
        // Payroll Support Menu
        JMenu payrollMenu = createStyledMenu("💰 Payroll Support", "Support payroll operations");
        payrollMenu.add(createStyledMenuItem("Employee Compensation", "Manage salary and benefits", this::manageCompensation));
        payrollMenu.add(createStyledMenuItem("Benefits Administration", "Manage employee benefits", this::manageBenefits));
        payrollMenu.add(createStyledMenuItem("Payroll Verification", "Verify payroll data", this::verifyPayrollData));
        payrollMenu.add(createStyledMenuItem("Tax Documents", "Manage tax certificates", this::manageTaxDocuments));
        
        // Analytics & Reports Menu
        JMenu analyticsMenu = createStyledMenu("📈 Analytics & Reports", "HR analytics and reporting");
        analyticsMenu.add(createStyledMenuItem("HR Dashboard", "View HR metrics", this::viewHRDashboard));
        analyticsMenu.add(createStyledMenuItem("Workforce Analytics", "Analyze workforce data", this::analyzeWorkforce));
        analyticsMenu.add(createStyledMenuItem("Turnover Analysis", "Employee turnover reports", this::analyzeTurnover));
        analyticsMenu.add(createStyledMenuItem("Attendance Reports", "Attendance analytics", this::generateAttendanceReports));
        analyticsMenu.add(createStyledMenuItem("Custom Reports", "Create custom reports", this::createCustomReports));
        analyticsMenu.add(createStyledMenuItem("Compliance Reports", "Generate compliance reports", this::generateComplianceReports));
        
        // Employee Relations Menu
        JMenu relationsMenu = createStyledMenu("🤝 Employee Relations", "Handle employee relations and issues");
        relationsMenu.add(createStyledMenuItem("Grievance Management", "Handle employee grievances", this::manageGrievances));
        relationsMenu.add(createStyledMenuItem("Disciplinary Actions", "Manage disciplinary cases", this::manageDisciplinaryActions));
        relationsMenu.add(createStyledMenuItem("Employee Surveys", "Conduct employee surveys", this::conductSurveys));
        relationsMenu.add(createStyledMenuItem("Exit Interviews", "Manage exit processes", this::manageExitInterviews));
        relationsMenu.add(createStyledMenuItem("Employee Engagement", "Track engagement metrics", this::trackEngagement));
        
        // System Menu
        JMenu systemMenu = createStyledMenu("⚙️ System", "System settings and preferences");
        systemMenu.add(createStyledMenuItem("HR Settings", "Configure HR settings", this::configureHRSettings));
        systemMenu.add(createStyledMenuItem("User Management", "Manage system users", this::manageUsers));
        systemMenu.add(createStyledMenuItem("Backup Data", "Backup HR data", this::backupData));
        
        // Help & Logout
        JMenu helpMenu = createStyledMenu("❓ Help", "Get help and support");
        helpMenu.add(createStyledMenuItem("HR User Guide", "HR system documentation", this::viewHRUserGuide));
        helpMenu.add(createStyledMenuItem("Support", "Contact technical support", this::contactSupport));
        
        JMenu logoutMenu = createStyledMenu("🚪 Logout", "Sign out of the system");
        logoutMenu.add(createStyledMenuItem("Logout", "Sign out safely", this::logout));
        
        menuBar.add(employeeMenu);
        menuBar.add(recruitmentMenu);
        menuBar.add(leaveMenu);
        menuBar.add(performanceMenu);
        menuBar.add(payrollMenu);
        menuBar.add(analyticsMenu);
        menuBar.add(relationsMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(systemMenu);
        menuBar.add(helpMenu);
        menuBar.add(logoutMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Header Panel
        JPanel headerPanel = createHRHeaderPanel();
        
        // Content Panel with tabs
        JTabbedPane tabbedPane = createHRContentTabs();
        
        // Status Panel
        JPanel statusPanel = createStatusPanel();
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHRHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HR_PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Welcome section
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setOpaque(false);
        
        JLabel hrIcon = new JLabel("👥");
        hrIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 3));
        textPanel.setOpaque(false);
        
        JLabel welcomeLabel = new JLabel("HR Management Center");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("Welcome, " + currentEmployee.getFirstName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel roleLabel = new JLabel(currentEmployee.getPosition() + " • Access Level: " + 
                                     PositionRoleMapper.getAccessLevel(currentEmployee.getPosition()));
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(Color.WHITE);
        
        textPanel.add(welcomeLabel);
        textPanel.add(nameLabel);
        textPanel.add(roleLabel);
        
        welcomePanel.add(hrIcon);
        welcomePanel.add(Box.createHorizontalStrut(15));
        welcomePanel.add(textPanel);
        
        // Quick stats and time
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        rightPanel.setOpaque(false);
        
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel quickStatsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        quickStatsPanel.setOpaque(false);
        
        quickStatsPanel.add(createMiniStatCard("Active Employees", "234", HR_SUCCESS));
        quickStatsPanel.add(createMiniStatCard("Pending Requests", "12", HR_WARNING));
        quickStatsPanel.add(createMiniStatCard("Open Positions", "5", HR_INFO));
        
        rightPanel.add(timeLabel);
        rightPanel.add(quickStatsPanel);
        
        headerPanel.add(welcomePanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JTabbedPane createHRContentTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // HR Dashboard Tab
        tabbedPane.addTab("📊 HR Dashboard", createHRDashboardTab());
        
        // Employee Management Tab
        tabbedPane.addTab("👥 Employees", createEmployeeManagementTab());
        
        // Recruitment Tab
        tabbedPane.addTab("🎯 Recruitment", createRecruitmentTab());
        
        // Leave Management Tab
        tabbedPane.addTab("📅 Leave Management", createLeaveManagementTab());
        
        // Performance Tab
        tabbedPane.addTab("📈 Performance", createPerformanceTab());
        
        // Reports Tab
        tabbedPane.addTab("📋 Reports", createReportsTab());
        
        return tabbedPane;
    }
    
    private JPanel createHRDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Key Metrics Panel
        JPanel metricsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        metricsPanel.setOpaque(false);
        
        metricsPanel.add(createHRStatCard("👥 Total Employees", "234", "Active workforce", HR_PRIMARY));
        metricsPanel.add(createHRStatCard("📈 This Month Hires", "8", "New joiners", HR_SUCCESS));
        metricsPanel.add(createHRStatCard("📊 Turnover Rate", "3.2%", "Last 12 months", HR_INFO));
        metricsPanel.add(createHRStatCard("⏰ Avg Time to Hire", "21 days", "Recruitment cycle", HR_WARNING));
        metricsPanel.add(createHRStatCard("📋 Open Positions", "5", "Active job posts", HR_ACCENT));
        metricsPanel.add(createHRStatCard("📅 Leave Requests", "12", "Pending approval", HR_DANGER));
        metricsPanel.add(createHRStatCard("🎯 Performance Reviews", "45", "Due this month", HR_SECONDARY));
        metricsPanel.add(createHRStatCard("📚 Training Programs", "3", "Active programs", HR_SUCCESS));
        
        // Quick Actions Panel
        JPanel actionsPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        actionsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HR_PRIMARY, 2), "Quick Actions"));
        actionsPanel.setBackground(Color.WHITE);
        
        actionsPanel.add(createQuickActionButton("👤 Add Employee", "Register new employee", this::addNewEmployee));
        actionsPanel.add(createQuickActionButton("📋 Approve Leaves", "Review leave requests", this::reviewLeaveRequests));
        actionsPanel.add(createQuickActionButton("📊 Generate Report", "Create HR reports", this::generateEmployeeReports));
        actionsPanel.add(createQuickActionButton("🎯 Post Job", "Create job posting", this::manageJobPostings));
        actionsPanel.add(createQuickActionButton("📅 View Calendar", "Check team schedule", this::viewLeaveCalendar));
        actionsPanel.add(createQuickActionButton("📈 Performance", "Review evaluations", this::managePerformanceReviews));
        actionsPanel.add(createQuickActionButton("🔍 Search Employee", "Find employee records", this::viewEmployeeDirectory));
        actionsPanel.add(createQuickActionButton("⚙️ HR Settings", "Configure settings", this::configureHRSettings));
        
        // Recent Activity Panel
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(HR_PRIMARY, 2), "Recent HR Activity"));
        activityPanel.setBackground(Color.WHITE);
        
        String[] activityColumns = {"Time", "Activity", "Employee", "Status", "Action By"};
        Object[][] activityData = {
            {"10:30 AM", "Leave Request Submitted", "John Doe", "Pending", "System"},
            {"09:45 AM", "New Employee Added", "Jane Smith", "Complete", currentEmployee.getFirstName()},
            {"09:15 AM", "Performance Review", "Bob Johnson", "Submitted", "Mike Wilson"},
            {"08:30 AM", "Training Completed", "Alice Brown", "Certified", "System"},
            {"Yesterday", "Interview Scheduled", "David Lee", "Confirmed", currentEmployee.getFirstName()}
        };
        
        JTable activityTable = new JTable(activityData, activityColumns);
        activityTable.setRowHeight(30);
        activityTable.getTableHeader().setBackground(HR_PRIMARY);
        activityTable.getTableHeader().setForeground(Color.WHITE);
        styleTable(activityTable);
        
        JScrollPane activityScroll = new JScrollPane(activityTable);
        activityPanel.add(activityScroll, BorderLayout.CENTER);
        
        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, actionsPanel, activityPanel);
        splitPane.setDividerLocation(180);
        splitPane.setOpaque(false);
        
        panel.add(metricsPanel, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createEmployeeManagementTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search and Filter Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Employee Search & Filters"));
        
        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HR_PRIMARY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        JComboBox<String> departmentFilter = new JComboBox<>(new String[]{
            "All Departments", "HR", "Finance", "IT", "Sales", "Operations"
        });
        
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{
            "All Status", "Regular", "Probationary", "Contract"
        });
        
        JButton searchBtn = createActionButton("🔍 Search", this::searchEmployees);
        JButton addBtn = createActionButton("➕ Add Employee", this::addNewEmployee);
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Department:"));
        searchPanel.add(departmentFilter);
        searchPanel.add(new JLabel("Status:"));
        searchPanel.add(statusFilter);
        searchPanel.add(searchBtn);
        searchPanel.add(addBtn);
        
        // Employee Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Employee Directory"));
        
        String[] employeeColumns = {"ID", "Name", "Position", "Department", "Status", "Hire Date", "Salary", "Actions"};
        Object[][] employeeData = {
            {"10001", "Garcia, Manuel III", "CEO", "Executive", "Regular", "2020-01-15", "₱90,000", "View | Edit"},
            {"10002", "Lim, Antonio", "COO", "Executive", "Regular", "2020-02-01", "₱60,000", "View | Edit"},
            {"10003", "Aquino, Bianca Sofia", "CFO", "Finance", "Regular", "2020-02-15", "₱60,000", "View | Edit"},
            {"10006", "Villanueva, Andrea Mae", "HR Manager", "HR", "Regular", "2020-03-01", "₱52,670", "View | Edit"},
            {"10007", "San Jose, Brad", "HR Team Leader", "HR", "Regular", "2020-04-01", "₱42,975", "View | Edit"}
        };
        
        JTable employeeTable = new JTable(employeeData, employeeColumns);
        employeeTable.setRowHeight(35);
        employeeTable.getTableHeader().setBackground(HR_PRIMARY);
        employeeTable.getTableHeader().setForeground(Color.WHITE);
        styleTable(employeeTable);
        
        JScrollPane employeeScroll = new JScrollPane(employeeTable);
        tablePanel.add(employeeScroll, BorderLayout.CENTER);
        
        // Employee Actions Panel
        JPanel employeeActionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        employeeActionsPanel.setOpaque(false);
        
        employeeActionsPanel.add(createActionButton("📊 Employee Report", this::generateEmployeeReports));
        employeeActionsPanel.add(createActionButton("📤 Export Data", this::exportEmployeeData));
        employeeActionsPanel.add(createActionButton("📋 Bulk Update", this::bulkUpdateEmployees));
        employeeActionsPanel.add(createActionButton("🏢 Org Chart", this::viewOrgChart));
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(employeeActionsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createRecruitmentTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Recruitment Pipeline
        JPanel pipelinePanel = new JPanel(new GridLayout(1, 5, 10, 0));
        pipelinePanel.setOpaque(false);
        
        pipelinePanel.add(createPipelineStage("📝 Applications", "24", "New applications", HR_INFO));
        pipelinePanel.add(createPipelineStage("📞 Phone Screen", "12", "To be screened", HR_WARNING));
        pipelinePanel.add(createPipelineStage("🎯 Interview", "8", "Scheduled", HR_ACCENT));
        pipelinePanel.add(createPipelineStage("✅ Final Review", "3", "Under review", HR_SECONDARY));
        pipelinePanel.add(createPipelineStage("🎉 Offer", "2", "Pending offer", HR_SUCCESS));
        
        // Job Postings Management
        JPanel jobsPanel = new JPanel(new BorderLayout());
        jobsPanel.setBackground(Color.WHITE);
        jobsPanel.setBorder(BorderFactory.createTitledBorder("Active Job Postings"));
        
        String[] jobColumns = {"Position", "Department", "Applications", "Posted Date", "Status", "Actions"};
        Object[][] jobData = {
            {"Software Developer", "IT", "15", "2024-11-01", "Active", "View | Edit"},
            {"HR Specialist", "HR", "8", "2024-11-15", "Active", "View | Edit"},
            {"Accountant", "Finance", "12", "2024-11-20", "Active", "View | Edit"},
            {"Sales Representative", "Sales", "20", "2024-11-25", "Active", "View | Edit"}
        };
        
        JTable jobTable = new JTable(jobData, jobColumns);
        jobTable.setRowHeight(30);
        jobTable.getTableHeader().setBackground(HR_PRIMARY);
        jobTable.getTableHeader().setForeground(Color.WHITE);
        styleTable(jobTable);
        
        JScrollPane jobScroll = new JScrollPane(jobTable);
        jobsPanel.add(jobScroll, BorderLayout.CENTER);
        
        // Recruitment Actions
        JPanel recruitmentActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        recruitmentActions.setOpaque(false);
        
        recruitmentActions.add(createActionButton("📝 New Job Post", this::manageJobPostings));
        recruitmentActions.add(createActionButton("📋 Review Applications", this::reviewApplications));
        recruitmentActions.add(createActionButton("📅 Schedule Interview", this::scheduleInterviews));
        recruitmentActions.add(createActionButton("📊 Recruitment Report", this::generateRecruitmentReport));
        
        panel.add(pipelinePanel, BorderLayout.NORTH);
        panel.add(jobsPanel, BorderLayout.CENTER);
        panel.add(recruitmentActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createLeaveManagementTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Leave Statistics
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        
        statsPanel.add(createHRStatCard("📋 Pending Requests", "12", "Awaiting approval", HR_WARNING));
        statsPanel.add(createHRStatCard("✅ Approved Today", "5", "Today's approvals", HR_SUCCESS));
        statsPanel.add(createHRStatCard("📊 Leave Usage", "78%", "Average utilization", HR_INFO));
        statsPanel.add(createHRStatCard("🏖️ Peak Season", "Dec 20-31", "Upcoming busy period", HR_DANGER));
        
        // Pending Leave Requests
        JPanel requestsPanel = new JPanel(new BorderLayout());
        requestsPanel.setBackground(Color.WHITE);
        requestsPanel.setBorder(BorderFactory.createTitledBorder("Pending Leave Requests"));
        
        String[] leaveColumns = {"Employee", "Type", "Start Date", "End Date", "Days", "Reason", "Actions"};
        Object[][] leaveData = {
            {"John Doe", "Vacation", "2024-12-20", "2024-12-22", "3", "Christmas holiday", "Approve | Reject"},
            {"Jane Smith", "Sick", "2024-12-03", "2024-12-03", "1", "Medical checkup", "Approve | Reject"},
            {"Bob Johnson", "Personal", "2024-12-10", "2024-12-11", "2", "Family event", "Approve | Reject"},
            {"Alice Brown", "Vacation", "2024-12-15", "2024-12-19", "5", "Year-end break", "Approve | Reject"}
        };
        
        JTable leaveTable = new JTable(leaveData, leaveColumns);
        leaveTable.setRowHeight(30);
        leaveTable.getTableHeader().setBackground(HR_PRIMARY);
        leaveTable.getTableHeader().setForeground(Color.WHITE);
        styleTable(leaveTable);
        
        JScrollPane leaveScroll = new JScrollPane(leaveTable);
        requestsPanel.add(leaveScroll, BorderLayout.CENTER);
        
        // Leave Management Actions
        JPanel leaveActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        leaveActions.setOpaque(false);
        
        leaveActions.add(createActionButton("📅 Leave Calendar", this::viewLeaveCalendar));
        leaveActions.add(createActionButton("⚙️ Leave Policies", this::manageLeavePolicies));
        leaveActions.add(createActionButton("📊 Leave Analytics", this::analyzeLeavePatterns));
        leaveActions.add(createActionButton("🎄 Manage Holidays", this::manageHolidays));
        
        panel.add(statsPanel, BorderLayout.NORTH);
        panel.add(requestsPanel, BorderLayout.CENTER);
        panel.add(leaveActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createPerformanceTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Performance Overview
        JPanel overviewPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        overviewPanel.setOpaque(false);
        
        overviewPanel.add(createHRStatCard("📈 Reviews Due", "45", "This month", HR_WARNING));
        overviewPanel.add(createHRStatCard("⭐ Avg Rating", "4.2/5", "Company average", HR_SUCCESS));
        overviewPanel.add(createHRStatCard("🎯 Goals Met", "87%", "Achievement rate", HR_INFO));
        overviewPanel.add(createHRStatCard("📚 Training", "3", "Active programs", HR_ACCENT));
        
        // Performance Content Split
        JSplitPane contentSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        contentSplit.setOpaque(false);
        
        // Performance Reviews Panel
        JPanel reviewsPanel = new JPanel(new BorderLayout());
        reviewsPanel.setBackground(Color.WHITE);
        reviewsPanel.setBorder(BorderFactory.createTitledBorder("Upcoming Performance Reviews"));
        
        String[] reviewColumns = {"Employee", "Position", "Review Type", "Due Date", "Status"};
        Object[][] reviewData = {
            {"John Doe", "Software Developer", "Annual", "2024-12-15", "Pending"},
            {"Jane Smith", "HR Specialist", "Quarterly", "2024-12-10", "In Progress"},
            {"Bob Johnson", "Accountant", "Probationary", "2024-12-05", "Completed"},
            {"Alice Brown", "Sales Rep", "Annual", "2024-12-20", "Scheduled"}
        };
        
        JTable reviewTable = new JTable(reviewData, reviewColumns);
        reviewTable.setRowHeight(25);
        styleTable(reviewTable);
        
        JScrollPane reviewScroll = new JScrollPane(reviewTable);
        reviewsPanel.add(reviewScroll, BorderLayout.CENTER);
        
        // Training Programs Panel
        JPanel trainingPanel = new JPanel(new BorderLayout());
        trainingPanel.setBackground(Color.WHITE);
        trainingPanel.setBorder(BorderFactory.createTitledBorder("Training Programs"));
        
        String[] trainingColumns = {"Program", "Participants", "Progress", "Completion Date"};
        Object[][] trainingData = {
            {"Leadership Development", "15", "60%", "2024-12-30"},
            {"Technical Skills Upgrade", "25", "80%", "2024-12-15"},
            {"Customer Service Excellence", "12", "45%", "2025-01-15"}
        };
        
        JTable trainingTable = new JTable(trainingData, trainingColumns);
        trainingTable.setRowHeight(25);
        styleTable(trainingTable);
        
        JScrollPane trainingScroll = new JScrollPane(trainingTable);
        trainingPanel.add(trainingScroll, BorderLayout.CENTER);
        
        contentSplit.setLeftComponent(reviewsPanel);
        contentSplit.setRightComponent(trainingPanel);
        contentSplit.setDividerLocation(600);
        
        // Performance Actions
        JPanel performanceActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        performanceActions.setOpaque(false);
        
        performanceActions.add(createActionButton("📊 Conduct Review", this::managePerformanceReviews));
        performanceActions.add(createActionButton("🎯 Set Goals", this::manageGoals));
        performanceActions.add(createActionButton("📚 Manage Training", this::manageTraining));
        performanceActions.add(createActionButton("📈 Performance Analytics", this::analyzePerformance));
        
        panel.add(overviewPanel, BorderLayout.NORTH);
        panel.add(contentSplit, BorderLayout.CENTER);
        panel.add(performanceActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Report Categories
        JPanel categoriesPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        categoriesPanel.setOpaque(false);
        
        categoriesPanel.add(createReportCategory("👥 Employee Reports", "Workforce analytics and employee data", this::generateEmployeeReports));
        categoriesPanel.add(createReportCategory("📅 Attendance Reports", "Time tracking and attendance analysis", this::generateAttendanceReports));
        categoriesPanel.add(createReportCategory("📊 Performance Reports", "Performance evaluation analytics", this::generatePerformanceReports));
        categoriesPanel.add(createReportCategory("💰 Payroll Reports", "Compensation and benefits analysis", this::generatePayrollReports));
        categoriesPanel.add(createReportCategory("🎯 Recruitment Reports", "Hiring and recruitment metrics", this::generateRecruitmentReport));
        categoriesPanel.add(createReportCategory("📋 Custom Reports", "Build your own reports", this::createCustomReports));
        
        // Recent Reports
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBorder(BorderFactory.createTitledBorder("Recent Reports"));
        
        String[] reportColumns = {"Report Name", "Type", "Generated By", "Date", "Actions"};
        Object[][] reportData = {
            {"Monthly Employee Summary", "Employee", currentEmployee.getFirstName(), "2024-12-01", "Download | View"},
            {"Leave Utilization Report", "Leave", currentEmployee.getFirstName(), "2024-11-30", "Download | View"},
            {"Recruitment Pipeline", "Recruitment", currentEmployee.getFirstName(), "2024-11-28", "Download | View"},
            {"Performance Dashboard", "Performance", currentEmployee.getFirstName(), "2024-11-25", "Download | View"}
        };
        
        JTable reportTable = new JTable(reportData, reportColumns);
        reportTable.setRowHeight(30);
        reportTable.getTableHeader().setBackground(HR_PRIMARY);
        reportTable.getTableHeader().setForeground(Color.WHITE);
        styleTable(reportTable);
        
        JScrollPane reportScroll = new JScrollPane(reportTable);
        recentPanel.add(reportScroll, BorderLayout.CENTER);
        
        // Split the layout
        JSplitPane reportSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, categoriesPanel, recentPanel);
        reportSplit.setDividerLocation(300);
        reportSplit.setOpaque(false);
        
        panel.add(reportSplit, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Helper Methods
    private JPanel createMiniStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        card.setPreferredSize(new Dimension(100, 50));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createHRStatCard(String title, String value, String subtitle, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        
        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(subtitleLabel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createQuickActionButton(String text, String tooltip, Runnable action) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setBackground(HR_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.addActionListener(e -> action.run());
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HR_SECONDARY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(HR_PRIMARY);
            }
        });
        
        return button;
    }
    
    private JPanel createPipelineStage(String title, String count, String description, Color color) {
        JPanel stage = new JPanel(new BorderLayout());
        stage.setBackground(Color.WHITE);
        stage.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel countLabel = new JLabel(count, SwingConstants.CENTER);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        countLabel.setForeground(color);
        
        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(Color.GRAY);
        
        stage.add(titleLabel, BorderLayout.NORTH);
        stage.add(countLabel, BorderLayout.CENTER);
        stage.add(descLabel, BorderLayout.SOUTH);
        
        return stage;
    }
    
    private JPanel createReportCategory(String title, String description, Runnable action) {
        JPanel category = new JPanel(new BorderLayout());
        category.setBackground(Color.WHITE);
        category.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HR_PRIMARY, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        category.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(HR_PRIMARY);
        
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descArea.setForeground(Color.GRAY);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        
        JButton generateBtn = createActionButton("Generate", action);
        
        category.add(titleLabel, BorderLayout.NORTH);
        category.add(descArea, BorderLayout.CENTER);
        category.add(generateBtn, BorderLayout.SOUTH);
        
        // Add click handler for entire panel
        category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.run();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                category.setBackground(new Color(250, 250, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                category.setBackground(Color.WHITE);
            }
        });
        
        return category;
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 220, 255));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }
    
    private JMenu createStyledMenu(String text, String tooltip) {
        JMenu menu = new JMenu(text);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menu.setToolTipText(tooltip);
        return menu;
    }
    
    private JMenuItem createStyledMenuItem(String text, String tooltip, Runnable action) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        item.setToolTipText(tooltip);
        item.addActionListener(e -> action.run());
        return item;
    }
    
    private JButton createActionButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setBackground(HR_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HR_SECONDARY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(HR_PRIMARY);
            }
        });
        
        return button;
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(HR_SECONDARY);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        statusLabel = new JLabel("HR System Ready");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel systemLabel = new JLabel("MotorPH HR Management System v2.0 | " + currentEmployee.getPosition());
        systemLabel.setForeground(Color.LIGHT_GRAY);
        systemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(systemLabel, BorderLayout.EAST);
        
        return statusPanel;
    }
    
    private void startClock() {
        clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                String timeText = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy • HH:mm:ss"));
                if (timeLabel != null) {
                    timeLabel.setText(timeText);
                }
            }
        });
        clockTimer.start();
    }
    
    private Image createHRIcon() {
        // Create HR-specific icon
        return new ImageIcon(new byte[0]).getImage();
    }
    
    // HR Action Methods
    private void viewEmployeeDirectory() {
        showHRMessage("Employee Directory", "Loading employee directory...");
    }
    
    private void addNewEmployee() {
        showHRMessage("Add Employee", "Opening new employee registration form...");
    }
    
    private void manageEmployeeRecords() {
        showHRMessage("Employee Records", "Managing employee records...");
    }
    
    private void generateEmployeeReports() {
        showHRMessage("Employee Reports", "Generating comprehensive employee reports...");
    }
    
    private void viewOrgChart() {
        showHRMessage("Organization Chart", "Loading organizational structure...");
    }
    
    private void manageDepartments() {
        showHRMessage("Department Management", "Managing company departments...");
    }
    
    private void manageJobPostings() {
        showHRMessage("Job Postings", "Managing active job postings...");
    }
    
    private void reviewApplications() {
        showHRMessage("Review Applications", "Reviewing job applications...");
    }
    
    private void scheduleInterviews() {
        showHRMessage("Interview Scheduling", "Scheduling candidate interviews...");
    }
    
    private void viewCandidatePipeline() {
        showHRMessage("Candidate Pipeline", "Viewing recruitment pipeline...");
    }
    
    private void manageOnboarding() {
        showHRMessage("Onboarding", "Managing new employee onboarding...");
    }
    
    private void reviewLeaveRequests() {
        showHRMessage("Leave Requests", "Reviewing pending leave requests...");
    }
    
    private void viewLeaveCalendar() {
        showHRMessage("Leave Calendar", "Opening team leave calendar...");
    }
    
    private void manageLeavePolicies() {
        showHRMessage("Leave Policies", "Managing company leave policies...");
    }
    
    private void analyzeLeavePatterns() {
        showHRMessage("Leave Analytics", "Analyzing leave usage patterns...");
    }
    
    private void manageHolidays() {
        showHRMessage("Holiday Management", "Managing company holidays...");
    }
    
    private void managePerformanceReviews() {
        showHRMessage("Performance Reviews", "Managing performance evaluations...");
    }
    
    private void manageGoals() {
        showHRMessage("Goal Management", "Setting and tracking employee goals...");
    }
    
    private void manageTraining() {
        showHRMessage("Training Programs", "Managing training and development...");
    }
    
    private void assessSkills() {
        showHRMessage("Skills Assessment", "Conducting skills assessment...");
    }
    
    private void planCareerDevelopment() {
        showHRMessage("Career Planning", "Planning career development paths...");
    }
    
    private void manageCompensation() {
        showHRMessage("Compensation Management", "Managing employee compensation...");
    }
    
    private void manageBenefits() {
        showHRMessage("Benefits Administration", "Managing employee benefits...");
    }
    
    private void verifyPayrollData() {
        showHRMessage("Payroll Verification", "Verifying payroll data accuracy...");
    }
    
    private void manageTaxDocuments() {
        showHRMessage("Tax Documents", "Managing tax certificates and documents...");
    }
    
    private void viewHRDashboard() {
        showHRMessage("HR Analytics", "Loading HR metrics dashboard...");
    }
    
    private void analyzeWorkforce() {
        showHRMessage("Workforce Analytics", "Analyzing workforce data...");
    }
    
    private void analyzeTurnover() {
        showHRMessage("Turnover Analysis", "Analyzing employee turnover...");
    }
    
    private void generateAttendanceReports() {
        showHRMessage("Attendance Reports", "Generating attendance analytics...");
    }
    
    private void createCustomReports() {
        showHRMessage("Custom Reports", "Opening custom report builder...");
    }
    
    private void generateComplianceReports() {
        showHRMessage("Compliance Reports", "Generating compliance reports...");
    }
    
    private void manageGrievances() {
        showHRMessage("Grievance Management", "Managing employee grievances...");
    }
    
    private void manageDisciplinaryActions() {
        showHRMessage("Disciplinary Actions", "Managing disciplinary cases...");
    }
    
    private void conductSurveys() {
        showHRMessage("Employee Surveys", "Conducting employee satisfaction surveys...");
    }
    
    private void manageExitInterviews() {
        showHRMessage("Exit Interviews", "Managing employee exit processes...");
    }
    
    private void trackEngagement() {
        showHRMessage("Employee Engagement", "Tracking engagement metrics...");
    }
    
    private void configureHRSettings() {
        showHRMessage("HR Settings", "Configuring HR system settings...");
    }
    
    private void manageUsers() {
        showHRMessage("User Management", "Managing system users and permissions...");
    }
    
    private void backupData() {
        showHRMessage("Data Backup", "Backing up HR data...");
    }
    
    private void viewHRUserGuide() {
        showHRMessage("HR User Guide", "Opening HR system documentation...");
    }
    
    private void contactSupport() {
        showHRMessage("Technical Support", "Contacting system support...");
    }
    
    // Additional methods for new functionality
    private void searchEmployees() {
        showHRMessage("Employee Search", "Searching employee database...");
    }
    
    private void exportEmployeeData() {
        showHRMessage("Export Data", "Exporting employee data...");
    }
    
    private void bulkUpdateEmployees() {
        showHRMessage("Bulk Update", "Opening bulk employee update tool...");
    }
    
    private void generateRecruitmentReport() {
        showHRMessage("Recruitment Report", "Generating recruitment analytics...");
    }
    
    private void analyzePerformance() {
        showHRMessage("Performance Analytics", "Analyzing performance trends...");
    }
    
    private void generatePerformanceReports() {
        showHRMessage("Performance Reports", "Generating performance reports...");
    }
    
    private void generatePayrollReports() {
        showHRMessage("Payroll Reports", "Generating payroll analytics...");
    }
    
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout from HR Management System?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (clockTimer != null) {
                clockTimer.stop();
            }
            showHRMessage("Logout", "Logging out safely...");
            dispose();
            // Return to login screen
        }
    }
    
    private void showHRMessage(String title, String message) {
        statusLabel.setText(message);
        JOptionPane.showMessageDialog(this, 
            message + "\n\nThis feature is available in the full HR Management System.", 
            "HR Management - " + title, 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
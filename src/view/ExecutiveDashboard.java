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
 * Executive Dashboard - Strategic Command Center
 * High-level interface for C-Suite executives with strategic insights
 */
public class ExecutiveDashboard extends JFrame {
    private Employee currentEmployee;
    private JLabel statusLabel;
    private JLabel timeLabel;
    private Timer clockTimer;
    
    // Executive Color Scheme - Professional and authoritative
    private static final Color EXEC_PRIMARY = new Color(44, 62, 80);    // Dark blue-gray
    private static final Color EXEC_SECONDARY = new Color(52, 73, 94);   // Slate
    private static final Color EXEC_GOLD = new Color(241, 196, 15);      // Gold accent
    private static final Color EXEC_SUCCESS = new Color(39, 174, 96);    // Green
    private static final Color EXEC_WARNING = new Color(230, 126, 34);   // Orange
    private static final Color EXEC_DANGER = new Color(231, 76, 60);     // Red
    private static final Color EXEC_INFO = new Color(52, 152, 219);      // Blue
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    
    public ExecutiveDashboard(Employee employee) {
        this.currentEmployee = employee;
        initializeExecutiveInterface();
        startClock();
    }
    
    private void initializeExecutiveInterface() {
        setTitle("MotorPH Executive Command Center - " + currentEmployee.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1000);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1400, 800));
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized for executives
        
        // Set premium look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            // Use default if system L&F fails
        }
        
        createExecutiveMenuBar();
        createExecutiveMainContent();
        setIconImage(createExecutiveIcon());
    }
    
    private void createExecutiveMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(EXEC_PRIMARY);
        menuBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Strategic Overview Menu
        JMenu overviewMenu = createStyledMenu("📊 Strategic Overview", "High-level company insights");
        overviewMenu.add(createStyledMenuItem("Executive Dashboard", "Strategic KPIs and metrics", this::viewExecutiveDashboard));
        overviewMenu.add(createStyledMenuItem("Company Performance", "Overall performance metrics", this::viewCompanyPerformance));
        overviewMenu.add(createStyledMenuItem("Financial Overview", "Revenue, costs, and profitability", this::viewFinancialOverview));
        overviewMenu.add(createStyledMenuItem("Operational Metrics", "Operational efficiency indicators", this::viewOperationalMetrics));
        overviewMenu.addSeparator();
        overviewMenu.add(createStyledMenuItem("Board Reports", "Reports for board meetings", this::generateBoardReports));
        overviewMenu.add(createStyledMenuItem("Investor Relations", "Investor presentations and reports", this::manageInvestorRelations));
        
        // People & Culture Menu
        JMenu peopleMenu = createStyledMenu("👥 People & Culture", "Workforce and organizational insights");
        peopleMenu.add(createStyledMenuItem("Workforce Analytics", "Employee metrics and trends", this::viewWorkforceAnalytics));
        peopleMenu.add(createStyledMenuItem("Leadership Team", "Senior management overview", this::viewLeadershipTeam));
        peopleMenu.add(createStyledMenuItem("Talent Pipeline", "Succession planning and development", this::viewTalentPipeline));
        peopleMenu.add(createStyledMenuItem("Culture Metrics", "Employee engagement and satisfaction", this::viewCultureMetrics));
        peopleMenu.add(createStyledMenuItem("Compensation Strategy", "Executive compensation overview", this::viewCompensationStrategy));
        
        // Financial Management Menu
        JMenu financialMenu = createStyledMenu("💰 Financial Management", "Financial planning and control");
        financialMenu.add(createStyledMenuItem("P&L Analysis", "Profit and loss analysis", this::analyzeProfitLoss));
        financialMenu.add(createStyledMenuItem("Budget Management", "Budget planning and tracking", this::manageBudgets));
        financialMenu.add(createStyledMenuItem("Cost Analysis", "Cost structure and optimization", this::analyzeCosts));
        financialMenu.add(createStyledMenuItem("ROI Dashboard", "Return on investment metrics", this::viewROIDashboard));
        financialMenu.add(createStyledMenuItem("Cash Flow", "Cash flow analysis and forecasting", this::manageCashFlow));
        financialMenu.add(createStyledMenuItem("Financial Forecasting", "Future financial projections", this::createFinancialForecasts));
        
        // Strategic Planning Menu
        JMenu strategyMenu = createStyledMenu("🎯 Strategic Planning", "Strategic initiatives and planning");
        strategyMenu.add(createStyledMenuItem("Strategic Initiatives", "Manage key strategic projects", this::manageStrategicInitiatives));
        strategyMenu.add(createStyledMenuItem("Goal Management", "Organizational goals and OKRs", this::manageOrganizationalGoals));
        strategyMenu.add(createStyledMenuItem("Market Analysis", "Market trends and competitive analysis", this::analyzeMarket));
        strategyMenu.add(createStyledMenuItem("Growth Planning", "Business expansion strategies", this::planGrowth));
        strategyMenu.add(createStyledMenuItem("Risk Management", "Enterprise risk assessment", this::manageRisks));
        strategyMenu.add(createStyledMenuItem("Innovation Pipeline", "New products and services", this::manageInnovation));
        
        // Operations Menu
        JMenu operationsMenu = createStyledMenu("⚙️ Operations", "Operational excellence and efficiency");
        operationsMenu.add(createStyledMenuItem("Operational Dashboard", "Real-time operational metrics", this::viewOperationalDashboard));
        operationsMenu.add(createStyledMenuItem("Process Optimization", "Business process improvement", this::optimizeProcesses));
        operationsMenu.add(createStyledMenuItem("Quality Metrics", "Quality assurance and control", this::viewQualityMetrics));
        operationsMenu.add(createStyledMenuItem("Supply Chain", "Supply chain management", this::manageSupplyChain));
        operationsMenu.add(createStyledMenuItem("Technology Strategy", "IT and digital transformation", this::manageTechnologyStrategy));
        
        // Governance Menu
        JMenu governanceMenu = createStyledMenu("🏛️ Governance", "Corporate governance and compliance");
        governanceMenu.add(createStyledMenuItem("Compliance Dashboard", "Regulatory compliance status", this::viewComplianceDashboard));
        governanceMenu.add(createStyledMenuItem("Audit Management", "Internal and external audits", this::manageAudits));
        governanceMenu.add(createStyledMenuItem("Policy Management", "Corporate policies and procedures", this::managePolicies));
        governanceMenu.add(createStyledMenuItem("Legal Affairs", "Legal matters and contracts", this::manageLegalAffairs));
        governanceMenu.add(createStyledMenuItem("Ethics & Integrity", "Ethics and compliance programs", this::manageEthics));
        
        // Executive Tools Menu
        JMenu toolsMenu = createStyledMenu("🛠️ Executive Tools", "Executive productivity tools");
        toolsMenu.add(createStyledMenuItem("Executive Calendar", "Schedule and appointments", this::manageExecutiveCalendar));
        toolsMenu.add(createStyledMenuItem("Meeting Center", "Meeting management and notes", this::manageMeetings));
        toolsMenu.add(createStyledMenuItem("Decision Log", "Track key decisions", this::manageDecisions));
        toolsMenu.add(createStyledMenuItem("Communication Hub", "Executive communications", this::manageCommunications));
        toolsMenu.add(createStyledMenuItem("Document Vault", "Confidential documents", this::manageDocuments));
        
        // System Menu
        JMenu systemMenu = createStyledMenu("⚙️ System", "System administration");
        systemMenu.add(createStyledMenuItem("System Settings", "Configure system preferences", this::configureSystem));
        systemMenu.add(createStyledMenuItem("User Access", "Manage user permissions", this::manageUserAccess));
        systemMenu.add(createStyledMenuItem("Data Security", "Security and backup settings", this::manageDataSecurity));
        
        // Help & Logout
        JMenu helpMenu = createStyledMenu("❓ Support", "Help and support");
        helpMenu.add(createStyledMenuItem("Executive Guide", "Executive dashboard guide", this::viewExecutiveGuide));
        helpMenu.add(createStyledMenuItem("Contact Support", "Executive support line", this::contactExecutiveSupport));
        
        JMenu logoutMenu = createStyledMenu("🚪 Sign Out", "Secure logout");
        logoutMenu.add(createStyledMenuItem("Sign Out", "Secure logout from system", this::logout));
        
        menuBar.add(overviewMenu);
        menuBar.add(peopleMenu);
        menuBar.add(financialMenu);
        menuBar.add(strategyMenu);
        menuBar.add(operationsMenu);
        menuBar.add(governanceMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(toolsMenu);
        menuBar.add(systemMenu);
        menuBar.add(helpMenu);
        menuBar.add(logoutMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void createExecutiveMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Executive Header
        JPanel headerPanel = createExecutiveHeaderPanel();
        
        // Main Content with Tabs
        JTabbedPane tabbedPane = createExecutiveContentTabs();
        
        // Status Panel
        JPanel statusPanel = createStatusPanel();
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createExecutiveHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(EXEC_PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Executive Welcome Section
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setOpaque(false);
        
        JLabel execIcon = new JLabel("👑");
        execIcon.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Executive Command Center");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel nameLabel = new JLabel("Good day, " + currentEmployee.getFirstName());
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameLabel.setForeground(Color.WHITE);
        
        JLabel positionLabel = new JLabel(currentEmployee.getPosition() + " • MotorPH Philippines");
        positionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        positionLabel.setForeground(EXEC_GOLD);
        
        textPanel.add(titleLabel);
        textPanel.add(nameLabel);
        textPanel.add(positionLabel);
        
        welcomePanel.add(execIcon);
        welcomePanel.add(Box.createHorizontalStrut(20));
        welcomePanel.add(textPanel);
        
        // Time and Key Metrics
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        rightPanel.setOpaque(false);
        
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel keyMetricsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        keyMetricsPanel.setOpaque(false);
        
        keyMetricsPanel.add(createExecutiveMetricCard("Revenue", "₱15.2M", "This Month", EXEC_SUCCESS));
        keyMetricsPanel.add(createExecutiveMetricCard("Profit", "₱3.8M", "Net Margin", EXEC_GOLD));
        keyMetricsPanel.add(createExecutiveMetricCard("Employees", "234", "Active", EXEC_INFO));
        
        rightPanel.add(timeLabel);
        rightPanel.add(keyMetricsPanel);
        
        headerPanel.add(welcomePanel, BorderLayout.WEST);
	headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JTabbedPane createExecutiveContentTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        // Strategic Dashboard Tab
        tabbedPane.addTab("📊 Strategic Dashboard", createStrategicDashboardTab());
        
        // Financial Performance Tab
        tabbedPane.addTab("💰 Financial Performance", createFinancialPerformanceTab());
        
        // People & Operations Tab
        tabbedPane.addTab("👥 People & Operations", createPeopleOperationsTab());
        
        // Market & Growth Tab
        tabbedPane.addTab("📈 Market & Growth", createMarketGrowthTab());
        
        // Risk & Governance Tab
        tabbedPane.addTab("🛡️ Risk & Governance", createRiskGovernanceTab());
        
        // Executive Tools Tab
        tabbedPane.addTab("🛠️ Executive Tools", createExecutiveToolsTab());
        
        return tabbedPane;
    }
    
    private JPanel createStrategicDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Key Performance Indicators
        JPanel kpiPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        kpiPanel.setOpaque(false);
        
        kpiPanel.add(createKPICard("📈 Revenue Growth", "+12.5%", "YoY Growth", "Target: +10%", EXEC_SUCCESS));
        kpiPanel.add(createKPICard("💰 Net Profit Margin", "23.8%", "Current Quarter", "Target: 20%", EXEC_SUCCESS));
        kpiPanel.add(createKPICard("👥 Employee Satisfaction", "4.3/5", "Latest Survey", "Target: 4.0", EXEC_SUCCESS));
        kpiPanel.add(createKPICard("🎯 Strategic Goals", "87%", "Achievement Rate", "Target: 85%", EXEC_SUCCESS));
        kpiPanel.add(createKPICard("🏭 Operational Efficiency", "94.2%", "Current Score", "Target: 90%", EXEC_SUCCESS));
        kpiPanel.add(createKPICard("📊 Market Share", "18.5%", "Industry Position", "Target: 20%", EXEC_WARNING));
        kpiPanel.add(createKPICard("🔄 Customer Retention", "92.3%", "12-Month Rate", "Target: 90%", EXEC_SUCCESS));
        kpiPanel.add(createKPICard("💡 Innovation Index", "3.8/5", "R&D Performance", "Target: 4.0", EXEC_WARNING));
        
        // Strategic Initiatives Progress
        JPanel initiativesPanel = new JPanel(new BorderLayout());
        initiativesPanel.setBackground(Color.WHITE);
        initiativesPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(EXEC_PRIMARY, 2), "Strategic Initiatives Progress"));
        
        String[] initiativeColumns = {"Initiative", "Owner", "Progress", "Budget", "Timeline", "Status"};
        Object[][] initiativeData = {
            {"Digital Transformation", "IT Department", "75%", "₱5.2M", "Q1 2025", "On Track"},
            {"Market Expansion - Visayas", "Sales Team", "60%", "₱8.1M", "Q2 2025", "On Track"},
            {"Process Automation", "Operations", "90%", "₱3.5M", "Dec 2024", "Ahead"},
            {"Talent Development Program", "HR Department", "45%", "₱2.8M", "Q3 2025", "At Risk"},
            {"Sustainability Initiative", "All Departments", "30%", "₱4.2M", "Q4 2025", "Planning"}
        };
        
        JTable initiativeTable = new JTable(initiativeData, initiativeColumns);
        initiativeTable.setRowHeight(35);
        initiativeTable.getTableHeader().setBackground(EXEC_PRIMARY);
        initiativeTable.getTableHeader().setForeground(Color.WHITE);
        styleExecutiveTable(initiativeTable);
        
        JScrollPane initiativeScroll = new JScrollPane(initiativeTable);
        initiativesPanel.add(initiativeScroll, BorderLayout.CENTER);
        
        // Executive Actions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        actionsPanel.setOpaque(false);
        
        actionsPanel.add(createExecutiveActionButton("📊 Board Report", "Generate board presentation", this::generateBoardReports));
        actionsPanel.add(createExecutiveActionButton("🎯 Review Goals", "Strategic goal review", this::manageOrganizationalGoals));
        actionsPanel.add(createExecutiveActionButton("💼 Leadership Meeting", "Schedule leadership sync", this::manageMeetings));
        actionsPanel.add(createExecutiveActionButton("📈 Performance Review", "Company performance analysis", this::viewCompanyPerformance));
        
        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, initiativesPanel, null);
        splitPane.setDividerLocation(300);
        splitPane.setOpaque(false);
        splitPane.setBottomComponent(actionsPanel);
        
        panel.add(kpiPanel, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFinancialPerformanceTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Financial Metrics
        JPanel financialMetricsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        financialMetricsPanel.setOpaque(false);
        
        financialMetricsPanel.add(createFinancialCard("💰 Total Revenue", "₱45.6M", "+15.2% YoY", EXEC_SUCCESS));
        financialMetricsPanel.add(createFinancialCard("📊 Gross Profit", "₱18.2M", "39.9% Margin", EXEC_SUCCESS));
        financialMetricsPanel.add(createFinancialCard("💡 Operating Income", "₱12.8M", "28.1% Margin", EXEC_SUCCESS));
        financialMetricsPanel.add(createFinancialCard("🎯 Net Income", "₱10.9M", "23.8% Margin", EXEC_SUCCESS));
        financialMetricsPanel.add(createFinancialCard("💳 Cash Flow", "₱8.7M", "Operating CF", EXEC_INFO));
        financialMetricsPanel.add(createFinancialCard("📈 EBITDA", "₱14.5M", "31.8% Margin", EXEC_SUCCESS));
        financialMetricsPanel.add(createFinancialCard("🏦 Total Assets", "₱125.3M", "+8.4% Growth", EXEC_INFO));
        financialMetricsPanel.add(createFinancialCard("📊 ROI", "18.7%", "Return on Investment", EXEC_SUCCESS));
        
        // Financial Charts Placeholder
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setOpaque(false);
        
        JPanel revenueChart = createChartPlaceholder("📈 Revenue Trend", "Monthly revenue progression");
        JPanel profitChart = createChartPlaceholder("💰 Profit Analysis", "Profit margin trends");
        
        chartsPanel.add(revenueChart);
        chartsPanel.add(profitChart);
        
        // Financial Actions
        JPanel financialActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        financialActions.setOpaque(false);
        
        financialActions.add(createExecutiveActionButton("📊 P&L Analysis", "Detailed profit analysis", this::analyzeProfitLoss));
        financialActions.add(createExecutiveActionButton("💰 Budget Review", "Budget performance review", this::manageBudgets));
        financialActions.add(createExecutiveActionButton("📈 Forecasting", "Financial projections", this::createFinancialForecasts));
        financialActions.add(createExecutiveActionButton("🎯 ROI Dashboard", "Investment returns", this::viewROIDashboard));
        
        panel.add(financialMetricsPanel, BorderLayout.NORTH);
        panel.add(chartsPanel, BorderLayout.CENTER);
        panel.add(financialActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createPeopleOperationsTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // People Metrics
        JPanel peopleMetricsPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        peopleMetricsPanel.setOpaque(false);
        
        peopleMetricsPanel.add(createPeopleCard("👥 Total Workforce", "234", "Active Employees", EXEC_INFO));
        peopleMetricsPanel.add(createPeopleCard("📈 Retention Rate", "94.2%", "12-Month Rate", EXEC_SUCCESS));
        peopleMetricsPanel.add(createPeopleCard("⭐ Satisfaction", "4.3/5", "Employee Survey", EXEC_SUCCESS));
        peopleMetricsPanel.add(createPeopleCard("🎓 Training Hours", "2,840", "This Quarter", EXEC_INFO));
        peopleMetricsPanel.add(createPeopleCard("🚀 Productivity", "127%", "vs Baseline", EXEC_SUCCESS));
        peopleMetricsPanel.add(createPeopleCard("🎯 Performance", "88%", "Goals Achievement", EXEC_SUCCESS));
        peopleMetricsPanel.add(createPeopleCard("💡 Innovation", "45", "Ideas Submitted", EXEC_WARNING));
        peopleMetricsPanel.add(createPeopleCard("🔄 Turnover", "5.8%", "Annual Rate", EXEC_SUCCESS));
        
        // Department Performance
        JPanel deptPanel = new JPanel(new BorderLayout());
        deptPanel.setBackground(Color.WHITE);
        deptPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(EXEC_PRIMARY, 2), "Department Performance Overview"));
        
        String[] deptColumns = {"Department", "Head Count", "Performance", "Budget Utilization", "Key Projects", "Status"};
        Object[][] deptData = {
            {"Sales & Marketing", "45", "125%", "92%", "Market Expansion", "Excellent"},
            {"Operations", "78", "118%", "88%", "Process Automation", "Good"},
            {"Finance & Accounting", "24", "112%", "85%", "System Upgrade", "Good"},
            {"Human Resources", "12", "108%", "90%", "Talent Development", "Good"},
            {"Information Technology", "35", "134%", "95%", "Digital Transform", "Excellent"},
            {"Supply Chain", "40", "115%", "87%", "Optimization", "Good"}
        };
        
        JTable deptTable = new JTable(deptData, deptColumns);
        deptTable.setRowHeight(35);
        deptTable.getTableHeader().setBackground(EXEC_PRIMARY);
        deptTable.getTableHeader().setForeground(Color.WHITE);
        styleExecutiveTable(deptTable);
        
        JScrollPane deptScroll = new JScrollPane(deptTable);
        deptPanel.add(deptScroll, BorderLayout.CENTER);
        
        // People & Operations Actions
        JPanel peopleActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        peopleActions.setOpaque(false);
        
        peopleActions.add(createExecutiveActionButton("👥 Workforce Analytics", "Detailed people analytics", this::viewWorkforceAnalytics));
        peopleActions.add(createExecutiveActionButton("🎯 Leadership Review", "Leadership team performance", this::viewLeadershipTeam));
        peopleActions.add(createExecutiveActionButton("⚙️ Operations Review", "Operational efficiency", this::viewOperationalDashboard));
        peopleActions.add(createExecutiveActionButton("📊 Culture Metrics", "Employee engagement", this::viewCultureMetrics));
        
        panel.add(peopleMetricsPanel, BorderLayout.NORTH);
        panel.add(deptPanel, BorderLayout.CENTER);
        panel.add(peopleActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createMarketGrowthTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Market Metrics
        JPanel marketMetricsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        marketMetricsPanel.setOpaque(false);
        
        marketMetricsPanel.add(createMarketCard("🏆 Market Share", "18.5%", "Motorcycle Parts", "Industry: ↑2.1%"));
        marketMetricsPanel.add(createMarketCard("📈 Growth Rate", "+12.5%", "Revenue Growth", "Industry: +8.2%"));
        marketMetricsPanel.add(createMarketCard("🎯 Customer Base", "15,847", "Active Customers", "↑15% YoY"));
        marketMetricsPanel.add(createMarketCard("🌟 Brand Value", "₱2.1B", "Estimated Value", "↑18% YoY"));
        marketMetricsPanel.add(createMarketCard("🚀 Innovation Score", "3.8/5", "R&D Performance", "Target: 4.0"));
        marketMetricsPanel.add(createMarketCard("🌍 Market Reach", "3 Regions", "Geographic Coverage", "Expanding"));
        
        // Growth Initiatives
        JPanel growthPanel = new JPanel(new BorderLayout());
        growthPanel.setBackground(Color.WHITE);
        growthPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(EXEC_PRIMARY, 2), "Growth Initiatives & Market Opportunities"));
        
        String[] growthColumns = {"Initiative", "Market", "Investment", "ROI Projection", "Timeline", "Risk Level"};
        Object[][] growthData = {
            {"Visayas Expansion", "Regional", "₱8.1M", "145% (3 years)", "Q2 2025", "Medium"},
            {"E-commerce Platform", "Digital", "₱3.5M", "230% (2 years)", "Q1 2025", "Low"},
            {"Premium Product Line", "Luxury", "₱5.2M", "180% (3 years)", "Q3 2025", "Medium"},
            {"B2B Marketplace", "Corporate", "₱4.8M", "165% (2.5 years)", "Q4 2025", "Medium"},
            {"International Export", "ASEAN", "₱12.5M", "190% (4 years)", "2026", "High"}
        };
        
        JTable growthTable = new JTable(growthData, growthColumns);
        growthTable.setRowHeight(35);
        growthTable.getTableHeader().setBackground(EXEC_PRIMARY);
        growthTable.getTableHeader().setForeground(Color.WHITE);
        styleExecutiveTable(growthTable);
        
        JScrollPane growthScroll = new JScrollPane(growthTable);
        growthPanel.add(growthScroll, BorderLayout.CENTER);
        
        // Market & Growth Actions
        JPanel marketActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        marketActions.setOpaque(false);
        
        marketActions.add(createExecutiveActionButton("📊 Market Analysis", "Competitive intelligence", this::analyzeMarket));
        marketActions.add(createExecutiveActionButton("🚀 Growth Planning", "Strategic growth initiatives", this::planGrowth));
        marketActions.add(createExecutiveActionButton("💡 Innovation Pipeline", "New product development", this::manageInnovation));
        marketActions.add(createExecutiveActionButton("🌍 Expansion Strategy", "Geographic expansion", this::manageStrategicInitiatives));
        
        panel.add(marketMetricsPanel, BorderLayout.NORTH);
        panel.add(growthPanel, BorderLayout.CENTER);
        panel.add(marketActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createRiskGovernanceTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Risk Overview
        JPanel riskOverviewPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        riskOverviewPanel.setOpaque(false);
        
        riskOverviewPanel.add(createRiskCard("🛡️ Overall Risk", "Medium", "Risk Assessment", EXEC_WARNING));
        riskOverviewPanel.add(createRiskCard("📊 Compliance", "98.5%", "Regulatory Compliance", EXEC_SUCCESS));
        riskOverviewPanel.add(createRiskCard("🔒 Cybersecurity", "High", "Security Posture", EXEC_SUCCESS));
        riskOverviewPanel.add(createRiskCard("💰 Financial Risk", "Low", "Financial Stability", EXEC_SUCCESS));
        riskOverviewPanel.add(createRiskCard("🏭 Operational Risk", "Medium", "Process Risks", EXEC_WARNING));
        riskOverviewPanel.add(createRiskCard("👥 HR Risk", "Low", "People Risks", EXEC_SUCCESS));
        riskOverviewPanel.add(createRiskCard("🌍 Market Risk", "Medium", "External Factors", EXEC_WARNING));
        riskOverviewPanel.add(createRiskCard("📋 Audit Score", "92%", "Internal Audit", EXEC_SUCCESS));
        
        // Risk Matrix and Governance
        JSplitPane riskSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        riskSplit.setOpaque(false);
        
        // Risk Register
        JPanel riskRegisterPanel = new JPanel(new BorderLayout());
        riskRegisterPanel.setBackground(Color.WHITE);
        riskRegisterPanel.setBorder(BorderFactory.createTitledBorder("Active Risk Register"));
        
        String[] riskColumns = {"Risk", "Probability", "Impact", "Risk Level", "Mitigation", "Owner"};
        Object[][] riskData = {
            {"Supply Chain Disruption", "Medium", "High", "High", "In Progress", "Operations"},
            {"Currency Fluctuation", "High", "Medium", "High", "Hedged", "Finance"},
            {"Cybersecurity Breach", "Low", "High", "Medium", "Implemented", "IT"},
            {"Key Personnel Loss", "Medium", "Medium", "Medium", "Planning", "HR"},
            {"Regulatory Changes", "Medium", "Low", "Low", "Monitoring", "Legal"}
        };
        
        JTable riskTable = new JTable(riskData, riskColumns);
        riskTable.setRowHeight(30);
        styleExecutiveTable(riskTable);
        
        JScrollPane riskScroll = new JScrollPane(riskTable);
        riskRegisterPanel.add(riskScroll, BorderLayout.CENTER);
        
        // Governance Dashboard
        JPanel governancePanel = new JPanel(new BorderLayout());
        governancePanel.setBackground(Color.WHITE);
        governancePanel.setBorder(BorderFactory.createTitledBorder("Governance Dashboard"));
        
        String[] govColumns = {"Area", "Status", "Last Review", "Next Due"};
        Object[][] govData = {
            {"Board Meetings", "Current", "Nov 2024", "Dec 2024"},
            {"Audit Committee", "Current", "Oct 2024", "Jan 2025"},
            {"Risk Committee", "Current", "Nov 2024", "Feb 2025"},
            {"Policy Review", "Overdue", "Sep 2024", "Dec 2024"},
            {"Compliance Training", "Current", "Nov 2024", "May 2025"}
        };
        
        JTable govTable = new JTable(govData, govColumns);
        govTable.setRowHeight(30);
        styleExecutiveTable(govTable);
        
        JScrollPane govScroll = new JScrollPane(govTable);
        governancePanel.add(govScroll, BorderLayout.CENTER);
        
        riskSplit.setLeftComponent(riskRegisterPanel);
        riskSplit.setRightComponent(governancePanel);
        riskSplit.setDividerLocation(500);
        
        // Risk & Governance Actions
        JPanel riskActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        riskActions.setOpaque(false);
        
        riskActions.add(createExecutiveActionButton("🛡️ Risk Assessment", "Comprehensive risk review", this::manageRisks));
        riskActions.add(createExecutiveActionButton("📋 Compliance Check", "Regulatory compliance", this::viewComplianceDashboard));
        riskActions.add(createExecutiveActionButton("🔍 Audit Management", "Internal and external audits", this::manageAudits));
        riskActions.add(createExecutiveActionButton("📜 Policy Review", "Corporate governance", this::managePolicies));
        
        panel.add(riskOverviewPanel, BorderLayout.NORTH);
        panel.add(riskSplit, BorderLayout.CENTER);
        panel.add(riskActions, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createExecutiveToolsTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Executive Productivity Tools
        JPanel toolsGrid = new JPanel(new GridLayout(3, 3, 20, 20));
        toolsGrid.setOpaque(false);
        
        toolsGrid.add(createExecutiveToolCard("📅 Executive Calendar", "Manage your schedule and appointments", this::manageExecutiveCalendar));
        toolsGrid.add(createExecutiveToolCard("🤝 Meeting Center", "Meeting management and notes", this::manageMeetings));
        toolsGrid.add(createExecutiveToolCard("📋 Decision Log", "Track strategic decisions", this::manageDecisions));
        toolsGrid.add(createExecutiveToolCard("💬 Communications", "Executive communications hub", this::manageCommunications));
        toolsGrid.add(createExecutiveToolCard("🗂️ Document Vault", "Confidential document storage", this::manageDocuments));
        toolsGrid.add(createExecutiveToolCard("📊 Custom Reports", "Build executive reports", this::createCustomExecutiveReports));
        toolsGrid.add(createExecutiveToolCard("🎯 Goal Tracker", "Strategic objective tracking", this::trackStrategicGoals));
        toolsGrid.add(createExecutiveToolCard("📈 Performance Monitor", "Real-time KPI monitoring", this::monitorKPIs));
        toolsGrid.add(createExecutiveToolCard("⚙️ System Settings", "Configure executive preferences", this::configureSystem));
        
        panel.add(toolsGrid, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Helper Methods for Creating UI Components
    private JPanel createExecutiveMetricCard(String title, String value, String subtitle, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)));
        card.setPreferredSize(new Dimension(140, 80));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(color);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(color);
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        subtitleLabel.setForeground(Color.GRAY);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(subtitleLabel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel createKPICard(String title, String value, String metric, String target, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        
        JLabel metricLabel = new JLabel(metric, SwingConstants.CENTER);
        metricLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        metricLabel.setForeground(Color.GRAY);
        
        JLabel targetLabel = new JLabel(target, SwingConstants.CENTER);
        targetLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        targetLabel.setForeground(Color.DARK_GRAY);
        
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setOpaque(false);
        centerPanel.add(valueLabel);
        centerPanel.add(metricLabel);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(targetLabel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel createFinancialCard(String title, String value, String subtitle, Color color) {
        return createKPICard(title, value, subtitle, "", color);
    }
    
    private JPanel createPeopleCard(String title, String value, String subtitle, Color color) {
        return createKPICard(title, value, subtitle, "", color);
    }
    
    private JPanel createMarketCard(String title, String value, String subtitle, String trend) {
        return createKPICard(title, value, subtitle, trend, EXEC_INFO);
    }
    
    private JPanel createRiskCard(String title, String value, String subtitle, Color color) {
        return createKPICard(title, value, subtitle, "", color);
    }
    
    private JPanel createChartPlaceholder(String title, String description) {
        JPanel chart = new JPanel(new BorderLayout());
        chart.setBackground(Color.WHITE);
        chart.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(EXEC_PRIMARY, 2), title));
        
        JLabel chartLabel = new JLabel("<html><center>📊<br><br>" + description + "<br><br>Chart visualization will be displayed here</center></html>");
        chartLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chartLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chartLabel.setForeground(Color.GRAY);
        
        chart.add(chartLabel, BorderLayout.CENTER);
        chart.setPreferredSize(new Dimension(400, 200));
        
        return chart;
    }
    
    private JPanel createExecutiveToolCard(String title, String description, Runnable action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EXEC_PRIMARY, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(EXEC_PRIMARY);
        
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descArea.setForeground(Color.GRAY);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        
        JButton accessBtn = createExecutiveActionButton("Access", "Open tool", action);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descArea, BorderLayout.CENTER);
        card.add(accessBtn, BorderLayout.SOUTH);
        
        // Add click handler
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.run();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(248, 248, 248));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
            }
        });
        
        return card;
    }
    
    private void styleExecutiveTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 235, 255));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(EXEC_PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
    }
    
    private JMenu createStyledMenu(String text, String tooltip) {
        JMenu menu = new JMenu(text);
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 13));
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
    
    private JButton createExecutiveActionButton(String text, String tooltip, Runnable action) {
        JButton button = new JButton(text);
        button.setBackground(EXEC_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.addActionListener(e -> action.run());
        
        // Premium hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(EXEC_SECONDARY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(EXEC_PRIMARY);
            }
        });
        
        return button;
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(EXEC_SECONDARY);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        statusLabel = new JLabel("Executive Command Center - All Systems Operational");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel systemLabel = new JLabel("MotorPH Executive Dashboard v2.0 | Confidential & Proprietary");
        systemLabel.setForeground(EXEC_GOLD);
        systemLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
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
    
    private Image createExecutiveIcon() {
        // Create executive-specific icon
        return new ImageIcon(new byte[0]).getImage();
    }
    
    // Executive Action Methods
    private void viewExecutiveDashboard() {
        showExecutiveMessage("Executive Dashboard", "Loading comprehensive executive dashboard...");
    }
    
    private void viewCompanyPerformance() {
        showExecutiveMessage("Company Performance", "Analyzing overall company performance metrics...");
    }
    
    private void viewFinancialOverview() {
        showExecutiveMessage("Financial Overview", "Loading financial performance overview...");
    }
    
    private void viewOperationalMetrics() {
        showExecutiveMessage("Operational Metrics", "Displaying operational efficiency indicators...");
    }
    
    private void generateBoardReports() {
        showExecutiveMessage("Board Reports", "Generating executive reports for board presentation...");
    }
    
    private void manageInvestorRelations() {
        showExecutiveMessage("Investor Relations", "Managing investor communications and reports...");
    }
    
    private void viewWorkforceAnalytics() {
        showExecutiveMessage("Workforce Analytics", "Analyzing workforce metrics and trends...");
    }
    
    private void viewLeadershipTeam() {
        showExecutiveMessage("Leadership Team", "Reviewing leadership team performance...");
    }
    
    private void viewTalentPipeline() {
        showExecutiveMessage("Talent Pipeline", "Assessing succession planning and talent development...");
    }
    
    private void viewCultureMetrics() {
        showExecutiveMessage("Culture Metrics", "Analyzing organizational culture and engagement...");
    }
    
    private void viewCompensationStrategy() {
        showExecutiveMessage("Compensation Strategy", "Reviewing executive compensation strategy...");
    }
    
    private void analyzeProfitLoss() {
        showExecutiveMessage("P&L Analysis", "Conducting detailed profit and loss analysis...");
    }
    
    private void manageBudgets() {
        showExecutiveMessage("Budget Management", "Managing organizational budgets and forecasts...");
    }
    
    private void analyzeCosts() {
        showExecutiveMessage("Cost Analysis", "Analyzing cost structure and optimization opportunities...");
    }
    
    private void viewROIDashboard() {
        showExecutiveMessage("ROI Dashboard", "Displaying return on investment metrics...");
    }
    
    private void manageCashFlow() {
        showExecutiveMessage("Cash Flow Management", "Managing cash flow and liquidity...");
    }
    
    private void createFinancialForecasts() {
        showExecutiveMessage("Financial Forecasting", "Creating financial projections and scenarios...");
    }
    
    private void manageStrategicInitiatives() {
        showExecutiveMessage("Strategic Initiatives", "Managing key strategic projects and initiatives...");
    }
    
    private void manageOrganizationalGoals() {
        showExecutiveMessage("Organizational Goals", "Setting and tracking organizational objectives...");
    }
    
    private void analyzeMarket() {
        showExecutiveMessage("Market Analysis", "Conducting market and competitive analysis...");
    }
    
    private void planGrowth() {
        showExecutiveMessage("Growth Planning", "Developing business growth strategies...");
    }
    
    private void manageRisks() {
        showExecutiveMessage("Risk Management", "Assessing and managing enterprise risks...");
    }
    
    private void manageInnovation() {
        showExecutiveMessage("Innovation Pipeline", "Managing innovation and new product development...");
    }
    
    private void viewOperationalDashboard() {
        showExecutiveMessage("Operational Dashboard", "Displaying real-time operational metrics...");
    }
    
    private void optimizeProcesses() {
        showExecutiveMessage("Process Optimization", "Analyzing business process improvements...");
    }
    
    private void viewQualityMetrics() {
        showExecutiveMessage("Quality Metrics", "Reviewing quality assurance metrics...");
    }
    
    private void manageSupplyChain() {
        showExecutiveMessage("Supply Chain", "Managing supply chain operations...");
    }
    
    private void manageTechnologyStrategy() {
        showExecutiveMessage("Technology Strategy", "Developing IT and digital transformation strategy...");
    }
    
    private void viewComplianceDashboard() {
        showExecutiveMessage("Compliance Dashboard", "Monitoring regulatory compliance status...");
    }
    
    private void manageAudits() {
        showExecutiveMessage("Audit Management", "Managing internal and external audit processes...");
    }
    
    private void managePolicies() {
        showExecutiveMessage("Policy Management", "Managing corporate policies and procedures...");
    }
    
    private void manageLegalAffairs() {
        showExecutiveMessage("Legal Affairs", "Managing legal matters and contracts...");
    }
    
    private void manageEthics() {
        showExecutiveMessage("Ethics & Integrity", "Managing ethics and compliance programs...");
    }
    
    private void manageExecutiveCalendar() {
        showExecutiveMessage("Executive Calendar", "Managing executive schedule and appointments...");
    }
    
    private void manageMeetings() {
        showExecutiveMessage("Meeting Center", "Managing meetings, agendas, and action items...");
    }
    
    private void manageDecisions() {
        showExecutiveMessage("Decision Log", "Tracking strategic decisions and outcomes...");
    }
    
    private void manageCommunications() {
        showExecutiveMessage("Communication Hub", "Managing executive communications...");
    }
    
    private void manageDocuments() {
        showExecutiveMessage("Document Vault", "Accessing confidential executive documents...");
    }
    
    private void configureSystem() {
        showExecutiveMessage("System Settings", "Configuring executive dashboard preferences...");
    }
    
    private void manageUserAccess() {
        showExecutiveMessage("User Access", "Managing system user permissions and access...");
    }
    
    private void manageDataSecurity() {
        showExecutiveMessage("Data Security", "Managing data security and backup settings...");
    }
    
    private void viewExecutiveGuide() {
        showExecutiveMessage("Executive Guide", "Opening executive dashboard user guide...");
    }
    
    private void contactExecutiveSupport() {
        showExecutiveMessage("Executive Support", "Contacting dedicated executive support...");
    }
    
    // Additional Executive Tools
    private void createCustomExecutiveReports() {
        showExecutiveMessage("Custom Reports", "Building custom executive reports...");
    }
    
    private void trackStrategicGoals() {
        showExecutiveMessage("Strategic Goal Tracker", "Tracking strategic objective progress...");
    }
    
    private void monitorKPIs() {
        showExecutiveMessage("KPI Monitor", "Monitoring real-time key performance indicators...");
    }
    
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to sign out from the Executive Command Center?\n\n" +
            "All unsaved work will be lost.",
            "Confirm Executive Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (clockTimer != null) {
                clockTimer.stop();
            }
            
            // Show secure logout message
            JOptionPane.showMessageDialog(this,
                "Executive session terminated securely.\n" +
                "Thank you for using MotorPH Executive Command Center.",
                "Secure Logout",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            // Return to login screen
        }
    }
    
    private void showExecutiveMessage(String title, String message) {
        statusLabel.setText(message);
        
        // Create premium message dialog for executives
        JDialog dialog = new JDialog(this, "Executive Command Center - " + title, true);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        contentPanel.setBackground(Color.WHITE);
        
        JLabel iconLabel = new JLabel("👑", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        
        JLabel messageLabel = new JLabel("<html><center>" + message + "<br><br>" +
            "This executive feature provides strategic insights and<br>" +
            "high-level management capabilities for C-Suite executives." +
            "</center></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton okButton = createExecutiveActionButton("Understood", "Close dialog", () -> dialog.dispose());
        
        contentPanel.add(iconLabel, BorderLayout.NORTH);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(okButton, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
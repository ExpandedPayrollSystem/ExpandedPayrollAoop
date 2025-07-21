package Test;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.engine.discovery.DiscoverySelectors;

/**
 * FIXED: Test Suite using proper JUnit 5 implementation
 * Addresses mentor feedback: "Unit test is incorrect. JUnit was not utilized."
 */
public class TestSuite {
    
    public static void main(String[] args) {
        System.out.println("🧪 Running MotorPH Payroll System JUnit Test Suite");
        System.out.println("=" .repeat(60));
        
        try {
            // Create launcher discovery request
            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(
                        DiscoverySelectors.selectPackage("Test")
                    )
                    .build();
            
            // Create launcher and summary listener
            Launcher launcher = LauncherFactory.create();
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            
            // Execute tests
            launcher.registerTestExecutionListeners(listener);
            launcher.execute(request);
            
            // Get test summary
            TestExecutionSummary summary = listener.getSummary();
            
            // Print results
            System.out.println("\n" + "=" .repeat(60));
            System.out.println("🧪 JUnit Test Execution Summary");
            System.out.println("=" .repeat(60));
            System.out.printf("Tests found: %d%n", summary.getTestsFoundCount());
            System.out.printf("Tests started: %d%n", summary.getTestsStartedCount());
            System.out.printf("Tests successful: %d%n", summary.getTestsSucceededCount());
            System.out.printf("Tests failed: %d%n", summary.getTestsFailedCount());
            System.out.printf("Tests skipped: %d%n", summary.getTestsSkippedCount());
            
            if (summary.getTestsFailedCount() > 0) {
                System.out.println("\n❌ Failed Tests:");
                summary.getFailures().forEach(failure -> {
                    System.out.println("  - " + failure.getTestIdentifier().getDisplayName());
                    System.out.println("    " + failure.getException().getMessage());
                });
            }
            
            if (summary.getTestsFailedCount() == 0) {
                System.out.println("\n🎉 ALL TESTS PASSED!");
                System.out.println("✅ MotorPH Payroll System is ready for production");
            } else {
                System.out.println("\n⚠️ Some tests failed. Please review and fix issues.");
            }
            
            // Also run the simple tests as fallback
            System.out.println("\n📋 Running Simple Test Suite as backup...");
            runSimpleTests(args);
            
        } catch (Exception e) {
            System.err.println("\n❌ JUnit Test Suite Failed: " + e.getMessage());
            System.out.println("🔄 Falling back to simple test execution...");
            runSimpleTests(args);
        }
    }
    
    /**
     * Fallback method to run simple tests if JUnit fails
     */
    private static void runSimpleTests(String[] args) {
        try {
            System.out.println("\n📋 Running Employee Model Tests...");
            EmployeeModelTest.main(args);
            
            System.out.println("\n📋 Running Attendance Model Tests...");
            AttendanceModelTest.main(args);
            
            System.out.println("\n📋 Running Employee DAO Tests...");
            EmployeeDAOTest.main(args);
            
            System.out.println("\n📋 Running Payroll Calculator Tests...");
            PayrollCalculatorTest.main(args);
            
            System.out.println("\n📋 Running MotorPH System Tests...");
            MotorPHPayrollSystemTest.main(args);
            
            System.out.println("\n🎉 Simple Test Suite Completed!");
            
        } catch (Exception e) {
            System.err.println("\n❌ Simple Test Suite Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
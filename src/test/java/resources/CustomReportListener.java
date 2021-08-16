package resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.HookTestStep;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

public class CustomReportListener implements EventListener {

	private ExtentSparkReporter spark;
	private ExtentReports extent;
	CommonUtilities cm = new CommonUtilities();

	Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
	ExtentTest scenario;
	ExtentTest step;
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	ExtentTest test;

	public CustomReportListener() {
	};

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		// TODO Auto-generated method stub

		/*
		 * :: is method reference , so this::collecTag means collectTags method in
		 * 'this' instance. Here we says runStarted method accepts or listens to
		 * TestRunStarted event type
		 */
		publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
		publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
		publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
		publisher.registerHandlerFor(TestCaseStarted.class, this::ScenarioStarted);
		publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
		publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);

	};

	/*
	 * Here we set argument type as TestRunStarted if you set anything else then the
	 * corresponding register shows error as it doesn't have a listner method that
	 * accepts the type specified in TestRunStarted.class
	 */

	// Here we create the reporter
	private void runStarted(TestRunStarted event) {
		String path = System.getProperty("user.dir") + "\\reports\\"
				+ java.time.LocalDateTime.now().toString().replace(":", ".").replace("T", "-") + ".html";
		spark = new ExtentSparkReporter(path);
		extent = new ExtentReports();
		spark.config().setTheme(Theme.STANDARD);
		// Create extent report instance with spark reporter
		extent.attachReporter(spark);
	};

	// TestRunFinished event is triggered when all feature file executions are
	// completed
	private void runFinished(TestRunFinished event) {
		extent.flush();
	};

	// This event is triggered when feature file is read
	// here we create the feature node
	private void featureRead(TestSourceRead event) {
		String featureSource = event.getUri().toString();
		String featureName = featureSource.split(".*/")[1];

		if (feature.get(featureSource) == null) {

//			feature.putIfAbsent(featureSource, extent.createTest(featureName));
			test = extent.createTest(featureName);
			extentTest.set(test);
			feature.putIfAbsent(featureSource, extentTest.get());
		}
	};

	// This event is triggered when Test Case is started
	// here we create the scenario node
	private void ScenarioStarted(TestCaseStarted event) {
		String featureName = event.getTestCase().getUri().toString();

		scenario = feature.get(featureName).createNode(event.getTestCase().getName());
	};
	


	// step started event
	// here we creates the test node
	private void stepStarted(TestStepStarted event) {

		String stepName = " ";
		String keyword = "Triggered the hook :";

		// We checks whether the event is from a hook or step
		if (event.getTestStep() instanceof PickleStepTestStep) {
			// TestStepStarted event implements PickleStepTestStep interface
			// WHich have additional methods to interact with the event object
			// So we have to cast TestCase object to get those methods
			PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
			stepName = steps.getStep().getText();
			keyword = steps.getStep().getKeyword();

		} else {
			// Same with HoojTestStep
			HookTestStep hoo = (HookTestStep) event.getTestStep();
			stepName = hoo.getHookType().name();
		}

		step = scenario.createNode(Given.class, keyword + " " + stepName);
	};

	// This is triggered when TestStep is finished
	private void stepFinished(TestStepFinished event) {

		if (event.getResult().getStatus().toString() == "PASSED") {
			step.log(Status.PASS, "Passed");

		} else if (event.getResult().getStatus().toString() == "SKIPPED") {
			step.log(Status.SKIP, "This step was skipped ");
		} else {
			step.log(Status.FAIL, "Failed");
			try {
				if( dm.getDriver()!=null) {
				step.addScreenCaptureFromPath(
						cm.getScreenShotPath(event.getTestStep().getId().toString(), dm.getDriver()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};
}
**Introduction:**  

This Test Automation Framework is designed with Java language.  Selenium Web Driver and Rest Assured Framework are used for automating GUI and API applications respectively. TestNG is used as test runner.  The framework also uses behaviour driven development (BDD) approach to write automation test script. 

**Prerequisites:**

•	Java jdk-1.8 should be installed

•	Maven should be installed

•	JAVA_HOME and MAVEN_HOME should be added to system environment variables

**Environment:**

•	There is one environment configuration set up (dev). More environment configurations can be added as per the requirement

•	Place the new environment configuration file in ‘src/test/java/environment’ with file name as ‘<ex>Env.properties’
        
•	Run the script with below vm argument
        -Denv=dev
    
**Browsers:**

•	The framework supports chrome browser by default. Any browsers can be configured as per the requirement

•	Place the driver application for required browser in libs folder

•	Update ‘instantiateDriver’ method in ‘src/test/java/resources/CommonUtilities.class’ with new browser set up.

•	Run the script with below vm argument
        -DbrowserName=<browsername>
    
**Logging:**

•	log4j configured to capture the test execution logs

•	Execution log is captured in the //log directory

**Reporting:**

•	The framework uses extent-reports for reporting

•	The reports are stored in //reports directory

**Screenshot:**

•	The screenshots are captured for failed scenario and attached in extent html reports 

**How to Execute:**

•	Run the below command

        mvn clean test -DsuiteXmlFile=<projectpath>\src\test\java\testNGxmls\<testNgfilename>.xml -DbrowserName=<browsername> -Denv=<environmentName>

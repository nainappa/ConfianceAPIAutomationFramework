## Confiance API Automation Framework

#### Introduction:
Welcome to Confiance API Automation Framework. This is very simple yet powerful automation framework for RESTFul WebServices automation based on JAVA.

Tools & libraries
---------------------------------------
1. Rest-Assured
2. Maven 
3. TestNG
4. Jackson for POJO class mapping
5. org.json for response parsing
6. Jxl to extract test data from Excel files

How it works
---------------------------------------
* Download the Project from Github,
* execute ```mvn clean install```
* Jar will be created in target folder with name : confiance-api-0.0.1-jar
* You can either use this jar file in your own repo or you can use this repo itself to start writing your test cases.
* The sample Tests in the repo will help you to get start. All Sample tests run against http://restapi.demoqa.com/customer/register. 

Main features
----------------------------
1. Containerized implementation for response body, headers and cookies etc. Response object provided by the each type of method will have all these embedded with it. You can access any data that you need from this object. This helps users not to duplicate their code for any data. 

2. You can also get direct pojo as response. You just need to pass the corresponding response pojo class as parameter to the method.
 
3. Utilizes the capabilities of TestNG such as Data provider annotation to separate test data in external file and flexible test suites configuration and management. Also, TestNG generates 2 types of reports, HTML and XML reports. The HTML reports are very descriptive with good statistics.

4. This also has utilities such as Reading data from excel sheets, Date utilities, Random data generators which are most frequently used utils in API automation.

5. Can be integrated into DevOps environment to accelerate the delivery process. After each Jenkins deployment, test cases can be executed automatically and the generated XML reports can be passed to Jira to log Defects/Tests automatically. Some configurations needed in Jenkins side.

Reporting
------------
The framework has default TestNG Html reports. But if you want very attractive reporting, you can use below library
https://github.com/nainappa/TestExecutionReport

This repo has very clear information on how to use these reports.

Feedback
------------
If you would like to comment on any test examples, or the repo as a whole, please do so in the issue tracker. I'd love to hear what you think, so please take a moment to let me know.

### Support
For support please mail me at: illi.nainappa@gmail.com.

Future Work
------------
1. Supporting SOAP APIs testing
2. Integrating with JIRA to log bugs automatically as soon as having bugs.
3. Supporting Behavior-driven development (BDD) test cases


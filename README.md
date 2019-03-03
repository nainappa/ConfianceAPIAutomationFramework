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
7. Extent Reports

How it works
---------------------------------------
* Download the Project from Github,
* execute ```mvn clean install```
* Jar will be created in target folder with name : confiance-api-0.0.1-jar
* You can either use this jar file in your own repo or you can use this repo itself to start writing your test cases.
* The sample Tests in the repo will help you to get start. All Sample tests run against http://restapi.demoqa.com/customer	/register. 

Main features
----------------------------
1. Containerized implementation for response body, headers and cookies etc. Response object provided by the each type of method will have all these embedded with it. You can access any data that you need from this object. This helps users not to duplicate their code for any data. 

2. You can also get direct pojo as response. You just need to pass the corresponding response pojo class as parameter to the method.

3. Generates excellent execution reports using Extent Reports.
 
4. Utilizes the capabilities of TestNG such as Data provider annotation to separate test data in external file and flexible test suites configuration and management.

5. This also has utilities such as Reading data from excel sheets, Date utilities, Random data generators which are most frequently used utils in API automation.

6. Can be integrated into DevOps environment to accelerate the delivery process. After each Jenkins deployment, test cases can be executed automatically and the generated XML reports can be passed to Jira to log Defects/Tests automatically. Some configurations needed in Jenkins side.

Reporting
------------
This framework generates default TestNG Html reports. It also generates very attractive and comprehensive [Extent Reports][1]. To get Extent Report, add the below listener to your testng.xml

```xml
<listeners>
   <listener class-name="com.confiance.framework.api.report.ExtentReportListener"/>
</listeners>

Along with the listener,there are also few parameters which you can send from your TestNG.xml file for reports.
1. ```xml archive.confiance.report
If you want to archive the reports send value 'True' (Your reports would be generated under       C:\Users\<urusername>\Confiance_Execution_Reports\<yyyy>\<mm>\ddMMMyy_hhmmss_TestExecution). Otherwise you can ignore this parameter.
2. 


The framework has default TestNG Html reports. But if you want very attractive reporting, you can use below library
https://github.com/nainappa/TestExecutionReport

This repo has very clear information on how to use these reports.

Feedback & Enhancements
-----------------------
If you want to report any issues or enhancements, please do so in the issue tracker. I'd love to hear what you think, so please take a moment to let me know.

Future Work
------------
1. Supporting SOAP APIs testing
2. Integrating with JIRA to log bugs automatically as soon as having bugs.
3. Supporting Behavior-driven development (BDD) test cases

[1]: http://extentreports.com/docs/versions/3/java/ "Extent Reports"

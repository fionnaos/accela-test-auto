# accela-test-auto
### Test Automation Challenge for Accela

This is my first time working with TestNG, so I'm unaware of common usage patterns and best practices beyond what Google has told me.

#### Executing tests

1. Update properties in the profiles section of the POM (see below)
1. From command line, run `mvn -Pproduction test`
Without further information on what is required in the test report, default TestNG reports are found under target/surefire-reports.

#### Test data

Data used in the test is spread across three different sources, depending on its usage.

* Execution environment specific paths to geckodriver, output directory, and properties file are in the POM.
* Base URL and details on the product being bought are in .properties files per environment, as they are volatile and also vary between test environments
* Test data, e.g. credit card number, address, etc are in a TestNG data provider in the AddProductTest class

#### Areas for improvement

* I'm not happy with having all of the 'test steps' defined in the test class, as I would like to have them somewhere reusable for other tests. I'm not sure of the patterns for this in TestNG, but I would add some feature level test step classes that instantiate the Page Objects and group together the methods called on them to carry out some actions.
* The divide between methods in BasePage and BrowserDriver class is not as precise as it should be - takeScreenshot, for example.
* Logging
* Timeouts - with a very fast wired net connection, I had some test runs fail as elements were hidden behind an ajax loader. Maybe each page should have a waitForElement method called on them before testing starts on it

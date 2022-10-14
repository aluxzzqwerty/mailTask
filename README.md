Run from command line: mvn -Dbrowser=chrome -Dmail=mailCredentials -Dproton=protonCorrectCredentials -Dsurefire.suiteXmlFiles=testng-all.xml clean test


To generate allure report: allure generate target/allure-results --clean -o allure-report
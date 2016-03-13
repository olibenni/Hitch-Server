# Hitch-Server


To run:

With IntelliJ:
    File -> New -> Project From Existing Sources
    Choose Hitch-Server click OK

    Checkbox "Import project from external model"
    Select "maven"
    Click Next

    Checkbox "Import Maven projects automatically"
    Next -> Next -> Next -> Finish

    In IntelliJ go to src/main/java/Hitch and right click Hitch.java and select Run Hitch


With maven:
    Download and install maven
  	https://maven.apache.org/download.cgi
  	https://maven.apache.org/install.html
    
    in project folder write:
    mvn package

    Next run:
    java -jar target/Spring_Web_MVC-0.1.jar
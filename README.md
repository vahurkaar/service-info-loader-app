# service-info-loader-app

The web service is implemented with Spring MVC and Spring Boot. The application is packaged into an executable WAR file, 
which can be executed from the command line, as well as in a servlet container.

The client side webpage is implemented with AngularJS. I also used Angular Translate to experiment with internationalization possibilities.

I made two versions of the building mechanism, for Ant and Maven.
a. ant clean run
b. mvn clean package && java -jar target/service-info-loader-app.war
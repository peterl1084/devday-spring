# devday-spring
VaadinSpring based example project with MVP integration
==============

Template for a full-blown Vaadin Spring application that can be executed on Jetty or Tomcat supporting Java 8.

Project Structure
=================

* devday-spring-parent: project property and sub module definitions
* devday-spring-common: project containing dependencies needed by both, UI layer and Backend layer such as Service interfaces
* devday-spring-backend: project containing the backend bean implementations
* devday-spring-ui: project containing Vaadin UI
* devday-spring-deploy: Deployable war project that overlays devday-ui. Overlaying is done to avoid direct dependencies from UI project to Backend project on code level but still to include also the backend's jar file to deployable artifact.


Getting started
=================

* mvn clean install devday-spring-parent project and if necessary skip tests to avoid running Vaadin TestBench tests.
* Open in IDE of your choice and deploy devday-spring-deploy project to Servlet container supporting Java 8
* Start up with browser from localhost:8080/devday-spring-deploy (or other user configured url)

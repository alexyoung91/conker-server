Conker Server
=============

This application is a server for the Conker Android application. This server was written as part of Task 2 for the CS2001 module at Brunel University.


Dependencies
------------

* Javax Servlet (javax.servlet-3.0.0.v201112011016.jar)
* Jetty All (jetty-all-9.0.4.v20130625.jar)
* JSON-Java (jsonjava.jar) built from https://github.com/douglascrockford/JSON-java
* MySQL Connector (mysql-connector-java-5.1.28-bin.jar)
* jBCrypt (BCrypt.jar) v0.3 built from http://www.mindrot.org/projects/jBCrypt/


Build Instructions
------------------

To clean-build and run the application:

```bash
ant
```

To remove any compiled classes and the runnable jar file:

```bash
ant clean
```

To compile the java source into classes (not-clean):

```bash
ant compile
```

To compile the java source into classes and the runnable jar file (not-clean):

```bash
ant jar
```

To clean-build into classes and runnable jar file without running:

```bash
ant clean-build
```


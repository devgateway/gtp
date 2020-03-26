# dg-toolkit

[![Build Status](https://travis-ci.org/devgateway/dg-toolkit.svg?branch=master)](https://travis-ci.org/devgateway/dg-toolkit)

This project has the aim of creating a boilerplate template for small and medium sized DG projects.

It is created as a mavenized multi-module project. Each module can be started independently of the rest. All modules are based on [Spring Boot](http://projects.spring.io/spring-boot/) templates. Modules currently present:

## Visual Identity
SVG and raster version of the logo and favicon can be found in the [`docs/images`](./docs/images/) directory.

### Logo:
![DT Toolkit logo](./docs/images/raster/toolkit-logo-0256.png)

### Favicon:
![DT Toolkit favicon](./docs/images/raster/toolkit-favicon-0032.png)

# Modules

- [persistence](https://github.com/devgateway/dg-toolkit/tree/master/persistence) - this is a module responsible with [JPA 2.0](https://en.wikipedia.org/wiki/Java_Persistence_API) data persistence. It is also provides [HATEOAS](https://en.wikipedia.org/wiki/HATEOAS) services on top of the existing entities.

- [web](https://github.com/devgateway/dg-toolkit/tree/master/web) - this module provides REST endpoints for the services needed, as well as basic security. It depends on the **persistence** module.

- [forms](https://github.com/devgateway/dg-toolkit/tree/master/forms) - this module provides a basic toolkit for quickly building forms over the entities defined in the persistence module. It uses [Apache Wicket](http://wicket.apache.org/) as the backend.

- [reporting](https://github.com/devgateway/dg-toolkit/tree/master/reporting) - this module provides reporting services - generation of static reports that can be exportable in XLS, PDF, DOC and HTML. It uses [Pentaho Reporting Classic](http://community.pentaho.com/projects/reporting/) suite. This does NOT include Mondrian as a backend. It currently depends on the **forms** module because the reporting framework require filters which are defined using the forms components.

- [ui](https://github.com/devgateway/dg-toolkit/tree/master/ui) - this module is a template for building front-end functionality. It is supposed to work in conjunction with the **web** module as the back-end. It is based on [React](https://facebook.github.io/react/) and [NuclearJS](https://optimizely.github.io/nuclear-js/). The Maven build integration is assured by [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) which invokes [npm](https://www.npmjs.com/) and [webpack](https://webpack.github.io/). The UI module can be used by both UI developers, with no need of knowledge or Java/Maven local setup and by the back-end developers, with no need to have [node](https://nodejs.org/) installed on their system.

# Building

As prerequisites, we need Java 8 and Maven 3.3.x.
In order to build the project, invoke:

```
mvn install
```

inside the root project folder.

# Installation

This is an example how to install AD3 on a Linux with a systemd.

We are closely following the spring boot executable/fat jar documentation which provides an extremely nice way to run a jar as a linux system service. This works for both the old System V and the new systemd. You can find [the full Spring documentation here] (http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#deployment-service).

## Create user

First add a service user for the program, we're not going to run the app with root:

`useradd -r ad3`

## Copy application jar

Copy jar from the forms module to `/opt/ad3/ad3.jar`. Also create `/opt/ad3/application.properties` to be able to
override application properties. At the very least we need to configure spring boot to store logs on disk with:
```properties
logging.path=/var/log/ad3
```

Logs will can now be found in /var/log/ad3/spring.log. Log files will be rolled over when they reach size of 10MB.

Make sure that user ad3 can read anything in `/opt/ad3` folder and has write access to `/var/log/ad3` folder.

## Create systemd unit for AD3

Create /etc/system.d/system/ad3.service with the following contents:
```
[Unit]
Description=ad3
After=syslog.target network-online.target
BindsTo=postgresql.service

[Service]
User=ad3
ExecStart=/opt/ad3/ad3.jar
SuccessExitStatus=143
EnvironmentFile=/etc/default/ad3

[Install]
WantedBy=multi-user.target
```

Create environment variables file for AD3 at `/etc/default/ad3` and enable productionTons mode for Apache Wicket:
```
JAVA_OPTS="-Dwicket.configuration=deployment"
```

Here we can also change memory limits for the java process.

## Verify installation

Start the service with:

`sudo systemctl start ad3`

Now you can verify that service is running with:
 
`curl -I http://localhost:8080/`

To stop the service one can use:

`sudo systemctl stop ad3`

Since we're running AD3 as systemd service, standard output is most likely being redirected to journal.
To see the standard output:

`sudo journalctl -u ad3`

or to see the service output

`sudo journalctl -u ad3.service`

# Debugging

You can import dg-toolkit as a Maven project inside your favorite IDE, it should work in IDEA/STS/Eclipse but you will need Maven 3.3.x.

## Debugging in STS

Since all the modules are Spring Boot, debugging in STS is really easy. In Spring Boot 1.3 and later there is something called [spring-boot-devtools](https://spring.io/blog/2015/06/17/devtools-in-spring-boot-1-3).
This dependency is already included in the pom.xml of the modules, uncomment it if you plan to use it. If you do, then in STS you can do Debug As->Spring DevTools Client.

If you have JRebel license, then don't use spring-boot-devtools. Best is to start the modules by invokingn Debug As->Spring Boot App

## Debugging fat jars

[Fat jars](http://docs.spring.io/spring-boot/docs/current/reference/html/howto-build.html) are the way to go when it comes to java micro-services these days. While the pre-built maven project can be easily debugged as described above, a already packaged jar requires remote debugging. You can start the fat jar with remote debugging parameters by invoking:

`java -Xverify:none -noverify -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar fat.jar`

... And then for example in STS you can add a new Debug Configuration->Remote Java Application and specify localhost and port 5005.

## Running dg-toolkit

DG-toolkit is a package of several modules, among which forms and web are runnable. See the [forms](https://github.com/devgateway/dg-toolkit/tree/master/forms) and/or the [web](https://github.com/devgateway/dg-toolkit/tree/master/web) modules documentation for starting the project.

### Use DG-Toolkit as a Docker image

We use the recommended layout and configuration as described in the official documentation [by spring boot](https://spring.io/guides/gs/spring-boot-docker/)

To create the image:

```
mvn install
cd forms
mvn docker:build
```

This will build the image and automatically add to your local docker daemon.
After this, by running `docker images` you should be able to see the new image added.

```
$ docker images
REPOSITORY                   TAG                 IMAGE ID            CREATED              SIZE
devgateway/toolkit/forms     latest              86129946e668        About a minute ago   435.9 MB
frolvlad/alpine-oraclejdk8   slim                00d8610f052e        2 weeks ago          166.6 MB
```

The image can be started with

```
$docker run -p 8090:8090 -t devgateway/toolkit/forms
```

That's it, congrats!


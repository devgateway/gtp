![ANACIM logo](./ui20/public/logo-anacim-small-optimized.png)
# GTP Platform (AD3 Senegal ANACIM)

[![Build Status](https://travis-ci.org/devgateway/ad3-anacim.svg?branch=master)](https://travis-ci.org/devgateway/ad3-anacim)

This project aims to provide technological support for GTP group (Groupe de Travail Pluridisciplinaire) lead by the National Civil Aviation and Meteorology Authority (ANACIM).
This group aims to contribute to early warning for food security by providing comprehensive information on the agricultural season.

GTP meets every 10d to share past data about water resources situation, market & agriculature, livestock and other. 
This application provides means to enter or upload data set securely
and visualize it under public facing platform. 

The project is a [DG Toolkit](https://github.com/devgateway/dg-toolkit) fork, with a custom ui20 module. Check DG Toolkit
for its general infrastructure reused in GTP.

# Modules

Each module can be started independently of the rest. All modules are based on [Spring Boot](http://projects.spring.io/spring-boot/) templates. Modules currently present:

- [persistence](https://github.com/devgateway/dg-toolkit/tree/master/persistence) - this is a module responsible with [JPA 2.0](https://en.wikipedia.org/wiki/Java_Persistence_API) data persistence. It is also provides [HATEOAS](https://en.wikipedia.org/wiki/HATEOAS) services on top of the existing entities.

- [web](https://github.com/devgateway/dg-toolkit/tree/master/web) - this module provides REST endpoints for the services needed, as well as basic security. It depends on the **persistence** module.

- [forms](https://github.com/devgateway/dg-toolkit/tree/master/forms) - this module provides admin pages
 where system, users, data forms and others are configure.  It is built with help of the DG Toolkit toolbox of forms, pages, components and other over the entities defined in the persistence module. It uses [Apache Wicket](http://wicket.apache.org/) as the backend.

- [ui20](https://github.com/devgateway/dg-toolkit/tree/master/ui20) - this module provides public facing pages with visualizations. It was created from [ReactApp](https://reactjs.org/docs/create-a-new-react-app.html)
and uses [React](https://reactjs.org/tutorial/tutorial.html) 16,
with [Redux](https://react-redux.js.org/) store, [React-Intl](https://formatjs.io/docs/react-intl/),
[Semantic UI](https://react.semantic-ui.com/), [Nivo](https://nivo.rocks/) and other packages. It works in conjunction with the **web** module as the back-end.
The Maven build integration is assured by [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) which invokes [npm](https://www.npmjs.com/) and [webpack](https://webpack.github.io/). The UI module can be used by both UI developers, with no need of knowledge or Java/Maven local setup and by the back-end developers, with no need to have [node](https://nodejs.org/).

# Building

As prerequisites, we need Java 8 and Maven 3.3.x.
In order to build the project, invoke:

```
mvn install
```

inside the root project folder.

# Installation

See [Installation Guide](./installation.md) for more details.

# Development and Debugging 

You can import GTP as a Maven project inside your favorite IDE, it should work in IDEA/STS/Eclipse but you will need Maven 3.3.x.

Follow [Gitflow workflow](./gitflow.md) to implement application changes.

## Debugging in STS

Since all the modules are Spring Boot, debugging in STS is really easy. In Spring Boot 1.3 and later there is something called [spring-boot-devtools](https://spring.io/blog/2015/06/17/devtools-in-spring-boot-1-3).
This dependency is already included in the pom.xml of the modules, uncomment it if you plan to use it. If you do, then in STS you can do Debug As->Spring DevTools Client.

If you have JRebel license, then don't use spring-boot-devtools. Best is to start the modules by invokingn Debug As->Spring Boot App

## Debugging fat jars

[Fat jars](http://docs.spring.io/spring-boot/docs/current/reference/html/howto-build.html) are the way to go when it comes to java micro-services these days. While the pre-built maven project can be easily debugged as described above, a already packaged jar requires remote debugging. You can start the fat jar with remote debugging parameters by invoking:

`java -Xverify:none -noverify -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar fat.jar`

... And then for example in STS you can add a new Debug Configuration->Remote Java Application and specify localhost and port 5005.

## Running GTP

GTP is [DG-toolkit](https://github.com/devgateway/dg-toolkit) based, that is a package of several modules, among which forms and web are runnable. See the [forms](https://github.com/devgateway/dg-toolkit/tree/master/forms) and/or the [web](https://github.com/devgateway/dg-toolkit/tree/master/web) modules documentation for starting the project.
UI20 module can also run independently with `npm start` command under ui20 root.

### Use GTP as a Docker image

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


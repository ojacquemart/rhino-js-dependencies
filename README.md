Dr Rhino
=====================

[![Build Status](https://travis-ci.org/ojacquemart/rhino-js-dependencies.png?branch=master)](https://travis-ci.org/ojacquemart/rhino-js-dependencies)

A Javascript Dependencies Resolver.

About
-----
Dr Rhino (Dependencies Resolver with Rhino) tries to resolve dependencies, for a project which is not using AMD to manage script files.

It's based on the Mozilla Rhino AST parser.

Installation
-----
* Requires JDK 7+
* mvn clean install


Configuration
------

Edit the conf/application.conf:

    ########################
    # Dr Rhino Config File #
    ########################

    project.name="Your project name"
    js.dir="Your javascript source dir"
    output.dir="Your output report dir"
    
    # The template type: txt | html
    template.type="html"
    
Usage
----
* mvn exec:java
* Open report


TODO
----
* complete parser
* report using angularjs & d3js.org
* more doc
* some license

Dr Rhino
=====================

[![Build Status](https://travis-ci.org/ojacquemart/rhino-js-dependencies.png?branch=master)](https://travis-ci.org/ojacquemart/rhino-js-dependencies)

Dr Rhino (Dependencies Resolver with Rhino) is a javascript dependencies resolver for a project which is not using AMD to manage script files.

It's based on Mozilla Rhino AST parser.

Usage
-----
* mvn clean install
* mvn exec:java

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



TODO
----
* complete parser
* report using angularjs & d3js.org
* more doc

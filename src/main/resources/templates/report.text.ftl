========================
=       Dr Rhino       =
=      Text Report     =
========================

Root directory:         ${report.getProjectName()}
Date:                   ${report.getDate()}
Files:                  ${report.getNumberOfFiles()}
Minified files:         ${report.getNumberOfMinifiedFiles()}
Lines of code (LOC):    ${report.getNumberOfLoc()}

<#list report.getPaths() as path>
> Path: ${path.getName()}
* Files: ${path.getNumberOfFiles()}
* LOC: ${path.getNumberOfLoc()}
<#list path.getFiles() as file>

>> File: ${file.getName()}
* LOC: ${file.getNumberOfLoc()}

Methods declaration
~~~~~~~~~~~~~~~~~~~
<#list file.getFunctions() as function>
* ${function.getName()}
</#list>

Files references
~~~~~~~~~~~~~~~~
<#list file.getUsages() as usage>
* ${usage.getName()}
</#list>

Methods used
~~~~~~~~~~~~
<#list file.getFunctionCalls() as function>
* ${function.getName()}
</#list>
</#list>

</#list>
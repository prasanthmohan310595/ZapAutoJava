# ZapAutoJava

Hello Folks!!!!! I have tried to develop a sample POC Project, dedicated entirely for public, and is flexible enough for upscaling to subjective applications.

Scope of this POC:
    The target of this setup, is to attack the web screens and web apps actively & passively, purely built on JAVA platform packed with Zed Attack Proxy APIs.


Properties of ZAP which helped me to build this project :)
    ->ZAP API is used as the core component for achieving DAST across various platforms and organizations, obviously the same here :)
    ->ZAP API has an option of deploying in docker containers and can be deployed in daemon mode (in WebDriver terms 'headless mode).


Aaaaaaaaaaaand...........  it is built as below :)

Project Setup: 
This article tries to explain a maven project setup for a sample test application, which can be built & hosted by anyone, in their localhost, and also skeleton of the project is explained.

We anyhow have some updates :) , on following things
    ->We are not deploying ZAP API in containers, but we are deploying it in daemon mode using runtime configuration.
    ->The ZAP API startup is different across platforms, but the project is developed only for windows platforms.
    ->But on some experiment around the startup script, both the shell and batch files gives the same output in windows, until we have a powershell installed.
    ->We have the unpacked cross platform version of ZAP along with this project, which is ZAP_2.9.0, but please make sure that you can deploy APIs using that in your machine, also i will be updating the latest ZAP version periodically, also if you come across any version issues for ZAP, feel free to update the cross platform version by yourself :).


Sample test application link :
    https://laptrinhx.com/link/?l=https%3A%2F%2Fcode.google.com%2Farchive%2Fp%2Fbodgeit%2F

Setup :
    https://laptrinhx.com/automate-zap-security-tests-with-selenium-webdriver-4100166036/

GITHUB link for downloading the required Jars :
    https://github.com/iriusrisk/zap-webdriver/blob/master/libs/
    
    
Other links referenced ,

-> https://dev.to/giannispapadakis/web-security-testing-with-owasp-zap-and-selenium-3gf5 - Not much to refer here, but has the daemon setup of deploying ZAP in docker containers as Shell script.

-> https://www.netjstech.com/2016/10/how-to-run-shell-script-from-java-program.html - Possible ways to run shell scripts


INCOMPLETE ACTIVITIES:
    ->Reporting.


FUTURE ADVANCEMENTS PLANNED:
Larger implementations :
    ->Deploying on docker containers.
    ->TestNGListeners for initialisation & teardown.

Quick implementations:

    ->Cross platform support(Have to update environments.properties & init.java).
    ->To bring up the port to listen ZAP API in runtime using the PORT property in environments.properties. 

I would be glad to get pull requests from these areas :)
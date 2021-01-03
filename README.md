# ZapAutoJava

Hello Folks!!!!! I have tried to develop a sample POC Project, dedicated entirely for public, and is flexible enough for upscaling to subjective applications.

Scope of this POC:
    The target of this setup, is to attack the web screens and web apps actively & passively, purely built on JAVA platform packed with Zed Attack Proxy APIs.


Properties of ZAP which helped me to build this project :)
* ZAP Client API jar, is the java package, which is used as the core component for achieving DAST across various platforms and organizations, and also can be easily integrated with Selenium WebDriver, obviously the same here :)
* ZAP API has an option of deploying in docker containers and can be deployed in daemon mode (in WebDriver terms 'headless mode).
* ZAP Cross platform packages enhances easy migration of local APIs along with the centralised versions.



And...........  it is built as below :)

Project Setup: 
The article below tries to explain a maven project setup for a sample test application, which can be built & hosted by anyone, in their localhost.

We anyhow have done some updates :) , on following things
* We are not deploying ZAP API in containers, but we are deploying it in daemon mode in runtime.
* The ZAP API startup is different across platforms, but the project is developed only for windows platforms.
* We have the unpacked cross platform version of ZAP along with this project, which is ZAP_2.9.0, but please make sure that you can deploy the  ZAP API using that in your machine, also i will be updating the latest cross platform ZAP version periodically, also if you come across any version issues for ZAP, feel free to update the cross platform version by yourself :).


Sample test application link :
    https://laptrinhx.com/link/?l=https%3A%2F%2Fcode.google.com%2Farchive%2Fp%2Fbodgeit%2F

Setup :
https://laptrinhx.com/automate-zap-security-tests-with-selenium-webdriver-4100166036/

GITHUB link for downloading the required Jars :
https://github.com/iriusrisk/zap-webdriver/blob/master/libs/ - please make sure to add them in your pom.xml correctly.
    
    
Other links referenced ,

==> https://dev.to/giannispapadakis/web-security-testing-with-owasp-zap-and-selenium-3gf5 - Not much to refer here, but has the daemon setup of deploying ZAP in docker containers as Shell script.

==> https://www.netjstech.com/2016/10/how-to-run-shell-script-from-java-program.html - Possible ways to run shell scripts



FUTURE ADVANCEMENTS PLANNED:

Long term implementations :
* Deploying on docker containers.
* TestNGListeners for initialisation & teardown.

Quick/Short term implementations:
* Cross platform support(Have to update environments.properties & init.java).
* To bring up the port to listen ZAP API in runtime using the PORT property in environments.properties.
* As the code is not yet implemented to decide the port in run time, we hav to first open the ZAP tool ourselves and have to set our port manually which the ZAP API should be listened.

I would be happy to review pull requests from these areas :)

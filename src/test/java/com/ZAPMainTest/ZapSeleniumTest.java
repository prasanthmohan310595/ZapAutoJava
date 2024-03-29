package com.ZAPMainTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import com.ZAPDriverUtils.Init;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.Alert;
import com.ZAPDriverUtils.DriverBase;
import com.ZAPTargetPages.AttackSmoke;
import net.continuumsecurity.proxy.ScanningProxy;
import net.continuumsecurity.proxy.Spider;
import net.continuumsecurity.proxy.ZAProxyScanner;

public class ZapSeleniumTest extends Init {
    /*
     * Provide details about ZAP Proxy
     */
    static Logger log = Logger.getLogger(ZapSeleniumTest.class.getName());

    //Chrome driver path
    private static final String BROWSER_DRIVER_PATH = System.getProperty("user.dir")+File.separator+"drivers"+File.separator+"chromedriver.exe";

    //ZAP ATTACK CONFIGURATIONS
    private final static String MEDIUM = "MEDIUM";
    private final static String HIGH = "HIGH";

    private ScanningProxy zapScanner;
    private Spider zapSpider;
    private WebDriver driver;
    private AttackSmoke siteNavigation;

    //Report path
    private static final String REPORT_PATH = System.getProperty("user.dir")+File.separator+"testoutput"+File.separator;

    //Kill ZAP Batch file path
    private static final String KILLZAP_PATH = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"killZap.bat";

    // Provide scan policy names
    private final static String[] policyNames =
            {"directory-browsing","cross-site-scripting",
                    "sql-injection","path-traversal","remote-file-inclusion",
                    "server-side-include","script-active-scan-rules",
                    "server-side-code-injection","external-redirect",
                    "crlf-injection"};

    int currentScanID;

    // Create ZAP proxy by specifying proxy host and proxy port
    private Proxy createZapProxyConfiguration(String HOST,int PORT) {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(HOST + ":" + PORT);
        proxy.setSslProxy(HOST + ":" + PORT);
        return proxy;
    }

    /*
     * Method to configure ZAP scanner, API client and perform User Registration
     */
    @BeforeMethod()
    public void setUp() throws IOException, InterruptedException {
        //Initialise the project parameters
        initialise();

        //Initialised variables
        System.out.println(Init.HOST);
        System.out.println(Init.PORT);
        System.out.println(Init.BASEURL);
        System.out.println(Init.USERNAME);
        System.out.println(Init.PASSWORD);
        System.out.println(Init.DAEMON);

        // Configure ZAP Scanner
        zapScanner = new ZAProxyScanner(Init.HOST, Integer.parseInt(Init.PORT), null);

        // Start new session
        zapScanner.clear();
        log.info("Started a new session: Scanner");

        // Create ZAP API client
        zapSpider=(Spider) zapScanner;
        log.info("Created client to ZAP API");

        // Create driver object
        driver = DriverBase.createChromeDriver(createZapProxyConfiguration(Init.HOST, Integer.parseInt(Init.PORT)), BROWSER_DRIVER_PATH);
        siteNavigation = new AttackSmoke(driver);

        //Clean output folder on start-up
        refreshOutput();

        /*
         * Please have the login function of any application hosted in MX-DEV domain here
         *
         * HAVE THE LOGIN FUNCTION CREATED IN AttackSmoke class and call it here
         */

    }

    /*
     * Method to close the driver connection & API Ports of localhost
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        //Kill the WebDriver instance;
        driver.quit();

        //Kill the ZAP Port to free the port for next execution
        killZap(KILLZAP_PATH,PORT);
    }



// ZAP Operations -- filterAlerts, setAlert_AttackStrength, activateZapPolicy, spiderwithZAP, scanWithZAP
// ---------------------------------------------------------------------------------------------------------

    /*
     * Method to filter the generated alerts based on Risk and Confidence
     */
    private List<Alert> filterAlerts(List<Alert> alerts)
    {
        List<Alert> filteredAlerts = new ArrayList<>();
        for (Alert alert : alerts)
        {
            // Filtering based on Risk: High and Confidence: Not Low
            if (alert.getRisk().equals(Alert.Risk.High) && alert.getConfidence() != Alert.Confidence.Low)
                filteredAlerts.add(alert);
        }
        return filteredAlerts;
    }

    /*
     * Method to specify the strength for the ZAP Scanner as High, Medium, or Low
     */
    public void setAlert_AttackStrength()
    {
        for (String ZapPolicyName : policyNames)
        {
            String ids = activateZapPolicy(ZapPolicyName);
            for (String id : ids.split(",")) {
                zapScanner.setScannerAlertThreshold(id,MEDIUM);
                zapScanner.setScannerAttackStrength(id,HIGH);
            }
        }
    }

    /*
     * Method to configure the ZAP Scanner for specified security policies and enable the scanner
     */
    private String activateZapPolicy(String policyName)
    {
        String scannerIds;

        // Compare the security policies and specify scannerIds (these scannerIds are standard)
        switch (policyName.toLowerCase()) {
            case "directory-browsing":
                scannerIds = "0";
                break;
            case "cross-site-scripting":
                scannerIds = "40012,40014,40016,40017";
                break;
            case "sql-injection":
                scannerIds = "40018";
                break;
            case "path-traversal":
                scannerIds = "6";
                break;
            case "remote-file-inclusion":
                scannerIds = "7";
                break;
            case "server-side-include":
                scannerIds = "40009";
                break;
            case "script-active-scan-rules":
                scannerIds = "50000";
                break;
            case "server-side-code-injection":
                scannerIds = "90019";
                break;
            case "remote-os-command-injection":
                scannerIds = "90020";
                break;
            case "external-redirect":
                scannerIds = "20019";
                break;
            case "crlf-injection":
                scannerIds = "40003";
                break;
            case "source-code-disclosure":
                scannerIds = "42,10045,20017";
                break;
            case "shell-shock":
                scannerIds = "10048";
                break;
            case "remote-code-execution":
                scannerIds = "20018";
                break;
            case "ldap-injection":
                scannerIds = "40015";
                break;
            case "xpath-injection":
                scannerIds = "90021";
                break;
            case "xml-external-entity":
                scannerIds = "90023";
                break;
            case "padding-oracle":
                scannerIds = "90024";
                break;
            case "el-injection":
                scannerIds = "90025";
                break;
            case "insecure-http-methods":
                scannerIds = "90028";
                break;
            case "parameter-pollution":
                scannerIds = "20014";
                break;
            default : throw new RuntimeException("No policy found for: "+policyName);
        }

        zapScanner.setEnableScanners(scannerIds, true);
        return scannerIds;
    }

    /*
     * Method to configure spider settings, execute ZAP spider, log the progress and found URLs
     */
    public void spiderWithZap() throws IOException {
        log.info("Spidering started");

        // Configure spider settings
        zapSpider.setThreadCount(5);
        zapSpider.setMaxDepth(5);
        zapSpider.setPostForms(false);

        // Execute the ZAP spider
        zapSpider.spider(AttackSmoke.BASE_URL);

        int currentSpiderID = zapSpider.getLastSpiderScanId();

        int progressPercent  = 0;
        while (progressPercent < 100) {
            progressPercent = zapSpider.getSpiderProgress(currentSpiderID);
            log.info("Spider is " + progressPercent + "% complete.");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        // Log the found URLs after spider
        for (String url : zapSpider.getSpiderResults(currentSpiderID)) {
            log.info("Found URL after spider: "+url);
        }

        log.info("Spidering ended");

        //Export the report
        getHtmlReport("SpiderScan.html");
    }

    /*
     * Method to execute an active scan and log the progress
     */
    public void scanWithZap() throws IOException {
        log.info("Scanning started");

        // Execute the ZAP scanner
        zapScanner.scan(AttackSmoke.BASE_URL);

        int currentScanId = zapScanner.getLastScannerScanId();

        int progressPercent  = 0;
        while (progressPercent < 100) {
            progressPercent = zapScanner.getScanProgress(currentScanId);
            log.info("Scan is " + progressPercent + "% complete.");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        log.info("Scanning ended");

        //Export the report
        getHtmlReport("ActiveScan.html");
    }

    /*
    KILL THE PORTS LISTENING ZAP API
     */
    public void killZap(String batPath, String port) throws IOException {
        Process killZap = Runtime.
                getRuntime().
                exec("cmd.exe /C start "+batPath+" "+port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                killZap.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    /*
    THIS FUNCTION REFRESHES THE ARCHIVE DIRECTORY TO HOLD THE REPORTS OF LAST RUN
    */
    public void refreshOutput() throws IOException {
        log.info("Refreshing the archive directory");

        //CREATE THE OUTPUT DIRECTORY FOR THE REPORT
        File reportDir = new File(REPORT_PATH);
        if(!reportDir.exists()){
            Assert.assertTrue(reportDir.mkdir(),"TEST OUTPUT FOLDER IS NOT CREATED");
        }

        //DELETE THE EXISTING ARCHIVED FILES
        File archiveDir = new File(REPORT_PATH+File.separator+"ARCHIVES"+File.separator);
        if(archiveDir.exists()){
            log.info("ARCHIVE DIRECTORY IS ALREADY PRESENT!!");
            for(File f : Objects.requireNonNull(archiveDir.listFiles())){
                Assert.assertTrue(f.delete(), "EXISTING REPORT IS NOT DELETED !!");
            }
        }
        //CREATE A NEW ARCHIVE DIRECTORY IF IT DOESN'T EXISTS
        else{
            Assert.assertTrue(archiveDir.mkdir(), "ARCHIVE DIRECTORY IS NOT CREATED !!");
        }

        for(File f : Objects.requireNonNull(reportDir.listFiles())){
            if(f.getName().endsWith(".html")) {
                Files.copy(Paths.get(f.getPath()), Paths.get(REPORT_PATH + File.separator + "ARCHIVES" + File.separator + f.getName()),
                        StandardCopyOption.REPLACE_EXISTING);
                Assert.assertTrue(f.delete(), "EXISTING TEST REPORT IS NOT DELETED !!");
            }
        }
    }

    /*
    THIS FUNCTION WRITES THE REPORT WHICH IS EXPORTED AT THAT INSTANT
     */
    public void getHtmlReport(String reportName) throws IOException {
        //GET THE LATEST HTML REPORT
        byte[] htmlReport = zapScanner.getHtmlReport();

        //CREATE THE FILE VARIABLE FOR THE REPORT TO BE CREATED
        Path pathToFile = Paths.get(REPORT_PATH+reportName);

        //SAVE THE LATEST TEST REPORT TO THE OUTPUT DIRECTORY
        Files.createDirectories(pathToFile.getParent());
        Files.write(pathToFile, htmlReport);
    }


    /*
     * Test method containing test steps and
     * log the found alerts and assert the count of alerts
     */
    @Test
    public void testVulnerabilitiesAfterLogin() throws IOException {
         /*
         Update this below line with the smoke use case used in any of your projects for crawling across
         Have the smoke test template created in AttackSmoke class
         */
        driver.get(BASEURL);

        //Export the report
        getHtmlReport("PassiveSmoke.html");

        // Using ZAP Spider
        log.info("Started spidering");
        spiderWithZap();
        log.info("Ended spidering");

        // Setting alert and attack
        setAlert_AttackStrength();
        zapScanner.setEnablePassiveScan(true);

        // Using ZAP Policy Scanner
        log.info("Started scanning");
        scanWithZap();
        log.info("Ended scanning");

        //getAlerts() throws a parse exception with ZAP 2.11.0

        /*List<Alert> generatedAlerts = filterAlerts(zapScanner.getAlerts());

        for (Alert alert : generatedAlerts)
        {
            log.info("Alert: "+alert.getAlert()+" at URL: "+alert.getUrl()+" Parameter: "+alert.getParam()+" CWE ID: "+alert.getCweId());
        }

        assertThat(generatedAlerts.size(), equalTo(0));*/
        }
    }

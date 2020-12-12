package com.ZAPDriverUtils;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;

public class Init {
    public static String BASEURL = null;
    public static String USERNAME = null;
    public static String PASSWORD = null;
    public static String HOST = null;
    public static String PORT = null;
    public static String DAEMON = null;

    public void initialise() throws IOException, InterruptedException {
        Properties prop = readPropertiesFile(System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"environments.properties");
        BASEURL=prop.getProperty("BASEURL");
        USERNAME=prop.getProperty("USERNAME");
        PASSWORD=prop.getProperty("PASSWORD");
        HOST=prop.getProperty("HOST");
        PORT=prop.getProperty("PORT");
        DAEMON=prop.getProperty("DAEMON","false");

        String libDirectory = System.getProperty("user.dir") + File.separator + "ZAP_2.9.0" + File.separator;
        String command;
        if(Boolean.parseBoolean(DAEMON)) {
            command = "java -Xmx512m -jar "
                    + libDirectory +
                    "zap-2.9.0.jar" +
                    " -daemon" +
                    " -host " + HOST +
                    " -port " + PORT;
        }
        else{
            command = "java -Xmx512m -jar "
                    + libDirectory +
                    "zap-2.9.0.jar" +
                    " -host " + HOST +
                    " -port " + PORT;
        }
        System.out.println(command);
        runCommand("cmd", "/C", command);
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(IOException io) {
            io.printStackTrace();
        } finally {
            assert fis != null;
            fis.close();
        }
        return prop;
    }

    public void runCommand(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder().command(command);

        try {
            //Start the process using ProcessBuilder
            processBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("INFO-->ZAP STARTUP FAILED");
        }
            System.out.println("INFO-->ZAP STARTED SUCCESSFULLY");
    }
}

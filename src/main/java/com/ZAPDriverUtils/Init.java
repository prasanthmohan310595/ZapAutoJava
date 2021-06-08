package com.ZAPDriverUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Init {
    public static String BASEURL = null;
    public static String USERNAME = null;
    public static String PASSWORD = null;
    public static String HOST = null;
    public static String PORT = null;
    public static String DAEMON = null;

    public void initialise() throws IOException, InterruptedException {
        Properties prop = readPropertiesFile(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "environments.properties");
        BASEURL = prop.getProperty("BASEURL");
        USERNAME = prop.getProperty("USERNAME");
        PASSWORD = prop.getProperty("PASSWORD");
        HOST = prop.getProperty("HOST");
        PORT = prop.getProperty("PORT");
        DAEMON = prop.getProperty("DAEMON", "false");

        String batFileDirectory = System.getProperty("user.dir") + File.separator + "ZAP_2.10.0"+File.separator;
        String isDaemon;
        if (Boolean.parseBoolean(DAEMON)) {
            isDaemon = "-daemon";
        } else {
            isDaemon = null;
        }
        System.out.println(isDaemon);
        runCommand(isDaemon,batFileDirectory);
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            assert fis != null;
            fis.close();
        }
        return prop;
    }

    public void runCommand(String isDaemon, String directory) {
        ProcessBuilder processBuilder = new ProcessBuilder().command(Arrays.asList(directory+"zap.bat",directory,isDaemon, "-port " +PORT));

        try {
            //Start the process using ProcessBuilder
            processBuilder.inheritIO();
            Process p = processBuilder.start();

//            Process p = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor(30, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

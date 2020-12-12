package com.ZAPTargetPages;

import com.ZAPDriverUtils.Init;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

public class AttackSmoke extends Init {

    WebDriver driver;

    /*
     * Necessary information is provided about AUT (Test application - BodgeIt Store)
     * URL, Logout_URL, Username, Password to be used for registration
     */

    public static String BASE_URL = Init.BASEURL;
    public static String USERNAME = Init.USERNAME;
    public static String PASSWORD = Init.PASSWORD;

    /*
     * Apply synchronization/wait techniques
     */
    public AttackSmoke(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        this.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }
}

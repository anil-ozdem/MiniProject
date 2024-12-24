package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Driver {

    private Driver() {

    }

    public static WebDriver driver;

    public static WebDriver getDriver() {


        String calisacakBrowser = ConfigReader.getProperty("browser");

        if (driver == null) {

            switch (calisacakBrowser) {

                case "safari":
                    driver = new SafariDriver();
                    break;

                case "edge":
                    driver = new EdgeDriver();
                    break;

                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                default:
                    driver = new ChromeDriver();

            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        }


        return driver;
    }


    public static void quitDriver() {
        driver.quit(); // acik olan browser'lari kapatir
        driver = null; // bu class'daki driver objesine null degeri atayarak
        // yeni browser acilmasina imkan tanir
    }
}
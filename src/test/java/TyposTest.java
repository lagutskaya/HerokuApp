import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class TyposTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void checkParagraphSpelling() {
        driver.get("https://the-internet.herokuapp.com/typos");

        WebElement paragraph = driver.findElement(By.tagName("p"));
        String expectedText = paragraph.getText();

        SoftAssert softAssert = new SoftAssert();
        boolean textMatches = false;

        for (int i = 0; i < 7; i++) {
            paragraph = driver.findElement(By.tagName("p"));
            String actualText = paragraph.getText();

            if (actualText.equals(expectedText)) {
                textMatches = true;
                break;
            } else {
                driver.navigate().refresh();
            }
        }

        softAssert.assertTrue(textMatches, "Текст в параграфе не совпадает с ожидаемым после 7 попыток.");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class CheckboxesTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void checkCheckboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        SoftAssert softAssert = new SoftAssert();
        boolean isFirstCheckboxSelected = driver.findElement(By.cssSelector("[type=checkbox]:nth-of-type(1)")).isSelected();
        softAssert.assertFalse(isFirstCheckboxSelected, "Первый чекбокс должен быть НЕ выбран.");
        driver.findElement(By.cssSelector("[type=checkbox]:nth-of-type(1)")).click();
        isFirstCheckboxSelected = driver.findElement(By.cssSelector("[type=checkbox]:nth-of-type(1)")).isSelected();
        softAssert.assertTrue(isFirstCheckboxSelected, "Первый чекбокс должен быть выбран.");
        softAssert.assertAll();

        boolean isSecondCheckboxSelected = driver.findElement(By.cssSelector("[type=checkbox]:nth-of-type(2)")).isSelected();
        softAssert.assertTrue(isSecondCheckboxSelected, "Второй чекбокс должен быть выбран.");
        if (isSecondCheckboxSelected) {
            driver.findElement(By.cssSelector("[type=checkbox]:nth-of-type(2)")).click();
        }
        isSecondCheckboxSelected = driver.findElement(By.cssSelector("[type=checkbox]:nth-of-type(2)")).isSelected();
        softAssert.assertFalse(isSecondCheckboxSelected, "Второй чекбокс должен быть НЕ выбран.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class DropdownTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void checkDropdown() {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        SoftAssert softAssert = new SoftAssert();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dropdownWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));
        dropdownWait.click();

        WebElement secondOption = driver.findElement(By.xpath("//*[@id='dropdown']/option[2]"));
        secondOption.click();
        boolean isSecondOptionSelected = secondOption.getAttribute("selected") != null;
        softAssert.assertTrue(isSecondOptionSelected, "Значение 'Option 1' должно быть выбрано.");

        WebElement thirdOption = driver.findElement(By.xpath("//*[@id='dropdown']/option[3]"));
        thirdOption.click();
        boolean isThirdOptionSelected = thirdOption.getAttribute("selected") != null;
        softAssert.assertTrue(isThirdOptionSelected, "Значение 'Option 2' должно быть выбрано.");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

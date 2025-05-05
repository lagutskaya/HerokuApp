import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));

        Select dropdown = new Select(dropdownElement);

        dropdown.selectByVisibleText("Option 2");
        boolean isSecondOptionSelected = dropdown.getFirstSelectedOption().getText().equals("Option 2");
        softAssert.assertTrue(isSecondOptionSelected, "Значение 'Option 2' должно быть выбрано.");

        dropdown.selectByVisibleText("Option 1");
        boolean isFirstOptionSelected = dropdown.getFirstSelectedOption().getText().equals("Option 1");
        softAssert.assertTrue(isFirstOptionSelected, "Значение 'Option 1' должно быть выбрано.");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

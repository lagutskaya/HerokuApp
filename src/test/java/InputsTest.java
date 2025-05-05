import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class InputsTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void checkInputs() {
        driver.get("https://the-internet.herokuapp.com/inputs");

        WebElement inputField = driver.findElement(By.tagName("input"));

        String inputText = "tralalelo tralala";
        inputField.sendKeys(inputText);

        SoftAssert softAssert = new SoftAssert();

        String inputValue = inputField.getAttribute("value");
        softAssert.assertNotEquals(inputValue, inputText, "Текст в поле ввода не должен отображаться");

        inputField.clear();
        String inputInt = "5";
        inputField.sendKeys(inputInt);

        inputValue = inputField.getAttribute("value");
        softAssert.assertEquals(inputValue, inputInt, "В поле input должна отображаться цифра 5");

        inputField.sendKeys(Keys.ARROW_UP);
        inputValue = inputField.getAttribute("value");
        softAssert.assertEquals(inputValue, "6", "В поле input должна отображаться цифра 6");

        inputField.sendKeys(Keys.ARROW_DOWN);
        inputValue = inputField.getAttribute("value");
        softAssert.assertEquals(inputValue, "5", "В поле input должна отображаться цифра 5");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

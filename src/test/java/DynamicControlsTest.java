import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.*;

public class DynamicControlsTest {

    WebDriver driver;
    private final String filePath = "src/test/java/хомяк.jpg";
    private final String fileName = "хомяк.jpg";

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.manage().window().maximize();
    }

    @Test
    public void checkContextMenu() {

        driver.get("https://the-internet.herokuapp.com/context_menu");
        WebElement contextMenu = driver.findElement(By.id("hot-spot"));

        Actions actions = new Actions(driver);
        actions.contextClick(contextMenu).perform();

        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();

        Assert.assertEquals(alertText,
                "You selected a context menu",
                "Текст в алерте не совпадает с ожидаемым");

        alert.accept();
    }

    @Test
    public void checkDynamicControls() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.xpath("//*[text()='Remove']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkbox")));
    }

    @Test
    public void checkEnabledField() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        Boolean fieldIsDisabled = !driver.findElement(By.cssSelector("[type='text']")).isEnabled();
        assertTrue(fieldIsDisabled, "Поле не раздизейблено");

        driver.findElement(By.xpath("//*[text()='Enable']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        Boolean fieldIsEnabled = driver.findElement(By.cssSelector("[type='text']")).isEnabled();
        assertTrue(fieldIsEnabled, "Поле не задизейблено");
    }

    @Test
    public void checkFileUpload() {
        driver.get("https://the-internet.herokuapp.com/upload");

        WebElement fileInput = driver.findElement(By.id("file-upload"));

        java.io.File file = new java.io.File(filePath);
        fileInput.sendKeys(file.getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("file-submit"));
        uploadButton.click();

        WebElement uploadedFileName = driver.findElement(By.id("uploaded-files"));
        Assert.assertEquals(uploadedFileName.getText(), fileName, "Имя файла не совпадает с загруженным");
    }

    @Test
    public void checkFrames() {
        driver.get("https://the-internet.herokuapp.com/frames");

        driver.findElement(By.xpath("//*[text()='iFrame']")).click();
        driver.switchTo().frame("mce_0_ifr");

        WebElement paragraph = driver.findElement(By.tagName("p"));
        String actualText = paragraph.getText();
        String expectedText = "Your content goes here.";

        Assert.assertEquals(actualText, expectedText, "Текст в фрейме не совпадает с ожидаемым результатом");
        driver.switchTo().defaultContent();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
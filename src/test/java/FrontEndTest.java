import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class FrontEndTest extends Base {

    @SneakyThrows
    @BeforeClass()
    public void openPageAndLogin() {
        String url = "https://hrb-mf-root.acceptance.k8s.hrblizz.dev/login";
        webDriver.get(url);
        log.info("Navigated to: " + url);
    }

    @Test(description = "Verify that user can log in", priority = 1)
    public void loginTest() {
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        webDriver.findElement(By.name("email")).sendKeys("demo+JM-01140@mercans.com");
        webDriver.findElement(By.name("password")).sendKeys("Employee1!");
        webDriver.findElement(By.id("perform-login")).click();

        String welcomeText = webDriver.findElement(By.className("lp-dashboard__bubble--title")).getText();

        Assert.assertEquals(welcomeText, "Employee Self Service");
    }

    @Test(description = "Verify that user can open My leaves page", priority = 2)
    public void openLeavesPageTest() {
        webDriver.findElement(By.id("leaves")).click();
        webDriver.findElement(By.linkText("My leaves")).click();

        String pageTitle = webDriver.findElement(By.className("page-title")).getText();

        Assert.assertEquals(pageTitle, "My leaves");
    }

    @Test(description = "Verify that user can request leave", priority = 3)
    public void requestLeaveTest() throws InterruptedException {
        webDriver.findElement(By.id("new-leave-request")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.className("date-picker__header__content")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.className("date-picker__header__content")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.id("2022")).click();
        webDriver.findElement(By.id("2022-05")).click();
        webDriver.findElement(By.id("2022-05-07")).click();
        webDriver.findElement(By.id("2022-05-08")).click();
        webDriver.findElement(By.id("notes")).sendKeys("Added notes");
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollBy(0,250)", "");
        webDriver.findElement(By.xpath("//button[@id='submit-approve']")).click();
        webDriver.findElement(By.id("confirmAction")).click();

        boolean requestBox = webDriver.findElement(By.className("request-tiles__wrapper")).isDisplayed();

        Assert.assertTrue(requestBox);
    }

}
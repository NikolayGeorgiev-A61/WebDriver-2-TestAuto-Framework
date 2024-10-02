package saucedemotests.core;

import com.saucedemo.pages.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import testframework.PropertiesManager;
import testframework.core.BaseWebTest;

import java.util.List;

import static testframework.DriverManager.quitDriver;

public class SauceDemoBaseWebTest extends BaseWebTest {
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected ShoppingCartPage shoppingCartPage;
    protected CheckoutOverviewPage checkoutOverviewPage;
    protected CheckoutYourInformationPage checkoutYourInformationPage;
    protected CheckoutCompletePage checkoutCompletePage;

    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeEach
    public void beforeTests() {

        inventoryPage = new InventoryPage();
        loginPage = new LoginPage();
        shoppingCartPage = new ShoppingCartPage();
        checkoutOverviewPage = new CheckoutOverviewPage();
        checkoutYourInformationPage = new CheckoutYourInformationPage();
        checkoutCompletePage = new CheckoutCompletePage();

        // Navigate to base page
        driver().get(PropertiesManager.getConfigProperties().getProperty("sauceDemoBaseUrl"));
    }

    @BeforeAll
    public static void beforeAll() {
        // perform some code before all tests start
    }

    // close driver
    @AfterEach
    public void afterTest() {
        driver().close();
        // perform some code after each test has finished
    }

    @AfterAll
    public static void afterAll() {
        //quitDriver();
    }

    // Extract methods that use multiple pages
    public void authenticateWithUser(String username, String pass) {
        loginPage.submitLoginForm(username, pass);

        inventoryPage.waitForPageTitle();
    }
    public static String getSummaryPrice(){
        return driver.findElement(By.className("summary_total_label")).getText();
    }

    public static Double getSummaryPriceNumber(){
        return Double.parseDouble(getSummaryPrice().replaceAll("$", ""));
    }
}
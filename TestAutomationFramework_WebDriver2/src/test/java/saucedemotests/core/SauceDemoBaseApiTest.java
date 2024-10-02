package saucedemotests.core;

import com.saucedemo.api.SauceProductsService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import testframework.core.BaseApiTest;

import java.util.logging.Logger;

public class SauceDemoBaseApiTest extends BaseApiTest {
    protected SauceProductsService sauceProductsService;
    private static final Logger logger = Logger.getLogger(String.valueOf(SauceDemoBaseApiTest.class));

    @BeforeAll
    public static void beforeAll() {
        // Perform some code before all tests start
        logger.info("Starting SauceDemo API tests.");
        // Here you can initialize any static resources or configurations needed for all tests
    }

    @BeforeEach
    public void beforeTests() {
        // Perform some code before each test starts
        logger.info("Setting up SauceProductsService for the test.");
        sauceProductsService = new SauceProductsService();
    }

    @AfterEach
    public void afterTest() {
        // Perform some code after each test has finished
        logger.info("Finished executing the test.");
        // Here you can clean up any resources that were used during the test
    }

    @AfterAll
    public static void afterAll() {
        logger.info("Completed all SauceDemo API tests.");
        // Perform any cleanup or final steps after all tests have finished
    }
}
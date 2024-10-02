package saucedemotests.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import saucedemotests.core.SauceDemoBaseWebTest;
import saucedemotests.enums.TestData;
import testframework.enums.BrowserMode;
import testframework.enums.BrowserType;

public class ProductsTests extends SauceDemoBaseWebTest {
    private final String BACKPACK_TITLE = "Sauce Labs Backpack";
    private final String SHIRT_TITLE = "Sauce Labs Bolt T-Shirt";

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        // Log in
        authenticateWithUser(TestData.STANDARD_USER_USERNAME.getValue(), TestData.STANDARD_USER_PASSWORD.getValue());

        // Add products to shopping cart
        inventoryPage.addProductsByTitle(BACKPACK_TITLE, SHIRT_TITLE);

        // Go to shopping cart
        inventoryPage.clickShoppingCartLink();

        // Assert items in shopping cart
        Assertions.assertTrue(shoppingCartPage.getShoppingCartItems().stream()
                .anyMatch(item -> item.getText().contains(BACKPACK_TITLE)), "Backpack should be in the cart.");
        Assertions.assertTrue(shoppingCartPage.getShoppingCartItems().stream()
                .anyMatch(item -> item.getText().contains(SHIRT_TITLE)), "Shirt should be in the cart.");

        // Assert cart contains 2 items
        Assertions.assertEquals(2, inventoryPage.getShoppingCartItemsNumber(), "Cart should contain 2 items.");
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {
        // Log in
        authenticateWithUser(TestData.STANDARD_USER_USERNAME.getValue(), TestData.STANDARD_USER_PASSWORD.getValue());

        // Add products to shopping cart
        inventoryPage.addProductsByTitle(BACKPACK_TITLE, SHIRT_TITLE);

        // Go to shopping cart
        inventoryPage.clickShoppingCartLink();

        // Proceed to checkout
        shoppingCartPage.clickCheckout();

        // Fill user information
        checkoutYourInformationPage.fillShippingDetails("John", "Doe", "12345");
        checkoutYourInformationPage.clickContinue();

        //Get price and item count
        var items = checkoutOverviewPage.getShoppingCartItems();
        Assertions.assertEquals(2, items.size(), "Items count not as expected");

        //var total = SauceDemoBaseWebTest.getSummaryPriceNumber();
        double expectedPrice = 29.99 + 15.99 + 3.68;

        // Assert cart items in overview
        Assertions.assertTrue(checkoutOverviewPage.getShoppingCartItems().stream()
                .anyMatch(item -> item.getText().contains(BACKPACK_TITLE)), "Backpack should be in the overview.");
        Assertions.assertTrue(checkoutOverviewPage.getShoppingCartItems().stream()
                .anyMatch(item -> item.getText().contains(SHIRT_TITLE)), "Shirt should be in the overview.");

        // Assert total label contains a valid total
        String totalLabel = checkoutOverviewPage.getTotalLabelText();
        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        Assertions.assertTrue(totalLabel.contains("Total"), "The total should be displayed.");
        //Assertions.assertEquals(expectedPrice, total, "Items total price not as expected");

    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {
        // Log in
        authenticateWithUser(TestData.STANDARD_USER_USERNAME.getValue(), TestData.STANDARD_USER_PASSWORD.getValue());

        // Add Backpack and T-shirt to shopping cart
        inventoryPage.addProductsByTitle(BACKPACK_TITLE, SHIRT_TITLE);
        // Go to shopping cart
        inventoryPage.clickShoppingCartLink();

        // Proceed to checkout
        shoppingCartPage.clickCheckout();

        // Fill in user details
        checkoutYourInformationPage.fillShippingDetails("Jane", "Doe", "54321");
        checkoutYourInformationPage.clickContinue();

        // Confirm items in the overview
        Assertions.assertTrue(checkoutOverviewPage.getShoppingCartItems().stream()
                .anyMatch(item -> item.getText().contains(BACKPACK_TITLE)), "Backpack should be in the cart overview.");
        Assertions.assertTrue(checkoutOverviewPage.getShoppingCartItems().stream()
                .anyMatch(item -> item.getText().contains(SHIRT_TITLE)), "Shirt should be in the cart overview.");

        // Complete the order
        checkoutOverviewPage.clickFinish();

        // Assert that the shopping cart is now empty
        Assertions.assertEquals(0, inventoryPage.getShoppingCartItemsNumber(), "Shopping cart should be empty after order completion.");
    }
}
package com.akhila.infrrd.travel_pom_automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // All flight result cards
    private By resultCards = By.cssSelector("div.listingCard");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ------------------ CHECK RESULTS LOADED ------------------
    public boolean isResultsLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("flight/search"),
                    ExpectedConditions.presenceOfElementLocated(resultCards)
            ));

            System.out.println("✔ Results page loaded");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Results NOT loaded: " + e.getMessage());
            return false;
        }
    }

    // ------------------ PRINT CHEAPEST FLIGHTS ------------------
    public void printCheapestFlights() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultCards));

            List<WebElement> cards = driver.findElements(resultCards);

            if (cards.isEmpty()) {
                System.out.println("❌ No flights found");
                return;
            }

            System.out.println("✔ Total flights found: " + cards.size());

            WebElement cheapest = cards.get(0);
            System.out.println("💸 Cheapest Flight:\n" + cheapest.getText());

            if (cards.size() > 1) {
                WebElement secondCheapest = cards.get(1);
                System.out.println("💸 Second Cheapest:\n" + secondCheapest.getText());
            }

        } catch (Exception e) {
            System.out.println("❌ Error reading flight results: " + e.getMessage());
        }
    }
    // ------------------ GET ALL FLIGHT PRICES ------------------
    public List<Integer> getAllFlightPrices() {
        List<WebElement> cards = driver.findElements(By.cssSelector("div.listingCard"));

        List<Integer> prices = new ArrayList<>();

        for (WebElement card : cards) {
            try {
                String priceText = card.findElement(By.cssSelector("span.fare")).getText();
                priceText = priceText.replaceAll("[^0-9]", "");  // remove ₹ and commas
                prices.add(Integer.parseInt(priceText));
            } catch (Exception ignored) {}
        }

        return prices;
    }

    // ------------------ CHECK ASC SORTING ------------------
    public boolean isListSortedAscending(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

}

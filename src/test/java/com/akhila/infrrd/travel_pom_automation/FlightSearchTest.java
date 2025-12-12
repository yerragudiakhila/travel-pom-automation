package com.akhila.infrrd.travel_pom_automation;

import com.akhila.infrrd.travel_pom_automation.pages.HomePage;
import com.akhila.infrrd.travel_pom_automation.pages.SearchResultsPage;
import com.akhila.infrrd.travel_pom_automation.utils.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class FlightSearchTest extends BaseTest {



    @Test
    public void fullFlowTest() throws InterruptedException {

        HomePage home = new HomePage(DriverFactory.getDriver());
        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        System.out.println("TEST STARTED ");

        home.clearPopups();
        home.selectOneWayTrip();
        home.enterSource("Delhi");
        home.enterDestination("Bengaluru");
        home.selectDateNextMonth(12);
        home.clickSearchButton();

        Assert.assertTrue(results.isResultsLoaded(), " Flight results did NOT load!");

        results.printCheapestFlights();

        // Open New Tab
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        js.executeScript("window.open('about:blank','_blank');");

        ArrayList<String> tabs = new ArrayList<>(DriverFactory.getDriver().getWindowHandles());
        DriverFactory.getDriver().switchTo().window(tabs.get(1));

        System.out.println("✔ Switched to new tab");

        DriverFactory.getDriver().navigate().to("https://www.google.com");
        Thread.sleep(2000);

        String googleTitle = DriverFactory.getDriver().getTitle();
        Assert.assertTrue(
                googleTitle.toLowerCase().contains("google"),
                " Google title mismatch!"
        );

        System.out.println(" Google title verified successfully");
        System.out.println("TEST COMPLETED SUCCESSFULLY ");
    }

    //   Validate PRICE SORTING (Low → High)

    @Test
    public void validatePriceSorting() throws InterruptedException {

        HomePage home = new HomePage(DriverFactory.getDriver());
        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        System.out.println("PRICE SORTING TEST ");

        home.clearPopups();
        home.selectOneWayTrip();
        home.enterSource("Delhi");
        home.enterDestination("Bengaluru");
        home.selectDateNextMonth(12);
        home.clickSearchButton();

        Assert.assertTrue(results.isResultsLoaded(), " Results did NOT load!");

        System.out.println("✔ Results loaded");

        // Fetch all prices
        var prices = results.getAllFlightPrices();

        System.out.println("Fetched Prices: " + prices);

        // Check if sorted ascending
        Assert.assertTrue(results.isListSortedAscending(prices),
                " Prices are NOT sorted from Low → High");

        System.out.println("✔ Price sorting validated successfully");
        System.out.println("PRICE SORTING TEST COMPLETED");
    }

}

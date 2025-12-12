package com.akhila.infrrd.travel_pom_automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static java.lang.Thread.sleep;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    //  POPUP HANDLER 
    public void clearPopups() {
        try {
            sleep(1000);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("""
                document.querySelectorAll('.loginOverlay,.sc-jrQzAO,.modalContent,.overlay,.hsBackDrop')
                .forEach(e => e.remove());
            """);

            System.out.println(" Popups cleared");
        } catch (Exception e) {
            System.out.println(" No popups found");
        }
    }

    //LOCATORS
    private By suggestionFirst = By.xpath("(//ul/li)[1]");
    private By oneWay = By.xpath("//p[text()='One-way']/ancestor::div[contains(@class,'radio')]");

    //  ONE WAY
    public void selectOneWayTrip() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(oneWay)).click();
            System.out.println(" One-way selected");
        } catch (Exception e) {
            System.out.println(" One-way already selected");
        }
    }

    // SOURCE CITY
    public void enterSource(String city) {

        By box = By.xpath("//label[@for='fromCity']");
        By input = By.id("fromCity");

        removeOverlays();
        sleepSafe(400);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(box)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(box));
        }

        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(input));

        field.sendKeys(Keys.CONTROL + "a");
        field.sendKeys(city);

        wait.until(ExpectedConditions.elementToBeClickable(suggestionFirst)).click();
        System.out.println(" Source: " + city);
    }

    // DESTINATION CITY
    public void enterDestination(String city) {

        By box = By.xpath("//label[@for='toCity']");
        By input = By.id("toCity");

        removeOverlays();
        sleepSafe(400);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(box)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(box));
        }

        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(input));

        field.sendKeys(Keys.CONTROL + "a");
        field.sendKeys(city);

        wait.until(ExpectedConditions.elementToBeClickable(suggestionFirst)).click();
        System.out.println(" Destination: " + city);
    }

    //  REMOVE OVERLAYS
    private void removeOverlays() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("""
                document.querySelectorAll('.sc-ibQAlb, .bTQQZz, .hsBackDrop')
                .forEach(e => e.style.display='none');
            """);
        } catch (Exception ignored) {}
    }

    // DATE SELECTION
    public void selectDateNextMonth(int day) {

        WebElement departureBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Departure')]/ancestor::div[contains(@class,'fsw_inputBox')]")
        ));
        departureBox.click();
        System.out.println(" Calendar opened");

        WebElement nextArrow = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//span[contains(@class,'DayPicker-NavButton--next')])[1]")
        ));
        nextArrow.click();
        System.out.println(" Next month opened");

        String xpath = "//div[contains(@class,'DayPicker-Day') and contains(@aria-label,'" + day + "')]";
        WebElement date = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", date);
        System.out.println(" Selected date: " + day);

        driver.findElement(By.tagName("body")).click();
        System.out.println(" Calendar closed");

        sleepSafe(1500);
    }

    //  CLICK SEARCH
    public void clickSearchButton() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By searchBtn = By.xpath("//a[contains(@class,'widgetSearchBtn')]");

        try {
            // Remove overlays first
            js.executeScript("""
                document.querySelectorAll('.sc-ibQAlb, .overlay, .hsBackDrop')
                .forEach(e => e.style.display='none');
            """);

            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));

            try {
                btn.click();
                System.out.println(" SEARCH clicked normally");
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", btn);
                System.out.println(" SEARCH clicked via JavaScript");
            }

            sleepSafe(2000);

        } catch (Exception e) {
            System.out.println(" Could not click SEARCH button: " + e.getMessage());
        }
    }

    //  SAFE SLEEP
    private void sleepSafe(long time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

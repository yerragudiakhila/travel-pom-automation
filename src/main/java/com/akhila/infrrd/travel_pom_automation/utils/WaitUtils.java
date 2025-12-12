package com.akhila.infrrd.travel_pom_automation.utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private static final int TIMEOUT = 15;

    public static WebElement waitForElementClickable(WebDriver driver, WebElement el) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(el));
    }

    public static WebElement waitForElementVisible(WebDriver driver, WebElement el) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOf(el));
    }
}


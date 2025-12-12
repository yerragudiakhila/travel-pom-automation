
package com.akhila.infrrd.travel_pom_automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String name) {
        try {
            Path dir = Path.of("screenshots");
            if (!Files.exists(dir)) Files.createDirectories(dir);
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String fileName = name + "_" + LocalDateTime.now().format(f) + ".png";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), dir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + dir.resolve(fileName).toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}

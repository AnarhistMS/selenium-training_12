package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://localhost/litecart/admin/login.php");
        wait.until(titleIs("My Store"));
        WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@name='login']"));
        username.sendKeys("admin");
        password.sendKeys("admin");
        loginButton.click();
        wait.until(titleIs("My Store"));
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        driver.findElement(By.xpath("//a[text()=' Add New Product']")).click();
        wait.until(titleContains("Add New Product | My Store"));
        driver.findElement(By.xpath("//input[@name='name[en]']")).sendKeys("Testing Duck");
        driver.findElement(By.xpath("//input[@name='code']")).sendKeys("Testing Code");
        driver.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).click();
        driver.findElement(By.xpath("//td[text()='Unisex']/preceding::td/input[@value='1-3']")).click();
        driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("10");

        File file = new File("src/test/resources/test.jpg");
        String path = file.getAbsolutePath();
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(path);

        driver.findElement(By.xpath("//input[@name='date_valid_from']")).sendKeys("01012020");
        driver.findElement(By.xpath("//input[@name='date_valid_to']")).sendKeys("01012021");

        driver.findElement(By.xpath("//li/a[text()='Information']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Select manufacturer = new Select(driver.findElement(By.xpath("//select[@name='manufacturer_id']")));
        manufacturer.selectByValue("1");

        driver.findElement(By.xpath("//input[@name='keywords']")).sendKeys("testing keywords");
        driver.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys("testing description");
        ((JavascriptExecutor)driver).executeScript(
                "var desc = document.getElementsByClassName(\"trumbowyg-editor\");" +
                        "desc[0].innerText = \"New testing Description\"; ");

        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("Test Title");
        driver.findElement(By.xpath("//input[@name='meta_description[en]']")).sendKeys("Test Meta Description");

        driver.findElement(By.xpath("//li/a[text()='Prices']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement purchasePrice = driver.findElement(By.xpath("//input[@name='purchase_price']"));
        purchasePrice.clear();
        purchasePrice.sendKeys("10");

        Select purchasePriceCurrencyCode = new Select(driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']")));
        purchasePriceCurrencyCode.selectByValue("USD");

        driver.findElement(By.xpath("//input[@name='prices[USD]']")).sendKeys("10");
        driver.findElement(By.xpath("//input[@name='prices[EUR]']")).sendKeys("10");

        driver.findElement(By.xpath("//button[@name='save']")).click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//td/a[text()='Testing Duck']")).click();
    }

    @After
    public void stop(){
        driver.quit();
        driver= null;
    }
}
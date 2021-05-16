package es.upm.dit.isst.educovid.epics.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
public class ConsultaProfesorTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	System.setProperty("webdriver.chrome.driver", "/home/kali/isst/eduCOVID/educovid-backend/educovid-backend/chromedriver");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void consultaProfesor() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(1920, 918));
    driver.findElement(By.linkText("Accede")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(1)")).click();
    {
      WebElement dropdown = driver.findElement(By.id("formRole"));
      dropdown.findElement(By.xpath("//option[. = 'Profesor']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("formRole"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("formRole"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("formRole"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("formRole")).click();
    driver.findElement(By.id("formCenter")).click();
    driver.findElement(By.id("formCenter")).sendKeys("IES Gran Capitán");
    driver.findElement(By.id("formUsername")).click();
    driver.findElement(By.id("formUsername")).sendKeys("22222222A");
    driver.findElement(By.id("formBasicPassword")).click();
    driver.findElement(By.id("formBasicPassword")).sendKeys("22222222A");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".nord-button")).click();
  }
}

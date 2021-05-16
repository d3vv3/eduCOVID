package es.upm.dit.isst.educovid.epics.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
public class RegistroCentroYConsultaTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	System.setProperty("webdriver.chrome.driver", "chromedriver");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void registroCentroYConsulta() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(1920, 918));
    driver.findElement(By.linkText("Registra tu centro")).click();
    driver.findElement(By.id("formResponsibleName")).click();
    driver.findElement(By.id("formResponsibleName")).sendKeys("Álvaro");
    driver.findElement(By.id("formCenterName")).click();
    driver.findElement(By.id("formCenterName")).sendKeys("Mi Centro Favorito");
    driver.findElement(By.cssSelector(".register-container")).click();
    driver.findElement(By.id("formAdminID")).click();
    driver.findElement(By.id("formAdminID")).sendKeys("11858734A");
    driver.findElement(By.id("formBasicPassword")).click();
    driver.findElement(By.id("formBasicPassword")).sendKeys("11858734A");
    driver.findElement(By.id("formGDPRCheckbox")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    //driver.findElement(By.cssSelector("img:nth-child(2)")).click();
    driver.findElement(By.cssSelector("input")).sendKeys("/home/kali/Downloads/plantilla_profesores.csv");
    driver.findElement(By.cssSelector("input")).sendKeys("/home/kali/Downloads/plantilla_alumnos.csv");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.linkText("Accede")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(1)")).click();
    {
      WebElement dropdown = driver.findElement(By.id("formRole"));
      dropdown.findElement(By.xpath("//option[. = 'Responsable de COVID']")).click();
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
    driver.findElement(By.id("formCenter")).sendKeys("Mi Centro Favorito");
    driver.findElement(By.id("formUsername")).click();
    driver.findElement(By.id("formUsername")).sendKeys("11858734A");
    driver.findElement(By.id("formBasicPassword")).click();
    driver.findElement(By.id("formBasicPassword")).sendKeys("11858734A");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".nord-button")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".nord-button"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
//    driver.findElement(By.linkText("Atrás")).click();
  }
}

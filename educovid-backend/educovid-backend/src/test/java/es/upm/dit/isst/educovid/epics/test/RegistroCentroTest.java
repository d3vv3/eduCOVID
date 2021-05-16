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
import org.openqa.selenium.chrome.ChromeDriver;
public class RegistroCentroTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @Before
  public void setUp() {
	System.setProperty("webdriver.chrome.driver", "chromedriver");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void registroCentro() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(1920, 918));
    driver.findElement(By.linkText("Registra tu centro")).click();
    driver.findElement(By.id("formResponsibleName")).click();
    driver.findElement(By.id("formResponsibleName")).sendKeys("Álvaro");
    driver.findElement(By.id("formCenterName")).sendKeys("IES Gran Capitán");
    driver.findElement(By.id("formAdminID")).sendKeys("11858734A");
    driver.findElement(By.id("formBasicPassword")).sendKeys("11858734A");
    driver.findElement(By.id("formGDPRCheckbox")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".dropzone-container > div")).click();
    driver.findElement(By.cssSelector("input")).sendKeys("/home/kali/Downloads/plantilla_profesores.csv");
    driver.findElement(By.cssSelector("input")).sendKeys("/home/kali/Downloads/plantilla_alumnos.csv");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
  }
}

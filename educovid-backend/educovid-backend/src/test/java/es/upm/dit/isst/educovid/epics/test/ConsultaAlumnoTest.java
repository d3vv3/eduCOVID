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
public class ConsultaAlumnoTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	System.setProperty("webdriver.chrome.driver", "chromedriverMac");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void consultaAlumno() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(1920, 918));
    driver.findElement(By.linkText("Accede")).click();
    driver.findElement(By.id("formCenter")).sendKeys("Mi Centro Favorito");
    driver.findElement(By.id("formUsername")).click();
    driver.findElement(By.id("formUsername")).sendKeys("mtAAAAA");
    driver.findElement(By.id("formBasicPassword")).click();
    driver.findElement(By.id("formBasicPassword")).sendKeys("mtAAAAA");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".nord-button")).click();
  }
}

package es.upm.dit.isst.educovid.epics.test;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;
public class ConfinarProfesoresTestTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void confinarProfesoresTest() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(2560, 1415));
    driver.findElement(By.linkText("Accede")).click();
    driver.findElement(By.id("formRole")).click();
    {
      WebElement dropdown = driver.findElement(By.id("formRole"));
      dropdown.findElement(By.xpath("//option[. = 'Responsable de COVID']")).click();
    }
    driver.findElement(By.id("formCenter")).click();
    driver.findElement(By.id("formCenter")).sendKeys("Mi Centro Favorito");
    driver.findElement(By.id("formUsername")).click();
    driver.findElement(By.id("formUsername")).sendKeys("11858734A");
    driver.findElement(By.id("formBasicPassword")).click();
    driver.findElement(By.id("formBasicPassword")).sendKeys("11858734A");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".card:nth-child(2) > .container-dashboard")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(1) > .nord-button:nth-child(1)")).click();
    driver.findElement(By.id("formConfinedText")).click();
    driver.findElement(By.id("formConfinedText")).sendKeys("Has sido confinado por contacto estrecho con alumno");
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(1) > .nord-button:nth-child(2)")).click();
    driver.findElement(By.id("formUnconfinedText")).click();
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(2)")).click();
  }
}

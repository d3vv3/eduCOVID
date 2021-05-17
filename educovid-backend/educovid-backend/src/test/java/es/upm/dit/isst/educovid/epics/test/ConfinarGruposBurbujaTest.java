package es.upm.dit.isst.educovid.epics.test;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.net.URL;
public class ConfinarGruposBurbujaTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriverMac");
	    driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	    js = (JavascriptExecutor) driver;
	    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void confinarGruposBurbuja() {
	  driver.get("http://localhost:3000/");
	    driver.manage().window().setSize(new Dimension(2560, 1415));
	    driver.findElement(By.linkText("Accede")).click();
	    driver.findElement(By.id("formRole")).click();
	    {
	      WebElement dropdown = driver.findElement(By.id("formRole"));
	      dropdown.findElement(By.xpath("//option[. = 'Responsable de COVID']")).click();
	    }
	    driver.findElement(By.id("formCenter")).click();
	    driver.findElement(By.id("formCenter")).sendKeys("Xavier\'s School for Gifted Youngsters");
	    driver.findElement(By.id("formUsername")).click();
	    driver.findElement(By.id("formUsername")).sendKeys("00000002C");
	    driver.findElement(By.id("formBasicPassword")).click();
	    driver.findElement(By.id("formBasicPassword")).sendKeys("00000002C");
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".card:nth-child(3) > .container-dashboard")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".nord-button:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    {
      WebElement element = driver.findElement(By.linkText("Clases"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".nord-button:nth-child(5)")).click();
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(2)")).click();
  }
}

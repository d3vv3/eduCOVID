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
public class GestionarCentroTest {
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
  public void gestionarCentro() {
    driver.get("http://localhost:3000/");
    driver.manage().window().setSize(new Dimension(1920, 918));
    driver.findElement(By.linkText("Accede")).click();
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
    {
      WebElement element = driver.findElement(By.cssSelector(".nord-button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".card:nth-child(1) > .container-dashboard")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)")).click();
    driver.findElement(By.id("formStudentName")).click();
//    driver.findElement(By.id("formStudentName")).clear();
    driver.findElement(By.id("formStudentName")).sendKeys("Jaime Conde Segovio");
    driver.findElement(By.id("formNumMat")).click();
//    driver.findElement(By.id("formNumMat")).clear();
    driver.findElement(By.id("formNumMat")).sendKeys("mtAAAAB");
    {
      WebElement dropdown = driver.findElement(By.id("formStudentClass"));
      dropdown.findElement(By.xpath("//option[. = '12']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("formStudentClass")).click();
    {
      WebElement element = driver.findElement(By.id("formStudentBubbleGroup"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentBubbleGroup"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentBubbleGroup"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("formStudentBubbleGroup")).click();
    driver.findElement(By.cssSelector(".btn > div")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".selected")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(5)")).click();
    js.executeScript("window.scrollTo(0,0)");
    driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(1)")).click();
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
    driver.findElement(By.id("formStudentName")).click();
    driver.findElement(By.id("formStudentName")).sendKeys("Mariano");
    driver.findElement(By.id("formNumMat")).sendKeys("S00004");
    driver.findElement(By.cssSelector(".form-group:nth-child(3)")).click();
    {
      WebElement dropdown = driver.findElement(By.id("formStudentClass"));
      dropdown.findElement(By.xpath("//option[. = '12']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("formStudentClass")).click();
    {
      WebElement dropdown = driver.findElement(By.id("formStudentClass"));
      dropdown.findElement(By.xpath("//option[. = '11']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("formStudentClass"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("formStudentClass")).click();
    driver.findElement(By.id("formStudentBubbleGroup")).click();
    driver.findElement(By.cssSelector(".btn > div")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".list-container > .person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector("form")).click();
//    driver.findElement(By.cssSelector(".selected")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = '11 - GRUPO BS']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = '12 - GRUPO JH']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = 'Todos']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = '11 - GRUPO BS']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = 'Todos']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = '11 - GRUPO BS']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = 'Todos']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = '11 - GRUPO BS']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
//    {
//      WebElement dropdown = driver.findElement(By.id("group"));
//      dropdown.findElement(By.xpath("//option[. = 'Todos']")).click();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).clickAndHold().perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).perform();
//    }
//    {
//      WebElement element = driver.findElement(By.id("group"));
//      Actions builder = new Actions(driver);
//      builder.moveToElement(element).release().perform();
//    }
//    driver.findElement(By.id("group")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".red")).click();
    driver.findElement(By.linkText("Profesores")).click();
    {
      WebElement element = driver.findElement(By.linkText("Profesores"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".selected")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)")).click();
    driver.findElement(By.id("formProfessorName")).click();
//    driver.findElement(By.id("formProfessorName")).clear();
    driver.findElement(By.id("formProfessorName")).sendKeys("Peter Parkera");
    driver.findElement(By.id("formNifNie")).click();
//    driver.findElement(By.id("formNifNie")).clear();
    driver.findElement(By.id("formNifNie")).sendKeys("22222223R");
    {
      WebElement dropdown = driver.findElement(By.id("professorClasses"));
      dropdown.findElement(By.xpath("//option[. = '11']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.id("professorClasses"));
      dropdown.findElement(By.xpath("//option[. = '12']")).click();
    }
    driver.findElement(By.cssSelector(".btn > div")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".padded")).click();
    System.out.println("1");
//    driver.findElement(By.cssSelector(".nord-button:nth-child(1)")).click();
    driver.findElement(By.id("formProfessorName")).click();
    driver.findElement(By.id("formProfessorName")).sendKeys("Logan");
    driver.findElement(By.id("formNifNie")).click();
    driver.findElement(By.id("formNifNie")).sendKeys("22558866E");
    {
      WebElement dropdown = driver.findElement(By.id("professorClasses"));
      dropdown.findElement(By.xpath("//option[. = '11']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.id("professorClasses"));
      dropdown.findElement(By.xpath("//option[. = '12']")).click();
    }
    driver.findElement(By.cssSelector(".btn > div")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(5)")).click();
    driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".padded:nth-child(2) > .nord-button:nth-child(1)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(1)")).click();
    System.out.println("2");
    driver.findElement(By.cssSelector(".person-card:nth-child(5)")).click();
    driver.findElement(By.cssSelector(".red")).click();
    driver.findElement(By.linkText("Clases")).click();
    {
      WebElement element = driver.findElement(By.linkText("Clases"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".person-card:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(1)")).click();
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
    {
      WebElement dropdown = driver.findElement(By.id("classProfessors"));
      dropdown.findElement(By.xpath("//option[. = 'Peter ParkerPeter Parkera']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.id("classProfessors"));
      dropdown.findElement(By.xpath("//option[. = 'Yod Samuel Martín García']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.id("classProfessors"));
      dropdown.findElement(By.xpath("//option[. = 'Manuel Sierra']")).click();
    }
    System.out.println("3");
    driver.findElement(By.id("formIndivClassName")).click();
//    driver.findElement(By.id("formIndivClassName")).clear();
    driver.findElement(By.id("formIndivClassName")).sendKeys("14");
    driver.findElement(By.id("formFechaInicioConmutacion")).click();
    driver.findElement(By.id("formFechaInicioConmutacion")).clear();
    driver.findElement(By.id("formFechaInicioConmutacion")).sendKeys("2020-03-05");
    driver.findElement(By.id("formTiempoConmutacion")).click();
    driver.findElement(By.id("formTiempoConmutacion")).clear();
    driver.findElement(By.id("formTiempoConmutacion")).sendKeys("7");
    driver.findElement(By.cssSelector(".btn > div")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(3)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(1)")).click();
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
    driver.findElement(By.id("formIndivClassName")).click();
    driver.findElement(By.id("formIndivClassName")).sendKeys("18");
    driver.findElement(By.id("formFechaInicioConmutacion")).click();
    driver.findElement(By.id("formFechaInicioConmutacion")).clear();
    driver.findElement(By.id("formFechaInicioConmutacion")).sendKeys("2020-03-07");
    driver.findElement(By.id("formTiempoConmutacion")).click();
    driver.findElement(By.id("formTiempoConmutacion")).clear();
    driver.findElement(By.id("formTiempoConmutacion")).sendKeys("14");
    {
      WebElement dropdown = driver.findElement(By.id("classProfessors"));
      dropdown.findElement(By.xpath("//option[. = 'Peter ParkerPeter Parkera']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.id("classProfessors"));
      dropdown.findElement(By.xpath("//option[. = 'Juan Carlos Yelmo García']")).click();
    }
    driver.findElement(By.cssSelector(".btn > div")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".nord-button:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".modal-footer > .btn:nth-child(1)")).click();
    driver.findElement(By.cssSelector(".person-card:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".red")).click();
    driver.findElement(By.linkText("Profesores")).click();
    {
      WebElement element = driver.findElement(By.linkText("Profesores"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.linkText("Clases")).click();
    {
      WebElement element = driver.findElement(By.linkText("Clases"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    System.out.println("4");
    driver.findElement(By.cssSelector(".nord-button:nth-child(3)")).click();
    driver.findElement(By.linkText("Atrás")).click();
  }
}

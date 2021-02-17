package Examen;

import Clase6.DataGenerator;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class prueba_netflix {

    WebDriver driver;
    String email="";
    public prueba_netflix(String mail){
        email = mail;
    }
    @BeforeMethod
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.netflix.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test (priority = 0)
    public void validarTituloTest(){
        String title = driver.getTitle();
        Assert.assertEquals(title, "Netflix Uruguay: Ve series online, ve películas online");
    }
    @Test (priority = 1)
    public void iniciarSesionPageTest(){
        driver.findElement(By.linkText("Iniciar sesión")).click();
        Assert.assertNotEquals(driver.getTitle(), "Netflix Uruguay: Ve series online, ve películas online");

        List<WebElement> listaDeH1s = driver.findElements(By.tagName("h1"));
        boolean tex=false;
        for (WebElement h1 : listaDeH1s){
            System.out.println("H1 -> " + h1.getText());
            if (h1.getText().equals("Iniciar sesión")){
                tex = true;
                Assert.assertTrue(tex);
       }
        }
        WebElement texfacebook= driver.findElement(By.xpath("//span[contains(text(),'Iniciar sesión con Facebook')]"));
        Assert.assertEquals(texfacebook.getText(), "Iniciar sesión con Facebook");
    }
    @Test (priority = 2)
    public void loginToNetflixErrorTest(){
        driver.findElement(By.linkText("Iniciar sesión")).click();
        driver.findElement(By.id("id_userLoginId")).sendKeys("XXX");
        driver.findElement(By.id("id_password")).sendKeys("holamundo");

        WebElement recuerdame= driver.findElement(By.xpath("//*[@class='login-remember-me-label-text']"));
        boolean seleccionado= false;
        if (recuerdame.isSelected()){
            seleccionado = true;
            Assert.assertTrue(seleccionado);
        }

        WebElement mensajeerror= driver.findElement(By.xpath("//div[contains(text(),'Escribe un email válido.')]"));
        Assert.assertEquals(mensajeerror.getText(), "Escribe un email válido.");

        WebElement boton = driver.findElement(By.xpath("//*[@type='submit']"));
        boton.click();
    }

    @Test (priority = 3)
    public void fakeEmailTest(){
        Faker faker_data = new Faker();
        String email = faker_data.internet().emailAddress();
        driver.findElement(By.id("id_email_hero_fuji")).sendKeys(email);
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("signup"));

    }

    @Test ( priority = 4, dataProvider = "mails", dataProviderClass = DataGeneratorExamen.class)
    public void dataProviderEmailTest(String email){

        driver.findElement(By.id("id_email_hero_fuji")).sendKeys(email);
        driver.findElement(By.xpath("//*[@type='submit']")).click();
    }

    @Test (priority = 5)
    @Parameters({"tagName"})
    public void printTagsTest(String tagName){

        List<WebElement> listaElementos = driver.findElements(By.tagName(tagName));
        if (tagName.equalsIgnoreCase("h1")){
            System.out.println("Se mostraran los h1");
        }
        else {
                System.out.println("No se encontraron elementos");
        }
    }
    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
    }

}

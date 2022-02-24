package ui.stepDefinitions;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import ui.pages.AmazonPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.Log;
import utilities.ReusableMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class amazonStepDefinitions {
    AmazonPage amazonPage=new AmazonPage();


    @And("Sayfa URL’inin {string} oldugunu dogrulayin")
    public void sayfaURLIninOldugunuDogrulayin(String URL) {
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), URL);

    }





    @And("resimleri indirir")
    public void resimleriIndirir() throws IOException {
        //WebElement resim= Driver.getDriver().findElement(By.xpath("(//img[@class='s-image'])"));
        List<WebElement> resim = (List<WebElement>) Driver.getDriver().findElements(By.xpath("//img[@class='s-image']"));
        for (int i = 1; i < resim.size(); i++) {
            String resimSRC = resim.get(i).getAttribute("src");
            URL resimURL = new URL(resimSRC);
            BufferedImage resmiKaydet = ImageIO.read(resimURL);
            ImageIO.write(resmiKaydet, "png", new File("pictureStore/AmazonArama" + i + ".png"));
            Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }
    }

    @Then("kullanici ALL butonuna tiklar")
    public void kullaniciALLButonunaTiklar() {
        amazonPage.AllMenusu.click();
    }


    @And("kullanici {string} Basligina Tiklar")
    public void kullaniciBasliginaTiklar(String title) {
        List<WebElement> titles=Driver.getDriver().findElements(By.xpath("//a[@class='hmenu-item']//div"));
        for (int i = 0; i <titles.size() ; i++) {

            if (titles.get(i).getText().equals(title)) {
                titles.get(i).click();
            }
        }
    }

    @Then("The user should be able to see and click all the titles in the ALL menu.")
    public void theUserShouldBeAbleToSeeAndClickAllTheTitlesInTheALLMenu() throws InterruptedException {

//        List<WebElement> altMenu=Driver.getDriver().findElements(By.xpath("(//div[@id='hmenu-content']//ul[5]//li)"));
//        Thread.sleep(5000);
//        for (int i = 3; i <altMenu.size() ; i++) {
//            altMenu.get(i).click();
//        }
        Driver.getDriver().findElements(By.xpath("(//div[@id='hmenu-content']//ul[5]//li)")).get(Random.class.getModifiers());

    }

    @Given("User goes to url.")
    public void userGoesToUrl() {
        Driver.getDriver().get(ConfigReader.getProperty("amazonUrl"));
    }

    @Then("The user verifies that at {string}.")
    public void theUserVerifiesThatAt(String pageURL) {
        Driver.getDriver().getCurrentUrl().equals(pageURL);

    }

    @And("The user closes the page.")
    public void theUserClosesThePage() {
        Driver.closeDriver();
    }

    @Then("The user clicks the Sign-In button..")
    public void theUserClicksTheSignInButton() {
        amazonPage.signInButon.click();
    }

    @When("User enters valid email.")
    public void userEntersValidEmail() {
        amazonPage.eMailInput.sendKeys(ConfigReader.getProperty("ValidEmail"));
    }

    @Then("The user clicks the continue button.")
    public void theUserClicksTheContinueButton() {
        amazonPage.continueButon.click();
    }

    @When("User enters valid password.")
    public void userEntersValidPassword() {
        amazonPage.passwordInput.sendKeys(ConfigReader.getProperty("ValidPassword"));
    }

    @Then("The user clicks the Sign-In button.")
    public void UserClicksTheSignInButton() {
        amazonPage.signInSubmit.click();
    }

    @And("The user sees the text {string}.")
    public void theUserSeesTheText(String userText) {
    amazonPage.usersSignInText.getText().equals("Hello, mehmet");
    }

    @When("User enters invalid password.")
    public void userEntersInvalidPassword() {
        amazonPage.passwordInput.sendKeys(ConfigReader.getProperty("InValidPassword"));
    }

    @And("The user sees the Your password is incorrect.")
    public void theUserSeesTheYourPasswordIsIncorrect() {
        System.out.println(amazonPage.wrongPasswordText.getText());
        amazonPage.wrongPasswordText.getText().contains("There was a problem");
    }

    @When("User enters invalid email.")
    public void userEntersInvalidEmail() {
        amazonPage.eMailInput.sendKeys(ConfigReader.getProperty("invalidEmail"));
    }

    @And("The user sees {string}")
    public void theUserSees(String text) {
        amazonPage.passwordInput.getText().contains(text);
    }

    @When("User types the {string} in the search box.")
    public void userTypesTheInTheSearchBox(String aranacakKelime) {
        amazonPage.searchBox.sendKeys(aranacakKelime + Keys.ENTER);
    }

    @Then("User verifies that the {string} he is looking for includes the products listed.")
    public void userVerifiesThatTheHeIsLookingForIncludesTheProductsListed(String arananKelime) {
        Assert.assertTrue(amazonPage.sonucYazisi.getText().contains(arananKelime));
    }

    @Then("User logs out of account")
    public void userLogsOutOfAccount() throws InterruptedException {
        Thread.sleep(2000);

        Actions action = new Actions(Driver.getDriver());

        action.moveToElement(amazonPage.signInButon).perform();

        Thread.sleep(2000);
        amazonPage.closeToAccount.click();
    }
    @Then("The user selects the appropriate filter to list the products he has listed.")
    public void the_user_selects_the_appropriate_filter_to_list_the_products_he_has_listed() throws InterruptedException {
        int i = 0;
        List<WebElement> checkBox=Driver.getDriver().findElements(By.xpath("(//div[@class='a-checkbox a-checkbox-fancy s-navigation-checkbox aok-float-left'])"+"["+i+"]"));

        Thread.sleep(3000);

       checkBox.stream().forEach(t->t.click());

    }




    @And("The user sees that the filtered products are listed.")
    public void theUserSeesThatTheFilteredProductsAreListed() {
        Assert.assertTrue(amazonPage.results.getText().contains("RESULTS"));
    }


    @When("The user searches and lists the {string} he wants to buy in the search box")
    public void theUserSearchesAndListsTheHeWantsToBuyInTheSearchBox(String aranacakKelime) throws InterruptedException {
        List<String>prices=new ArrayList<>();

        amazonPage.searchBox.sendKeys(aranacakKelime + Keys.ENTER);
        String first=amazonPage.firstProduct.getText();
        amazonPage.firstProduct.click();
        Thread.sleep(3000);

        prices.add(amazonPage.prodPrice.getText()+","+amazonPage.prodPriceFrac.getText());
        System.out.println(prices);
        System.out.println("Price : "+amazonPage.prodPrice.getText()+","+amazonPage.prodPriceFrac.getText());

    }

    @Then("The user chooses the products he wants to buy.")
    public void theUserChoosesTheProductsHeWantsToBuy() throws InterruptedException {
        amazonPage.addToCartButon.click();
        Thread.sleep(3000);

    }

    @And("User goes to cart.")
    public void userGoesToCart() {
     amazonPage.goToCart.click();
     Log.info("go to cart");
    }

    @And("The user confirms that the selected products have been added to the cart.")
    public void theUserConfirmsThatTheSelectedProductsHaveBeenAddedToTheCart() {
        List<WebElement>basketList=Driver.getDriver().findElements(By.xpath("(//span[@class='a-truncate a-size-medium'])"));
        for (int i = 0; i <basketList.size() ; i++) {
            System.out.println(basketList.get(i).getText());
        }
    }

    @Then("The user confirms that the total price of the selected products is the same as the total price in the basket.")
    public void theUserConfirmsThatTheTotalPriceOfTheSelectedProductsIsTheSameAsTheTotalPriceInTheBasket() {
        System.out.println(amazonPage.totalPriceToCart.getText());
        Log.info("Alinan ürünlerin toplamı sepetteki toplam ile karsilastirildi");
    }


    @When("User goes to FOOTER.")
    public void userGoesToFOOTER() {
        Actions actions=new Actions(Driver.getDriver());
        actions.sendKeys(Keys.END).perform();
        Assert.assertTrue(amazonPage.footer.isDisplayed());
        Log.info("Amazon Footer görünür mü?");
    }

    @Then("The user clicks on a link.")
    public void theUserClicksOnALink() {
        amazonPage.FooterAmazonScienceLink.click();
        Log.info("Footer Amazon Science Linkine tiklandi");
    }

    @And("The user verifies the title of the page he is directed to.")
    public void theUserVerifiesTheTitleOfThePageHeIsDirectedTo() {
        Assert.assertTrue(Driver.getDriver().getTitle().equals("Amazon Science Homepage"));
        Log.info("Amazon Science Homepage sayfasına gidildi");
    }


    @And("User hover-over Account &Lists.")
    public void userHoverOverAccountLists() {
        Actions actions=new Actions(Driver.getDriver());
        actions.moveToElement(amazonPage.usersSignInText).perform();
    }


    @When("The user clicks on the Create a List link.")
    public void theUserClicksOnTheCreateAListLink() {
        amazonPage.createAList.click();
    }

    @Then("User clicks Create a List button.")
    public void userClicksCreateAListButton() {
        amazonPage.createAListButton.click();
    }

    @Then("User enters List Name.")
    public void userEntersListName() {
        amazonPage.listNameInput.click();
        amazonPage.listNameInput.clear();
        amazonPage.listNameInput.sendKeys(ConfigReader.getProperty("listName"));
    }

    @And("User clicks end of Create a List button.")
    public void userClicksEndOfCreateAListButton() throws InterruptedException {
        Thread.sleep(2000);
      amazonPage.endOfCreateAListButon.click();
      Log.info("new list created");
    }

    @Given("User verifies that the list is created.")
    public void userVerifiesThatTheListIsCreated() {
        Assert.assertTrue(amazonPage.NewlistName.getText().equals(ConfigReader.getProperty("listName")));
    Log.info("List name is True");
    }

    @Then("User goes to Manage list.")
    public void userGoesToManageList() throws InterruptedException {

      Actions actions=new Actions(Driver.getDriver());
      actions.moveToElement(amazonPage.ucNokta).click().perform();
        Thread.sleep(2000);
        amazonPage.manageYourList.click();
    }

    @And("User clicks on Delete list button")
    public void userClicksOnDeleteListButton() throws InterruptedException {
        amazonPage.deleteListButon.click();
        Thread.sleep(2000);
        amazonPage.confirmDeleteButon.click();
        Log.info("List delete click");
    }

    @And("The user confirms that the list has been deleted.")
    public void theUserConfirmsThatTheListHasBeenDeleted() {
        Assert.assertTrue(amazonPage.forAllYourShoppingNeeds.isDisplayed());
        Log.info("liste silindi ,for all your shopping needs yazisi görünür");
    }

    @Then("The user goes to the product page.")
    public void theUserGoesToTheProductPage() {
        amazonPage.firstProduct.click();
    }

    @And("User clicks Add to List button.")
    public void userClicksAddToListButton() {
        ReusableMethods.waitForPageToLoad(10);
        amazonPage.productAddToListButon.click();
    }

    @Then("The user confirms that the selected product has been added to the list.")
    public void theUserConfirmsThatTheSelectedProductHasBeenAddedToTheList() {
//        Assert.assertTrue(amazonPage.productName.getText().contains(amazonPage.productInList.getText()));
        Log.info("ürün listeye ekleme doğrulandi");
    }

    @And("The user clicks the View Your List button and sees the List.")
    public void theUserClicksTheViewYourListButtonAndSeesTheList() {
        ReusableMethods.waitForPageToLoad(5);
        amazonPage.viewYourList.click();
        Assert.assertTrue(amazonPage.listOfMehmet.isDisplayed());
        Log.info("Liste görüntülendi");

    }

    @Then("The user clicks the Delete button and sees the Deleted alert.")
    public void theUserClicksTheDeleteButtonAndSeesTheDeletedAlert() {
        amazonPage.deleteProductInListButon.click();
        Assert.assertTrue(amazonPage.deleteIsDisplayed.isDisplayed());
        Log.info("Listedeki ürünü silme basarili");
    }
}
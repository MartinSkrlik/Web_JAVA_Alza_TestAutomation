package pages.alza;

import generic.Element;
import generic.IPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ALZA3_Page {

    public enum Alza3_Page implements IPageObject {

        PAGE_TITLE
                (By.xpath("//h1")),
        I_UNDERSTAND_BTN
                (By.xpath("//div/a[@class='btnx cookies-info__button js-cookies-info-accept']")),
        SEARCH_INPUT
                (By.xpath("//input[@id='edtSearch']")),
        SEARCH_BTN
                (By.xpath("//div[@id='btnSearch']")),
        ITEM_NAME
                (By.xpath("//h1[@itemprop='name']")),
        TOP_RATED_TAB
                (By.xpath("//*[text()='Top-Rated']")),
        TOP_RATED_SELECTED
                (By.xpath("//li[@aria-controls='nejlepehodnocene']")),
        NAME_PRODUCT
                (By.xpath("(//div[@id='boxes']/div)[1]//a[@class='name browsinglink impression-binded']")),
        PRICE_PRODUCT
                (By.xpath("(//div[@id='boxes']/div)[1]//a[@class='name browsinglink impression-binded']//ancestor::div[@class='top']/following-sibling::div//span[@class='price-box__price']")),
        AVAILABILITY_PRODUCT
                (By.xpath("(//div[@id='boxes']/div)[1]//a[@class='name browsinglink impression-binded']//ancestor::div[@class='top']/following-sibling::div//div[@class='postfix']")),
        FAVOURITE_CHECKBOX
                (By.xpath("//div[@id='arrowDialog']/div/div[//*[contains(text(), 'Favourite')]]")),
        FAVOURITE_DIALOG
                (By.xpath("//div[@class='js-shopping-lists-select shoppingListsSelect']")),
        FAVOURITE_ICON
                (By.xpath("//span[@class='alzaico-f-heart']")),
        FAVOURITE_TAB
                (By.xpath("//span[@class='normalBlock']/span[text()='Favourites']")),
        NAME_FAVOURITE
                (By.xpath("//a[@class='name']")),
        AVAILABILITY_FAVOURITE
                (By.xpath("//span[@class='avail avlVal avl0 none']")),
        PRICE_FAVOURITE
                (By.xpath("//span[@class='price']")),
        MINIUS_BTN
                (By.xpath("//span[@class='countMinus']")),
        ;
        By findBy;

        Alza3_Page(By findBy) {
            this.findBy = findBy;
        }

        @Override
        public By getLocator() {
            return findBy;
        }

        @Override
        public Element<Alza3_Page> getElement(String... msg) {
            return new Element<>(getLocator(), getDescription(msg));
        }
    }

    private final String value;

    public ALZA3_Page(String value) {
        this.value = value;
    }

    public Element<?> getModelName(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getModelDPrice(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']/ancestor::div[@data-almostnew='false']//span[@class='price-box__price']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getModelAvailability(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']/ancestor::div[@data-almostnew='false']//div[@class='postfix']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getModelFavourites(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']/parent::div//span[@title='Add to favourites']");

        return new Element<>(driver, locator, "Xpath");
    }







}






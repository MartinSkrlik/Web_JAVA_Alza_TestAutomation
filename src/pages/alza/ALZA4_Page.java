package pages.alza;

import generic.Element;
import generic.IPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ALZA4_Page {

    public enum Alza4_Page implements IPageObject {

        PAGE_TITLE
                (By.xpath("//h1")),
        I_UNDERSTAND_BTN
                (By.xpath("//div/a[@class='btnx cookies-info__button js-cookies-info-accept']")),
        CART_BUTTON
                (By.xpath("//*[text()='Cart']")),
        CART_INDEX
                (By.xpath("//a[@class='m m1 sel']")),
        ADD_MORE_PRODUCT_TAB
                (By.xpath("//*[@class='insertItemBlock insertProduct']//div[@class='insertItemTitle']")),
        PRODUCT_NAME_INPUT
                (By.xpath("//div[@class='insertItemBlock insertProduct active']")),
        INPUT_NAME
                (By.xpath("//input[@class='inputText ui-autocomplete-input']")),
        PICK_ITEM
                (By.xpath("//li[@class='ui-menu-item']")),
        FIND_ELEMENTS
                (By.xpath("//td[@class='c2']//following-sibling::td[@class='c5']")),
        LAST_PRICE
                (By.xpath("//span[@class='last price']")),
        ;
        By findBy;

        Alza4_Page(By findBy) {
            this.findBy = findBy;
        }

        @Override
        public By getLocator() {
            return findBy;
        }

        @Override
        public Element<Alza4_Page> getElement(String... msg) {
            return new Element<>(getLocator(), getDescription(msg));
        }
    }

    private final String value;

    public ALZA4_Page(String value) {
        this.value = value;
    }

    public Element<?> getRemoveButton(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']//ancestor::tr//span[@class='item-options__trigger js-item-options-trigger']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getConfirmRemove(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']//ancestor::tr//span[@class='item-options__trigger js-item-options-trigger']/following-sibling::div//*[text()='Remove']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getProductName(WebDriver driver) {
        By locator = By.xpath("//*[text()='" + value + "']");

        return new Element<>(driver, locator, "Xpath");
    }



}






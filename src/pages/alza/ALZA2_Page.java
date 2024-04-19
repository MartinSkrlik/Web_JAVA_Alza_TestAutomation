package pages.alza;

import generic.Element;
import generic.IPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ALZA2_Page {

    public enum Alza2_Page implements IPageObject {

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
        ITEM_DESCRIPTION
                (By.xpath("//div[@class='nameextc']/span")),
        ITEM_PRICE
                (By.xpath("//span[@class='price-box__price']")),
        IMG_TEXT
                (By.xpath("//div[@class='chat-wrapper minimalized opened']")),
        CLOSE_BUTTON
                (By.xpath("//div[@class='chat-icon open-chat close']")),
        ;
        By findBy;

        Alza2_Page(By findBy) {
            this.findBy = findBy;
        }

        @Override
        public By getLocator() {
            return findBy;
        }

        @Override
        public Element<Alza2_Page> getElement(String... msg) {
            return new Element<>(getLocator(), getDescription(msg));
        }
    }

    private final String value;

    public ALZA2_Page(String value) {
        this.value = value;
    }

    public Element<?> getModelName(WebDriver driver) {
        By locator = By.xpath("//a[text()='" + value + "']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getModelDescription(WebDriver driver) {
        By locator = By.xpath("//a[text()='" + value + "']/following-sibling::div");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getModelPrice(WebDriver driver) {
        By locator = By.xpath("//a[text()='" + value + "']//ancestor::div[@class='top']/following-sibling::div//span[@class='price-box__price']");

        return new Element<>(driver, locator, "Xpath");
    }

}






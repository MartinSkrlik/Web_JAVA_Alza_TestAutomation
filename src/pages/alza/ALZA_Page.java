package pages.alza;

import generic.Element;
import generic.IPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ALZA_Page {

    public enum Alza_Page implements IPageObject {

        PAGE_TITLE
                (By.xpath("//h1")),
        I_UNDERSTAND_BTN
                (By.xpath("//div/a[@class='btnx cookies-info__button js-cookies-info-accept']")),
        SEARCH_INPUT
                (By.xpath("//input[@id='edtSearch']")),
        SEARCH_BTN
                (By.xpath("//div[@id='btnSearch']")),
        TOP_RATED_TAB
                (By.xpath("//a[@href='#nejlepehodnocene']")),
        MODEL_NAME
                (By.xpath("//a[@class='productInfo__texts__productName']")),
        PROCEED_BTN
                (By.xpath("(//*[text()='Proceed to Checkout'])[1]")),
        MAIN_ITEM
                (By.xpath("//a[@class='mainItem']")),
        CART_TAB
                (By.xpath("//a[@class='m m1 sel']")),
        PRICE
                (By.xpath("//span[@class='last price']")),
        CONTINUE_BTN
                (By.xpath("//*[text()='Continue']")),
        PAYMANET_TAB
                (By.xpath("//span[@class='m m2 sel']")),
        CHECKBOX_BTN
                (By.xpath("//label[@for='deliveryCheckbox-684']")),
        POPUP_TITLE
                (By.xpath("//*[contains(text(),'Bratislava - headquarters')]")),
        ALZA_DIALOG
                (By.xpath("//div[@id='alzaDialog']")),
        DELIVERY_PRICE
                (By.xpath("//div[@class='delivery-item selected']/span")),
        CONFIRM_BTN
                (By.xpath("//*[text()='Confirm your selection']")),
        DISPATCH_ITEM
                (By.xpath("//span[text()='Bratislava - main shop']")),
        MAIN_SHOP_PRICE
                (By.xpath("//span[@class='price']")),
        PAYMENT_CHECKBOX
                (By.xpath("//label[@for='paymentCheckbox-216']")),
        PAYMENT_PRICE
                (By.xpath("//span[@class='payment-price free']")),
        ADDRESS_TAB
                (By.xpath("//a[@class='m m3 sel']")),
        ITEM_PRICE
                (By.xpath("//tr[@class='item']/td[@class='price']/span")),
        FINAL_PRICE
                (By.xpath("(//tr[@class='withVat']/td)[2]/span")),
        ;
        By findBy;

        Alza_Page(By findBy) {
            this.findBy = findBy;
        }

        @Override
        public By getLocator() {
            return findBy;
        }

        @Override
        public Element<Alza_Page> getElement(String... msg) {
            return new Element<>(getLocator(), getDescription(msg));
        }
    }
    private final String value;
    public ALZA_Page(String value){
        this.value = value;
    }
    public Element<?> getRadioButtonElement() {
        By locator = By.xpath("//input[@value='" + value + "']");

        return new Element<>(locator);
    }
    public Element<?> getModelName(WebDriver driver) {
        By locator = By.xpath("//*[normalize-space()='" + value + "']");

        return new Element<>(driver,locator,"Xpath");
    }
    public Element<?> getModelPrice(WebDriver driver) {
        By locator = By.xpath("//*[normalize-space()='" + value + "']/ancestor::div[@data-almostnew='false']//span[@class='price-box__price']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getBuy(WebDriver driver) {
        By locator = By.xpath("//*[normalize-space()='" + value + "']/ancestor::div[@data-almostnew='false']//span[@class='btnkx canc canwatchdog cannotChangeQuantity ']");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getName(WebDriver driver) {
        By locator = By.xpath("//*[contains(text(),'1x " + value + "')]");

        return new Element<>(driver, locator, "Xpath");
    }

    public Element<?> getProceedToCart(WebDriver driver) {
            By locator = By.xpath("//*[text()='" + value + "']");

            return new Element<>(driver, locator,"Xpath");
    }

    public Element<?> getCheckboxName(WebDriver driver) {
        By locator = By.xpath("//*[contains(text(),'" + value + "')]//parent::div/preceding-sibling::div/input");

        return new Element<>(driver, locator,"Xpath");
    }

    public Element<?> getPaymentMethod(WebDriver driver) {
        By locator = By.xpath("//td[@class='text']/*[text()='" + value + "']");

        return new Element<>(driver, locator,"Xpath");
    }




}
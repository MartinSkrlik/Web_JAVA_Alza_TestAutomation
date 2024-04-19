package pages.rahul;

import generic.Element;
import generic.IPageObject;
import org.openqa.selenium.By;

public class RAHUL_Page {

    public enum Rahul_Page implements IPageObject {

        PAGE_TITLE
                (By.xpath("//h1")),
        Feedback_Input
                (By.xpath("//input[@value='TomasIsCool']")),
        INPUT_COUNTRY
                (By.xpath("//input[@id='autocomplete']")),
        SET_COUNTRY
                (By.xpath("//li[@class='ui-menu-item']")),
        VERIFY_COUNTRY
                (By.xpath("//div[@role='status']//div[@style='display: none;']//following-sibling::div")),
        SELECT_DROPDOWN
                (By.xpath("//select[@id='dropdown-class-example']")),
        SELECT_CHECKBOX
                (By.xpath("//input[@id='checkBoxOption3']")),
        SELECT_WINDOW
                (By.xpath("//*[text()='Open Window']")),
        POP_UP_WINDOW
                (By.xpath("//*[text()='NO THANKS']")),
        SELECT_TAB
                (By.xpath("//a[@id='opentab']")),
        TAB_NAME
                (By.xpath("//head/title")),
        INPUT_NAME
                (By.xpath("//input[@id='name']")),
        CLICK_ALERT
                (By.xpath("//input[@id='alertbtn']")),
        CLICK_CONFIRM
                (By.xpath("//input[@id='confirmbtn']")),
        INPUT_SELECT
                (By.xpath("//input[@id='displayed-text']")),
        SELECT_HIDE
                (By.xpath("//input[@id='hide-textbox']")),
        EXAMPLE_TABLE
                (By.xpath("//tbody/tr/*[contains(text(),'Rahul Shetty')]")),
        MOUSE_BTN
                (By.xpath("//button[@id='mousehover']")),
        TOP_BTN
                (By.xpath("//a[contains(text(),'Top')]")),
        IFRAME_ID
                (By.xpath("//iframe[@id='courses-iframe']")),
        TABLE_HEADER
                (By.xpath("(//table[@id='product']/tbody)[2]/tr")),
        ;
        By findBy;

        Rahul_Page(By findBy) {
            this.findBy = findBy;
        }

        @Override
        public By getLocator() {
            return findBy;
        }

        @Override
        public Element<Rahul_Page> getElement(String... msg) {
            return new Element<>(getLocator(), getDescription(msg));
        }
    }
    private final String value;
    public RAHUL_Page(String value){
        this.value = value;
    }
    public Element<?> getRadioButtonElement() {
        By locator = By.xpath("//input[@value='" + value + "']");

        return new Element<>(locator);
    }
    public Element<?> getCheckBoxElement() {
        By locator = By.xpath("//select[@id='dropdown-class-example']//*[text()='" + value + "']");

        return new Element<>(locator);
    }

    public Element<?> getWindowTitle() {
        By locator = By.xpath("//*[text()='" + value + "']");

        return new Element<>(locator);
    }





}
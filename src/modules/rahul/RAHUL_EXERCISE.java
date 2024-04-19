package modules.rahul;

import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.rahul.RAHUL_Page;
import utility.Log;
import utility.Report;
import utility.Validation;

import java.util.List;
import java.util.Map;
import static pages.rahul.RAHUL_Page.Rahul_Page.*;
import static utility.Constant.InputKeys.DRIVER;
import static utility.Constant.Input_RAHUL_TC.*;

public class RAHUL_EXERCISE extends AbstractModule implements IWebPageModule {

    private String module;
    private String title;

    private Element<?> page_title = PAGE_TITLE.getElement();
    private Element<?> feedback_input = Feedback_Input.getElement();
    private Element<?> input_country = INPUT_COUNTRY.getElement();
    private Element<?> set_country = SET_COUNTRY.getElement();
    private Element<?> verify_country = VERIFY_COUNTRY.getElement();
    private Element<?> select_dropdown = SELECT_DROPDOWN.getElement();
    private Element<?> select_checkbox = SELECT_CHECKBOX.getElement();
    private Element<?> select_window = SELECT_WINDOW.getElement();
    private Element<?> pop_up_window = POP_UP_WINDOW.getElement();
    private Element<?> select_tab = SELECT_TAB.getElement();
    private Element<?> tab_name = TAB_NAME.getElement();
    private Element<?> input_name = INPUT_NAME.getElement();
    private Element<?> click_alert = CLICK_ALERT.getElement();
    private Element<?> click_confirm = CLICK_CONFIRM.getElement();
    private Element<?> select_hide = SELECT_HIDE.getElement();
    private Element<?> input_select = INPUT_SELECT.getElement();
    private Element<?> example_table = EXAMPLE_TABLE.getElement();
    private Element<?> mouse_btn = MOUSE_BTN.getElement();
    private Element<?> top_btn = TOP_BTN.getElement();
    private Element<?> iframe_id = IFRAME_ID.getElement();
    private Element<?> table_header = TABLE_HEADER.getElement();



    public RAHUL_EXERCISE(Map<String, Object> input) {

        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        this.title = input.get(TITLE.get()) != null ? (String) input.get(TITLE.get()) : "-";

        initializeElements(page_title,feedback_input,input_country,set_country,verify_country,select_dropdown,
                select_checkbox,select_window,pop_up_window,select_tab,tab_name,input_name,click_alert,
                click_confirm, select_hide,input_select,example_table,mouse_btn,top_btn,iframe_id,table_header);


    }
    public void verifyPage() {
        waitForPageLoad();
        verifyPageTitle();
    }

    private void verifyPageTitle() {
        waitForElementVisible(page_title, 10);
        new Validation("PAGE TITLE", getElementText(page_title), title).stringEquals();
        logScreenshot(driver, module);
    }

    public void clickAndVerifyRadioButton() {
        String radio_name = (String) input.get(RADIO.get());
        RAHUL_Page page = new RAHUL_Page(radio_name);
        Element<?> radio_btn = page.getRadioButtonElement();
        initializeElements(radio_btn);
        clickElement(radio_btn);
        new Validation(radio_name + " is selected", verifyIsSelected(radio_btn)).isTrue();
        logScreenshot(driver, module);

    }

    @Override
    public void waitForPageLoad() {
        waitForElementDisplayed(page_title, 10);
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
    }

    public void setTextAndVerify() {
        String get_country_name = (String) input.get(COUNTRY.get());
        Element<?> input = input_country;
        setElementText(input, get_country_name);
        sleep(2000);
        Element<?> set = set_country;
        clickElement(set);
        Element<?> verify = verify_country;
        String name_country = getElementText(verify);
        new Validation("VERIFY TEXT SELECTED", name_country, get_country_name).stringEquals();
        logScreenshot(driver, module);
    }

    public void selectDropDownAndVerify() {
        String option_name = (String) input.get(OPTION.get());
        RAHUL_Page page = new RAHUL_Page(option_name);
        Element<?> option_btn = select_dropdown;
        selectElementFromListByVisibleText(option_btn, option_name);
        Element<?> dropdown = page.getCheckBoxElement();
        initializeElements(dropdown);
        new Validation(option_name + " is selected", verifyIsSelected(dropdown)).isTrue();
        logScreenshot(driver, module);
    }

    public void selectCheckBoxAndVerify() {
        String checkbox_name = (String) input.get(CHECKBOX.get());
        Element<?> checkbox_btn = select_checkbox;
        clickElement(checkbox_btn);
        new Validation(checkbox_name + " is selected", verifyIsSelected(checkbox_btn)).isTrue();
        logScreenshot(driver, module);
    }

    public void openAndVerifyWindowExample() {
        String window_example_title = (String) input.get(WINDOW_TITLE.get());
        RAHUL_Page page = new RAHUL_Page(window_example_title);
        Element<?> window_btn = select_window;
        clickElement(window_btn);
        switchToBrowserTab(driver, 2);
        Element<?> pop_up = pop_up_window;
        if (verifyElementIsPresent(pop_up)) {
            logScreenshot(driver, module);
            clickElement(pop_up);
        } else {
            Element<?> window_title = page.getWindowTitle();
            initializeElements(window_title);
            waitForElementVisible(window_title, 10);
            new Validation("WINDOW TITLE", getElementText(window_title), window_example_title).stringEquals();
            logScreenshot(driver, module);
            driver.close();
            switchToBrowserTab(driver, 1);
        }
    }

    public void openAndVerifyTabExample() {
        String window_tab_title = (String) input.get(TAB_TITLE.get());
        Element<?> tab_btn = select_tab;
        clickElement(tab_btn);
        switchToBrowserTab(driver, 2);
        sleep(4000);
        Element<?> tab_title = tab_name;
        new Validation("TAB TITLE", getElementText(tab_title), window_tab_title).contains();
        logScreenshot(driver, module);
        driver.close();
        switchToBrowserTab(driver, 1);
    }

    public void enterAlertNameAndVerify() {
        String ALERT_NAME = (String) input.get(ALERT.get());
        String INPUT = (String) input.get(NAME.get());
        Element<?> name_input = input_name;
        waitForElementVisible(name_input, 10);
        setElementText(name_input, INPUT);
        Element<?> select_alert = click_alert;
        clickElement(select_alert);
        driver.switchTo().alert();
        String alertText = driver.switchTo().alert().getText();
        new Validation("ALERT TITLE", alertText, ALERT_NAME).stringEquals();
        acceptAlert(driver);
    }

    public void enterConfirmNameAndVerify() {
        String ALERT_NAME = (String) input.get(CONFIRM_ALERT.get());
        String INPUT = (String) input.get(NAME.get());
        Element<?> name_input = input_name;
        waitForElementVisible(name_input, 10);
        setElementText(name_input, INPUT);
        Element<?> select_confirm = click_confirm;
        clickElement(select_confirm);
        driver.switchTo().alert();
        String alertText = driver.switchTo().alert().getText();
        new Validation("CONFIRM TITLE", alertText, ALERT_NAME).stringEquals();
        dismissAlert(driver);
    }

    public void verifyInputAndClickHide() {
        Element<?> example_input = input_select;
        scrollElementIntoView(example_input);
        new Validation("ELEMENT IS PRESENT", verifyElementIsPresent(example_input)).isTrue();
        logScreenshot(driver, module);
        sleep(2000);
        Element<?> hide_btn = select_hide;
        clickElement(hide_btn);
        scrollElementIntoView(example_input);
        String name = example_input.getDriver().findElement((By)example_input.getElementLocator()).getAttribute("style");
        new Validation("VERIFY TAB IS HIDE",name, "display: none;").contains();
        logScreenshot(driver, module);
    }

    public void countAllrowsInTable(){
        Element<?> num_rows = example_table;
        String rows_number = (String) input.get(NUMBER_OF_ROWS.get());
        Integer count = driver.findElements((By)example_table.getElementLocator()).size();
        String number =  Integer.toString(count);
        new Validation("NUMBER OF ROWS", rows_number,number).contains();
        scrollElementIntoView(num_rows);
        logScreenshot(driver, module);
    }

    public void verifyEmptyRowInTable(){
        List<WebElement> elements = driver.findElements((By)table_header.getElementLocator());
        int i = elements.size();
        String xpath = "//div[@class='tableFixHead']//table[@id='product']/tbody/tr[";
        String end = "]";
        for (int j = 1; j <= i; j++) {
            String n = driver.findElement(By.xpath(xpath + j + end)).getText();
            if (n.isEmpty()) {Report.logInfoMessage(" In row " + j + " is empty row");;}
            else {Report.logInfoMessage(" In row " + j + " is no empty row");}
        }}

    public void printSpecificRow(){
        List<WebElement> elements = driver.findElements((By)table_header.getElementLocator());
        for(WebElement row : elements){
            String premenna = row.getText();
                if (premenna.contains("Mumbai")){Report.logSuccessMessage("Row = " + premenna + " , contains text 'Mumbai' ");};
        }
    }

    public void hoverMouseOverHoverButton(){
        Element<?> mouse_button = mouse_btn;
        scrollElementIntoView(mouse_button);
        mouseOverElement(mouse_button);
        Element<?> top_button = top_btn;
        waitForElementClickable(top_button, 10);
        logScreenshot(driver, module);
        sleep(2000);
        clickElement(top_button);
        logScreenshot(driver, module);
    }

    public void switchToFrameAndVerify(){
        String window_tab_title = (String) input.get(TAB_TITLE.get());
        Element<?> iframe = iframe_id;
        switchToFrameByLocator(iframe);
        Element<?> iframe_t = tab_name;
        String porovnat = getElementText(iframe_t);
        new Validation("IFRAME TITLE", porovnat ,window_tab_title.trim()).stringEquals();
    }
}

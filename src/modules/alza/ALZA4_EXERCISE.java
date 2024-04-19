package modules.alza;

import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.alza.ALZA4_Page;
import utility.Report;
import utility.Validation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static pages.alza.ALZA4_Page.Alza4_Page.*;
import static utility.Constant.InputKeys.DRIVER;
import static utility.Constant.Input_ALZA4_TC.*;
import java.util.ArrayList;

public class ALZA4_EXERCISE extends AbstractModule implements IWebPageModule {
    static HashMap<String, String> elementMapper = new HashMap<>();


    private String module;
    private String title;

    private Element<?> page_title = PAGE_TITLE.getElement();
    private Element<?> i_understand_btn = I_UNDERSTAND_BTN.getElement();
    private Element<?> cart_button = CART_BUTTON.getElement();
    private Element<?> cart_index = CART_INDEX.getElement();
    private Element<?> add_more_product_tab = ADD_MORE_PRODUCT_TAB.getElement();
    private Element<?> product_name_input = PRODUCT_NAME_INPUT.getElement();
    private Element<?> input_name = INPUT_NAME.getElement();
    private Element<?> pick_item = PICK_ITEM.getElement();
    private Element<?> find_element = FIND_ELEMENTS.getElement();
    private Element<?> last_price = LAST_PRICE.getElement();

    public ALZA4_EXERCISE(Map<String, Object> input) {

        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        this.title = input.get(TITLE.get()) != null ? (String) input.get(TITLE.get()) : "-";

        initializeElements(page_title,i_understand_btn,cart_button,cart_index,add_more_product_tab,
                product_name_input,input_name,pick_item,find_element,last_price);

    }

    @Override
    public void waitForPageLoad() {
        waitForElementDisplayed(page_title, 10);
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
    }

    public void AcceptPopUp(){
        Element<?> pop_up = i_understand_btn;
        if (verifyElementIsPresent(pop_up)) {
            clickElement(pop_up);
            logScreenshot(driver, module);}
    }
    public void OpenCart(){
        waitForElementVisible(cart_button,10);
        clickElement(cart_button);
        waitForElementVisible(cart_index,10);
        logScreenshot(driver, module);
    }

    public void AddFirstItem(){
        String product_name = (String) input.get(PRODUCT_TYPE1.get());
        clickElement(add_more_product_tab);
        String name = product_name_input.getDriver().findElement((By)product_name_input.getElementLocator()).getAttribute("class");
        new Validation("VERIFY TAB 'PRODUCT CODE AND NAME' IS ACTIVE",name, "insertProduct active").contains();
        waitForElementVisible(input_name,10);
        setElementText(input_name,product_name);
        waitForElementVisible(pick_item,10);
        clickElement(pick_item);
        waitForElementVisible(add_more_product_tab,10);
        logScreenshot(driver,module);
    }

    public void AddSecondItem(){
        String product_name = (String) input.get(PRODUCT_TYPE2.get());
        clickElement(add_more_product_tab);
        String name = product_name_input.getDriver().findElement((By)product_name_input.getElementLocator()).getAttribute("class");
        new Validation("VERIFY TAB 'PRODUCT CODE AND NAME' IS ACTIVE",name, "insertProduct active").contains();
        waitForElementVisible(input_name,10);
        setElementText(input_name,product_name);
        waitForElementVisible(pick_item,10);
        clickElement(pick_item);
        waitForElementVisible(add_more_product_tab,10);
        logScreenshot(driver,module);
    }

    public void AddThirdItem(){
        String product_name = (String) input.get(PRODUCT_TYPE3.get());
        clickElement(add_more_product_tab);
        String name = product_name_input.getDriver().findElement((By)product_name_input.getElementLocator()).getAttribute("class");
        new Validation("VERIFY TAB 'PRODUCT CODE AND NAME' IS ACTIVE",name, "insertProduct active").contains();
        waitForElementVisible(input_name,10);
        setElementText(input_name,product_name);
        waitForElementVisible(pick_item,10);
        clickElement(pick_item);
        waitForElementVisible(add_more_product_tab,10);
        logScreenshot(driver,module);
    }

    public void GetPricesOfProduct(){
        ArrayList<String> arr = new ArrayList<>();
        List<WebElement> elements = driver.findElements((By)find_element.getElementLocator());
        for (WebElement element : elements) {
            String pocet = element.getText();
            arr.add(pocet);
        }
        String i = arr.get(0).replaceAll("€","").replaceAll("&nbsp;","");
        String j = arr.get(1).replaceAll("€","").replaceAll("&nbsp;","");
        String k = arr.get(2).replaceAll("€","").replaceAll("&nbsp;","");
        Report.logInfoMessage(i);Report.logInfoMessage(j);Report.logInfoMessage(k);
        double price1,price2,price3,overall,factor,price9;price1 = Double.parseDouble(i);price2 = Double.parseDouble(j);
        price3 = Double.parseDouble(k);overall = price1+price2+price3;factor = 1e2F;price9 = Math.round(overall * factor) / factor;

        String sum_price = String.format("%.2f", price9);
        String Last_price = getElementText(last_price).replaceAll("€","").replaceAll("&nbsp;","");

        new Validation("VERIFY THE WHOLE PRICE OF PRODUCTS", sum_price + " €",Last_price + " €").stringEquals();

    }

    public void RemoveOneProduct(){
        String product_name = (String) input.get(PRODUCT_REMOVED.get());
        ALZA4_Page page = new ALZA4_Page(product_name);
        Element<?> dialog = page.getRemoveButton(driver);
        waitForElementVisible(dialog,10);
        clickElement(dialog);
        Element<?> remove = page.getConfirmRemove(driver);
        waitForElementVisible(remove,10);
        clickElement(remove);
        Element<?> name = page.getProductName(driver);
        verifyElementIsPresent(name);
    }

    public void ComparePriceofProduct(){
        List<WebElement> elements = driver.findElements((By)find_element.getElementLocator());
        ArrayList<String> aaa = new ArrayList<>();
        for (WebElement element : elements) {
            String pocet = element.getText();
            aaa.add(pocet);
        }
        Report.logInfoMessage(aaa.get(0));Report.logInfoMessage(aaa.get(1));

        String i = aaa.get(0).replaceAll("€","").replaceAll("&nbsp;","");
        String j = aaa.get(1).replaceAll("€","").replaceAll("&nbsp;","");

        double price1,price2,overall,factor,price9;price1 = Double.parseDouble(i);price2 = Double.parseDouble(j);
        overall = price1+price2;factor = 1e2F;
        price9 = Math.round(overall * factor) / factor;

        String sum_price = String.format("%.2f", price9);
        String Last_price = getElementText(last_price).replaceAll("€","").replaceAll("&nbsp;","");

        new Validation("VERIFY PRICE AFTER REMOVE ONE PRODUCT", sum_price + " €",Last_price + " €").stringEquals();

    }






}

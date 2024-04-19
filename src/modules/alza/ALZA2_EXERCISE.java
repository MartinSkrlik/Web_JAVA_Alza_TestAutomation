package modules.alza;

import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.alza.ALZA2_Page;
import utility.Report;
import utility.Validation;
import java.util.Map;

import static pages.alza.ALZA2_Page.Alza2_Page.*;
import static utility.Constant.InputKeys.DRIVER;
import static utility.Constant.Input_ALZA2_TC.*;
import static utility.Constant.Input_ALZA_TC.PHONE_NAME;

public class ALZA2_EXERCISE extends AbstractModule implements IWebPageModule {


    private String module;
    private String title;

    private Element<?> page_title = PAGE_TITLE.getElement();
    private Element<?> i_understand_btn = I_UNDERSTAND_BTN.getElement();
    private Element<?> search_input = SEARCH_INPUT.getElement();
    private Element<?> search_btn = SEARCH_BTN.getElement();
    private Element<?> item_name = ITEM_NAME.getElement();
    private Element<?> item_description = ITEM_DESCRIPTION.getElement();
    private Element<?> item_price = ITEM_PRICE.getElement();
    private Element<?> img_text = IMG_TEXT.getElement();
    private Element<?> close_button = CLOSE_BUTTON.getElement();

    public ALZA2_EXERCISE(Map<String, Object> input) {

        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        this.title = input.get(TITLE.get()) != null ? (String) input.get(TITLE.get()) : "-";

        initializeElements(page_title,i_understand_btn,search_input,search_btn,item_name,item_description,item_price,
                img_text,close_button);
    }

    @Override
    public void waitForPageLoad() {
        waitForElementDisplayed(page_title, 10);
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
    }

    public void AcceptPopUPAndSearchForItem(){
        String product_name = (String) input.get(PRODUCT_NAME.get());
        Element<?> pop_up = i_understand_btn;
        if (verifyElementIsPresent(pop_up)) {
            clickElement(pop_up);
            logScreenshot(driver, module);}
        Element<?> input = search_input;
        waitForElementVisible(input,10);
        setElementText(input,product_name);
        Element<?> search = search_btn;
        clickElement(search);

    }

    public void ChooseProductAndCompareDetails(){
        String phone_name = (String) input.get(PRODUCT_NAME.get());
        ALZA2_Page page = new ALZA2_Page(phone_name);
        Element<?> name1 = page.getModelName(driver);
        String name_product1 = getElementText(name1).trim();
        Element<?> description1 = page.getModelDescription(driver);
        String description_product1 = getElementText(description1).trim();
        String veta1 = description_product1.trim();
        Element<?> price1 = page.getModelPrice(driver);
        String price_product1 = getElementText(price1).replace("&nbsp;"," ");
        logScreenshot(driver, module);
        clickElement(name1);
        Element<?> name2 = item_name;
        Element<?> pop_up = img_text;
        Element<?> close_btn = close_button;
        if (verifyElementIsPresent(pop_up)){logScreenshot(driver,module);clickElement(close_btn);}
        waitForElementVisible(name2, 10);
        String name_product2 = getElementText(name2).trim();
        Element<?> description2 = item_description;
        String description_product2 = getElementText(description2).trim();
        String veta2 = description_product2.trim();
        Element<?> price2 = item_price;
        String price_product2 = getElementText(price2).replace("&nbsp;"," ");
        logScreenshot(driver,module);

        new Validation("VERIFY MODEL NAME", name_product1, name_product2).stringEquals();
        new Validation("VERIFY MODEL DESCRIPTION", veta1, veta2).contains();
        new Validation("VERIFY MODEL PRICE", price_product1, price_product2).stringEquals();
    }

    public void GoBackToPreviousPage(){
        driver.navigate().back();
    }

    


}

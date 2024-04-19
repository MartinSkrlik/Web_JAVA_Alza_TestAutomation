package modules.alza;

import org.openqa.selenium.By;
import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import org.openqa.selenium.WebDriver;
import pages.alza.ALZA3_Page;
import utility.Constant;
import utility.Report;
import utility.Validation;

import java.util.HashMap;
import java.util.Map;
import static pages.alza.ALZA3_Page.Alza3_Page.*;
import static utility.Constant.InputKeys.DRIVER;
import static utility.Constant.Input_ALZA3_TC.PRODUCT_NAME;
import static utility.Constant.Input_ALZA3_TC.TITLE;
import static utility.Constant.Input_ALZA3_TC.*;

public class ALZA3_EXERCISE extends AbstractModule implements IWebPageModule {
    static HashMap<String, String> elementMapper = new HashMap<>();

    private String module;
    private String title;

    private Element<?> page_title = PAGE_TITLE.getElement();
    private Element<?> i_understand_btn = I_UNDERSTAND_BTN.getElement();
    private Element<?> search_input = SEARCH_INPUT.getElement();
    private Element<?> search_btn = SEARCH_BTN.getElement();
    private Element<?> item_name = ITEM_NAME.getElement();
    private Element<?> top_rated_tab = TOP_RATED_TAB.getElement();
    private Element<?> name_product = NAME_PRODUCT.getElement();
    private Element<?> top_rated_selected = TOP_RATED_SELECTED.getElement();
    private Element<?> title_product = NAME_PRODUCT.getElement();
    private Element<?> price_product = PRICE_PRODUCT.getElement();
    private Element<?> favourite_checkbox = FAVOURITE_CHECKBOX.getElement();
    private Element<?> favourite_icon = FAVOURITE_ICON.getElement();
    private Element<?> availability_product = AVAILABILITY_PRODUCT.getElement();
    private Element<?> favourite_tab = FAVOURITE_TAB.getElement();
    private Element<?> name_favourite = NAME_FAVOURITE.getElement();
    private Element<?> price_favourite = PRICE_FAVOURITE.getElement();
    private Element<?> availability_favourite = AVAILABILITY_FAVOURITE.getElement();
    private Element<?> favourite_dialog = FAVOURITE_DIALOG.getElement();
    private Element<?> minus_btn = MINIUS_BTN.getElement();

    public ALZA3_EXERCISE(Map<String, Object> input) {

        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        this.title = input.get(TITLE.get()) != null ? (String) input.get(TITLE.get()) : "-";

        initializeElements(page_title,i_understand_btn,search_input,search_btn,item_name,top_rated_tab,
                name_product,top_rated_selected,title_product,price_product,favourite_checkbox,
                favourite_icon,availability_product,favourite_tab,name_favourite,price_favourite,
                availability_favourite,favourite_dialog,minus_btn);

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

    public void SelectTopRated(){
        waitForElementVisible(top_rated_tab,10);
        clickElement(top_rated_tab);
        Element<?> top = top_rated_selected;
        String meno = top.getDriver().findElement((By)top_rated_selected.getElementLocator()).getAttribute("class");
        new Validation("VERIFY IF TOP-ROTATED IS SELECTED", meno, "state-active").contains();
        logScreenshot(driver, module);
    }

    public void ChooseFirstProductAndMemoryDetails(){
        String phone_name = (String) input.get(PRODUCT_TYPE.get());
        ALZA3_Page page = new ALZA3_Page(phone_name);
        Element<?> name1 = page.getModelName(driver);
        waitForElementVisible(name1,10);
        String name = getElementText(name1);
        elementMapper.put("meno",name);
        Element<?> price1 = page.getModelDPrice(driver);
        String price = getElementText(price1).replace("&nbsp;","");
        elementMapper.put("price",price);
        Element<?> availability1 = page.getModelAvailability(driver);
        String availability = getElementText(availability1).replace("&nbsp;","");
        elementMapper.put("availability",availability);
    }

    public void PickProductToFavourteList(){
        String phone_name = (String) input.get(PRODUCT_TYPE.get());
        ALZA3_Page page = new ALZA3_Page(phone_name);
        Element<?> name1 = page.getModelFavourites(driver);
        scrollElementIntoView(name1);
        waitForPageLoad();
        clickElement(name1);
        waitForElementVisible(favourite_checkbox,10);
        clickElement(favourite_checkbox);
        logScreenshot(driver, module);
        refreshPage(driver);
        waitForElementVisible(favourite_icon,10);

    }

    public void OpenFavouriteList(){
        scrollElementIntoView(favourite_icon);
        clickElement(favourite_icon);
        waitForElementVisible(favourite_tab,10);
    }

    public void CheckCountOfProduct(){
        waitForElementVisible(minus_btn,10);
        clickElement(minus_btn);

    }

    public void CompareDetails(){
        waitForElementVisible(name_favourite,10);
        String name = getElementText(name_favourite);
        elementMapper.put("meno1",name);
        String price = getElementText(price_favourite).replace("&nbsp;","");
        elementMapper.put("price1",price);
        String availability = getElementText(availability_favourite).replace("&nbsp;","");
        elementMapper.put("availability1",availability);

        new Validation ("VERIFY NAME OF PRODUCT", elementMapper.get("meno"),elementMapper.get("meno1")).stringEquals();
        new Validation ("VERIFY PRICE OF PRODUCT", elementMapper.get("price"),elementMapper.get("price1")).stringEquals();
        new Validation ("VERIFY AVAILABILITY OF PRODUCT", elementMapper.get("availability"),elementMapper.get("availability1")).stringEquals();

        logScreenshot(driver, module);
    }



}

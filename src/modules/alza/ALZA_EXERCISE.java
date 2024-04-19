package modules.alza;

import generic.Element;
import modules.AbstractModule;
import modules.IWebPageModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.alza.ALZA_Page;
import utility.Report;
import utility.Validation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import static pages.alza.ALZA_Page.Alza_Page.*;
import static utility.Constant.InputKeys.DRIVER;
import static utility.Constant.Input_ALZA_TC.*;
import static utility.Constant.Input_RAHUL_TC.NUMBER_OF_ROWS;
public class ALZA_EXERCISE extends AbstractModule implements IWebPageModule {

    private String module;
    private String title;

    private Element<?> page_title = PAGE_TITLE.getElement();
    private Element<?> i_understand_btn = I_UNDERSTAND_BTN.getElement();
    private Element<?> search_input = SEARCH_INPUT.getElement();
    private Element<?> search_btn = SEARCH_BTN.getElement();
    private Element<?> top_rated_tab = TOP_RATED_TAB.getElement();
    private Element<?> model_name = MODEL_NAME.getElement();
    private Element<?> proceed_btn = PROCEED_BTN.getElement();
    private Element<?> main_item = MAIN_ITEM.getElement();
    private Element<?> cart_tab = CART_TAB.getElement();
    private Element<?> price = PRICE.getElement();
    private Element<?> continue_btn = CONTINUE_BTN.getElement();
    private Element<?> payment_tab = PAYMANET_TAB.getElement();
    private Element<?> checkbox_btn = CHECKBOX_BTN.getElement();
    private Element<?> popup_title = POPUP_TITLE.getElement();
    private Element<?> alza_dialog = ALZA_DIALOG.getElement();
    private Element<?> delivery_prcie = DELIVERY_PRICE.getElement();
    private Element<?> confirm_btn = CONFIRM_BTN.getElement();
    private Element<?> dispatch_item = DISPATCH_ITEM.getElement();
    private Element<?> main_shop_price = MAIN_SHOP_PRICE.getElement();
    private Element<?> payment_checkbox = PAYMENT_CHECKBOX.getElement();
    private Element<?> payment_price = PAYMENT_PRICE.getElement();
    private Element<?> address_tab = ADDRESS_TAB.getElement();
    private Element<?> item_price = ITEM_PRICE.getElement();
    private Element<?> final_price = FINAL_PRICE.getElement();

    public ALZA_EXERCISE(Map<String, Object> input) {

        this.module = getModuleName();
        this.input = input;
        this.driver = (WebDriver) input.get(DRIVER.get());
        this.title = input.get(TITLE.get()) != null ? (String) input.get(TITLE.get()) : "-";

        initializeElements(page_title,i_understand_btn,search_input,search_btn,top_rated_tab,model_name,
                proceed_btn,main_item,cart_tab,price,continue_btn,payment_tab,checkbox_btn,alza_dialog,popup_title,
                delivery_prcie,confirm_btn,dispatch_item,main_shop_price,payment_checkbox,payment_price,
                address_tab,item_price,final_price);
    }

    @Override
    public void waitForPageLoad() {
        waitForElementDisplayed(page_title, 10);
        addCurrentUrlToReport(driver);
        logScreenshot(driver, module);
    }

    public void SearchForPhoneAndSelectTopRated(){
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
        Element<?> top_rated = top_rated_tab;
        waitForElementVisible(top_rated,10);
        clickElement(top_rated);
        logScreenshot(driver, module);
    }

    public void ChooseProductAndRememberNameAndPrice(){
        String phone_name = (String) input.get(PHONE_NAME.get());
        ALZA_Page page = new ALZA_Page(phone_name);
        Element<?> name = page.getModelName(driver);
        waitForElementVisible(name,10);
        String model_name = getElementText(name);
        Element<?> price = page.getModelPrice(driver);
        String item_price = getElementText(price).replaceAll("&nbsp;€","");
        Report.logInfoMessage("Model price is " + item_price + " €");
        Report.logInfoMessage("Model name is " + model_name);
    }

    public void ClickBuy(){
        String phone_name = (String) input.get(PHONE_NAME.get());
        ALZA_Page page = new ALZA_Page(phone_name);
        Element<?> buy_btn = page.getBuy(driver);
        scrollElementIntoView(buy_btn);
        logScreenshot(driver,module);
        clickElement(buy_btn);
    }

    public void VerifyProductNameAndProceedButton(){
        String phone_full_name = (String) input.get(FULL_NAME.get());
        String proceed_txt = (String) input.get(TEXT.get());
        ALZA_Page Page = new ALZA_Page(proceed_txt);
        Element<?> proceed = Page.getProceedToCart(driver);
        Element<?> name_model = model_name;
        waitForElementVisible(name_model,10);
        new Validation("VALIDATION 'Product added to Cart' IS VISIBLE", proceed_txt,getElementText(proceed)).stringEquals();
        new Validation("VALIDATION FULL MODEL NAME", phone_full_name,getElementText(name_model)).stringEquals();
        logScreenshot(driver, module);
    }

    public void ClickProceedToCheckout(){
        Element<?> proceedTo_btn = proceed_btn;
        clickElement(proceedTo_btn);
        Element<?> model_name = main_item;
        waitForElementVisible(model_name,10);
        logScreenshot(driver, module);
    }

    public void VerifyTabModelNameAndPriceToPay(){
        Element<?> cart_number = cart_tab;
        verifyElementIsPresent(cart_number);
        String phone_full_name = (String) input.get(FULL_NAME.get());
        Element<?> name_model = main_item;
        new Validation("VALIDATION FULL MODEL NAME", phone_full_name,getElementText(name_model)).stringEquals();
        Element<?> model_price = price;
        new Validation("VALIDATION MODEL PRICE", "2.39",getElementText(model_price).replaceAll("&nbsp;€","")).stringEquals();
        //spytat sa na validaciu (String vo vnutri funkcie)
        //spytat sa na prepozivanie funkcii
    }
    public void ClickContinue(){
        Element<?> continue_button = continue_btn;
        waitForElementVisible(continue_button,10);
        clickElement(continue_button);
        logScreenshot(driver, module);
        }

    public void VerifyTabAndModelName(){
        String phone_name = (String) input.get(FULL_NAME.get());
        Element<?> payment_number = payment_tab;
        waitForElementVisible(payment_number,10);
        verifyElementIsPresent(payment_number);
        ALZA_Page page = new ALZA_Page(phone_name);
        Element<?> product_txt = page.getName(driver);
        String number = " 1x ";
        String whole_name = number + phone_name;
        new Validation("VERIFY MODEL NAME IS VISIBLE",getElementText(product_txt).trim(), whole_name.trim()).stringEquals();
    }

    public void SelectBratislavaMainShop(){
        Element<?> checkbox = checkbox_btn;
        waitForElementVisible(checkbox,10);
        clickElement(checkbox);
        Element<?> dialog = alza_dialog;
        waitForElementVisible(dialog,10);
        logScreenshot(driver,module);
    }

    public void VerifyTitleCheckboxAndPrice(){
        String dispatch_price = (String) input.get(DISPATCH_PRICE.get());
        String checkbox_title = (String) input.get(CHECKBOX_TITLE.get());
        String pop_up_title = (String) input.get(POP_UP_TITLE.get());
        ALZA_Page page = new ALZA_Page(checkbox_title);
        Element<?> pop_up = popup_title;
        Element<?> checkbox = page.getCheckboxName(driver);
        new Validation("VERIFY POP UP TITLE", getElementText(pop_up),pop_up_title).stringEquals();
        verifyIsSelected(checkbox);
        Element<?> price = delivery_prcie;
        new Validation("VERIFY DISPATCH PRICE", getElementText(price).replaceAll("&nbsp;"," "),dispatch_price).stringEquals();

    }

    public void SelectConfirm(){
        Element<?> confirm = confirm_btn;
        clickElement(confirm);
        Element<?> dispatch = dispatch_item;
        waitForElementVisible(dispatch,10);
        logScreenshot(driver,module);
    }

    public void VerifyMainShopIsSelected(){
        String checkbox_title = (String) input.get(DELIVRY_METHOD.get());
        ALZA_Page page = new ALZA_Page(checkbox_title);
        Element<?> checkbox = page.getCheckboxName(driver);
        waitForElementVisible(checkbox,10);
        verifyIsSelected(checkbox);
        logScreenshot(driver,module);
    }

    public void VerifyModelNameIsVisible(){
        String phone_name = (String) input.get(FULL_NAME.get());
        ALZA_Page page = new ALZA_Page(phone_name);
        Element<?> product_txt = page.getName(driver);
        String number = " 1x ";
        String whole_name = number + phone_name;
        new Validation("VERIFY MODEL NAME IS VISIBLE",getElementText(product_txt).trim(), whole_name.trim()).stringEquals();
        logScreenshot(driver,module);
    }

    public void VerifyDispatchPriceIsVisible(){
        String dispatch_price = (String) input.get(DISPATCH_PRICE.get());
        Element<?> price = main_shop_price;
        new Validation("VERIFY DISPATCH PRICE", getElementText(price).replaceAll("&nbsp;"," "),dispatch_price).stringEquals();
        logScreenshot(driver,module);

    }

    public void SelectPaymentMethod(){
        String payment = (String) input.get(PAYMENT_METHOD.get());
        ALZA_Page page = new ALZA_Page(payment);
        Element<?> payment_method = page.getPaymentMethod(driver);
        Element<?> payment_btn = payment_checkbox;
        clickElement(payment_btn);
        waitForElementVisible(payment_method,10);
        scrollElementToMiddleOfScreen(payment_method);
        logScreenshot(driver,module);
    }

    public void VerifyCreditCardPaymentIsForFree() {
        Element<?> payment = payment_price;
        //waitForElementVisible(payment, 10);
        sleep(3000);
        new Validation("VERIFY CREDIT CARD PAYMENT IS FOR FREE", getElementText(payment), "free").stringEquals();

    }

    public void SelectContinue(){
        Element<?> continue_button = continue_btn ;
        waitForElementVisible(continue_button,10);
        clickElement(continue_btn);
    }

    public void VerifyTabPaymentIsPresent(){
        Element<?> address = address_tab;
        waitForElementVisible(address,10);
        verifyElementIsPresent(address);
    }

    public void VerifyProductPrice(){
        String model_price = (String) input.get(MODEL_PRICE.get());
        Element<?> price = item_price;
        String value = getElementText(price);
        new Validation("VERIFY MODEL PRICE", value.replaceAll("&nbsp;"," "), model_price).stringEquals();
        logScreenshot(driver, module);
    }

    public void VerifyDeliveryMethod(){
        String delivery_method = (String) input.get(DELIVRY_METHOD.get());
        Element<?> method = dispatch_item;
        new Validation("VERIFY DELIVERY METHOD", getElementText(method), delivery_method).stringEquals();
        logScreenshot(driver, module);
    }

    public void VerifyPaymentMethod(){
        String payment = (String) input.get(PAYMENT_METHOD.get());
        ALZA_Page page = new ALZA_Page(payment);
        Element<?> payment_method = page.getPaymentMethod(driver);
        new Validation("VERIFY PAYMENT METHOD", getElementText(payment_method), payment).stringEquals();
        logScreenshot(driver, module);
    }

    public void VerifyTotalPrice(){
        String payment = (String) input.get(TOTAL_PRICE.get());
        String cena1 = getElementText(item_price).replaceAll("&nbsp;€","");
        String cena3 = cena1;
        String cena2 = getElementText(main_shop_price).replaceAll("&nbsp;€","");
        double str1 = Double.parseDouble(cena3);double str2 = Double.parseDouble(cena2);
        double str3 = str1 + str2; String cena = String.valueOf(str3);
        new Validation("VERIFY FINAL PRODUCT PRICE", cena + " €", payment).stringEquals();
    }

}

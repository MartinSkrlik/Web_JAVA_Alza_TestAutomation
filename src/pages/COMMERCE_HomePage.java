package pages;

import org.openqa.selenium.By;
import generic.Element;
import generic.IPageObject;

public class COMMERCE_HomePage {
    
    public enum Home_Page implements IPageObject {
        
        HOME_PAGE_HEADER
        (By.xpath("//nav[@*='lgi-navigation']")),
        BUNDLE_TAB
        (By.xpath("//li[contains(@*,'level1')]/*[contains(@*,'/internet-tv/')]/..")),
        BUNDLE_TAB_SELECTED
        (By.xpath("//li[contains(@*,'level1')]/*[contains(@*,'/internet-tv/')]/parent::*[contains(@class,'active')]")),
        ;
        
        By findBy;
        
        private Home_Page (By findBy) {
            this.findBy = findBy;
        }
        
        @Override
        public By getLocator() {
            return findBy;
        }
        
        @Override
        public Element<Home_Page> getElement(String... msg) {
            return new Element<Home_Page>(getLocator(), getDescription(msg));
        }
    }
    
    public enum Bundle_Page implements IPageObject {
        
        BUNDLE_PRODUCTS
        (By.xpath("//*[@id='rfs_promo_holder_duo']/*[contains(@id,'duo_column')][not(@class='hidden')]")),
        ADDRESS_CHECK_SECTION
        (By.xpath("//*[@id='namieru_holder']")),
        CITY_TEXTBOX
        (By.id("namieru_city")),
        STREET_TEXTBOX
        (By.id("namieru_street")),
        HOUSE_NUMBER_TEXTBOX
        (By.id("namieru_number")),
        CHECK_ADDRESS_BUTTON
        (By.id("namieru_submit")),
        SERVICES_AVAILABLE_ON_ADDRESS_MESSAGE
        (By.xpath("//*[@id='namieru_holder'][contains(@class,'result-ok')]")),

        PRODUCT_PRICE_TEXT
        (By.xpath(".//*[contains(@id,'duo_price')]//*[contains(@class,'xl')]")),
        ORDER_BUTTON
        (By.xpath(".//*[contains(@class,'button-buy')]")),   
        ;
        
        By findBy;
        
        private Bundle_Page (By findBy) {
            this.findBy = findBy;
        }
        
        @Override
        public By getLocator() {
            return findBy;
        }
        
        @Override
        public Element<Bundle_Page> getElement(String... msg) {
            return new Element<Bundle_Page>(getLocator(), getDescription(msg));
        }
    }
    
    
    private String binding;
    private String internet;
    private String tv;

    
    public COMMERCE_HomePage(String binding) {
        this.binding = binding;
    }
    
    public COMMERCE_HomePage(String internet, String tv) {
        this.internet = internet;
        this.tv = tv;
    }
    
    
    public Element<?> getBundleProductLocator() {
        By locator = By.xpath("//*[@id='rfs_promo_holder_duo']//*[contains(@id,'block')][.//*[text()='" 
                + internet + 
                "']][.//*[text()='" 
                + tv + 
                "']]");
        
        return new Element<>(locator, String.format(BUNDLE, internet, tv));
    }
    
    public By getBindingPeriodButtonLocator() {
        return By.xpath("//*[contains(@id,'fix_switch')]//*[text()='" + binding + "']");
    }
    
    private final String BUNDLE = "Bundle Section -> Bundle -> %s & %s";
}
package pages;

import org.openqa.selenium.By;
import generic.Element;
import generic.IPageObject;

public class COMMERCE_OrderPage {
    
    public enum Contact_Page implements IPageObject {
        
        CONTACT_INFORMATION_SECTION
        (By.id("dop_form_v3")),
        NAME_AND_SURNAME_TEXTBOX
        (By.id("dop_name")),
        EMAIL_TEXTBOX
        (By.id("dop_email")),
        PHONE_TEXTBOX
        (By.id("dop_phone")),
        CITY_TEXTBOX
        (By.id("dop_city")),
        STREET_TEXTBOX
        (By.id("dop_street")),
        HOUSE_NUMBER_TEXTBOX
        (By.id("dop_number")),
        MARKETING_OPTION_1_CHECKBOX
        (By.xpath("//*[@id='suhlas_edm']/following-sibling::*")),
        MARKETING_OPTION_2_CHECKBOX
        (By.id("//*[@id='suhlas_dirmrk']/following-sibling::*")),
        CONTINUE_TO_ORDER_BUTTON
        (By.xpath("//input[@type='submit']")),
        ;
        
        By findBy;
        
        private Contact_Page (By findBy) {
            this.findBy = findBy;
        }
        
        @Override
        public By getLocator() {
            return findBy;
        }
        
        @Override
        public Element<Contact_Page> getElement(String... msg) {
            return new Element<Contact_Page>(getLocator(), getDescription(msg));
        }
    } 
}
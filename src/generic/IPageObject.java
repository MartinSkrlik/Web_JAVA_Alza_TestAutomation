package generic;

import org.openqa.selenium.By;

public interface IPageObject {
    
    By getLocator();
    Element<?> getElement(String... msg);
    
    public default String getDescription(String... message) {
        String base = getClass().getSimpleName().replace("_", " ") + " -> " + toString().toLowerCase().replace("_", " ");
        String messageText = message.length > 0 ? " -> " + message[0]: "";
        return base + messageText;
     }
}
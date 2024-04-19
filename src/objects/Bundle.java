package objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Bundle {
    
    private String expectedPrice;
    private String actualPrice;

    private String internet;
    private String tv;
    private String bindingPeriod;
    
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this);
    }  
}
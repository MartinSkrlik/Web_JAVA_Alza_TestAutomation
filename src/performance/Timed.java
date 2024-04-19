package performance;

import java.lang.annotation.*;
import utility.Constant.TimedKeys;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Timed {  
    TimedKeys step();
}
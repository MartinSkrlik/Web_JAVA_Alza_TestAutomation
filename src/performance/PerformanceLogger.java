package performance;

import static utility.Constant.PerformanceKeys.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import generic.Element;
import utility.FrameworkActions;

@Aspect
public class PerformanceLogger {
    
    static Map<Integer, Map<String,Object>> allSteps = new HashMap<>();
      
    @Around("execution(* modules..*.*(..)) && @annotation(timed)")
    public Object around(ProceedingJoinPoint point, performance.Timed timed) throws Throwable {
        
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        long executionTime = end - start;
        String elementName;
		Element<?> element;
       
        String stepType = timed.step().toString();
        String methodName = point.getSignature().toShortString();
        
        if (argumentIsElement(point)) {
        	element = (Element<?>) point.getArgs()[0];
        	elementName = element.getDescription();
        }
        else {
        	elementName = ELEMENT_NULL.get();
        }
        
        Map<String,Object> steps = new HashMap<>();
        steps.put(DESCRIPTION.get(), getMethodName(methodName) + " - " + elementName);   
        steps.put(TYPE.get(), stepType);
        steps.put(START.get(), new Timestamp(start));
        steps.put(END.get(), new Timestamp(end));
        steps.put(DURATION.get(), executionTime);
        
        allSteps.put(allSteps.size() + 1, steps);
        return result;
    }
    
    private String getMethodName(String fullText) {
        String methodNameRegex = "(?<=\\.).+?(?=\\()";
        String method = FrameworkActions.cropString(methodNameRegex, fullText);
        String splitName = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(method),' ');
        return StringUtils.capitalize(splitName);
    }
    
    public static Map<Integer,?> getStepMap() {
        return allSteps;
    }
    
    public static void clearSteps() {
    	allSteps.clear();
    }
    
    private boolean argumentIsElement(ProceedingJoinPoint point) {
    	return point.getArgs()[0].toString().toLowerCase().contains("element");
    }
}
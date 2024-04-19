package performance;

import static utility.Constant.InputKeys.*;
import static utility.Queries.Test_steps.*;
import static utility.Queries.Test_result.*;
import static utility.Constant.PerformanceKeys.*;
import utility.DatabaseUtils;
import utility.Exceptions;
import utility.Log;
import java.util.Map;
import config.Config.Database;

public class RoboSteps extends Exceptions {   
    
    private Map<String, Object> input;
    private Map<Integer, Map<String,Object>> allSteps;
    private DatabaseUtils db;
    private String run;
    private String result;
    
    @SuppressWarnings("unchecked")
    public RoboSteps(Map<String, Object> input) {
        this.input = input;
        this.allSteps = (Map<Integer, Map<String, Object>>) input.get(ALL_STEPS.get());
    }
    
    public void execute() {  
        try {
        	this.db = new DatabaseUtils(Database.ROBO); 
        	this.run = System.getProperty(ROBORUN.get());
        	this.result = (String) input.get(GLOBAL_RESULT_ID.get());
        	
            sendToRoboStepsTable();
            updateStepsLoadedInRoborunTable();
            PerformanceLogger.clearSteps();
        } 
        catch (Exception e) {
            processException(e);
        }   
    }
    
    private void sendToRoboStepsTable() {
  
    	int index = 1;
           
    	while (index <= allSteps.size()) {
    		Log.info(LINE_BREAK);
    		
    		String portal = (String) input.get(PORTAL.get());
    		String flow   = (String) input.get(FLOW.get());
    		Map<String,Object> steps = allSteps.get(index);
    		steps.put(STEP_ID.get(), index);
               
    		steps.entrySet().forEach
    			(entry -> {Log.info(entry.getKey() + " : " + entry.getValue());});
               
            db.insertQuery(String.format(INSERT_STEPS.get(), 
            		run,
            		result,
            		System.getProperty(TESTSET.get()),
            		input.get(TEST_CASE_NR.get()),
            		portal + " " + flow,
            		index,
            		steps.get(DESCRIPTION.get()),
            		steps.get(START.get()),
            		steps.get(END.get()),
            		steps.get(DURATION.get()),
            		steps.get(TYPE.get()),
            		TBD_STATUS));  		
               index ++;
               
               Log.info(LINE_BREAK);
           }
    }
    
    private void updateStepsLoadedInRoborunTable() {
    	db.insertQuery(String.format(UPDATE_STEPS_LOADED.get(), run, result));
    }
    
    //private static final String TBD_COMMENT = null;
    private static final String TBD_STATUS = "Info";
    private static final String LINE_BREAK = "=======================================================";
}
package dataProvider;

import static config.Directories.DATA_DIR;
import static utility.Constant.Numbering.*;
import static java.util.Comparator.*;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import com.google.common.collect.Iterables;
import utility.Log;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlSuite;

public class FileDataProvider {

    final static List<String[]> rawlist = new ArrayList<>();
    final static List<String[]> list    = new ArrayList<>();
    final static List<String> tcList    = new ArrayList<>();
    
    @DataProvider(name = "File")
    public static Iterator<String[]> testData(Method method, ITestContext context) throws Exception {       
        for (final String line: 
            Files.readAllLines(Paths.get(getNameOfTestList(context)), 
                               StandardCharsets.UTF_8))
        	skipAllEmptyAndCommentedLines(line);
            mergeMultilineTestIntoSingleLine();
            checkIfAllTcIdIsUnique();
        return list.iterator();
    }
    
    private static void skipAllEmptyAndCommentedLines(String line) {
        if (!line.startsWith("#") && 
            !line.isEmpty()) {
            Log.info("Input data: " + line);
            rawlist.add(new String[]{line});
            tcList.add(line);
        }
    }
 
 // if one Test case is in several lines (+) merge it into one    
    private static void mergeMultilineTestIntoSingleLine() {
        for (String[] listItem: rawlist) {
            if (listItem[0].startsWith("+")) {

                String[] parentTc = Iterables.getLast(list);
                int indexOfParentTc = list.indexOf(parentTc);
                String[] mergedTc = new String[] {parentTc[0] + "," 
                        + listItem[0].
                                      toString().
                                      replaceAll("\\+","")};    
                Log.info(mergedTc[0]);
                list.set(indexOfParentTc, mergedTc);
            }
            else {
                list.add(listItem);
           }
        }
    }
    
//    Check if test set contains duplicates of test case IDs
    private static void checkIfAllTcIdIsUnique() {
    	List<String> tcIdList   = new ArrayList<>();
    	Set<String> tcIdHashSet = new HashSet<>();
    	for (String tcListItem : tcList) {
    		String[] array = tcListItem.split("\\,", -1);
    		tcIdList.add(array[TEST_CASE_ID_INPUT_INDEX.get()]);
    		tcIdHashSet.add(array[TEST_CASE_ID_INPUT_INDEX.get()]);
    	}
    	if(tcIdHashSet.size() < tcIdList.size()) {
    		Log.error("The testset contains duplicates of testcase IDs. Please check the numbering of testcases");
            Assert.fail("The testset contains duplicates of testcase IDs. Please check the numbering of testcases");
        }
    }

 // Test list name is taken during runtime. 
 // This way you can have multiple test lists for single test case
    
    private static String getNameOfTestList(ITestContext context) {
        XmlSuite suiteName = context.getCurrentXmlTest().getSuite(); 
        String testngFilePath = suiteName.getFileName();
        String testngFile = FilenameUtils.getName(testngFilePath).replace("xml", "");
        
        File testDataFolder = new File(DATA_DIR.get());
        String fileName = "";
        
        for (File testDataFile : testDataFolder.listFiles()) {
            String name = testDataFile.getName();
            if (name.startsWith(testngFile)) {
                fileName = testDataFile.getAbsolutePath();
                break;
            }
        }
        return fileName;
    }
 
  // Returns number of test cases in current test suite
    
    public int getNumberOfProcessedLines() {
        return list.size();
    }

  // Returns environment of test cases in current test suite. 
  // Multiple environments (e.g. in a suite with both DEV and QA test cases) are in alphabetical order and separated by comma.
    
    public String getEnvironments() {
        List<String> envAll = new ArrayList<>();
        
        list.stream().
             forEach(i -> { String e = i[0].split("\\,", -1)[1];
                                       envAll.add(e);});        
        String envDistinct = envAll.stream().
                                    distinct().
                                    sorted(naturalOrder()).
                                    collect(Collectors.joining(", "));
        return envDistinct;
    }
}
package cases;

public class TestCaseFactory {
    
    public TestCases getTestCase(String line) {
        
        String[] input = line.split("\\,", -1);
        String portal  = input[0];
        String flow    = input[2];
              
        if (portal.equals("UPC") && flow.equals("ORDER PACKAGE POSITIVE")) {
            return new COMMERCE_OrderPackagePositive(line);
        }
        if (portal.equalsIgnoreCase("Saucedemo") && flow.equalsIgnoreCase("LOGIN")) {
            return new SAUCEDEMO_LoginTC(line);
        }
        if (portal.equalsIgnoreCase("Rahul") && flow.equalsIgnoreCase("Exercise_1")) {
            return new RAHUL_Exercise1_TC(line);
        }
        if (portal.equalsIgnoreCase("Alza") && flow.equalsIgnoreCase("Exercise_1")) {
            return new ALZA_Exercise1_TC(line);
        }
        if (portal.equalsIgnoreCase("Alza2") && flow.equalsIgnoreCase("Exercise_1")) {
            return new ALZA2_Exercise1_TC(line);
        }
        if (portal.equalsIgnoreCase("Alza3") && flow.equalsIgnoreCase("Exercise_1")) {
            return new ALZA3_Exercise1_TC(line);
        }
        if (portal.equalsIgnoreCase("Alza4") && flow.equalsIgnoreCase("Exercise_1")) {
            return new ALZA4_Exercise1_TC(line);
        }

        else {
            return new UnknownTest();
        }
    }
}
package REXSH.REXAUTO;

import REXSH.REXAUTO.Component.AppAction.BaseAction;
import REXSH.REXAUTO.LocalException.PageTitleException;
import Utility.readProperity;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static REXSH.REXAUTO.Component.Driver.ScreenShot.ScreenShot;


/**
 * Unit test for simple App.
 */
public class AppTestOnProjectExpenseWithType extends AppTestTemplate {
    private AppiumDriver driver;

    String autoOSType = readProperity.getProValue("autoRunningOSType");
    String extTEAccount = readProperity.getProValue("TEAccount");
    String extTEAccountPSW = readProperity.getProValue("TEAccountPSW");
    String accontFromXls = readProperity.getProValue(this.getClass().getSimpleName() + "_account");
    String accontPSWFromXls = readProperity.getProValue(this.getClass().getSimpleName() + "_accountPSW");

    String mobileOS = readProperity.getProValue("targetMobileOS");
    String deviceORsimulator = readProperity.getProValue("deviceORsimulator");
    String startdate = readProperity.getProValue("normal_startdate");
    String enddate = readProperity.getProValue("normal_enddate");
    String ab_startdate = readProperity.getProValue("abnormal_startdate");
    String ab_enddate = readProperity.getProValue("abnormal_enddate");
    Date date = new Date();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = format.format(date);
    DesiredCapabilities capabilities = new DesiredCapabilities();
    StringBuilder cPath = new StringBuilder().append(System.getProperty("user.dir")).append(File.separator).append("logs").append(File.separator).append(mobileOS).append(File.separator).append(this.getClass().getSimpleName()).append("_").append(time.replaceAll(":","_"));
    File pfile = new File(cPath.toString());
    Workbook XLSXwb = null;
    Sheet XLSXsheet = null;
    String testMethod4XLSX = null;
    Row headerRow = null;
    Row row4TCsingle;
    Cell cell4TCsingle;
    ArrayList<String> list4result = new ArrayList<String>();
    FileOutputStream theFileOutStream = null;
    int globalRowId = 0;
    int rownum = 1;
    public String port = "4723";
    String[] TestStepList_addFirstExpense = new String[]{"login", "createFirstExpense", "addExpense", "addExpenseEntry", "createFirstExpenseEntry_Check"};
    String[] TestStepList_addMoreCarRentalExpense = new String[]{"login", "addMoreExpense", "addExpense", "addExpenseEntry_basic", "addExpenseEntry_Car_part2", "addExpenseEntry_Check"};
    String[] TestStepList_addMoreAirExpense = new String[]{"login", "addMoreExpense", "addExpense", "addExpenseEntry_basic", "addExpenseEntry_Air_part2", "addExpenseEntry_Check"};
    String[] TestStepList_addMoreRailExpense = new String[]{"login", "addMoreExpense", "addExpense", "addExpenseEntry_basic", "addExpenseEntry_Rail_Date_part2", "addExpenseEntry_Check"};

    String[] TestStepList_addMoreCarRentalExpenseDate = new String[]{"login", "addMoreExpense", "addExpense", "addExpenseEntry_basic", "addExpenseEntry_Car_Date_part2", "addExpenseEntry_Check"};
    String[] TestStepList_addMoreAirExpenseDate = new String[]{"login", "addMoreExpense", "addExpense", "addExpenseEntry_basic", "addExpenseEntry_Air_Date_part2", "addExpenseEntry_Check"};
    String[] TestStepList_addMoreRailExpenseDate = new String[]{"login", "addMoreExpense", "addExpense", "addExpenseEntry_basic", "addExpenseEntry_Rail_Date_part2", "addExpenseEntry_Check"};
    @DataProvider
    public Object[][] addCarExpense() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "00::purpose 4 a::task FOR OT IN REX::Car Rental::description::9::ven::yes::fff::222t::ttt::addinfo", "regular", ""}, //purpose 4 a::task FOR OT IN REX::Meals::description::67
        };
    }

    @DataProvider
    public Object[][] addCarExpenseDate() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "00::purpose 4 a::task FOR OT IN REX::Car Rental::description::9::ven::yes::fff::222t::" + this.startdate + "::" + this.enddate + "::ttt::addinfo", "regular", ""}, //purpose 4 a::task FOR OT IN REX::Meals::description::67
                {this.accontFromXls, this.accontPSWFromXls, "00::purpose 4 a::task FOR OT IN REX::Car Rental::description::9::ven::yes::fff::222t::" + this.ab_startdate + "::" + this.ab_enddate + "::ttt::addinfo", "save error", ""}, //purpose 4 a::task FOR OT IN REX::Meals::description::67
        };
    }

    @DataProvider
    public Object[][] addAirExpense() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "00::purpose 4 a::task FOR OT IN REX::Car Rental::description::9::ven::yes::fff::222t::ttt::addinfo", "regular", ""}, //purpose 4 a::task FOR OT IN REX::Meals::description::67
        };
    }

    @DataProvider
    public Object[][] addRailExpense() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "00::purpose 4 a::task FOR OT IN REX::Car Rental::description::9::ven::yes::fff::222t::ttt::addinfo", "regular", ""}, //purpose 4 a::task FOR OT IN REX::Meals::description::67
        };
    }

    @DataProvider
    public Object[][] createFirstExpense() {
        return new Object[][]{
                {this.accontFromXls, this.accontPSWFromXls, "00::purpose123asd::task342043::TAXI::description::341", "regular", ""},
        };
    }

    public AppTestOnProjectExpenseWithType() throws FileNotFoundException {
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        File app = null;
        if (mobileOS.equalsIgnoreCase("Android")) {
            if (!autoOSType.toLowerCase().contains("windows")) {
                app = new File(("\\Users\\appledev131\\Downloads").replaceAll("\\\\", File.separator), "app-debug.apk");
            } else {
                app = new File("C:\\Paul", "app-debug.apk");
            }
        } else if (mobileOS.toLowerCase().startsWith("ios")) {
            app = new File(("\\\\Users\\\\appledev131\\\\Downloads\\\\").replaceAll("\\\\", File.separator), "REX.app");
            System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        }
        if (mobileOS.equalsIgnoreCase("Android")) {
            driver = super.setupAndroid(app, this.port);
        } else if (mobileOS.toLowerCase().equals("ios")) {
            if (deviceORsimulator.equalsIgnoreCase("device")) {
                driver = super.setupIOSdevice(app, this.port);
            } else if (deviceORsimulator.equalsIgnoreCase("simulator")) {
                driver = super.setupIOSsimulator(app, this.port);
            }
        } else if (mobileOS.toLowerCase().contains("ipad")) {
            if (deviceORsimulator.equalsIgnoreCase("device")) {
                driver = super.setupIPADdevice(app, this.port);
            } else if (deviceORsimulator.equalsIgnoreCase("simulator")) {
                driver = super.setupIPADsimulator(app, this.port);
            }
        }
        pfile.mkdir();
    }

    public void recover() throws Exception {
        driver.closeApp();
        driver.quit();
        Thread.sleep(5000);
        File app = null;
        if (mobileOS.equalsIgnoreCase("Android")) {
            if (!autoOSType.toLowerCase().contains("windows")) {
                app = new File(("\\Users\\appledev131\\Downloads").replaceAll("\\\\", File.separator), "app-debug.apk");
            } else {
                app = new File("C:\\Paul", "app-debug.apk");
            }
        } else if (mobileOS.toLowerCase().startsWith("ios")) {
            app = new File(("\\\\Users\\\\appledev131\\\\Downloads\\\\").replaceAll("\\\\", File.separator), "REX.app");
            System.out.println("System.getPre + user.dir : " + System.getProperty("user.dir"));
        }
        if (mobileOS.equalsIgnoreCase("Android")) {
            driver = super.recoverAndroid(app, this.port);
        } else if (mobileOS.toLowerCase().equals("ios")) {
            if (deviceORsimulator.equalsIgnoreCase("device")) {
                driver = super.recoverIOSdevice(app, this.port);
            } else if (deviceORsimulator.equalsIgnoreCase("simulator")) {
                driver = super.recoverIOSsimulator(app, this.port);
            }
        } else if (mobileOS.toLowerCase().contains("ipad")) {
            if (deviceORsimulator.equalsIgnoreCase("device")) {
                driver = super.setupIPADdevice(app, this.port);
            } else if (deviceORsimulator.equalsIgnoreCase("simulator")) {
                driver = super.setupIPADsimulator(app, this.port);
            }
        }
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        try {
            System.out.println("*******beforeMethod********");
            String nameOFmethod = Thread.currentThread().getStackTrace()[1].getMethodName();
            XLSXwb = new XSSFWorkbook();
            XLSXsheet = XLSXwb.createSheet("Test Record");
            testMethod4XLSX = null;
            final String[] titles = {
                    "TestCase Name", "Time", "input Para", "Except Result Mode", "Reality Result", "Status"};
            headerRow = XLSXsheet.createRow(0);
            Row row4TCsingle;
            Cell cell4TCsingle;
            ArrayList<String> list4result = new ArrayList<String>();
            FileOutputStream out = null;
            globalRowId = 0;
            rownum = 1;

            // set head row
            headerRow.setHeightInPoints(12.75f);
            for (int i = 0; i < titles.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(titles[i]);
            }
            globalRowId = 1;
            String XLSXfile = cPath + File.separator + "demotest" + "_Test Result.xlsx";
            theFileOutStream = new FileOutputStream(XLSXfile);
            testMethod4XLSX = Thread.currentThread().getStackTrace()[1].getMethodName();
        } catch (Exception e) {
            throw e;
        }
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("*******aftermethod********");
        String[] templist4r = null;
        try {
            for (int ind = 0; ind < list4result.size(); ind++) {
                String resultL = list4result.get(ind).toString();
                templist4r = resultL.split("#");
                Row row;
                Cell cell;
                row = XLSXsheet.createRow(rownum + globalRowId);
                for (int index = 0; index < templist4r.length; index++) {
                    cell = row.createCell(index);
                    cell.setCellValue(templist4r[index]);
                }
                rownum++;
            }
            globalRowId = globalRowId + list4result.size();
            XLSXwb.write(theFileOutStream);

            XLSXwb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test(dataProvider = "addCarExpense") // , dependsOnMethods = { "addFirstExpense" }) //, alwaysRun = true)
    public void addCarExpense(String theUserPara, String thePSW, String paraGroup, String targetResult, String expectResult) throws Exception {
        String nameOFmethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        globalRowId++;
        String result4XLSX = "Failed";
        date = new Date();
        time = format.format(date);
        String preRoleName = null;
        String[] extPara = paraGroup.split("::");
        if (extPara.length != 0) {
            if (extPara[extPara.length - 1].length() > 10) {//如果长度大于10则截取
                preRoleName = new String(extPara[extPara.length - 1].substring(0, 9));  // for screen
                preRoleName = filter4space(preRoleName);
            } else {
                preRoleName = extPara[extPara.length - 1];
            }
        }
        String[] para4Action = preapareActionParameter(new String[]{theUserPara, thePSW}, extPara);  // for action
        String cMethodPath = this.cPath + File.separator + nameOFmethod + "_" + preRoleName + "_" + time.replaceAll(":", "_");
        list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + "JustStart" + "#" + "waiting4result");
        try {
            BaseAction theAction = new BaseAction(driver, null, 1, para4Action, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            if (mobileOS.equalsIgnoreCase("Android")) {
                theAction.comOperationInAND((AndroidDriver) driver, this.TestStepList_addMoreCarRentalExpense, para4Action);   // 2nd para is the action sequence
            } else if (mobileOS.toLowerCase().startsWith("ios")) {
                theAction.comOperationInIOS((IOSDriver) driver, this.TestStepList_addMoreCarRentalExpense, para4Action, mobileOS);   // 2nd para is the action sequence
            }
            Thread.sleep(6000);
            System.out.println("Here is the result for this case : " + theAction.getExeResult());

            date = new Date();
            time = format.format(date);
            boolean boolean4assert = assertTestResult(theAction.getExeResult().toString(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + theAction.getExeResult().toString() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (PageTitleException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Single result judgement for PageTitleException ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean boolean4assert = assertTestResult(e.getMessage(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + e.getMessage() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (Exception e) {
            date = new Date();
            time = format.format(date);
            String expName = e.getClass().getSimpleName();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ " + expName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ScreenShot(driver, nameOFmethod + time + expName + ".png", cMethodPath);
            String newRow4Cell = formatResult4XLSX(nameOFmethod, preRoleName, targetResult, expName, time);
            list4result.add(newRow4Cell);
            e.printStackTrace();
            assert false;
            recover();
        } finally {
            driver.closeApp();
            Thread.sleep(1000);
            driver.launchApp();
        }
    }


    @Test(dataProvider = "addCarExpenseDate") // , dependsOnMethods = { "addFirstExpense" }) //, alwaysRun = true)
    public void addCarExpenseDate(String theUserPara, String thePSW, String paraGroup, String targetResult, String expectResult) throws Exception {
        String nameOFmethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        globalRowId++;
        String result4XLSX = "Failed";
        date = new Date();
        time = format.format(date);
        String preRoleName = null;
        String[] extPara = paraGroup.split("::");
        if (extPara.length != 0) {
            if (extPara[extPara.length - 1].length() > 10) {//如果长度大于10则截取
                preRoleName = new String(extPara[extPara.length - 1].substring(0, 9));  // for screen
                preRoleName = filter4space(preRoleName);
            } else {
                preRoleName = extPara[extPara.length - 1];
            }
        }
        String[] para4Action = preapareActionParameter(new String[]{theUserPara, thePSW}, extPara);  // for action
        String cMethodPath = this.cPath + File.separator + nameOFmethod + "_" + preRoleName + "_" + time.replaceAll(":", "_");
        list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + "JustStart" + "#" + "waiting4result");
        try {
            BaseAction theAction = new BaseAction(driver, null, 1, para4Action, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            if (mobileOS.equalsIgnoreCase("Android")) {
                theAction.comOperationInAND((AndroidDriver) driver, this.TestStepList_addMoreCarRentalExpenseDate, para4Action);   // 2nd para is the action sequence
            } else if (mobileOS.toLowerCase().startsWith("ios")) {
                theAction.comOperationInIOS((IOSDriver) driver, this.TestStepList_addMoreCarRentalExpenseDate, para4Action, mobileOS);   // 2nd para is the action sequence
            }
            Thread.sleep(6000);
            System.out.println("Here is the result for this case : " + theAction.getExeResult());

            date = new Date();
            time = format.format(date);
            boolean boolean4assert = assertTestResult(theAction.getExeResult().toString(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + theAction.getExeResult().toString() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (PageTitleException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Single result judgement for PageTitleException ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean boolean4assert = assertTestResult(e.getMessage(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + e.getMessage() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (Exception e) {
            date = new Date();
            time = format.format(date);
            String expName = e.getClass().getSimpleName();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ " + expName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ScreenShot(driver, nameOFmethod + time + expName + ".png", cMethodPath);
            String newRow4Cell = formatResult4XLSX(nameOFmethod, preRoleName, targetResult, expName, time);
            list4result.add(newRow4Cell);
            e.printStackTrace();
            assert false;
            recover();
        } finally {
            driver.closeApp();
            Thread.sleep(1000);
            driver.launchApp();
        }
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        theFileOutStream.close();
        driver.closeApp();
        driver.quit();
    }


    ////@Test(dataProvider = "createFirstExpense")
    public void addFirstExpense(String theUserPara, String thePSW, String paraGroup, String targetResult, String expectResult) throws Exception {
        String nameOFmethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        globalRowId++;
        String result4XLSX = "Failed";
        date = new Date();
        time = format.format(date);
        String preRoleName = null;
        String[] extPara = paraGroup.split("::");
        if (extPara.length != 0) {
            if (extPara[extPara.length - 1].length() > 10) {//如果长度大于10则截取
                preRoleName = new String(extPara[extPara.length - 1].substring(0, 9));  // for screen
                preRoleName = filter4space(preRoleName);
            } else {
                preRoleName = extPara[extPara.length - 1];
            }
        }
        String[] para4Action = preapareActionParameter(new String[]{theUserPara, thePSW}, extPara);  // for action
        //  String[] para4Aaction = preapareActionParameter( extPara);
        String cMethodPath = this.cPath + File.separator + nameOFmethod + "_" + preRoleName + "_" + time.replaceAll(":", "_");
        // for screen
        list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + "JustStart" + "#" + "waiting4result");
        try {
            //  BaseAction theAction = new BaseAction(driver, null, 1, para4Aaction, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            //   theAction.comOperationInAND(driver, this.TestStepList_addFirstExpense, para4Aaction);   // 2nd para is the action sequence
            BaseAction theAction = new BaseAction(driver, null, 1, para4Action, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            if (mobileOS.equalsIgnoreCase("Android")) {
                theAction.comOperationInAND((AndroidDriver) driver, this.TestStepList_addFirstExpense, para4Action);   // 2nd para is the action sequence
            } else if (mobileOS.toLowerCase().startsWith("ios")) {
                theAction.comOperationInIOS((IOSDriver) driver, this.TestStepList_addFirstExpense, para4Action, mobileOS);   // 2nd para is the action sequence
            }
            Thread.sleep(6000);
            System.out.println("Here is the result for this case : " + theAction.getExeResult());

            date = new Date();
            time = format.format(date);
            boolean boolean4assert = assertTestResult(theAction.getExeResult().toString(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + theAction.getExeResult().toString() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (PageTitleException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Single result judgement for PageTitleException ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean boolean4assert = assertTestResult(e.getMessage(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + e.getMessage() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (Exception e) {
            date = new Date();
            time = format.format(date);
            String expName = e.getClass().getSimpleName();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ " + expName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ScreenShot(driver, nameOFmethod + time + expName + ".png", cMethodPath);
            String newRow4Cell = formatResult4XLSX(nameOFmethod, preRoleName, targetResult, expName, time);
            list4result.add(newRow4Cell);
            e.printStackTrace();
            assert false;
            recover();
        } finally {
            driver.closeApp();
            Thread.sleep(1000);
            driver.launchApp();
        }
    }


    @Test(dataProvider = "addAirExpense") // , dependsOnMethods = { "addFirstExpense" }) //, alwaysRun = true)
    public void addAirExpense(String theUserPara, String thePSW, String paraGroup, String targetResult, String expectResult) throws Exception {
        String nameOFmethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        globalRowId++;
        String result4XLSX = "Failed";
        date = new Date();
        time = format.format(date);
        String preRoleName = null;
        String[] extPara = paraGroup.split("::");
        if (extPara.length != 0) {
            if (extPara[extPara.length - 1].length() > 10) {//如果长度大于10则截取
                preRoleName = new String(extPara[extPara.length - 1].substring(0, 9));  // for screen
                preRoleName = filter4space(preRoleName);
            } else {
                preRoleName = extPara[extPara.length - 1];
            }
        }
        String[] para4Action = preapareActionParameter(new String[]{theUserPara, thePSW}, extPara);  // for action
        String cMethodPath = this.cPath + File.separator + nameOFmethod + "_" + preRoleName + "_" + time.replaceAll(":", "_");
        list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + "JustStart" + "#" + "waiting4result");
        try {
            BaseAction theAction = new BaseAction(driver, null, 1, para4Action, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            if (mobileOS.equalsIgnoreCase("Android")) {
                theAction.comOperationInAND((AndroidDriver) driver, this.TestStepList_addMoreAirExpense, para4Action);   // 2nd para is the action sequence
            } else if (mobileOS.toLowerCase().startsWith("ios")) {
                theAction.comOperationInIOS((IOSDriver) driver, this.TestStepList_addMoreAirExpense, para4Action, mobileOS);   // 2nd para is the action sequence
            }
            Thread.sleep(6000);
            System.out.println("Here is the result for this case : " + theAction.getExeResult());

            date = new Date();
            time = format.format(date);
            boolean boolean4assert = assertTestResult(theAction.getExeResult().toString(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + theAction.getExeResult().toString() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (PageTitleException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Single result judgement for PageTitleException ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean boolean4assert = assertTestResult(e.getMessage(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + e.getMessage() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (Exception e) {
            date = new Date();
            time = format.format(date);
            String expName = e.getClass().getSimpleName();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ " + expName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ScreenShot(driver, nameOFmethod + time + expName + ".png", cMethodPath);
            String newRow4Cell = formatResult4XLSX(nameOFmethod, preRoleName, targetResult, expName, time);
            list4result.add(newRow4Cell);
            e.printStackTrace();
            assert false;
            recover();
        } finally {
            driver.closeApp();
            Thread.sleep(1000);
            driver.launchApp();
        }
    }


    @Test(dataProvider = "addRailExpense") // , dependsOnMethods = { "addFirstExpense" }) //, alwaysRun = true)
    public void addRailExpense(String theUserPara, String thePSW, String paraGroup, String targetResult, String expectResult) throws Exception {
        String nameOFmethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        globalRowId++;
        String result4XLSX = "Failed";
        date = new Date();
        time = format.format(date);
        String preRoleName = null;
        String[] extPara = paraGroup.split("::");
        if (extPara.length != 0) {
            if (extPara[extPara.length - 1].length() > 10) {//如果长度大于10则截取
                preRoleName = new String(extPara[extPara.length - 1].substring(0, 9));  // for screen
                preRoleName = filter4space(preRoleName);
            } else {
                preRoleName = extPara[extPara.length - 1];
            }
        }
        String[] para4Action = preapareActionParameter(new String[]{theUserPara, thePSW}, extPara);  // for action
        String cMethodPath = this.cPath + File.separator + nameOFmethod + "_" + preRoleName + "_" + time.replaceAll(":", "_");
        list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + "JustStart" + "#" + "waiting4result");
        try {
            BaseAction theAction = new BaseAction(driver, null, 1, para4Action, cMethodPath, expectResult);  // 2nd para is for os type, last para is the excepted result str
            if (mobileOS.equalsIgnoreCase("Android")) {
                theAction.comOperationInAND((AndroidDriver) driver, this.TestStepList_addMoreRailExpense, para4Action);   // 2nd para is the action sequence
            } else if (mobileOS.toLowerCase().startsWith("ios")) {
                theAction.comOperationInIOS((IOSDriver) driver, this.TestStepList_addMoreRailExpense, para4Action, mobileOS);   // 2nd para is the action sequence
            }
            Thread.sleep(6000);
            System.out.println("Here is the result for this case : " + theAction.getExeResult());

            date = new Date();
            time = format.format(date);
            boolean boolean4assert = assertTestResult(theAction.getExeResult().toString(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + theAction.getExeResult().toString() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (PageTitleException e) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Single result judgement for PageTitleException ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            boolean boolean4assert = assertTestResult(e.getMessage(), targetResult); // for judge
            if (boolean4assert == true) {
                result4XLSX = "PASS";
            }
            list4result.add(nameOFmethod + "#" + time + "#" + preRoleName + "#" + targetResult + "#" + e.getMessage() + "#" + result4XLSX);  // for xlsx
            if (boolean4assert == false)  // judge the result
            {
                assert false;
            }
        } catch (Exception e) {
            date = new Date();
            time = format.format(date);
            String expName = e.getClass().getSimpleName();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ " + expName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~");
            ScreenShot(driver, nameOFmethod + time + expName + ".png", cMethodPath);
            String newRow4Cell = formatResult4XLSX(nameOFmethod, preRoleName, targetResult, expName, time);
            list4result.add(newRow4Cell);
            e.printStackTrace();
            assert false;
            recover();
        } finally {
            driver.closeApp();
            Thread.sleep(1000);
            driver.launchApp();
        }
    }


}




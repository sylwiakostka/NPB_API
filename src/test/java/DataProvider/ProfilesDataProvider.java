package DataProvider;

import Utilities.ReadExcel;
import org.testng.annotations.DataProvider;

public class ProfilesDataProvider {

    @DataProvider(name="DataToAddProfiles")
    public Object[][] correctDataProfiles() throws Exception{
        Object[][] testObjArray = ReadExcel.getData("C://Users//user//Desktop//NPB_API//src//test//java//Excels//profiles.xlsx", "Dodawanie nowego profilu");
        return (testObjArray);
    }

    @DataProvider(name="EditProfile")
    public Object[][] dataToEditProfiles() throws Exception{
        Object[][] testObjArray = ReadExcel.getData("C://Users//user//Desktop//NPB_API//src//test//java//Excels//profiles.xlsx", "Edycja profilu");
        return (testObjArray);
    }

    @DataProvider(name="AddOffice")
    public Object[][] dataToAddOffice() throws Exception{
        Object[][] testObjArray = ReadExcel.getData("C://Users//user//Desktop//NPB_API//src//test//java//Excels//offices.xlsx", "Dodawanie nowej siedziby");
        return (testObjArray);
    }

}

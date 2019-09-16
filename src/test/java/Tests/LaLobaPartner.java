package Tests;

import Constants.EndPoints;
import DataProvider.ProfilesDataProvider;
import Profile_model.Profile;
import Profile_model.ProfileModel;
import Profile_model.ProfilesIdToRewrite;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import Utilities.Utilities;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.TreeMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;


public class LaLobaPartner {
    String partnerId = "1540";
    String partnerName = "LA LOBA";
    String lastCratedProfileId = "";
    String firstCratedProfileId = "";

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeClass
    public void setup() {
        reqSpec = Utilities.getRequestSpecification();
        resSpec = Utilities.getResponseSpecification();
    }

//    @Test
//    public void logout() {
//        given()
//                .spec(reqSpec)
//                .when()
//                .post(EndPoints.LOGOUT)
//                .then()
//                .spec(resSpec);
//    }

    @Test
    public void get_partners_list() {
        Response response =
                given()
                        .spec(reqSpec)
                        .when()
                        .get(EndPoints.GET_PARTNERS_LIST)
                        .then()
                        .spec(resSpec)
                        .body("result.partners", is(not(empty())))
                        .extract().response();
        Assert.assertEquals(response.path("result.partners." + partnerId), partnerName);
    }

    @Test
    public void get_partner_by_id() {
        given()
                .spec(reqSpec)
                .when()
                .get(EndPoints.GET_PARTNER_BY_ID, partnerId)
                .then()
                .spec(resSpec);
    }

    @Test(dependsOnMethods = "get_partner_by_id")
    public void profiles_list() {
        given()
                .spec(reqSpec)
                .body("{}")
                .when()
                .post(EndPoints.PROFILES_LIST)
                .then()
                .spec(resSpec)
                .and()
                .body("result.profiles.name", hasItems("Bez limitów", "premium"));
    }


    @Test(dependsOnMethods = "get_partner_by_id")
    public void get_profiles_by_id() {
        String profileId = "5297";
        given()
                .spec(reqSpec)
                .when()
                .get(EndPoints.GET_PROFILES_BY_ID, profileId)
                .then()
                .spec(resSpec)
                .and()
                .body("result.profile.name", equalTo("Bez limitów"));
    }

    @Test(dependsOnMethods = "get_partner_by_id", dataProvider = "DataToAddProfiles", dataProviderClass = ProfilesDataProvider.class)
    public void add_profile(String name, String maxTariff, String startHour, String startMinute, String endHour, String endMinute, String maxOrders, String totalAmount, String warnAmount,
                            String orderDays, String commentForCC) {

        Profile profile = new Profile();
        profile.setName(name);
        profile.setStartHour(startHour);
        profile.setEndHour(endHour);
        profile.setStartMinute(startMinute);
        profile.setEndMinute(endMinute);
        profile.setMaxOrders(maxOrders);
        profile.setMaxTotalAmountGr(totalAmount);
        profile.setWarnAmountGr(warnAmount);
        profile.setMaxTariffGr(maxTariff);
        profile.setOrderDays(orderDays);
        profile.setCommentForCC(commentForCC);

        ProfileModel profileModel = new ProfileModel();
        profileModel.setProfile(profile);

        given()
                .spec(reqSpec)
                .body(profileModel)
                .log().body()
                .when()
                .post(EndPoints.ADD_PROFILE)
                .then()
                .log().all()
                .spec(resSpec);
    }


    @Test(dependsOnMethods = "get_partner_by_id")
    public void get_profiles_short() {
        given()
                .spec(reqSpec)
                .when()
                .get("/profiles/short")
                .then()
                .spec(resSpec);
    }

    @Test(dependsOnMethods = {"get_partner_by_id", "get_last_created_profileId"}, dataProvider = "EditProfile", dataProviderClass = ProfilesDataProvider.class)
    public void edit_profile(String name, String maxTariff, String startHour, String startMinute, String endHour, String endMinute, String maxOrders, String totalAmount, String warnAmount,
                             String orderDays, String commentForCC) {

        Profile profile = new Profile();
        profile.setId(lastCratedProfileId);
        profile.setName(name);
        profile.setStartHour(startHour);
        profile.setEndHour(endHour);
        profile.setStartMinute(startMinute);
        profile.setEndMinute(endMinute);
        profile.setMaxOrders(maxOrders);
        profile.setMaxTotalAmountGr(totalAmount);
        profile.setWarnAmountGr(warnAmount);
        profile.setMaxTariffGr(maxTariff);
        profile.setOrderDays(orderDays);
        profile.setCommentForCC(commentForCC);

        ProfileModel profileModel = new ProfileModel();
        profileModel.setProfile(profile);

        given()
                .spec(reqSpec)
                .body(profileModel)
                .log().body()
                .when()
                .post(EndPoints.EDIT_PROFILE)
                .then()
                .log().all()
                .spec(resSpec);
    }

    @Test(dependsOnMethods = {"get_partner_by_id", "get_last_created_profileId", "get_first_created_profileId"})
    public void rewrite_users_from_profile() {
        ProfilesIdToRewrite profilesIdToRewrite = new ProfilesIdToRewrite();
        profilesIdToRewrite.setTo(firstCratedProfileId);
        profilesIdToRewrite.setFrom(lastCratedProfileId);


        given()
                .spec(reqSpec)
                .body(profilesIdToRewrite)
                .log().body()
                .when()
                .post(EndPoints.REWRITE_USERS_FROM_PROFILE)
                .then()
                .log().all()
                .spec(resSpec);
    }

    @Test(dependsOnMethods = "get_partner_by_id")
    public void get_last_created_profileId() {
        Response response =
                given()
                        .spec(reqSpec)
                        .when()
                        .get(EndPoints.PROFILES_LIST_SHORT)
                        .then()
                        .spec(resSpec)
                        .and()
                        .extract().response();

        Map<String, String> hashMap = response.jsonPath().getMap("result.data");
        System.out.println(hashMap.values());


        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.putAll(hashMap);

        lastCratedProfileId = treeMap.lastKey();
        System.out.println(lastCratedProfileId);
    }

    @Test(dependsOnMethods = "get_partner_by_id")
    public void get_first_created_profileId() {
        Response response =
                given()
                        .spec(reqSpec)
                        .when()
                        .get(EndPoints.PROFILES_LIST_SHORT)
                        .then()
                        .spec(resSpec)
                        .and()
                        .extract().response();

        Map<String, String> hashMap = response.jsonPath().getMap("result.data");
        System.out.println(hashMap.values());


        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.putAll(hashMap);

        firstCratedProfileId = treeMap.firstKey();
        System.out.println(firstCratedProfileId);
    }
}





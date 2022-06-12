package client;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.AuthResponse;
import models.User;

import static client.Constants.ORDER_PATH;
import static client.Settings.getBaseSpec;
import static client.Constants.USER_PATH;
import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Order creation")
    public ValidatableResponse orderCreate(String json) {
        ValidatableResponse userCreateResponse= given()
                .spec(getBaseSpec())
                .and()
                .body(json)
                .post(ORDER_PATH)
                .then();
        return userCreateResponse;
    }

    @Step("Order creation with Login")
    public ValidatableResponse orderCreateWithLogin(String json,User user) {
        Response responseLogin = given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login");
        AuthResponse authResponse = responseLogin.as(AuthResponse.class);

        ValidatableResponse userCreateResponse= given()
                .spec(getBaseSpec())
                .header(new Header("Authorization",authResponse.getAccessToken()))
                .and()
                .body(json)
                .post(ORDER_PATH)
                .then();
        return userCreateResponse;
    }

    @Step("Getting orders of the current user")
    public ValidatableResponse getOrders(User user) {
        Response responseLogin = given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login");
        AuthResponse authResponse = responseLogin.as(AuthResponse.class);

        ValidatableResponse getOrdersResponse = given()
                .spec(getBaseSpec())
                .header(new Header("Authorization",authResponse.getAccessToken()))
                .and()
                .get(ORDER_PATH)
                .then();
        return getOrdersResponse;
    }

    @Step("Getting orders of the current user without authorization")
    public ValidatableResponse getOrdersWithoutLogin() {
        ValidatableResponse getOrdersResponse = given()
                .spec(getBaseSpec())
                .and()
                .get(ORDER_PATH)
                .then();
        return getOrdersResponse;
    }

}

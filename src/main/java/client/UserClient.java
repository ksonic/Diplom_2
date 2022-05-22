package client;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.AuthResponse;
import models.User;

import static client.Settings.getBaseSpec;
import static io.restassured.RestAssured.given;
import static client.Constants.USER_PATH;

public class UserClient {

    @Step("User creation")
    public ValidatableResponse userCreate(User user) {
        ValidatableResponse userCreateResponse= given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/register")
                .then();
        return userCreateResponse;
    }

    @Step("User login")
    public ValidatableResponse userLogin(User user) {
        ValidatableResponse userLoginResponse= given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login")
                .then();
        return userLoginResponse;
    }

    @Step("User data change")
    public ValidatableResponse userUpdate(User user,User newData) {
        Response responseLogin = given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login");
        AuthResponse authResponse = responseLogin.as(AuthResponse.class);

        ValidatableResponse userUpdateResponse= given()
                .spec(getBaseSpec())
                .header(new Header("Authorization",authResponse.getAccessToken()))
                .and()
                .body(newData)
                .patch(USER_PATH+"/user")
                .then();
        return userUpdateResponse;
    }

    @Step("User delete")
    public void userDelete(User user) {
        Response responseLogin = given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login");

        AuthResponse authResponse = responseLogin.as(AuthResponse.class);

       // Отправляем запрос на удаление с использованием id
        Response responseDelete = given()
                .spec(getBaseSpec())
                .header(new Header("Authorization",authResponse.getAccessToken()))
                .and()
                .delete(USER_PATH+"/user");
    }
    @Step("Getting user data by the token")
    public Response userQuery(User user) {
        Response responseLogin = given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login");
        AuthResponse authResponse = responseLogin.as(AuthResponse.class);
        Response response = given()
                .spec(getBaseSpec())
                .header(new Header("Authorization",authResponse.getAccessToken()))
                .and()
                .get(USER_PATH+"/user");
            return response;
    }

}

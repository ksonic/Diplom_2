package client;

import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.AuthResponse;
import models.User;

import static client.Settings.getBaseSpec;
import static io.restassured.RestAssured.given;

public class UserClient {
    public static final String USER_PATH = "api/auth";

    // Создание пользователя
    public ValidatableResponse userCreate(User user) {
        ValidatableResponse userCreateResponse= given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/register")
                .then();
        return userCreateResponse;
    }


    // Логин пользователя
    public ValidatableResponse userLogin(User user) {
        ValidatableResponse userLoginResponse= given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .post(USER_PATH+"/login")
                .then();
        return userLoginResponse;
    }


    // Изменение данных пользователя
    public ValidatableResponse userUpdate(User user,User newData) {
        // Отправляем запрос логина, что бы получить id
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

    // Метод удаления пользователя
    public void userDelete(User user) {

        // Отправляем запрос логина, что бы получить id
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
                //.body(responseLogin.getBody().print())
                .delete(USER_PATH+"/user");
    }

    // Метод получения данных пользователя по токену
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

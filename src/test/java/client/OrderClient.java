package client;

import io.restassured.http.Header;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.AuthResponse;
import models.User;
import org.junit.jupiter.api.Test;

import java.io.File;

import static client.OrderGenerator.getIngredientsRandom;
import static client.Settings.getBaseSpec;
import static client.UserClient.USER_PATH;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String ORDER_PATH = "api/orders";

    // Создание заказа
    public ValidatableResponse orderCreate(String json) {
        ValidatableResponse userCreateResponse= given()
                .spec(getBaseSpec())
                .and()
                .body(json)
                .post(ORDER_PATH)
                .then();
        return userCreateResponse;
    }

    // Создание заказа с логином
    public ValidatableResponse orderCreateWithLogin(String json,User user) {
        // Отправляем запрос логина, что бы получить id
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

    // Получение всех заказов конкретного пользователя
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

    // Получение всех заказов конкретного пользователя без авторизации
    public ValidatableResponse getOrdersWithoutLogin() {
        ValidatableResponse getOrdersResponse = given()
                .spec(getBaseSpec())
                .and()
                .get(ORDER_PATH)
                .then();
        return getOrdersResponse;
    }

}

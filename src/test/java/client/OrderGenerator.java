package client;

import models.Ingredients;
import models.IngredientsData;

import java.util.List;
import java.util.Random;

public class OrderGenerator {
    OrderClient orderClient=new OrderClient();
    static IngredientClient ingredientClient=new IngredientClient();

    public static String json =String.format("{\n" +
            "\"ingredients\": [\"%s\",\"%s\"]\n" +
            "}",getIngredientsRandom(ingredientClient),getIngredientsRandom(ingredientClient));
    public static String jsonWithoutIngredients="";
    public static String jsonInvalidIngredients =String.format("{\"ingredients\": [\"test\"]}");


    public static String getIngredientsRandom(IngredientClient ingredientClient){

        List<IngredientsData> data=ingredientClient.ingredients().extract().body().as(Ingredients.class).getData();
        int randomSuit = new Random().nextInt(data.size());

        return data.get(randomSuit).get_id();
    }

}

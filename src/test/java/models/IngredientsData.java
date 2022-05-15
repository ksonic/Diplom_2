package models;

import io.restassured.response.ValidatableResponse;

public class IngredientsData {
  /*  public String _id;
    public String name;
    public String type;
    public String proteins;
    public String fat;
    public String carbohydrates;
    public String calories;
    public String price;
    public String image;
    public String image_mobile;
    public String image_large;*/
  public IngredientType type;
    public String name;
    public float price;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String _id;

    public IngredientsData(IngredientType type, String name, float price, String _id) {
        this.type = type;
        this.name = name;
        this.price = price;
        this._id = _id;
    }

    public IngredientsData(String _id) {
        this._id =_id;
    }
    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public IngredientType getType() {
        return type;
    }

}

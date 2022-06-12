package models;

public class IngredientsData {
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

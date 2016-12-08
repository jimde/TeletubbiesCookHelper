package ca.uottawa.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    public static IngredientDataSource ingredientDB;
    public static RecipeDataSource recipeDB;
    private static RecentDataSource recentDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */
        setContentView(R.layout.activity_home_screen);

        ingredientDB = new IngredientDataSource(this);
        recipeDB = new RecipeDataSource(this);
        recentDB = new RecentDataSource(this);
        ingredientDB.open();
        recipeDB.open();
        recentDB.open();
        try {
            addDefaultFoods();
        }
        catch(Exception e){
            System.out.println( e.getClass().getCanonicalName());
        }
    }
    public void addDefaultFoods() throws IOException{
        ingredientDB = new IngredientDataSource(this);
        recipeDB = new RecipeDataSource(this);
        recentDB = new RecentDataSource(this);
        ingredientDB.open();
        recipeDB.open();
        recentDB.open();

        List<String> pancakeIngredients = new ArrayList<>();
        pancakeIngredients.add("Flour");
        pancakeIngredients.add("Baking_Powder");
        pancakeIngredients.add("Salt");
        pancakeIngredients.add("White_Sugar");
        pancakeIngredients.add("Milk");
        pancakeIngredients.add("Egg");
        pancakeIngredients.add("Melted_Butter");
        Recipe pancakes = new Recipe("Pancakes",pancakeIngredients,"Breakfast","Canadian",
                "1. In a large bowl, sift together the flour, " +
                "baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; " +
                "mix until smooth. \n2. Heat a lightly oiled griddle or frying pan over medium high heat. " +
                "Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. " +
                "Brown on both sides and serve hot." );
        recipeDB.addToDB(pancakes);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(pancakeIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> chocolateChipCookiesIngredients = new ArrayList<String>();
        chocolateChipCookiesIngredients.add("Butter");
        chocolateChipCookiesIngredients.add("White_Sugar");
        chocolateChipCookiesIngredients.add("Brown_Sugar");
        chocolateChipCookiesIngredients.add("Eggs");
        chocolateChipCookiesIngredients.add("Vanilla_Extract");
        chocolateChipCookiesIngredients.add("All_Purpose_Flour");
        chocolateChipCookiesIngredients.add("Baking_Soda");
        chocolateChipCookiesIngredients.add("Water");
        chocolateChipCookiesIngredients.add("Salt");
        chocolateChipCookiesIngredients.add("Chocolate_Chips");
        chocolateChipCookiesIngredients.add("Walnuts");
        Recipe chocolateChipCookies = new Recipe("Chocolate Chip Cookies",chocolateChipCookiesIngredients,"Dessert","Murica",
                "1. Preheat oven to 350 degrees F (175 degrees C)." +
                        "\n2. Cream together the butter, white sugar, and brown sugar until smooth. " +
                        "Beat in the eggs one at a time, then stir in the vanilla. " +
                        "Dissolve baking soda in hot water. Add to batter along with salt. " +
                        "Stir in flour, chocolate chips, and nuts. Drop by large spoonfuls onto ungreased pans." +
                        "\n3. Bake for about 10 minutes in the preheated oven, or until edges are nicely browned.");
        recipeDB.addToDB(chocolateChipCookies);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(chocolateChipCookiesIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> macAndCheeseIngredients = new ArrayList<String>();
        macAndCheeseIngredients.add("Elbow_Macaroni");
        macAndCheeseIngredients.add("Cheddar_Cheese");
        macAndCheeseIngredients.add("Parmesan_Cheese");
        macAndCheeseIngredients.add("Milk");
        macAndCheeseIngredients.add("Butter");
        macAndCheeseIngredients.add("All_Purpose_Flour");
        macAndCheeseIngredients.add("Bread_Crumbs");
        macAndCheeseIngredients.add("Paprika");
        Recipe macAndCheese = new Recipe("Mac and Cheese",macAndCheeseIngredients,"Lunch","Murica",
                "1. Cook macaroni according to the package directions. Drain." +
                        "\n2. In a saucepan, melt butter or margarine over medium heat. " +
                        "Stir in enough flour to make a roux. Add milk to roux slowly, stirring constantly. " +
                        "Stir in cheeses, and cook over low heat until cheese is melted and the sauce is a little thick. " +
                        "Put macaroni in large casserole dish, and pour sauce over macaroni. Stir well." +
                        "\n3. Melt butter or margarine in a skillet over medium heat. " +
                        "Add breadcrumbs and brown. Spread over the macaroni and cheese to cover. " +
                        "Sprinkle with a little paprika." +
                        "\n4. Bake at 350 degrees F (175 degrees C) for 30 minutes. Serve.");
        recipeDB.addToDB(macAndCheese);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(macAndCheeseIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> newYorkCheeseCakeIngredients = new ArrayList<String>();
        newYorkCheeseCakeIngredients.add("Crushed_Graham_Crackers");
        newYorkCheeseCakeIngredients.add("Melted_Butter");
        newYorkCheeseCakeIngredients.add("Cream_Cheese");
        newYorkCheeseCakeIngredients.add("White_Sugar");
        newYorkCheeseCakeIngredients.add("Milk");
        newYorkCheeseCakeIngredients.add("Eggs");
        newYorkCheeseCakeIngredients.add("Sour_Cream");
        newYorkCheeseCakeIngredients.add("Vanilla_Extract");
        newYorkCheeseCakeIngredients.add("All_Purpose_Flour");
        Recipe newYorkCheeseCake = new Recipe("New York Cheesecake",newYorkCheeseCakeIngredients,"Dessert","Murica",
                "1. Preheat oven to 350 degrees F (175 degrees C). Grease a 9 inch springform pan." +
                        "\n2. In a medium bowl, mix graham cracker crumbs with melted butter. " +
                        "Press onto bottom of springform pan." +
                        "\n3. In a large bowl, mix cream cheese with sugar until smooth. " +
                        "Blend in milk, and then mix in the eggs one at a time, mixing just enough to incorporate. " +
                        "Mix in sour cream, vanilla and flour until smooth. Pour filling into prepared crust." +
                        "\n4. Bake in preheated oven for 1 hour. Turn the oven off, and let cake cool in oven " +
                        "with the door closed for 5 to 6 hours; this prevents cracking. Chill in refrigerator until serving.");
        recipeDB.addToDB(newYorkCheeseCake);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(newYorkCheeseCakeIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> bakedPotatoIngredients = new ArrayList<String>();
        bakedPotatoIngredients.add("Potato");
        Recipe bakedPotato = new Recipe("Baked Potato", bakedPotatoIngredients,"Dinner","Greek",
                "1. Preheat the oven to 300 degrees F (150 degrees C). " +
                        "Scrub the potato, and pierce the skin several times with a knife or fork." +
                        "\n2. Place the potato in the preheated oven, and bake for 90 minutes, " +
                        "or until slightly soft and golden brown.");
        recipeDB.addToDB(bakedPotato);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(bakedPotatoIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> applePieIngredients = new ArrayList<String>();
        applePieIngredients.add("Pie_Crust");
        applePieIngredients.add("Unsalted_Butter");
        applePieIngredients.add("All_Purpose_Flour");
        applePieIngredients.add("Water");
        applePieIngredients.add("White_Sugar");
        applePieIngredients.add("Packed_Brown_Sugar");
        applePieIngredients.add("Granny_Smith_Apples");
        Recipe applePie = new Recipe("Granny Smith Apple Pie", applePieIngredients, "Dessert", "Canadian",
                "Preheat oven to 425 degrees F (220 degrees C). Melt the butter in a saucepan. " +
                        "Stir in flour to form a paste. Add water, white sugar and brown sugar, " +
                        "and bring to a boil. Reduce temperature and let simmer. " +
                       "Place the bottom crust in your pan. Fill with apples, mounded slightly. " +
                        "Cover with a lattice work crust. Gently pour the sugar and butter liquid over the crust. " +
                        "Pour slowly so that it does not run off. Bake 15 minutes in the preheated oven. " +
                        "Reduce the temperature to 350 degrees F (175 degrees C). " +
                        "Continue baking for 35 to 45 minutes, until apples are soft.");
        recipeDB.addToDB(applePie);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(applePieIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> padThaiIngredients = new ArrayList<String>();
        padThaiIngredients.add("Rice_Noodles");
        padThaiIngredients.add("Butter");
        padThaiIngredients.add("Chicken_Breast");
        padThaiIngredients.add("Vegetable_Oil");
        padThaiIngredients.add("Eggs");
        padThaiIngredients.add("White_Vinegar");
        padThaiIngredients.add("Fish_Sauce");
        padThaiIngredients.add("White_Sugar");
        padThaiIngredients.add("Red_Pepper");
        padThaiIngredients.add("Bean_Sprouts");
        padThaiIngredients.add("Peanuts");
        padThaiIngredients.add("Green_Onions");
        padThaiIngredients.add("Lemon");
        Recipe padThai = new Recipe("Pad Thai", padThaiIngredients, "Dinner", "Thai",
                "Soak rice noodles in cold water 30 to 50 minutes, or until soft. Drain, and set aside. " +
                        "Heat butter in a wok or large heavy skillet. Saute chicken until browned. " +
                        "Remove, and set aside. Heat oil in wok over medium-high heat. " +
                        "Crack eggs into hot oil, and cook until firm. Stir in chicken, and cook for 5 minutes. " +
                        "Add softened noodles, and vinegar, fish sauce, sugar and red pepper. " +
                        "Adjust seasonings to taste. Mix while cooking, until noodles are tender. " +
                        "Add bean sprouts, and mix for 3 minutes.");
        recipeDB.addToDB(padThai);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(padThaiIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }

        List<String> hummusIngredients = new ArrayList<String>();
        hummusIngredients.add("Beans");
        hummusIngredients.add("Garlic");
        hummusIngredients.add("Cumin");
        hummusIngredients.add("Salt");
        hummusIngredients.add("Olive_Oil");
        Recipe hummus = new Recipe("Easy Hummus", hummusIngredients, "Appetizer", "Greek",
                "In a blender or food processor combine garbanzo beans, garlic, cumin, salt and olive oil. " +
                        "Blend on low speed, gradually adding reserved bean liquid, until desired consistency is achieved.");
        recipeDB.addToDB(hummus);
        for(int i = 0; i < pancakeIngredients.size(); i++){
            try {
                ingredientDB.addToDB(new Ingredient(hummusIngredients.get(i)));
            }
            catch(Exception e){
                System.out.println( e.getClass().getCanonicalName());
            }
        }


    }
    public void toSearchScreenPage(View view){
        Intent myIntent = new Intent(this, Search.class);
        startActivity(myIntent);
    }
    public void toRecentResults(View view){
        Intent myIntent = new Intent(this, RecentResults.class);
        startActivity(myIntent);
    }
    public void toSettingsScreen(View view){
        Intent myIntent = new Intent(this, Settings.class);
        startActivity(myIntent);
    }
    public void toIngredientsScreen(View view){
        Intent myIntent = new Intent(this, Pantry.class);
        startActivity(myIntent);
    }
    public void toAllRecipes(View view){
        Intent myIntent = new Intent(this, RecipeList.class);
        startActivity(myIntent);
    }
    public void toEgg(View view){
        Intent myIntent = new Intent(this, Egg.class);
        startActivity(myIntent);
    }
}

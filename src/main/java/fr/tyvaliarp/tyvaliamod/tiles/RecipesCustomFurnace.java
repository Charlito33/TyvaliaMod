package fr.tyvaliarp.tyvaliamod.tiles;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RecipesCustomFurnace {
    public static final HashMap<ItemStack[], ItemStack> recipes = new HashMap<>();

    static {
        addRecipe(Items.APPLE, Items.ARROW, Items.BAKED_POTATO);
    }

    private static void addRecipe(Item ingredient1, Item ingredient2, Item result1) {
        addRecipe(new ItemStack(ingredient1), new ItemStack(ingredient2), new ItemStack(result1));
    }

    private static void addRecipe(ItemStack ingredient1, ItemStack ingredient2, ItemStack result1) {
        recipes.put(new ItemStack[]{ingredient1, ingredient2}, result1);
    }

    private static boolean areKeysEqual(ItemStack[] key1, ItemStack[] key2) {
        if (key1.length != key2.length) return false;

        for (int i = 0; i < key1.length; i++) {
            ItemStack s1 = key1[i];
            ItemStack s2 = key2[i];

            if (s1.isEmpty() && !s2.isEmpty()) return false;
            if (!s1.isEmpty() && s2.isEmpty()) return false;
            if (s1.getItem() != s2.getItem()) return false;
            if (s1.getItemDamage() != s2.getItemDamage()) return false;
        }

        return true;
    }

    public static ItemStack getRecipeResult(ItemStack[] ingredients) {
        Iterator<Map.Entry<ItemStack[], ItemStack>> it = recipes.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<ItemStack[], ItemStack> entry = it.next();
            if (areKeysEqual(entry.getKey(), ingredients)) {
                return entry.getValue();
            }
        }

        return null;
    }


}

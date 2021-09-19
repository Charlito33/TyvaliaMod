package fr.tyvaliarp.tyvaliamod.init;

import fr.tyvaliarp.tyvaliamod.items.ItemKnife;
import fr.tyvaliarp.tyvaliamod.plants.ItemWeedSeed;
import fr.tyvaliarp.tyvaliamod.utils.ModItem;
import fr.tyvaliarp.tyvaliamod.utils.ModItemFood;
import fr.tyvaliarp.tyvaliamod.utils.ModMetaItem;
import fr.tyvaliarp.tyvaliamod.utils.References;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final ModItems INSTANCE = new ModItems();

    private List<Item> items;
    private List<ModMetaItem> metaItems;

    public static Item weed, weed_seed, knife, energy, fan; //Normal Items
    public static Item driver_license, mining_card; //Meta Items

    //Custom Items
    public static IPlantable weedIPlantable;

    public void init() {
        items = new ArrayList<>();
        metaItems = new ArrayList<>();

        //Items
        new ModItem("identity_card");
        new ModItem("weed");
        new ModItem("artifact");
        new ModItem("back");
        new ModItem("backpack");
        new ModItem("key");
        new ModItem("close");
        mining_card = new ModItem("mining_card");
        energy = new ModItem("energy");
        fan = new ModItem("fan");

        //Food Items
        new ModItemFood("weed_ball", 1, 1.0f);
        new ModItemFood("weed_smoke", 1, 1.0f);
        new ModItemFood("bedo", 1, 1.0f);
        new ModItemFood("smoke", 1, 1.0f);

        //Meta Items
        driver_license = new ModMetaItem("driver_license", new String[]{"empty", "car", "motorcycle", "truck", "car+motorcycle", "car+truck", "motorcycle+truck"});
        new ModMetaItem("weapon_license", new String[]{"empty", "valid", "unvalid"});
        new ModMetaItem("phone", new String[]{"prepaid", "old", "i8", "xr", "note10"}).setMaxStackSize(1);
        new ModMetaItem("bill", new String[]{"5", "10", "20", "50", "100", "200", "500"});
        new ModMetaItem("coke", new String[]{"original", "zero", "light", "life"});
        new ModMetaItem("cokebag", new String[]{"0", "1", "2"});


        //Custom Items
        weedIPlantable = new ItemWeedSeed();
        weed_seed = (Item)weedIPlantable;

        knife = new ItemKnife();
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent e) {
        for (Item item : items) {
            registerModel(item);
        }

        for (ModMetaItem item : metaItems) {
            registerMetaModel(item, item.subTypes);
        }
    }

    private void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(References.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
    }

    private void registerMetaModel(Item item, String[] metaDiff) {
        for (int i = 0; i < metaDiff.length; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(References.MODID, item.getUnlocalizedName().substring(5) + "_" + metaDiff[i]), "inventory"));
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public List<ModMetaItem> getMetaItems() {
        return metaItems;
    }
}

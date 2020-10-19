package fr.tyvaliarp.tyvaliamod.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {
    public static KeyBinding originalF3Menu;

    public static void register() {
        originalF3Menu = new KeyBinding("key.originalF3Menu", Keyboard.KEY_UP, "key.categories.tyvaliarp");

        ClientRegistry.registerKeyBinding(originalF3Menu);
    }
}

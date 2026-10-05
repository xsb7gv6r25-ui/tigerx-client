package com.tigerx.client;

import com.tigerx.client.modules.visual.ChestESP;
import com.tigerx.client.ui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class TigerXClientClient implements ClientModInitializer {
    private static KeyBinding openGuiKey;
    private static KeyBinding toggleEspKey;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tigerx.opengui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.tigerx"
        ));

        toggleEspKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tigerx.toggleesp",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.tigerx"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (TigerXClient.moduleManager != null) {
                    client.setScreen(new ClickGUI(TigerXClient.moduleManager));
                }
            }
            while (toggleEspKey.wasPressed()) {
                if (TigerXClient.moduleManager != null) {
                    var mod = TigerXClient.moduleManager.getModule("ChestESP");
                    if (mod != null) mod.toggle();
                }
            }
        });

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (TigerXClient.moduleManager == null) return;
            var mod = TigerXClient.moduleManager.getModule("ChestESP");
            if (mod instanceof ChestESP esp && esp.isEnabled()) {
                List<BlockPos> chests = esp.scanChests(client);
                esp.render(context.matrixStack(), context.consumers(), context.camera().getPos(), chests);
            }
        });
    }
}

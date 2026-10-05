package com.tigerx.client.ui;

import com.tigerx.client.core.Module;
import com.tigerx.client.core.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class ClickGUI extends Screen {
    private final ModuleManager moduleManager;
    private Module.Category currentCategory = Module.Category.VISUAL;

    public ClickGUI(ModuleManager moduleManager) {
        super(Text.literal("TigerX Client"));
        this.moduleManager = moduleManager;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int width = this.width;
        int height = this.height;

        context.fill(0, 0, width, height, 0xCC0A1628);

        int panelWidth = 320;
        int panelHeight = 400;
        int panelX = (width - panelWidth) / 2;
        int panelY = (height - panelHeight) / 2;

        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xEE0D1B2A);
        context.fill(panelX, panelY, panelX + panelWidth, panelY + 40, 0xFF1B2A41);
        context.fill(panelX, panelY + panelHeight - 20, panelX + panelWidth, panelY + panelHeight, 0xFF1B2A41);

        String username = MinecraftClient.getInstance().getSession().getUsername();
        context.drawTextWithShadow(this.textRenderer, "Bienvenido, " + username, panelX + 12, panelY + 14, 0xFF7FB3FF);

        List<Module> modules = moduleManager.getModules();

        int y = panelY + 60;
        for (Module module : modules) {
            if (module.getCategory() != currentCategory) continue;

            int moduleX = panelX + 20;
            int moduleWidth = panelWidth - 40;
            int moduleHeight = 40;

            context.fill(moduleX, y, moduleX + moduleWidth, y + moduleHeight, 0xFF16263F);
            context.drawTextWithShadow(this.textRenderer, module.getName(), moduleX + 10, y + 8, 0xFFFFFFFF);
            context.drawTextWithShadow(this.textRenderer, module.getDescription(), moduleX + 10, y + 22, 0xFF8FA8C8);

            String state = module.isEnabled() ? "ON" : "OFF";
            int stateColor = module.isEnabled() ? 0xFF4CAF50 : 0xFFE74C3C;
            context.drawTextWithShadow(this.textRenderer, state, moduleX + moduleWidth - 40, y + 14, stateColor);

            y += moduleHeight + 6;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int width = this.width;
        int height = this.height;
        int panelWidth = 320;
        int panelHeight = 400;
        int panelX = (width - panelWidth) / 2;
        int panelY = (height - panelHeight) / 2;

        List<Module> modules = moduleManager.getModules();
        int y = panelY + 60;
        for (Module module : modules) {
            if (module.getCategory() != currentCategory) continue;

            int moduleX = panelX + 20;
            int moduleWidth = panelWidth - 40;
            int moduleHeight = 40;

            if (mouseX >= moduleX && mouseX <= moduleX + moduleWidth
                    && mouseY >= y && mouseY <= y + moduleHeight) {
                module.toggle();
                return true;
            }
            y += moduleHeight + 6;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

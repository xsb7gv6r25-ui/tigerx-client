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

    public ClickGUI(ModuleManager moduleManager) {
        super(Text.literal("TigerX Client"));
        this.moduleManager = moduleManager;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int w = this.width;
        int h = this.height;

        context.fill(0, 0, w, h, 0xCC0A1628);

        int panelW = 300;
        int panelH = 300;
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0xFF0D1B2A);
        context.fill(panelX, panelY, panelX + panelW, panelY + 36, 0xFF1B2A41);

        String username = MinecraftClient.getInstance().getSession().getUsername();
        context.drawTextWithShadow(this.textRenderer, "Bienvenido, " + username, panelX + 10, panelY + 12, 0xFF7FB3FF);

        List<Module> modules = moduleManager.getModules();
        int y = panelY + 50;

        for (Module module : modules) {
            int mX = panelX + 15;
            int mW = panelW - 30;
            int mH = 36;

            context.fill(mX, y, mX + mW, y + mH, 0xFF16263F);
            context.drawTextWithShadow(this.textRenderer, module.getName(), mX + 8, y + 6, 0xFFFFFFFF);
            context.drawTextWithShadow(this.textRenderer, module.getDescription(), mX + 8, y + 20, 0xFF8FA8C8);

            String state = module.isEnabled() ? "ON" : "OFF";
            int color = module.isEnabled() ? 0xFF4CAF50 : 0xFFE74C3C;
            context.drawTextWithShadow(this.textRenderer, state, mX + mW - 35, y + 12, color);

            y += mH + 4;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int w = this.width;
        int h = this.height;
        int panelW = 300;
        int panelH = 300;
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        List<Module> modules = moduleManager.getModules();
        int y = panelY + 50;

        for (Module module : modules) {
            int mX = panelX + 15;
            int mW = panelW - 30;
            int mH = 36;

            if (mouseX >= mX && mouseX <= mX + mW && mouseY >= y && mouseY <= y + mH) {
                module.toggle();
                return true;
            }
            y += mH + 4;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}

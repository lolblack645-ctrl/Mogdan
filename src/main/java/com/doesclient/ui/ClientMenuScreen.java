package com.doesclient.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ClientMenuScreen extends Screen {

    public ClientMenuScreen() {
        super(Text.literal("DoesClient Menu"));
    }

    @Override
    protected void init() {
        super.init();
        
        // Добавим кнопку закрытия по центру внизу
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Закрыть"), button -> {
            this.close();
        }).dimensions(this.width / 2 - 100, this.height / 2 + 40, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Затемняем фон позади меню
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // Рисуем заголовок меню по центру
        context.drawCenteredTextWithShadow(this.textRenderer, "§bDoesClient GUI", this.width / 2, this.height / 2 - 50, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "§7Настрой свою систему здесь", this.width / 2, this.height / 2 - 30, 0xFFFFFF00);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}

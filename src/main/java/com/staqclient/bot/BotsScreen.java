package com.staqclient.bot;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class BotsScreen extends Screen {
    private TextFieldWidget nameField;

    public BotsScreen() {
        super(Text.literal("StaqClient - Bot Manager"));
    }

    @Override
    protected void init() {
        super.init();

        // Поле ввода ника бота (по центру экрана)
        int fieldWidth = 200;
        int fieldHeight = 20;
        int x = (this.width - fieldWidth) / 2;
        int y = this.height / 4;

        this.nameField = new TextFieldWidget(this.textRenderer, x, y, fieldWidth, fieldHeight, Text.literal("Bot Nickname"));
        this.nameField.setMaxLength(16);
        this.nameField.setText("StaqBot_");
        this.addSelectableChild(this.nameField);

        // Кнопка подключения бота
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Connect Bot"), button -> {
            String nickname = nameField.getText();
            if (!nickname.isEmpty()) {
                BotManager.addAndConnectBot(nickname);
            }
        }).dimensions(x, y + 30, fieldWidth, 20).build());

        // Кнопка закрытия меню
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> {
            this.client.setScreen(null);
        }).dimensions(x, y + 60, fieldWidth, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // Заголовок меню
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
        this.nameField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.nameField.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}

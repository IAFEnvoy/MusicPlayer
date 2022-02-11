package net.iafenvoy.musicplayer.Configs;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.List;

public class GuiConfig extends GuiConfigsBase {
    private static Category currentTab = Category.GENERAL;

    public static final String MOD_ID = "musicplayer";

    public GuiConfig() {
        super(10, 50, MOD_ID, null, "config.musicplayer.title");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();
        int x = 10, y = 26;
        // tab buttons are set
        for (Category category : Category.values()) {
            ButtonGeneric tabButton = new TabButton(category, x, y, -1, 20, StringUtils.translate(category.getKey()));
            tabButton.setEnabled(true);
            this.addButton(tabButton, (buttonBase, i) -> {
                this.onSettingsChanged();
                // reload the GUI when tab button is clicked
                currentTab = ((TabButton) buttonBase).category;
                this.reCreateListWidget();
                //noinspection ConstantConditions
                this.getListWidget().resetScrollbarPosition();
                this.initGui();
            });
            x += tabButton.getWidth() + 2;
        }
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        // option buttons are set
        return ConfigOptionWrapper.createFor(currentTab.getConfigs());
    }

    public static class TabButton extends ButtonGeneric {
        private final Category category;

        public TabButton(Category category, int x, int y, int width, int height, String text, String... hoverStrings) {
            super(x, y, width, height, text, hoverStrings);
            this.category = category;
        }
    }
}

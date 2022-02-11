package net.iafenvoy.musicplayer.Render;

import fi.dy.masa.malilib.event.RenderEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;

public class InitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        LRCRender renderer = new LRCRender();
        RenderEventHandler.getInstance().registerGameOverlayRenderer(renderer);
        RenderEventHandler.getInstance().registerTooltipLastRenderer(renderer);
        RenderEventHandler.getInstance().registerWorldLastRenderer(renderer);
    }
}

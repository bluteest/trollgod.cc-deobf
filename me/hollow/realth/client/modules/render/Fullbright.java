package me.hollow.realth.client.modules.render;

import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.client.modules.Module;

@ModuleManifest(label="Fullbright", category=Module.Category.RENDER, listen=false)
public class Fullbright extends Module {

    public final static Fullbright INSTANCE = new Fullbright();

    private float oldGamma = -1;

    @Override
    public void onDisable() {
        if (oldGamma != -1) {
            mc.gameSettings.gammaSetting = oldGamma;
            mc.renderGlobal.loadRenderers();
        }
    }

    @Override
    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100F;
    }

}
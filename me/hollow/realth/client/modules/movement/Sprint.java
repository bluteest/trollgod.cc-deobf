package me.hollow.realth.client.modules.movement;

import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.api.property.Setting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleManifest(label="Sprint", category= Module.Category.MOVEMENT)
public class Sprint
        extends Module {
    private final Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.RAGE));


    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() == mc.player) {
            switch (mode.getValue()) {
                case RAGE:
                    if ((mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) && !(mc.player.isSneaking() || mc.player.collidedHorizontally || mc.player.getFoodStats().getFoodLevel() <= 6f)) {
                        mc.player.setSprinting(true);
                    }
                    break;
                case LEGIT:
                    if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.isSneaking() || mc.player.isHandActive() || mc.player.collidedHorizontally || mc.player.getFoodStats().getFoodLevel() <= 6f) && mc.currentScreen == null) {
                        mc.player.setSprinting(true);
                    }
                    break;
            }
        }
    }

    public static enum Mode {
        LEGIT,

        RAGE

    }

}
package me.hollow.realth.client.modules.movement;

import me.hollow.realth.JordoHack;
import me.hollow.realth.client.events.MoveEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.EntityUtil;
import me.hollow.realth.api.util.MovementUtil;
import net.b0at.api.event.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@ModuleManifest(label = "InstantSpeed", category = Module.Category.MOVEMENT)
public class InstantSpeed
        extends Module {
    Setting<Boolean> noLiquid = this.register(new Setting<Boolean>("NoLiquid", true));
    @Override
    public void onToggle() {
        if (fullNullCheck()) {
            return;
        }
    }

    @EventHandler
    public void onMove(MoveEvent e) {
        if (this.isDisabled() || mc.player.isElytraFlying()) {
            return;
        }
        if (this.noLiquid.getValue() && EntityUtil.isInLiquid() || mc.player.capabilities.isFlying) {
            return;
        }
        MovementUtil.strafe(e, MovementUtil.getSpeed());
    }
}

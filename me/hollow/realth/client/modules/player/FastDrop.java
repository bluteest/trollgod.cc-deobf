package me.hollow.realth.client.modules.player;

import me.hollow.realth.client.events.UpdateEvent;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.b0at.api.event.EventHandler;

@ModuleManifest(label="FastDrop", category=Module.Category.PLAYER)
public class FastDrop
        extends Module {

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (this.mc.player == null || this.mc.world == null || this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.isOnLadder()) {
            return;
        }
        if (this.mc.player.onGround) {
            this.mc.player.motionY -= (double)((float)20 / 10.0f);
        }
    }
}
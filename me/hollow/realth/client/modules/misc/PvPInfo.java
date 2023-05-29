package me.hollow.realth.client.modules.misc;

import me.hollow.realth.JordoHack;
import me.hollow.realth.api.property.Setting;
import me.hollow.realth.api.util.ColorUtil;
import me.hollow.realth.api.util.CombatUtil;
import me.hollow.realth.api.util.ItemUtil;
import me.hollow.realth.client.modules.Module;
import me.hollow.realth.client.modules.ModuleManifest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;


@ModuleManifest(label="PvPInfo", category= Module.Category.MISC)
public class PvPInfo extends Module {
        private final Setting<Integer> pvpInfoY = this.register(new Setting<Object>("Y", Integer.valueOf(60), Integer.valueOf(0), Integer.valueOf(500)));
        private final Setting<Integer> pvpInfoX = this.register(new Setting<Object>("X", Integer.valueOf(60), Integer.valueOf(2), Integer.valueOf(500)));
        public Setting<Float> breakRange = this.register(new Setting<Object>("Break Range", 5f , 1f, 6f));
        public Setting<Float> placeRange = this.register(new Setting<Float>("Place Range", 5f, 1f, 6f));


    @Override
        public void onRender2D() {
            String[] array = new String[]{"HTR", "PLR", String.valueOf(ItemUtil.getItemCount(Items.TOTEM_OF_UNDYING)), "PING " + (mc.getConnection() != null && mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) != null ? Integer.valueOf(mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime()) : "-1"), "LBY"};
            EntityPlayer player = CombatUtil.getTarget(breakRange.getValue().intValue());
            int offset = 0;
            for (int i = 0; i < 5; ++i) {
                String s = array[i];
                JordoHack.fontManager.drawString(s, this.pvpInfoX.getValue(), this.pvpInfoY.getValue() + offset, this.getColor(s, player), false);
                offset += 9;
            }
        }

        private int getColor(String s, EntityPlayer entityPlayer) {
            int green = 65280;
            int red = 0xFF0000;
            switch (s) {
                case "HTR": {
                    if (entityPlayer != null && mc.player.getDistance((Entity)entityPlayer) < breakRange.getValue()) {
                        return 65280;
                    }
                    return 0xFF0000;
                }
                case "PLR": {
                    if (entityPlayer != null && mc.player.getDistance((Entity)entityPlayer) < placeRange.getValue()) {
                        return 65280;
                    }
                    return 0xFF0000;
                }
                case "LBY": {
                    if (entityPlayer != null) {
                        int holeInfo = CombatUtil.isInHoleInt(entityPlayer);
                        if (holeInfo == 0) {
                            return 0xFF0000;
                        }
                        if (holeInfo == 1) {
                            return 65280;
                        }
                        if (holeInfo == 2) {
                            return 16727040;
                        }
                    }
                    return 0xFF0000;
                }
            }
            if (s.startsWith("PING")) {
                int num = Integer.parseInt(s.substring(5));
                if (num >= 100) {
                    return 0xFF0000;
                }
                return 65280;
            }
            int num = Integer.parseInt(s);
            if (num > 0) {
                return 65280;
            }
            return 0xFF0000;
        }
    }

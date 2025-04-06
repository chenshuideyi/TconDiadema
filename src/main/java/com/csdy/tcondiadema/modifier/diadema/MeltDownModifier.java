package com.csdy.tcondiadema.modifier.diadema;

import com.csdy.tcondiadema.modifier.DiademaModifier;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.sounds.SoundsRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class MeltDownModifier extends DiademaModifier{
    @Override
    public void onEquip(@NotNull IToolStackView tool, @NotNull ModifierEntry entry, EquipmentChangeContext context) {
        LivingEntity entity = context.getEntity();
        if (entity instanceof Player player) {
                if (context.getChangedSlot().isArmor()) {
                    player.level.playSound(player, player.blockPosition, SoundsRegister.MELTDOWN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    super.onEquip(tool,entry,context);
                }


        }
    }

    @Override
    protected DiademaType getDiademaType() {
        return DiademaRegister.MELT_DOWN.get();
    }
}

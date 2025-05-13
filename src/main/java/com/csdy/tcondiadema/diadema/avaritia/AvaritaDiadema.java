package com.csdy.tcondiadema.diadema.avaritia;


import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import com.csdy.tcondiadema.network.ParticleSyncing;
import com.csdy.tcondiadema.network.packets.AvaritaPacket;
import com.csdy.tcondiadema.sounds.SoundsRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AvaritaDiadema extends Diadema {
    static final double RADIUS = 6;

    public AvaritaDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }
    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override protected void perTick() {
        // 像这样就能获取到受影响的方块了，方块集合不自动更新所以得重复获取
        var blocks = range.getAffectingBlocks();
        //java比较弱智，Stream不能用来for,只能foreach
        blocks.forEach(blockPos -> {
            if (getLevel().getBlockState(blockPos).getBlock() == Blocks.AIR || getLevel().getBlockState(blockPos).getBlock() == Blocks.GOLD_BLOCK) return;
            getLevel().setBlockAndUpdate(blockPos, Blocks.GOLD_BLOCK.defaultBlockState());
        });
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (!isAlive() || !isPlayer() || event.getEntity() != getEntity()) {
            return;
        }
        // 获取玩家和捡起的物品
        Player player = (Player) event.getEntity();
        ItemStack itemStack = event.getItem().getItem();

        // 检查捡起的物品是否为金块
        if (itemStack.getItem() == Items.GOLD_BLOCK) {
            // 取消捡起行为
            event.setCanceled(true);
            event.getItem().discard();
            float currentAbsorption = player.getAbsorptionAmount();
            float newAbsorption = currentAbsorption + 8.0F; // 每次增加 4 颗黄心（8点吸收生命值）
            player.setAbsorptionAmount(newAbsorption);
            //发包
            player.level.playSound(null, player.blockPosition, SoundsRegister.LOLI_SUCCRSS.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}

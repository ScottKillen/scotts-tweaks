package com.scottkillen.mod.scottstweaks.tweaks.planting;

import com.scottkillen.mod.koresample.ForgeEventListener;
import com.scottkillen.mod.scottstweaks.config.Settings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

public final class Planter extends ForgeEventListener
{
    @SuppressWarnings({ "NumericCastThatLosesPrecision", "MethodMayBeStatic" })
    @SubscribeEvent
    public void onItemExpire(ItemExpireEvent event)
    {
        if (Settings.INSTANCE.doPlantGrowable())
        {
            final Item item = event.entityItem.getEntityItem().getItem();
            final Block block = Block.getBlockFromItem(item);

            if (block instanceof IGrowable)
            {
                final World world = event.entityItem.worldObj;
                final int x = (int) event.entityItem.posX;
                final int y = (int) event.entityItem.posY;
                final int z = (int) event.entityItem.posZ;
                final int metadata = event.entityItem.getEntityItem().getItemDamage();

                plantGrowable(world, x, y, z, block, metadata);
            }
        }
    }

    private static void plantGrowable(World world, int x, int y, int z, Block block, int metadata)
    {
        if (block.canPlaceBlockAt(world, x, y, z))
        {
            world.setBlock(x, y, z, block);
            world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
            world.scheduleBlockUpdate(x, y, z, block, 1);
        }
    }
}

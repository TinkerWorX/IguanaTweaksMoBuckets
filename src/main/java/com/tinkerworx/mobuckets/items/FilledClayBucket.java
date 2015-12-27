package com.tinkerworx.mobuckets.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.tinkerworx.mobuckets.MoBucketsMod;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class FilledClayBucket extends Item
{
    private Block contents;

    public FilledClayBucket(Block contents)
    {
        this.maxStackSize = 1;
        this.contents = contents;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        boolean isEmpty = this.contents == Blocks.air;
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, isEmpty);

        if (movingobjectposition == null)
        {
            return itemStack;
        }
        else
        {
            FillBucketEvent event = new FillBucketEvent(player, itemStack, world, movingobjectposition);
            if (MinecraftForge.EVENT_BUS.post(event))
            {
                return itemStack;
            }

            if (event.getResult() == Event.Result.ALLOW)
            {
                if (player.capabilities.isCreativeMode)
                {
                    return itemStack;
                }

                if (--itemStack.stackSize <= 0)
                {
                    return event.result;
                }

                if (!player.inventory.addItemStackToInventory(event.result))
                {
                    player.dropPlayerItemWithRandomChoice(event.result, false);
                }

                return itemStack;
            }
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, x, y, z))
                {
                    return itemStack;
                }
                else
                {
                    if (this.contents == Blocks.air)
                    {
                        return new ItemStack(MoBucketsMod.clayBucket);
                    }

                    if (movingobjectposition.sideHit == 0)
                    {
                        --y;
                    }

                    if (movingobjectposition.sideHit == 1)
                    {
                        ++y;
                    }

                    if (movingobjectposition.sideHit == 2)
                    {
                        --z;
                    }

                    if (movingobjectposition.sideHit == 3)
                    {
                        ++z;
                    }

                    if (movingobjectposition.sideHit == 4)
                    {
                        --x;
                    }

                    if (movingobjectposition.sideHit == 5)
                    {
                        ++x;
                    }

                    if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack))
                    {
                        return itemStack;
                    }

                    if (this.tryPlaceContainedLiquid(world, x, y, z) && !player.capabilities.isCreativeMode)
                    {
                        return new ItemStack(MoBucketsMod.clayBucket);
                    }
                }
            }

            return itemStack;
        }
    }

    private ItemStack func_150910_a(ItemStack itemStack, EntityPlayer player, Item item)
    {
        if (player.capabilities.isCreativeMode)
        {
            return itemStack;
        }
        else if (--itemStack.stackSize <= 0)
        {
            return new ItemStack(item);
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(item)))
            {
                player.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, 0), false);
            }

            return itemStack;
        }
    }

    /**
     * Attempts to place the liquid contained inside the bucket.
     */
    public boolean tryPlaceContainedLiquid(World world, int x, int y, int z)
    {
        if (this.contents == Blocks.air)
        {
            return false;
        }
        else
        {
            Material material = world.getBlock(x, y, z).getMaterial();
            boolean flag = !material.isSolid();

            if (!world.isAirBlock(x, y, z) && !flag)
            {
                return false;
            }
            else
            {
                if (!world.isRemote && flag && !material.isLiquid())
                {
                    world.func_147480_a(x, y, z, true);
                }

                world.setBlock(x, y, z, this.contents, 0, 3);

                return true;
            }
        }
    }
}
package com.tinkerworx.mobuckets;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ClayBucketEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void bucketFill (FillBucketEvent event)
    {
        if (event.current.getItem() == MoBucketsMod.clayBucket && event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            if(event.getResult() != Event.Result.DEFAULT)
                return;
            
            int x = event.target.blockX;
            int y = event.target.blockY;
            int z = event.target.blockZ;

            if (event.entityPlayer != null && !event.entityPlayer.canPlayerEdit(x, y, z, event.target.sideHit, event.current))
                return;

            Block block = event.world.getBlock(x, y, z);
            
            if(MoBucketsMod.hasMFR)
            {
                if (block == MoBucketsMod.sewageStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketSewage);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.sludgeStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketSludge);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.mobEssenceStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketMobEssence);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.bioFuelStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketBioFuel);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.meatStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketMeat);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.pinkSlimeStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketPinkSlime);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.chocolateMilkStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketChocolateMilk);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.mushroomSoupStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketMushroomSoup);
                    event.setResult(Event.Result.ALLOW);
                }
            }
            
            if(MoBucketsMod.hasBC)
            {
                if (block == MoBucketsMod.oilStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketOil);
                    event.setResult(Event.Result.ALLOW);
                }
                else if (block == MoBucketsMod.fuelStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketFuel);
                    event.setResult(Event.Result.ALLOW);
                }
            }
            
            if(MoBucketsMod.hasEN)
            {
                if (block == MoBucketsMod.witchWaterStill)
                {
                    event.world.setBlockToAir(x, y, z);
                    event.result = new ItemStack(MoBucketsMod.clayBucketWitchWater);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }
}

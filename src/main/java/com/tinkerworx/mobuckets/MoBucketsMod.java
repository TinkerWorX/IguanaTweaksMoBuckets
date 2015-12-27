package com.tinkerworx.mobuckets;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tinkerworx.mobuckets.items.FilledClayBucket;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MoBucketsMod.MODID, version = MoBucketsMod.VERSION, 
    dependencies = 
        "required-before:" + MoBucketsMod.IGT_MODID + ";" + // this should be "required-after"
        "after:" + MoBucketsMod.MFR_MODID + ";" +
        "after:" + MoBucketsMod.BC_MODID + ";")
public class MoBucketsMod
{
    public static ClayBucketEvents claybucketHandler;
    
    // core
    public static final String MODID = "MoBuckets";
    public static final String VERSION = "0.01";
    
    // tinkers construct iguana tweaks
    public static final String IGT_MODID = "IguanaTweaksTConstruct";
    public static Item clayBucket;
    
    // minefactory reloaded
    public static final String MFR_MODID = "MineFactoryReloaded";
    public static boolean hasMFR;
    
    public static Fluid bioFuel;
    public static Block bioFuelStill;
    public static Item clayBucketBioFuel;
    
    public static Fluid chocolateMilk;
    public static Block chocolateMilkStill;
    public static Item clayBucketChocolateMilk;

    public static Fluid meat;
    public static Block meatStill;
    public static Item clayBucketMeat;

    public static Fluid mobEssence;
    public static Block mobEssenceStill;
    public static Item clayBucketMobEssence;

    public static Fluid mushroomSoup;
    public static Block mushroomSoupStill;
    public static Item clayBucketMushroomSoup;

    public static Fluid pinkSlime;
    public static Block pinkSlimeStill;
    public static Item clayBucketPinkSlime;

    public static Fluid sewage;
    public static Block sewageStill;
    public static Item clayBucketSewage;

    public static Fluid sludge;
    public static Block sludgeStill;
    public static Item clayBucketSludge;

    // buildcraft
    public static final String BC_MODID = "BuildCraft|Energy";
    public static boolean hasBC;
    
    public static Fluid oil;
    public static Block oilStill;
    public static Item clayBucketOil;
    
    public static Fluid fuel;
    public static Block fuelStill;
    public static Item clayBucketFuel;
    
    // ex nihilo
    public static final String EN_MODID = "exnihilo";
    public static boolean hasEN;
    
    public static Fluid witchWater;
    public static Block witchWaterStill;
    public static Item clayBucketWitchWater;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Logger logger = LogManager.getLogger();
        
        hasMFR = Loader.isModLoaded(MFR_MODID);
        hasBC = Loader.isModLoaded(BC_MODID);
        hasEN = Loader.isModLoaded(EN_MODID);
        
        if(!hasMFR && !hasBC && !hasEN){
            logger.log(Level.WARN, "No supported mods detected");
            return;
        }
        
        clayBucket = GameRegistry.findItem(IGT_MODID, "clayBucketFired");
        claybucketHandler = new ClayBucketEvents();
        MinecraftForge.EVENT_BUS.register(claybucketHandler);
        
        // minefactory reloaded
        if(hasMFR)
        {
            logger.log(Level.INFO, "MineFactory Reloaded detected");
            
            // sewage
            sewage = FluidRegistry.getFluid("sewage");
            sewageStill = GameRegistry.findBlock(MFR_MODID, "sewage.still");
            if(sewage != null && sewageStill != null)
            {
                clayBucketSewage = new FilledClayBucket(sewageStill).setTextureName("mobuckets:clayBucket_sewage").setUnlocalizedName("clayBucketSewage");
                GameRegistry.registerItem(clayBucketSewage, "clayBucketSewage");
                FluidContainerRegistry.registerFluidContainer(sewage, new ItemStack(clayBucketSewage), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: sewage");
            }
            else
            {
                logger.log(Level.WARN, "Error: sewage");
            }
            
            // sludge
            sludge = FluidRegistry.getFluid("sludge");
            sludgeStill = GameRegistry.findBlock(MFR_MODID, "sludge.still");
            if(sewage != null && sewageStill != null)
            {
                clayBucketSludge = new FilledClayBucket(sludgeStill).setTextureName("mobuckets:clayBucket_sludge").setUnlocalizedName("clayBucketSludge");
                GameRegistry.registerItem(clayBucketSludge, "clayBucketSludge");
                FluidContainerRegistry.registerFluidContainer(sludge, new ItemStack(clayBucketSludge), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: sludge");
            }
            else
            {
                logger.log(Level.WARN, "Error: sludge");
            }
            
            // mob essence
            mobEssence = FluidRegistry.getFluid("mobessence");
            mobEssenceStill = GameRegistry.findBlock(MFR_MODID, "mobessence.still");
            if(mobEssence != null && mobEssenceStill != null)
            {
                clayBucketMobEssence = new FilledClayBucket(mobEssenceStill).setTextureName("mobuckets:clayBucket_mobessence").setUnlocalizedName("clayBucketMobEssence");
                GameRegistry.registerItem(clayBucketMobEssence, "clayBucketMobEssence");
                FluidContainerRegistry.registerFluidContainer(mobEssence, new ItemStack(clayBucketMobEssence), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: mobessence");
            }
            else
            {
                logger.log(Level.WARN, "Error: mobessence");
            }
            
            // biofuel
            bioFuel = FluidRegistry.getFluid("biofuel");
            bioFuelStill = GameRegistry.findBlock(MFR_MODID, "biofuel.still");
            if(bioFuel != null && bioFuelStill != null)
            {
                clayBucketBioFuel = new FilledClayBucket(bioFuelStill).setTextureName("mobuckets:clayBucket_biofuel").setUnlocalizedName("clayBucketBioFuel");
                GameRegistry.registerItem(clayBucketBioFuel, "clayBucketBioFuel");
                FluidContainerRegistry.registerFluidContainer(bioFuel, new ItemStack(clayBucketBioFuel), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: biofuel");
            }
            else
            {
                logger.log(Level.WARN, "Error: biofuel");
            }
            
            // liquid meat
            meat = FluidRegistry.getFluid("meat");
            meatStill = GameRegistry.findBlock(MFR_MODID, "meat.still");
            if(meat != null && meatStill != null)
            {
                clayBucketMeat = new FilledClayBucket(meatStill).setTextureName("mobuckets:clayBucket_meat").setUnlocalizedName("clayBucketMeat");
                GameRegistry.registerItem(clayBucketMeat, "clayBucketMeat");
                FluidContainerRegistry.registerFluidContainer(meat, new ItemStack(clayBucketMeat), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: meat");
            }
            else
            {
                logger.log(Level.WARN, "Error: meat");
            }
            
            // liquid pink slime
            pinkSlime = FluidRegistry.getFluid("pinkslime");
            pinkSlimeStill = GameRegistry.findBlock(MFR_MODID, "pinkslime.still");
            if(pinkSlime != null && pinkSlimeStill != null)
            {
                clayBucketPinkSlime = new FilledClayBucket(pinkSlimeStill).setTextureName("mobuckets:clayBucket_pinkslime").setUnlocalizedName("clayBucketPinkSlime");
                GameRegistry.registerItem(clayBucketPinkSlime, "clayBucketPinkSlime");
                FluidContainerRegistry.registerFluidContainer(pinkSlime, new ItemStack(clayBucketPinkSlime), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: pinkslime");
            }
            else
            {
                logger.log(Level.WARN, "Error: pinkslime");
            }
            
            // chocolate milk
            chocolateMilk = FluidRegistry.getFluid("chocolatemilk");
            chocolateMilkStill = GameRegistry.findBlock(MFR_MODID, "chocolatemilk.still");
            if(chocolateMilk != null && chocolateMilkStill != null)
            {
                clayBucketChocolateMilk = new FilledClayBucket(chocolateMilkStill).setTextureName("mobuckets:clayBucket_chocolatemilk").setUnlocalizedName("clayBucketChocolateMilk");
                GameRegistry.registerItem(clayBucketChocolateMilk, "clayBucketChocolateMilk");
                FluidContainerRegistry.registerFluidContainer(chocolateMilk, new ItemStack(clayBucketChocolateMilk), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: chocolateMilk");
            }
            else
            {
                logger.log(Level.WARN, "Error: chocolateMilk");
            }
            
            // mushroom soup
            mushroomSoup = FluidRegistry.getFluid("mushroomsoup");
            mushroomSoupStill = GameRegistry.findBlock(MFR_MODID, "mushroomsoup.still");
            if(mushroomSoup != null && mushroomSoupStill != null)
            {
                clayBucketMushroomSoup = new FilledClayBucket(mushroomSoupStill).setTextureName("mobuckets:clayBucket_mushroomsoup").setUnlocalizedName("clayBucketMushroomSoup");
                GameRegistry.registerItem(clayBucketMushroomSoup, "clayBucketMushroomSoup");
                FluidContainerRegistry.registerFluidContainer(mushroomSoup, new ItemStack(clayBucketMushroomSoup), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: mushroomsoup");
            }
            else
            {
                logger.log(Level.WARN, "Error: mushroomsoup");
            }
        }

        // buildcraft
        if(hasBC)
        {
            logger.log(Level.INFO, "BuildCraft Energy detected");
            
            // oil
            oil = FluidRegistry.getFluid("oil");
            oilStill = GameRegistry.findBlock(BC_MODID, "blockOil");
            if(oil != null && oilStill != null)
            {
                clayBucketOil = new FilledClayBucket(oilStill).setTextureName("mobuckets:clayBucket_oil").setUnlocalizedName("clayBucketOil");
                GameRegistry.registerItem(clayBucketOil, "clayBucketOil");
                FluidContainerRegistry.registerFluidContainer(oil, new ItemStack(clayBucketOil), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: oil");
            }
            else
            {
                logger.log(Level.WARN, "Error: oil");
            }
            
            // fuel
            fuel = FluidRegistry.getFluid("fuel");
            fuelStill = GameRegistry.findBlock(BC_MODID, "blockFuel");
            if(fuel != null && fuelStill != null)
            {
                clayBucketFuel = new FilledClayBucket(fuelStill).setTextureName("mobuckets:clayBucket_fuel").setUnlocalizedName("clayBucketFuel");
                GameRegistry.registerItem(clayBucketFuel, "clayBucketFuel");
                FluidContainerRegistry.registerFluidContainer(fuel, new ItemStack(clayBucketFuel), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: fuel");
            }
            else
            {
                logger.log(Level.WARN, "Error: fuel");
            }
        }

        // ex nihilo
        if(hasEN)
        {
            logger.log(Level.INFO, "Ex Nihilo detected");
            
            // witch water
            witchWater = FluidRegistry.getFluid("witchwater");
            witchWaterStill = GameRegistry.findBlock(EN_MODID, "witchwater");
            if(witchWater != null && witchWaterStill != null)
            {
                clayBucketWitchWater = new FilledClayBucket(witchWaterStill).setTextureName("mobuckets:clayBucket_witchwater").setUnlocalizedName("clayBucketWitchWater");
                GameRegistry.registerItem(clayBucketWitchWater, "clayBucketWitchWater");
                FluidContainerRegistry.registerFluidContainer(witchWater, new ItemStack(clayBucketWitchWater), new ItemStack(clayBucket));
                logger.log(Level.INFO, "Added: witchwater");
            }
            else
            {
                logger.log(Level.WARN, "Error: witchwater");
            }
        }
    }
}

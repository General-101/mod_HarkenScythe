package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventHookContainerClassHarkenScythe
{
    Side side = FMLCommonHandler.instance().getEffectiveSide();

    @ForgeSubscribe
    public void livingHurt(LivingHurtEvent var1)
    {
        EntityLiving var2;
        int var3;

        if (var1.source.isProjectile() && var1.source.getEntity() instanceof EntityLiving)
        {
            var2 = (EntityLiving)var1.source.getEntity();

            if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBlightAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
            {
                var3 = EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBlightAug.effectId, var2.getCurrentItemOrArmor(0));
                var1.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.id, 5 * var3 * 20, 0));
            }

            if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSHemorrhageAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
            {
                var3 = EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSHemorrhageAug.effectId, var2.getCurrentItemOrArmor(0));

                if (this.mobBloodCheck(var1.entityLiving) != 0)
                {
                    var1.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 5 * var3 * 20, 0));
                }
            }
        }

        int var5;

        if (var1.source.getEntity() instanceof EntityLiving)
        {
            var2 = (EntityLiving)var1.source.getEntity();

            if (var2.getCurrentItemOrArmor(0) != null)
            {
                if (var2.getCurrentItemOrArmor(0).getItem() instanceof ItemHSGlaive)
                {
                    ItemHSGlaive var11 = (ItemHSGlaive)var2.getCurrentItemOrArmor(0).getItem();

                    if (var11.glaiveCharged)
                    {
                        this.bloodImpale(var1, var2, var11.bloodPoolChance);
                    }
                }

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodlettingAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
                {
                    var3 = EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodlettingAug.effectId, var2.getCurrentItemOrArmor(0));
                    Random var4 = new Random();
                    var5 = var4.nextInt(100) + 1;

                    if (var3 * 10 + 1 >= var5)
                    {
                        this.bloodImpale(var1, var2, var3 - 1);
                    }
                }
            }
        }

        if (var1.entityLiving instanceof EntityPlayer)
        {
            EntityPlayer var10 = (EntityPlayer)var1.entityLiving;

            if (!var10.worldObj.isRemote && var10.worldObj.getMoonPhase() == 4 && !var10.worldObj.isDaytime() && var1.source.getEntity() instanceof EntityMob && var10.dimension == 0)
            {
                var1.ammount *= 3;
            }

            var3 = ItemHSSoulweaveArmor.soulweaveArmorCheck(var10);

            if (var3 >= 4 && (var10.isPotionActive(Potion.harm.id) || var1.source.damageType == "indirectMagic"))
            {
                var1.ammount = 0;
            }

            int var6;
            int var12;

            if ((var1.source.damageType == "magic" || var1.source.damageType == "wither") && EnchantmentHSExude.getTotalEnchantmentLvl(var10) > 0)
            {
                var12 = EnchantmentHSExude.getTotalEnchantmentLvl(var10);
                Random var14 = new Random();
                var6 = var14.nextInt(100) + 1;

                if (var12 * 4 >= var6)
                {
                    var1.ammount = 0;
                }
            }

            if (var1.ammount > 0)
            {
                boolean var13 = false;
                var5 = 25 - var10.getTotalArmorValue();
                var6 = var1.ammount * var5 + var10.carryoverDamage;
                var12 = var6 / 25;

                if (var12 > 0)
                {
                    if (EnchantmentHSVitality.getTotalEnchantmentLvl(var10) > 0)
                    {
                        int var7 = EnchantmentHSVitality.getTotalEnchantmentLvl(var10);

                        if (var12 <= var10.getFoodStats().getFoodLevel())
                        {
                            Random var8 = new Random();
                            int var9 = var8.nextInt(100) + 1;

                            if (var7 * 5 >= var9)
                            {
                                if (var10.getFoodStats().getSaturationLevel() > 0.0F)
                                {
                                    var10.getFoodStats().addExhaustion((float)var12 * var10.getFoodStats().getSaturationLevel());
                                }
                                else
                                {
                                    var10.getFoodStats().addExhaustion((float)var12);
                                    var1.ammount = 0;
                                }
                            }
                        }
                    }

                    if (var12 >= var10.getHealth())
                    {
                        var1.ammount = ItemHSTalisman.talismanActivate(var12, var10);
                    }
                }
            }
        }
    }

    @ForgeSubscribe
    public void LivingDrops(LivingDropsEvent var1)
    {
        if (var1.source.getEntity() instanceof EntityLiving)
        {
            EntityLiving var2 = (EntityLiving)var1.source.getEntity();

            if (var2.getCurrentItemOrArmor(0) != null)
            {
                int var3;
                Random var4;
                int var5;

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSAfterlifeAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
                {
                    var3 = EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSAfterlifeAug.effectId, var2.getCurrentItemOrArmor(0));
                    var4 = new Random();
                    var5 = var4.nextInt(100) + 1;

                    if (var3 * 10 + 1 >= var5)
                    {
                        this.augmentAfterlife(var1);
                    }
                }

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSDecapitateAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
                {
                    var3 = EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSDecapitateAug.effectId, var2.getCurrentItemOrArmor(0));
                    var4 = new Random();
                    var5 = var4.nextInt(100) + 1;

                    if (var3 * 10 + 1 >= var5)
                    {
                        this.augmentDecapitate(var1);
                    }
                }

                if (var2 instanceof EntityHSHarbinger)
                {
                    this.harbingerSoulReap(var1);
                    return;
                }

                if (var2.getCurrentItemOrArmor(0).getItem() instanceof ItemHSScythe)
                {
                    ItemHSScythe var6 = (ItemHSScythe)var2.getCurrentItemOrArmor(0).getItem();

                    if (var6.scytheCharged)
                    {
                        byte var7 = 0;

                        if (var2.getCurrentItemOrArmor(0).getItem() == mod_HarkenScythe.HSScytheSoulReaper)
                        {
                            var7 = 1;
                        }

                        this.soulReaping(var1, var7);
                        return;
                    }
                }

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulstealAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
                {
                    var3 = EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulstealAug.effectId, var2.getCurrentItemOrArmor(0));
                    var4 = new Random();
                    var5 = var4.nextInt(100) + 1;

                    if (var3 * 33 + 1 >= var5)
                    {
                        this.soulReaping(var1, 0);
                    }

                    return;
                }
            }
        }
    }

    public void augmentAfterlife(LivingDropsEvent var1)
    {
        int var2 = EntityList.getEntityID(var1.entity);

        if (var2 > 0 && EntityList.entityEggs.containsKey(Integer.valueOf(var2)))
        {
            ItemStack var3 = new ItemStack(Item.monsterPlacer, 1, var2);
            var1.entityLiving.entityDropItem(var3, 0.0F);
        }
    }

    public void augmentDecapitate(LivingDropsEvent var1)
    {
        if (var1.entityLiving instanceof EntityGolem)
        {
            var1.entityLiving.entityDropItem(new ItemStack(Block.pumpkin, 1), 0.0F);
        }

        if (var1.entityLiving instanceof EntitySkeleton)
        {
            EntitySkeleton var2 = (EntitySkeleton)var1.entityLiving;

            if (var2.getSkeletonType() == 1)
            {
                var1.entityLiving.entityDropItem(new ItemStack(Item.skull, 1, 1), 0.0F);
            }
            else
            {
                var1.entityLiving.entityDropItem(new ItemStack(Item.skull, 1, 0), 0.0F);
            }
        }

        if (var1.entityLiving instanceof EntityZombie)
        {
            EntityZombie var3 = (EntityZombie)var1.entityLiving;

            if (var1.entityLiving instanceof EntityPigZombie)
            {
                var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 3), 0.0F);
            }
            else if (var3.isVillager())
            {
                var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 4), 0.0F);
            }
            else
            {
                var1.entityLiving.entityDropItem(new ItemStack(Item.skull, 1, 2), 0.0F);
            }
        }

        if (var1.entityLiving instanceof EntityCreeper)
        {
            var1.entityLiving.entityDropItem(new ItemStack(Item.skull, 1, 4), 0.0F);
        }

        if (var1.entityLiving instanceof EntityPlayer)
        {
            var1.entityLiving.entityDropItem(new ItemStack(Item.skull, 1, 3), 0.0F);
        }

        if (var1.entityLiving instanceof EntitySpider)
        {
            if (var1.entityLiving instanceof EntityCaveSpider)
            {
                var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 1), 0.0F);
            }
            else
            {
                var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 0), 0.0F);
            }
        }

        if (var1.entityLiving instanceof EntityEnderman)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 2), 0.0F);
        }

        if (var1.entityLiving instanceof EntityVillager || var1.entityLiving instanceof EntityWitch)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 5), 0.0F);
        }

        if (var1.entityLiving instanceof EntityCow)
        {
            if (var1.entityLiving instanceof EntityMooshroom)
            {
                var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 7), 0.0F);
            }
            else
            {
                var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 6), 0.0F);
            }
        }

        if (var1.entityLiving instanceof EntityPig)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 8), 0.0F);
        }

        if (var1.entityLiving instanceof EntitySquid)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 9), 0.0F);
        }

        if (var1.entityLiving instanceof EntitySheep)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 10), 0.0F);
        }

        if (var1.entityLiving instanceof EntityWolf)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 11), 0.0F);
        }

        if (var1.entityLiving instanceof EntityOcelot)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 12), 0.0F);
        }

        if (var1.entityLiving instanceof EntityDragon)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 13), 0.0F);
        }

        if (var1.entityLiving instanceof EntityBat)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 14), 0.0F);
        }

        if (var1.entityLiving instanceof EntityChicken)
        {
            var1.entityLiving.entityDropItem(new ItemStack(mod_HarkenScythe.HSSkull, 1, 15), 0.0F);
        }
    }

    public void soulReaping(LivingDropsEvent var1, int var2)
    {
        boolean var3 = true;
        byte var4 = 1;

        if (!var1.entityLiving.worldObj.isRemote && var1.entityLiving.worldObj.getMoonPhase() == 4 && !var1.entityLiving.worldObj.isDaytime() && var1.entityLiving.dimension == 0)
        {
            var4 = 3;
        }

        int var7;

        if (!(var1.entityLiving instanceof EntityDragon) && !(var1.entityLiving instanceof EntityWither) && var1.entityLiving.getMaxHealth() < 200)
        {
            if (!(var1.entityLiving instanceof EntityHSSlime))
            {
                if (!(var1.entityLiving instanceof EntityGolem))
                {
                    if (!(var1.entityLiving instanceof EntityHSSpectralMinerEvil))
                    {
                        if (!(var1.entityLiving instanceof EntityHSSoulLost))
                        {
                            if (!(var1.entityLiving instanceof EntityHSSoul))
                            {
                                if (!this.isSpectralPet(var1.entityLiving))
                                {
                                    if (!var1.entityLiving.isChild())
                                    {
                                        if (var1.entityLiving instanceof EntitySlime)
                                        {
                                            EntitySlime var8 = (EntitySlime)var1.entityLiving;
                                            int var6 = var8.getSlimeSize();

                                            if (var6 < 3)
                                            {
                                                return;
                                            }
                                        }

                                        if (var1.entityLiving instanceof EntityHSHarbinger)
                                        {
                                            var7 = 10 * (var4 + var2);
                                            var1.entityLiving.worldObj.playAuxSFX(2002, (int)Math.round(var1.entityLiving.posX - 0.5D), (int)Math.round(var1.entityLiving.posY + 0.5D), (int)Math.round(var1.entityLiving.posZ), 0);
                                            EntityHSSoulCulled var11 = new EntityHSSoulCulled(var1.entityLiving.worldObj, (double)((int)Math.round(var1.entityLiving.posX - 0.5D)), (double)((int)Math.round(var1.entityLiving.posY + 0.5D)), (double)((int)Math.round(var1.entityLiving.posZ)), var7, var1.entityLiving);
                                            var1.entityLiving.worldObj.spawnEntityInWorld(var11);
                                        }
                                        else if (var1.entityLiving instanceof EntityPlayer)
                                        {
                                            var7 = 1 * (var4 + var2);
                                            var1.entityLiving.worldObj.playAuxSFX(2002, (int)Math.round(var1.entityLiving.posX - 0.5D), (int)Math.round(var1.entityLiving.posY + 0.5D), (int)Math.round(var1.entityLiving.posZ), 0);
                                            EntityHSSoulGrieving var10 = new EntityHSSoulGrieving(var1.entityLiving.worldObj, (double)((int)Math.round(var1.entityLiving.posX - 0.5D)), (double)((int)Math.round(var1.entityLiving.posY + 0.5D)), (double)((int)Math.round(var1.entityLiving.posZ)), var7, var1.entityLiving);
                                            var1.entityLiving.worldObj.spawnEntityInWorld(var10);
                                        }
                                        else
                                        {
                                            var7 = 1 * (var4 + var2);
                                            var1.entityLiving.worldObj.playAuxSFX(2002, (int)Math.round(var1.entityLiving.posX - 0.5D), (int)Math.round(var1.entityLiving.posY + 0.5D), (int)Math.round(var1.entityLiving.posZ), 0);
                                            EntityHSSoul var9 = new EntityHSSoul(var1.entityLiving.worldObj, (double)((int)Math.round(var1.entityLiving.posX - 0.5D)), (double)((int)Math.round(var1.entityLiving.posY + 0.5D)), (double)((int)Math.round(var1.entityLiving.posZ)), var7, var1.entityLiving);
                                            var1.entityLiving.worldObj.spawnEntityInWorld(var9);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            var7 = 40 * (var4 + var2);
            var1.entityLiving.worldObj.playAuxSFX(2002, (int)Math.round(var1.entityLiving.posX - 0.5D), (int)Math.round(var1.entityLiving.posY + 0.5D), (int)Math.round(var1.entityLiving.posZ), 0);
            EntityHSSoulWrathful var5 = new EntityHSSoulWrathful(var1.entityLiving.worldObj, (double)((int)Math.round(var1.entityLiving.posX - 0.5D)), (double)((int)Math.round(var1.entityLiving.posY + 0.5D)), (double)((int)Math.round(var1.entityLiving.posZ)), var7, var1.entityLiving);
            var1.entityLiving.worldObj.spawnEntityInWorld(var5);
        }
    }

    public void bloodImpale(LivingHurtEvent var1, EntityLiving var2, int var3)
    {
        int var4 = this.mobBloodCheck(var1.entityLiving);

        if (!this.isSpectralPet(var1.entityLiving))
        {
            if (var4 != 0)
            {
                if (var4 != 4)
                {
                    this.setSplashBlockMobCheck(var1.entityLiving.worldObj, mod_HarkenScythe.HSSplashBloodBlock, var4, 2, 5, 2, var1.entityLiving, var1.entityLiving.isChild(), var3);
                }

                var2.worldObj.playSoundAtEntity(var2, "damage.hit", 1.0F, 1.0F);
            }
        }
    }

    public void setSplashBlockMobCheck(World var1, Block var2, int var3, int var4, int var5, int var6, Entity var7, boolean var8, int var9)
    {
        int var10 = 95 - var9 * 2;

        for (int var11 = 0; var11 < var4; ++var11)
        {
            for (int var12 = 0; var12 < var5; ++var12)
            {
                for (int var13 = 0; var13 < var6; ++var13)
                {
                    Random var14 = new Random();
                    Random var15 = new Random();
                    Random var16 = new Random();
                    Random var17 = new Random();
                    Random var18 = new Random();
                    Random var19 = new Random();
                    Random var20 = new Random();
                    Random var21 = new Random();
                    int var22 = var14.nextInt(100) + 1;

                    if (var11 == 0 && var12 == 0 && var13 == 0 && (var7.worldObj.isAirBlock((int)Math.floor(var7.posX), (int)Math.floor(var7.posY), (int)Math.floor(var7.posZ)) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX), (int)Math.floor(var7.posY), (int)Math.floor(var7.posZ)), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX), (int)Math.floor(var7.posY), (int)Math.floor(var7.posZ)))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX), (int)Math.floor(var7.posY) - 1, (int)Math.floor(var7.posZ)))
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX), (int)Math.floor(var7.posY), (int)Math.floor(var7.posZ), var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) + var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) + var13), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) + var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12 - 1, (int)Math.floor(var7.posZ) + var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY + (double)var12), (int)Math.floor(var7.posZ) + var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var15.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) - var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) - var13), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) - var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12 - 1, (int)Math.floor(var7.posZ) - var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY - (double)var12), (int)Math.floor(var7.posZ) - var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var16.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) - var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) - var13), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) - var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12 - 1, (int)Math.floor(var7.posZ) - var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY - (double)var12), (int)Math.floor(var7.posZ) - var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var17.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) + var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) + var13), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) + var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12 - 1, (int)Math.floor(var7.posZ) + var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY + (double)var12), (int)Math.floor(var7.posZ) + var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var18.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) - var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) - var13), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) - var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) + var12 - 1, (int)Math.floor(var7.posZ) - var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY + (double)var12), (int)Math.floor(var7.posZ) - var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var19.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) + var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) + var13), var7.worldObj.getBlockMetadata((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) + var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) - var12 - 1, (int)Math.floor(var7.posZ) + var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY - (double)var12), (int)Math.floor(var7.posZ) + var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var20.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) + var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) + var13), var7.worldObj.getBlockId((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12, (int)Math.floor(var7.posZ) + var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY) - var12 - 1, (int)Math.floor(var7.posZ) + var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) + var11, (int)Math.floor(var7.posY - (double)var12), (int)Math.floor(var7.posZ) + var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }

                    var22 = var21.nextInt(100) + 1;

                    if ((var7.worldObj.isAirBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) - var13) || this.canBloodReplaceBlock(var7.worldObj.getBlockId((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) - var13), var7.worldObj.getBlockId((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12, (int)Math.floor(var7.posZ) - var13))) && var7.worldObj.doesBlockHaveSolidTopSurface((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY) + var12 - 1, (int)Math.floor(var7.posZ) - var13) && var22 >= var10)
                    {
                        var7.worldObj.setBlock((int)Math.floor(var7.posX) - var11, (int)Math.floor(var7.posY + (double)var12), (int)Math.floor(var7.posZ) - var13, var2.blockID, this.bloodRandomizer(var3, var8, var9), 2);
                    }
                }
            }
        }
    }

    public boolean canBloodReplaceBlock(int var1, int var2)
    {
        return var1 == Block.snow.blockID ? true : var1 == mod_HarkenScythe.HSSplashBloodBlock.blockID && (var2 == 4 || var2 == 8 || var2 == 12);
    }

    public int mobBloodCheck(EntityLiving var1)
    {
        byte var2 = 0;
        boolean var3;

        if (!(var1 instanceof EntityWither) && !(var1 instanceof EntityGhast))
        {
            if (!(var1 instanceof EntityEnderman) && !(var1 instanceof EntityDragon))
            {
                if (!(var1 instanceof EntityCreeper) && !(var1 instanceof EntitySpider) && !(var1 instanceof EntitySilverfish))
                {
                    if (!(var1 instanceof EntityBlaze) && !(var1 instanceof EntitySlime) && !(var1 instanceof EntitySkeleton) && !(var1 instanceof EntityGolem))
                    {
                        var3 = true;
                        return 1;
                    }
                    else
                    {
                        return var2;
                    }
                }
                else
                {
                    var3 = true;
                    return 2;
                }
            }
            else
            {
                var3 = true;
                return 3;
            }
        }
        else
        {
            var3 = true;
            return 4;
        }
    }

    public int bloodRandomizer(int var1, boolean var2, int var3)
    {
        int var4 = var1;
        Random var5 = new Random();
        int var6 = var5.nextInt(100) + 1;
        var6 -= var3 * 5;

        if (var2)
        {
            if (var1 == 1)
            {
                var4 = 4;
            }

            if (var1 == 2)
            {
                var4 = 8;
            }

            if (var1 == 3)
            {
                var4 = 12;
            }

            return var4;
        }
        else if (var6 >= 30)
        {
            if (var1 == 1)
            {
                var4 = 4;
            }

            if (var1 == 2)
            {
                var4 = 8;
            }

            if (var1 == 3)
            {
                var4 = 12;
            }

            return var4;
        }
        else if (var6 < 30 && var6 >= 20)
        {
            if (var1 == 1)
            {
                var4 = 3;
            }

            if (var1 == 2)
            {
                var4 = 7;
            }

            if (var1 == 3)
            {
                var4 = 11;
            }

            return var4;
        }
        else if (var6 < 20 && var6 >= 10)
        {
            if (var1 == 1)
            {
                var4 = 2;
            }

            if (var1 == 2)
            {
                var4 = 6;
            }

            if (var1 == 3)
            {
                var4 = 10;
            }

            return var4;
        }
        else
        {
            if (var1 == 1)
            {
                var4 = 1;
            }

            if (var1 == 2)
            {
                var4 = 5;
            }

            if (var1 == 3)
            {
                var4 = 9;
            }

            return var4;
        }
    }

    public boolean isSpectralPet(EntityLiving var1)
    {
        return var1 instanceof EntityHSSpectralChicken ? true : (var1 instanceof EntityHSSpectralCow ? true : (var1 instanceof EntityHSSpectralSheep ? true : (var1 instanceof EntityHSSpectralBat ? true : (var1 instanceof EntityHSSpectralPig ? true : (var1 instanceof EntityHSSpectralWolf ? true : (var1 instanceof EntityHSSpectralOcelot ? true : (var1 instanceof EntityHSSpectralSquid ? true : (var1 instanceof EntityHSSpectralVillager ? true : (var1 instanceof EntityHSSpectralCreeper ? true : (var1 instanceof EntityHSSpectralZombie ? true : (var1 instanceof EntityHSSpectralPigZombie ? true : (var1 instanceof EntityHSSpectralSkeleton ? true : (var1 instanceof EntityHSSpectralSkeletonWither ? true : (var1 instanceof EntityHSSpectralWitch ? true : (var1 instanceof EntityHSSpectralSpider ? true : (var1 instanceof EntityHSSpectralCaveSpider ? true : (var1 instanceof EntityHSSpectralSilverfish ? true : (var1 instanceof EntityHSSpectralEnderman ? true : (var1 instanceof EntityHSSpectralBlaze ? true : (var1 instanceof EntityHSSpectralGhast ? true : var1 instanceof EntityHSSpectralMiner))))))))))))))))))));
    }

    public void harbingerSoulReap(LivingDropsEvent var1)
    {
        EntityHSHarbinger var2 = (EntityHSHarbinger)var1.source.getEntity();

        if (var1.entityLiving instanceof EntityHSSoulWrathful)
        {
            var1.entityLiving.worldObj.playSoundAtEntity(var1.entityLiving, "mob.zombie.unfect", 1.0F, 1.0F);
            var1.entityLiving.setDead();
        }
        else if (var1.entityLiving instanceof EntityHSSoulCulled)
        {
            var1.entityLiving.worldObj.playSoundAtEntity(var1.entityLiving, "mob.zombie.unfect", 1.0F, 1.0F);
            var1.entityLiving.setDead();
        }
        else if (var1.entityLiving instanceof EntityHSSoulGrieving)
        {
            if (var2.ReAnimateResource >= 5)
            {
                EntityHSSpectralMinerEvil var7 = new EntityHSSpectralMinerEvil(var1.entityLiving.worldObj);
                float var4 = 2.0F;
                float var5 = 2.0F;
                var7.setLocationAndAngles(var1.entityLiving.posX, var1.entityLiving.posY + 0.5D, var1.entityLiving.posZ, var1.entityLiving.worldObj.rand.nextFloat() * 360.0F, 0.0F);
                var1.entityLiving.worldObj.spawnEntityInWorld(var7);
                var1.entityLiving.worldObj.playSoundAtEntity(var1.entityLiving, "mob.zombie.unfect", 1.0F, 1.0F);
                var2.ReAnimateResource -= 5;
            }

            var1.entityLiving.setDead();
        }
        else
        {
            EntityHSSoul var3;

            if (var1.entityLiving instanceof EntityHSSoul)
            {
                if (var2.ReAnimateResource > 0)
                {
                    var3 = (EntityHSSoul)var1.entityLiving;
                    EntityHSSoul.createSpectralPet(var3.fallenSoulName, var1.entityLiving.worldObj, var1.entityLiving.posX, var1.entityLiving.posY, var1.entityLiving.posZ, var1.entityLiving.worldObj.rand, Boolean.valueOf(true));
                    var1.entityLiving.worldObj.playSoundAtEntity(var1.entityLiving, "mob.zombie.unfect", 1.0F, 1.0F);
                    --var2.ReAnimateResource;
                }

                var1.entityLiving.setDead();
            }
            else if (var1.entityLiving instanceof EntityPlayer)
            {
                var1.entityLiving.worldObj.playAuxSFX(2002, (int)Math.round(var1.entityLiving.posX - 0.5D), (int)Math.round(var1.entityLiving.posY + 0.5D), (int)Math.round(var1.entityLiving.posZ), 0);
                EntityHSSoulGrieving var6 = new EntityHSSoulGrieving(var1.entityLiving.worldObj, (double)((int)Math.round(var1.entityLiving.posX - 0.5D)), (double)((int)Math.round(var1.entityLiving.posY + 0.5D)), (double)((int)Math.round(var1.entityLiving.posZ)), 1, var1.entityLiving);
                var1.entityLiving.worldObj.spawnEntityInWorld(var6);
                ++var2.ReAnimateResource;
            }
            else
            {
                var1.entityLiving.worldObj.playAuxSFX(2002, (int)Math.round(var1.entityLiving.posX - 0.5D), (int)Math.round(var1.entityLiving.posY + 0.5D), (int)Math.round(var1.entityLiving.posZ), 0);
                var3 = new EntityHSSoul(var1.entityLiving.worldObj, (double)((int)Math.round(var1.entityLiving.posX - 0.5D)), (double)((int)Math.round(var1.entityLiving.posY + 0.5D)), (double)((int)Math.round(var1.entityLiving.posZ)), 1, var1.entityLiving);
                var1.entityLiving.worldObj.spawnEntityInWorld(var3);
            }
        }
    }
}

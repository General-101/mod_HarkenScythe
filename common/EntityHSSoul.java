package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHSSoul extends EntityLiving
{
    public int innerRotation;
    public int health;
    private int soulWorth;

    /** the path for the texture of this entityLiving */
    private String texture;
    public int soulAge;
    public String fallenSoulName;

    public EntityHSSoul(World var1)
    {
        super(var1);
        this.soulAge = 0;
        this.texture = "/mod_HarkenScythe/HarkenScytheTex/soul/soulcommon.png";
        this.innerRotation = 0;
        this.preventEntitySpawning = false;
        this.setSize(0.5F, 0.5F);
        this.health = 5;
        this.innerRotation = this.rand.nextInt(100000);
        this.soulWorth = 0;
        this.fallenSoulName = "null";
    }

    public EntityHSSoul(World var1, double var2, double var4, double var6, int var8, EntityLiving var9)
    {
        this(var1);
        this.setPosition(var2, var4, var6);
        this.soulWorth = var8;
        this.fallenSoulName = this.getFallenSoulName(var9);
    }

    public int getSoulWorth()
    {
        return this.soulWorth;
    }

    public String getSoulTexture()
    {
        return this.texture = "/mods/mod_HarkenScythe/textures/models/soul/soulcommon.png";
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(8, Integer.valueOf(this.health));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.moveStrafing = this.moveForward = 0.0F;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.innerRotation;
        this.dataWatcher.updateObject(8, Integer.valueOf(this.health));

        if (this.worldObj.isAirBlock((int)Math.round(this.posX), (int)Math.round(this.posY - 1.0D), (int)Math.round(this.posZ)) && this.worldObj.isAirBlock((int)Math.round(this.posX), (int)Math.round(this.posY - 2.0D), (int)Math.round(this.posZ)))
        {
            --this.posY;
        }

        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getBlockId(var1, var2, var3) != mod_HarkenScythe.HSSoulLight.blockID && this.worldObj.getBlockId(var1, var2, var3) == 0)
        {
            this.worldObj.setBlock(var1, var2, var3, mod_HarkenScythe.HSSoulLight.blockID);
        }

        ++this.soulAge;

        if (this.soulAge >= 1000)
        {
            this.setDead();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        var1.setShort("Age", (short)this.soulAge);
        var1.setShort("Value", (short)this.soulWorth);
        var1.setString("fallenSoul", this.fallenSoulName);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        this.soulAge = var1.getShort("Age");
        this.soulWorth = var1.getShort("Value");

        if (var1.hasKey("fallenSoul"))
        {
            this.fallenSoulName = var1.getString("fallenSoul");
        }
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        return var1.getEntity() instanceof EntityHSHarbinger ? super.attackEntityFrom(var1, var2) : false;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.getCurrentItem();

        if (var2 == null)
        {
            return false;
        }
        else if ((var2.itemID != mod_HarkenScythe.HSSoulkeeper.itemID || var2.getItemDamage() == 0) && var2.itemID != mod_HarkenScythe.HSEssenceKeeper.itemID && (var2.itemID != mod_HarkenScythe.HSSoulVessel.itemID || var2.getItemDamage() == 0) && var2.itemID != mod_HarkenScythe.HSEssenceVessel.itemID)
        {
            return super.interact(var1);
        }
        else
        {
            ItemHSKeeper.soulkeeperFillCheck(var1, this.soulWorth);
            SpecialTierAbilities.sTALivingmetal(var1, 2);
            this.setDead();
            return true;
        }
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getBlockId(var1, var2, var3) == mod_HarkenScythe.HSSoulLight.blockID)
        {
            this.worldObj.setBlock(var1, var2, var3, 0);
        }

        this.playSound("mob.wither.shoot", 0.1F, 1.0F);
        super.setDead();
    }

    public String getFallenSoulName(EntityLiving var1)
    {
        String var2 = "null";

        if (var1 instanceof EntityChicken)
        {
            var2 = "Chicken";
            return "Chicken";
        }
        else if (var1 instanceof EntityCow)
        {
            var2 = "Cow";
            return "Cow";
        }
        else if (var1 instanceof EntitySheep)
        {
            var2 = "Sheep";
            return "Sheep";
        }
        else if (var1 instanceof EntityBat)
        {
            var2 = "Bat";
            return "Bat";
        }
        else if (var1 instanceof EntityPig)
        {
            var2 = "Pig";
            return "Pig";
        }
        else if (var1 instanceof EntityWolf)
        {
            var2 = "Wolf";
            return "Wolf";
        }
        else if (var1 instanceof EntityOcelot)
        {
            EntityOcelot var4 = (EntityOcelot)var1;

            if (var4.getTameSkin() == 1)
            {
                var2 = "Cat_Black";
                return "Cat_Black";
            }
            else if (var4.getTameSkin() == 2)
            {
                var2 = "Cat_Red";
                return "Cat_Red";
            }
            else if (var4.getTameSkin() == 3)
            {
                var2 = "Cat_Siamese";
                return "Cat_Siamese";
            }
            else
            {
                var2 = "Ocelot";
                return "Ocelot";
            }
        }
        else if (var1 instanceof EntitySquid)
        {
            var2 = "Squid";
            return "Squid";
        }
        else if (var1 instanceof EntityVillager)
        {
            var2 = "Villager";
            return "Villager";
        }
        else if (var1 instanceof EntityCreeper)
        {
            var2 = "Creeper";
            return "Creeper";
        }
        else if (var1 instanceof EntityZombie)
        {
            if (var1 instanceof EntityPigZombie)
            {
                var2 = "PigZombie";
                return "PigZombie";
            }
            else
            {
                var2 = "Zombie";
                return "Zombie";
            }
        }
        else if (var1 instanceof EntitySkeleton)
        {
            EntitySkeleton var3 = (EntitySkeleton)var1;

            if (var3.getSkeletonType() == 1)
            {
                var2 = "Skeleton_Wither";
                return "Skeleton_Wither";
            }
            else
            {
                var2 = "Skeleton";
                return "Skeleton";
            }
        }
        else if (var1 instanceof EntityWitch)
        {
            var2 = "Witch";
            return "Witch";
        }
        else if (var1 instanceof EntitySpider)
        {
            if (var1 instanceof EntityCaveSpider)
            {
                var2 = "CaveSpider";
                return "CaveSpider";
            }
            else
            {
                var2 = "Spider";
                return "Spider";
            }
        }
        else if (var1 instanceof EntitySilverfish)
        {
            var2 = "Silverfish";
            return "Silverfish";
        }
        else if (var1 instanceof EntityEnderman)
        {
            var2 = "Enderman";
            return "Enderman";
        }
        else if (var1 instanceof EntityBlaze)
        {
            var2 = "Blaze";
            return "Blaze";
        }
        else if (var1 instanceof EntityGhast)
        {
            var2 = "Ghast";
            return "Ghast";
        }
        else
        {
            return var2;
        }
    }

    public static boolean createSpectralPet(String var0, World var1, double var2, double var4, double var6, Random var8, Boolean var9)
    {
        if ("null".equals(var0))
        {
            return false;
        }
        else
        {
            float var11;
            float var12;

            if ("Chicken".equals(var0))
            {
                EntityHSSpectralChicken var32 = new EntityHSSpectralChicken(var1);
                var11 = 2.0F;
                var12 = 2.0F;
                var32.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                if (var9.booleanValue())
                {
                    var32.isHarbingerMinon = 1;
                    var32.setEntityHealth(15);
                }

                var1.spawnEntityInWorld(var32);
                return true;
            }
            else if ("Cow".equals(var0))
            {
                EntityHSSpectralCow var31 = new EntityHSSpectralCow(var1);
                var11 = 2.0F;
                var12 = 2.0F;
                var31.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                if (var9.booleanValue())
                {
                    var31.isHarbingerMinon = 1;
                    var31.setEntityHealth(15);
                }

                var1.spawnEntityInWorld(var31);
                return true;
            }
            else if ("Sheep".equals(var0))
            {
                EntityHSSpectralSheep var30 = new EntityHSSpectralSheep(var1);
                var11 = 2.0F;
                var12 = 2.0F;
                var30.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                if (var9.booleanValue())
                {
                    var30.isHarbingerMinon = 1;
                    var30.setEntityHealth(15);
                }

                var1.spawnEntityInWorld(var30);
                return true;
            }
            else if ("Bat".equals(var0))
            {
                EntityHSSpectralBat var29 = new EntityHSSpectralBat(var1);
                var11 = 2.0F;
                var12 = 2.0F;
                var29.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                if (var9.booleanValue())
                {
                    var29.isHarbingerMinon = 1;
                    var29.setEntityHealth(15);
                }

                var1.spawnEntityInWorld(var29);
                return true;
            }
            else if ("Pig".equals(var0))
            {
                EntityHSSpectralPig var28 = new EntityHSSpectralPig(var1);
                var11 = 2.0F;
                var12 = 2.0F;
                var28.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                if (var9.booleanValue())
                {
                    var28.isHarbingerMinon = 1;
                    var28.setEntityHealth(15);
                }

                var1.spawnEntityInWorld(var28);
                return true;
            }
            else if ("Wolf".equals(var0))
            {
                EntityHSSpectralWolf var27 = new EntityHSSpectralWolf(var1);
                var11 = 2.0F;
                var12 = 2.0F;
                var27.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                if (var9.booleanValue())
                {
                    var27.isHarbingerMinon = 1;
                    var27.setEntityHealth(15);
                }

                var1.spawnEntityInWorld(var27);
                return true;
            }
            else
            {
                EntityHSSpectralOcelot var26;

                if ("Cat_Black".equals(var0))
                {
                    var26 = new EntityHSSpectralOcelot(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var26.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);
                    var26.setTameSkin(1);

                    if (var9.booleanValue())
                    {
                        var26.isHarbingerMinon = 1;
                        var26.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var26);
                    return true;
                }
                else if ("Cat_Red".equals(var0))
                {
                    var26 = new EntityHSSpectralOcelot(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var26.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);
                    var26.setTameSkin(2);

                    if (var9.booleanValue())
                    {
                        var26.isHarbingerMinon = 1;
                        var26.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var26);
                    return true;
                }
                else if ("Cat_Siamese".equals(var0))
                {
                    var26 = new EntityHSSpectralOcelot(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var26.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);
                    var26.setTameSkin(3);

                    if (var9.booleanValue())
                    {
                        var26.isHarbingerMinon = 1;
                        var26.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var26);
                    return true;
                }
                else if ("Ocelot".equals(var0))
                {
                    var26 = new EntityHSSpectralOcelot(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var26.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var26.isHarbingerMinon = 1;
                        var26.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var26);
                    return true;
                }
                else if ("Squid".equals(var0))
                {
                    EntityHSSpectralSquid var25 = new EntityHSSpectralSquid(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var25.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var25.isHarbingerMinon = 1;
                        var25.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var25);
                    return true;
                }
                else if ("Villager".equals(var0))
                {
                    EntityHSSpectralVillager var24 = new EntityHSSpectralVillager(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var24.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var24.isHarbingerMinon = 1;
                        var24.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var24);
                    return true;
                }
                else if ("Creeper".equals(var0))
                {
                    EntityHSSpectralCreeper var23 = new EntityHSSpectralCreeper(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var23.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var23.isHarbingerMinon = 1;
                        var23.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var23);
                    return true;
                }
                else if ("Zombie".equals(var0))
                {
                    EntityHSSpectralZombie var22 = new EntityHSSpectralZombie(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var22.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var22.isHarbingerMinon = 1;
                        var22.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var22);
                    return true;
                }
                else if ("PigZombie".equals(var0))
                {
                    EntityHSSpectralPigZombie var21 = new EntityHSSpectralPigZombie(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var21.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var21.isHarbingerMinon = 1;
                        var21.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var21);
                    return true;
                }
                else if ("Skeleton".equals(var0))
                {
                    EntityHSSpectralSkeleton var20 = new EntityHSSpectralSkeleton(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var20.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var20.isHarbingerMinon = 1;
                        var20.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var20);
                    return true;
                }
                else if ("Skeleton_Wither".equals(var0))
                {
                    EntityHSSpectralSkeletonWither var19 = new EntityHSSpectralSkeletonWither(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var19.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var19.isHarbingerMinon = 1;
                        var19.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var19);
                    return true;
                }
                else if ("Witch".equals(var0))
                {
                    EntityHSSpectralWitch var18 = new EntityHSSpectralWitch(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var18.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var18.isHarbingerMinon = 1;
                        var18.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var18);
                    return true;
                }
                else if ("Spider".equals(var0))
                {
                    EntityHSSpectralSpider var17 = new EntityHSSpectralSpider(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var17.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var17.isHarbingerMinon = 1;
                        var17.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var17);
                    return true;
                }
                else if ("CaveSpider".equals(var0))
                {
                    EntityHSSpectralCaveSpider var16 = new EntityHSSpectralCaveSpider(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var16.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var16.isHarbingerMinon = 1;
                        var16.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var16);
                    return true;
                }
                else if ("Silverfish".equals(var0))
                {
                    EntityHSSpectralSilverfish var15 = new EntityHSSpectralSilverfish(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var15.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var15.isHarbingerMinon = 1;
                        var15.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var15);
                    return true;
                }
                else if ("Enderman".equals(var0))
                {
                    EntityHSSpectralEnderman var14 = new EntityHSSpectralEnderman(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var14.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var14.isHarbingerMinon = 1;
                        var14.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var14);
                    return true;
                }
                else if ("Blaze".equals(var0))
                {
                    EntityHSSpectralBlaze var13 = new EntityHSSpectralBlaze(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var13.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var13.isHarbingerMinon = 1;
                        var13.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var13);
                    return true;
                }
                else if ("Ghast".equals(var0))
                {
                    EntityHSSpectralGhast var10 = new EntityHSSpectralGhast(var1);
                    var11 = 2.0F;
                    var12 = 2.0F;
                    var10.setLocationAndAngles(var2, var4 + 0.5D, var6, var8.nextFloat() * 360.0F, 0.0F);

                    if (var9.booleanValue())
                    {
                        var10.isHarbingerMinon = 1;
                        var10.setEntityHealth(15);
                    }

                    var1.spawnEntityInWorld(var10);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

    public int getMaxHealth()
    {
        return 5;
    }
}

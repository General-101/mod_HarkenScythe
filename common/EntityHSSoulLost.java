package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHSSoulLost extends EntityMob
{
    public int innerRotation;
    public int health;
    public int soulAge;
    private int soulWorth;

    /** the path for the texture of this entityLiving */
    private String texture;
    private int specialNumber;

    public EntityHSSoulLost(World var1)
    {
        super(var1);
        this.soulAge = 0;
        this.texture = "/mod_HarkenScythe/HarkenScytheTex/soul/soullost.png";
        this.innerRotation = 0;
        this.preventEntitySpawning = false;
        this.setSize(0.5F, 0.5F);
        this.health = 5;
        this.innerRotation = this.rand.nextInt(100000);
        this.soulWorth = 0;
    }

    public EntityHSSoulLost(World var1, double var2, double var4, double var6, int var8)
    {
        this(var1);
        this.setPosition(var2, var4, var6);
        this.soulWorth = var8;
    }

    public int getSoulWorth()
    {
        return this.soulWorth;
    }

    public String getSoulTexture()
    {
        return this.texture = "/mods/mod_HarkenScythe/textures/models/soul/soullost.png";
    }

    public int getMaxHealth()
    {
        return 5;
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.innerRotation;
        this.dataWatcher.updateObject(8, Integer.valueOf(this.health));
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getBlockId(var1, var2, var3) != mod_HarkenScythe.HSSoulLight.blockID && this.worldObj.getBlockId(var1, var2, var3) == 0)
        {
            this.worldObj.setBlock(var1, var2, var3, mod_HarkenScythe.HSSoulLight.blockID);
        }

        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0)
        {
            this.setDead();
        }

        ++this.soulAge;

        if (this.soulAge >= 6000)
        {
            this.setDead();
        }

        if (this.worldObj.canBlockSeeTheSky(var1, var2, var3) || this.posY >= 60.0D)
        {
            this.setDead();
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);
        return !this.worldObj.canBlockSeeTheSky(var1, var2, var3) && this.posY < 60.0D ? super.getCanSpawnHere() : false;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        var1.setShort("Age", (short)this.soulAge);
        var1.setShort("Value", (short)this.soulWorth);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        this.soulAge = var1.getShort("Age");
        this.soulWorth = var1.getShort("Value");
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
            if (!var1.worldObj.isRemote)
            {
                EntityHSSpectralMinerEvil var3 = new EntityHSSpectralMinerEvil(this.worldObj);
                var3.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(var3);
                this.setDead();
            }

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

    public static boolean createSpectralPet(World var0, double var1, double var3, double var5, Random var7)
    {
        EntityHSSpectralMiner var8 = new EntityHSSpectralMiner(var0);
        float var9 = 2.0F;
        float var10 = 2.0F;
        var8.setLocationAndAngles(var1, var3 + 0.5D, var5, var7.nextFloat() * 360.0F, 0.0F);
        var0.spawnEntityInWorld(var8);
        return true;
    }
}

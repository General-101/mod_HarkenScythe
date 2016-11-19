package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHSSoulWrathful extends EntityHSSoul
{
    public int innerRotation;
    public int health;
    private int soulWorth;

    /** the path for the texture of this entityLiving */
    private String texture;
    public int soulAge;
    public String fallenSoulName;

    public EntityHSSoulWrathful(World var1)
    {
        super(var1);
        this.soulAge = 0;
        this.texture = "/mod_HarkenScythe/HarkenScytheTex/soul/soulwrathful.png";
        this.innerRotation = 0;
        this.preventEntitySpawning = false;
        this.setSize(0.5F, 0.5F);
        this.health = 5;
        this.innerRotation = this.rand.nextInt(100000);
        this.soulWorth = 0;
    }

    public EntityHSSoulWrathful(World var1, double var2, double var4, double var6, int var8, EntityLiving var9)
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
        return this.texture = "/mods/mod_HarkenScythe/textures/models/soul/soulwrathful.png";
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
            SpecialTierAbilities.sTALivingmetal(var1, 100);
            this.setDead();
            return true;
        }
    }

    public int getMaxHealth()
    {
        return 5;
    }
}

package mod_HarkenScythe.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class EntityHSSlimeSoul extends EntityHSSlime
{
    public float field_70813_a;
    public float field_70811_b;
    public float field_70812_c;
    private int slimeJumpDelay = 0;
    public int resourceWorthTotal;
    public int resourceWorth;
    private int startingSize;

    public EntityHSSlimeSoul(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/slimesoul.png";
        int var2 = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0F;
        this.slimeJumpDelay = this.rand.nextInt(20) + 10;
        this.setSlimeSize(var2);
        this.resourceWorthTotal = 0;
        this.startingSize = 0;
        this.resourceWorth = 0;
    }

    protected void setSlimeSize(int var1)
    {
        this.dataWatcher.updateObject(16, new Byte((byte)var1));
        this.setSize(0.6F * (float)var1, 0.6F * (float)var1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setEntityHealth(this.getMaxHealth());
        this.experienceValue = 1;
        this.resourceWorthTotal = 0;
        this.startingSize = 0;
        this.resourceWorth = 0;
    }

    public int getMaxHealth()
    {
        return this.getSlimeSize() * 5;
    }

    public int getSlimeSize()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setInteger("Size", this.getSlimeSize() - 1);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.setSlimeSize(var1.getInteger("Size") + 1);
    }

    protected String getSlimeParticle()
    {
        return "slime";
    }

    protected String getJumpSound()
    {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0 && this.getSlimeSize() > 0)
        {
            this.isDead = true;
        }

        this.field_70811_b += (this.field_70813_a - this.field_70811_b) * 0.5F;
        this.field_70812_c = this.field_70811_b;
        boolean var1 = this.onGround;
        super.onUpdate();
        int var2;

        if (this.onGround && !var1)
        {
            var2 = this.getSlimeSize();

            for (int var3 = 0; var3 < var2 * 8; ++var3)
            {
                float var4 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
                float var5 = this.rand.nextFloat() * 0.5F + 0.5F;
                float var6 = MathHelper.sin(var4) * (float)var2 * 0.5F * var5;
                float var7 = MathHelper.cos(var4) * (float)var2 * 0.5F * var5;
                this.worldObj.spawnParticle("spell", this.posX + (double)var6, this.boundingBox.minY, this.posZ + (double)var7, 0.0D, 0.0D, 0.0D);
            }

            if (this.makesSoundOnLand())
            {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.field_70813_a = -0.5F;
        }
        else if (!this.onGround && var1)
        {
            this.field_70813_a = 1.0F;
        }

        this.func_70808_l();

        if (this.worldObj.isRemote)
        {
            var2 = this.getSlimeSize();
            this.setSize(0.6F * (float)var2, 0.6F * (float)var2);
        }
    }

    protected void updateEntityActionState()
    {
        this.despawnEntity();
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);

        if (var1 != null)
        {
            this.faceEntity(var1, 10.0F, 20.0F);
        }

        if (this.onGround && this.slimeJumpDelay-- <= 0)
        {
            this.slimeJumpDelay = this.getJumpDelay();

            if (var1 != null)
            {
                this.slimeJumpDelay /= 3;
            }

            this.isJumping = true;

            if (this.makesSoundOnJump())
            {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }

            this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
            this.moveForward = (float)(1 * this.getSlimeSize());
        }
        else
        {
            this.isJumping = false;

            if (this.onGround)
            {
                this.moveStrafing = this.moveForward = 0.0F;
            }
        }
    }

    protected void func_70808_l()
    {
        this.field_70813_a *= 0.6F;
    }

    protected int getJumpDelay()
    {
        return this.rand.nextInt(20) + 10;
    }

    protected EntityHSSlimeSoul createInstance()
    {
        return new EntityHSSlimeSoul(this.worldObj);
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        int var1 = this.getSlimeSize();

        if (!this.worldObj.isRemote && var1 > 1)
        {
            byte var2 = 2;

            for (int var3 = 0; var3 < var2; ++var3)
            {
                float var4 = ((float)(var3 % 2) - 0.5F) * (float)var1 / 4.0F;
                float var5 = ((float)(var3 / 2) - 0.5F) * (float)var1 / 4.0F;
                EntityHSSlimeSoul var6 = this.createInstance();
                var6.setLocationAndAngles(this.posX + (double)var4, this.posY + 0.5D, this.posZ + (double)var5, this.rand.nextFloat() * 360.0F, 0.0F);
                var6.setSlimeSize(var1 - 1);
                var6.setResourceWorth(this.resourceWorth);
                this.worldObj.spawnEntityInWorld(var6);
            }
        }

        super.setDead();
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer var1)
    {
        if (this.canDamagePlayer())
        {
            int var2 = this.getSlimeSize();

            if (this.canEntityBeSeen(var1) && this.getDistanceSqToEntity(var1) < 0.6D * (double)var2 * 0.6D * (double)var2 && var1.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength()))
            {
                this.playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    protected boolean canDamagePlayer()
    {
        return this.getSlimeSize() > 1;
    }

    protected int getAttackStrength()
    {
        return this.getSlimeSize();
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return this.getSlimeSize() == 1 ? Item.slimeBall.itemID : 0;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean var1, int var2) {}

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));

        if (this.worldObj.getWorldInfo().getTerrainType().handleSlimeSpawnReduction(this.rand, this.worldObj))
        {
            return false;
        }
        else
        {
            if (this.getSlimeSize() == 1 || this.worldObj.difficultySetting > 0)
            {
                if (this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ)) == BiomeGenBase.swampland && this.posY > 50.0D && this.posY < 70.0D && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8))
                {
                    return super.getCanSpawnHere();
                }

                if (this.rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D)
                {
                    return super.getCanSpawnHere();
                }
            }

            return false;
        }
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F * (float)this.getSlimeSize();
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed()
    {
        return 0;
    }

    protected boolean makesSoundOnJump()
    {
        return this.getSlimeSize() > 0;
    }

    protected boolean makesSoundOnLand()
    {
        return this.getSlimeSize() > 2;
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
            ItemHSKeeper.soulkeeperFillCheck(var1, this.resourceWorth);
            this.worldObj.playSoundAtEntity(var1, "mob.slime.attack", 0.3F, 0.5F);
            this.setDead();
            return true;
        }
    }

    public void setResourceWorthTotal(int var1, int var2)
    {
        this.resourceWorthTotal = var1;
        this.startingSize = var2;

        if (var2 == 3)
        {
            this.resourceWorth = Math.round((float)(this.resourceWorthTotal / 3));
        }

        if (var2 == 2)
        {
            this.resourceWorth = Math.round((float)(this.resourceWorthTotal / 2));
        }

        if (var2 == 1)
        {
            this.resourceWorth = this.resourceWorthTotal;
        }
    }

    public int setResourceWorth(int var1)
    {
        return this.resourceWorth = var1 / 2;
    }
}

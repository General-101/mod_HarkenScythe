package mod_HarkenScythe.common;

import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHSSpectralBat extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;
    public int soulAge = 0;
    public int petDamageTamed;
    public int petDamageReg;
    private ChunkCoordinates currentFlightTarget;
    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private int field_70846_g;
    public int isHarbingerMinon;
    public int harbingerToggle;
    private EntityAINearestAttackableTarget harbinerMinon1 = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, false);

    public EntityHSSpectralBat(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_bat.png";
        this.setSize(0.8F, 0.9F);
        this.moveSpeed = 0.5F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAITempt(this, 0.25F, mod_HarkenScythe.HSCreepBall.itemID, false));
        this.tasks.addTask(7, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
        this.experienceValue = 0;
        this.petDamageReg = 1;
        this.petDamageTamed = 2;
        this.setIsBatHanging(true);
        this.isHarbingerMinon = 0;
        this.harbingerToggle = 0;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    protected void updateAITasks()
    {
        super.updateAITasks();

        if (!this.isSitting() && this.getAttackTarget() == null)
        {
            if (this.getIsBatHanging())
            {
                if (!this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), (int)this.posY + 1, MathHelper.floor_double(this.posZ)))
                {
                    this.setIsBatHanging(false);
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                }
                else
                {
                    if (this.rand.nextInt(200) == 0)
                    {
                        this.rotationYawHead = (float)this.rand.nextInt(360);
                    }

                    if (this.worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
                    {
                        this.setIsBatHanging(false);
                        this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    }
                }
            }
            else
            {
                if (this.currentFlightTarget != null && (!this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || this.currentFlightTarget.posY < 1))
                {
                    this.currentFlightTarget = null;
                }

                if (this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0F)
                {
                    this.currentFlightTarget = new ChunkCoordinates((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
                }

                double var1 = (double)this.currentFlightTarget.posX + 0.2D - this.posX;
                double var3 = (double)this.currentFlightTarget.posY + 0.1D - this.posY;
                double var5 = (double)this.currentFlightTarget.posZ + 0.2D - this.posZ;
                this.motionX += (Math.signum(var1) * 0.2D - this.motionX) * 0.10000000149011612D;
                this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
                this.motionZ += (Math.signum(var5) * 0.2D - this.motionZ) * 0.10000000149011612D;
                float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
                float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
                this.moveForward = 0.5F;
                this.rotationYaw += var8;

                if (this.rand.nextInt(100) == 0 && this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), (int)this.posY + 1, MathHelper.floor_double(this.posZ)))
                {
                    this.setIsBatHanging(true);
                }
            }
        }
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLiving var1)
    {
        super.setAttackTarget(var1);
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(this.getHealth()));
    }

    public int getMaxHealth()
    {
        return this.isTamed() ? 20 : 6;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)BlockCloth.getBlockFromDye(1)));
        this.dataWatcher.addObject(21, new Byte((byte)0));
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int var1, int var2, int var3, int var4)
    {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("SAge", (short)this.soulAge);
        var1.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(21));
        var1.setShort("Evil", (short)this.isHarbingerMinon);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.soulAge = var1.getShort("SAge");
        this.dataWatcher.updateObject(21, Byte.valueOf(var1.getByte("BatFlags")));
        this.isHarbingerMinon = var1.getShort("Evil");
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isTamed();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.bat.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.bat.death";
    }

    protected void playStepSound1(int var1, int var2, int var3, int var4)
    {
        this.playSound("mob.chicken.step", 0.15F, 1.0F);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.1F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.95F;
    }

    protected void func_85033_bc() {}

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float var1) {}

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    protected void updateFallState(double var1, boolean var3) {}

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    public boolean doesEntityNotTriggerPressurePlate()
    {
        return true;
    }

    public boolean func_70922_bv()
    {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv())
        {
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        }
        else
        {
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
        }

        if (this.func_70922_bv())
        {
            this.numTicksToChaseTarget = 10;
        }

        if (this.getIsBatHanging())
        {
            this.motionX = this.motionY = this.motionZ = 0.0D;
            this.posY = (double)MathHelper.floor_double(this.posY) + 1.0D - (double)this.height;
        }
        else if (this.getAttackTarget() == null)
        {
            this.motionY *= 0.6000000238418579D;
        }

        if (this.isHarbingerMinon == 1 && this.harbingerToggle == 0)
        {
            this.harbingerToggle = 1;
            this.targetTasks.addTask(4, this.harbinerMinon1);
        }

        ++this.soulAge;

        if (this.soulAge >= 200)
        {
            this.soulAge = 0;

            if (this.health == 1)
            {
                this.attackEntityFrom(DamageSource.starve, 1000);
            }
            else
            {
                --this.health;

                if (this.health <= 5 && this.isTamed())
                {
                    this.generateRandomParticles("angryVillager");
                }
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            Entity var3 = var1.getEntity();
            this.aiSit.setSitting(false);

            if (var3 instanceof EntityPlayer)
            {
                EntityPlayer var4 = (EntityPlayer)var3;

                if (var4.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote)
                {
                    return false;
                }
            }

            if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
            {
                var2 = (var2 + 1) / 2;
            }

            return super.attackEntityFrom(var1, var2);
        }
    }

    public boolean attackEntityAsMob(Entity var1)
    {
        int var2 = this.isTamed() ? this.petDamageTamed : this.petDamageReg;
        return var1.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.getCurrentItem();

        if (this.isTamed())
        {
            if (var2 != null && this.dataWatcher.getWatchableObjectInt(18) < 20 && !this.isPotionActive(Potion.regeneration) && (var2.itemID == mod_HarkenScythe.HSBloodkeeper.itemID || var2.itemID == mod_HarkenScythe.HSBloodVessel.itemID))
            {
                var2.damageItem(1, var1);
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));

                if (var2.itemID == mod_HarkenScythe.HSBloodkeeper.itemID)
                {
                    if (var2.getMaxDamage() - var2.getItemDamage() == 0)
                    {
                        var2.itemID = mod_HarkenScythe.HSEssenceKeeper.itemID;
                        var2.setItemDamage(-var2.getMaxDamage());
                    }

                    return true;
                }

                if (var2.itemID == mod_HarkenScythe.HSBloodVessel.itemID)
                {
                    if (var2.getMaxDamage() - var2.getItemDamage() == 0)
                    {
                        var2.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
                        var2.setItemDamage(-var2.getMaxDamage());
                    }

                    return true;
                }
            }

            if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && var1.isSneaking())
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
            }
        }
        else if (var2 != null && var2.itemID == mod_HarkenScythe.HSCreepBall.itemID && this.isHarbingerMinon == 0)
        {
            if (!var1.capabilities.isCreativeMode)
            {
                --var2.stackSize;
            }

            if (var2.stackSize <= 0)
            {
                var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
            }

            if (!this.worldObj.isRemote)
            {
                this.setTamed(true);
                this.setPathToEntity((PathEntity)null);
                this.setAttackTarget((EntityLiving)null);
                this.aiSit.setSitting(true);
                this.setEntityHealth(20);
                this.heal(this.getMaxHealth());
                this.setOwner(var1.username);
                this.playTameEffect(true);
                this.worldObj.setEntityState(this, (byte)7);
            }

            return true;
        }

        return super.interact(var1);
    }

    public void func_70918_i(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(19);

        if (var1)
        {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
        }
        else
        {
            this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
        }
    }

    public EntityAgeable createChild(EntityAgeable var1)
    {
        return null;
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();
    }

    private void generateRandomParticles(String var1)
    {
        double var2 = this.rand.nextGaussian() * 0.02D;
        double var4 = this.rand.nextGaussian() * 0.02D;
        double var6 = this.rand.nextGaussian() * 0.02D;
        this.worldObj.spawnParticle(var1, this.posX, this.posY + 0.5D, this.posZ, var2, var4, var6);
    }

    public boolean getIsBatHanging()
    {
        return (this.dataWatcher.getWatchableObjectByte(21) & 1) != 0;
    }

    public void setIsBatHanging(boolean var1)
    {
        byte var2 = this.dataWatcher.getWatchableObjectByte(21);

        if (var1)
        {
            this.dataWatcher.updateObject(21, Byte.valueOf((byte)(var2 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(21, Byte.valueOf((byte)(var2 & -2)));
        }
    }
}

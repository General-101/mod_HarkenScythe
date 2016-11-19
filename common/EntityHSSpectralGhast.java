package mod_HarkenScythe.common;

import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityHSSpectralGhast extends EntityTameable implements IRangedAttackMob
{
    private float field_70926_e;
    private float field_70924_f;
    public int soulAge = 0;
    public int petDamageTamed;
    public int petDamageReg;
    public int courseChangeCooldown = 0;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity = null;
    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    private int field_70846_g;
    private int aggroCooldown = 0;
    public int prevAttackCounter = 0;
    public int attackCounter = 0;
    private int explosionStrength = 1;
    public int isHarbingerMinon;
    public int harbingerToggle;
    private EntityAINearestAttackableTarget harbinerMinon1 = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, false);

    public EntityHSSpectralGhast(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_ghast.png";
        this.setSize(4.0F, 4.0F);
        this.moveSpeed = 0.15F;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIArrowAttack(this, 0.25F, 20, 60, 30.0F));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 50.0F, 10.0F));
        this.tasks.addTask(6, new EntityAITempt(this, 0.25F, mod_HarkenScythe.HSCreepBall.itemID, false));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.experienceValue = 0;
        this.petDamageReg = 1;
        this.petDamageTamed = 2;
        this.isImmuneToFire = true;
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
        return this.isTamed() ? 20 : 10;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(18, new Integer(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)BlockCloth.getBlockFromDye(1)));
        this.dataWatcher.addObject(21, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("SAge", (short)this.soulAge);
        var1.setInteger("ExplosionPower", this.explosionStrength);
        var1.setShort("Evil", (short)this.isHarbingerMinon);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.soulAge = var1.getShort("SAge");
        this.isHarbingerMinon = var1.getShort("Evil");

        if (var1.hasKey("ExplosionPower"))
        {
            this.explosionStrength = var1.getInteger("ExplosionPower");
        }
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
        return "mob.ghast.moan";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.ghast.scream";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.ghast.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.1F;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float var1) {}

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

        byte var1 = this.dataWatcher.getWatchableObjectByte(21);
        this.texture = var1 == 1 ? "/mods/mod_HarkenScythe/textures/models/mob/spectral_ghast_fire.png" : "/mods/mod_HarkenScythe/textures/models/mob/spectral_ghast.png";

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
        else if ("fireball".equals(var1.getDamageType()) && var1.getEntity() instanceof EntityPlayer)
        {
            if (((EntityPlayer)var1.getEntity()).username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote)
            {
                return false;
            }
            else
            {
                super.attackEntityFrom(var1, 1000);
                ((EntityPlayer)var1.getEntity()).triggerAchievement(AchievementList.ghast);
                return true;
            }
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

    public int getAttackStrength(Entity var1)
    {
        int var2 = this.isTamed() ? this.petDamageTamed : this.petDamageReg;
        return var2;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.getCurrentItem();
        ItemStack var3 = this.getHeldItem();

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

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLiving var1, float var2)
    {
        double var3 = 64.0D;

        if (this.getAttackTarget() != null && this.getAttackTarget().getDistanceSqToEntity(this) < var3 * var3)
        {
            ++this.attackCounter;
            double var5 = this.getAttackTarget().posX - this.posX;
            double var7 = this.getAttackTarget().boundingBox.minY + (double)(this.getAttackTarget().height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
            double var9 = this.getAttackTarget().posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(var5, var9)) * 180.0F / (float)Math.PI;

            if (this.canEntityBeSeen(this.getAttackTarget()))
            {
                if (this.attackCounter == 13)
                {
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                }

                ++this.attackCounter;

                if (this.attackCounter >= 15)
                {
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    EntityLargeFireball var11 = new EntityLargeFireball(this.worldObj, this, var5, var7, var9);
                    var11.field_92057_e = this.explosionStrength;
                    double var12 = 4.0D;
                    Vec3 var14 = this.getLook(1.0F);
                    var11.posX = this.posX + var14.xCoord * var12;
                    var11.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
                    var11.posZ = this.posZ + var14.zCoord * var12;
                    this.worldObj.spawnEntityInWorld(var11);
                    this.attackCounter = 0;
                }
            }
        }
        else
        {
            this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;
        }

        if (!this.worldObj.isRemote)
        {
            byte var15 = this.dataWatcher.getWatchableObjectByte(21);
            byte var6 = (byte)(this.attackCounter > 13 ? 1 : 0);

            if (var15 != var6)
            {
                this.dataWatcher.updateObject(21, Byte.valueOf(var6));
            }
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote && !this.isSitting())
        {
            if (this.getAttackTarget() == null)
            {
                double var1 = this.waypointX - this.posX;
                double var3 = this.waypointY - this.posY;
                double var5 = this.waypointZ - this.posZ;
                double var7 = var1 * var1 + var3 * var3 + var5 * var5;

                if (var7 < 1.0D || var7 > 3600.0D)
                {
                    this.waypointX = this.posX + (double)((this.rand.nextFloat() * 3.0F - 1.0F) * 16.0F);
                    this.waypointY = this.posY + (double)((this.rand.nextFloat() * 1.0F - 1.0F) * 16.0F);
                    this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 3.0F - 1.0F) * 16.0F);
                }

                if (this.courseChangeCooldown-- <= 0)
                {
                    this.courseChangeCooldown += this.rand.nextInt(5) + 2;
                    var7 = (double)MathHelper.sqrt_double(var7);

                    if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, var7))
                    {
                        this.motionX += var1 / var7 * 0.1D;
                        this.motionY += var3 / var7 * 1.0D;
                        this.motionZ += var5 / var7 * 0.1D;
                    }
                    else
                    {
                        this.waypointX = this.posX;
                        this.waypointY = this.posY + 1.0D;
                        this.waypointZ = this.posZ;
                    }
                }
            }

            if (this.getAttackTarget() != null && this.getAttackTarget() != null && this.getAttackTarget().posY + (double)this.getAttackTarget().getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset)
            {
                this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }
        }

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.1D;
        }

        super.onLivingUpdate();
    }

    private boolean isCourseTraversable(double var1, double var3, double var5, double var7)
    {
        double var9 = (this.waypointX - this.posX) / var7;
        double var11 = (this.waypointY - this.posY) / var7;
        double var13 = (this.waypointZ - this.posZ) / var7;
        AxisAlignedBB var15 = this.boundingBox.copy();

        for (int var16 = 1; (double)var16 < var7; ++var16)
        {
            var15.offset(var9, var11, var13);

            if (!this.worldObj.getCollidingBoundingBoxes(this, var15).isEmpty())
            {
                return false;
            }
        }

        return true;
    }
}

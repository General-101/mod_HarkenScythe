package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityHSSpectralCreeper extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;
    public int soulAge = 0;
    public int petDamageTamed;
    public int petDamageReg;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    public int isHarbingerMinon;
    public int harbingerToggle;
    private EntityAINearestAttackableTarget harbinerMinon1 = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, false);

    public EntityHSSpectralCreeper(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_creeper.png";
        this.setSize(0.8F, 1.8F);
        this.moveSpeed = 0.3F;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIHSSpectralCreeperSwell(this));
        this.tasks.addTask(3, this.aiSit);
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 0.25F, false));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAITempt(this, 0.25F, mod_HarkenScythe.HSCreepBall.itemID, false));
        this.tasks.addTask(8, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.experienceValue = 0;
        this.petDamageReg = 1;
        this.petDamageTamed = 2;
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
        this.dataWatcher.addObject(21, Byte.valueOf((byte) - 1));
        this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("SAge", (short)this.soulAge);
        var1.setShort("Evil", (short)this.isHarbingerMinon);

        if (this.dataWatcher.getWatchableObjectByte(22) == 1)
        {
            var1.setBoolean("powered", true);
        }

        var1.setShort("Fuse", (short)this.fuseTime);
        var1.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.soulAge = var1.getShort("SAge");
        this.dataWatcher.updateObject(22, Byte.valueOf((byte)(var1.getBoolean("powered") ? 1 : 0)));
        this.isHarbingerMinon = var1.getShort("Evil");

        if (var1.hasKey("Fuse"))
        {
            this.fuseTime = var1.getShort("Fuse");
        }

        if (var1.hasKey("ExplosionRadius"))
        {
            this.explosionRadius = var1.getByte("ExplosionRadius");
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
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.creeper.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int var1, int var2, int var3, int var4)
    {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float var1)
    {
        super.fall(var1);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + var1 * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
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
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int var1 = this.getCreeperState();

            if (var1 > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += var1;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                Explosion var2 = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, 10.0F);
                var2.isFlaming = true;
                var2.isSmoking = true;
                var2.doExplosionB(true);

                if (!this.worldObj.isRemote)
                {
                    for (int var3 = 0; var3 < this.worldObj.loadedEntityList.size(); ++var3)
                    {
                        Object var4 = this;

                        if (this.side == Side.CLIENT)
                        {
                            var4 = (Entity)this.worldObj.getLoadedEntityList().get(var3);
                        }

                        if (this.side == Side.SERVER)
                        {
                            var4 = (Entity)this.worldObj.loadedEntityList.get(var3);
                        }

                        EntityTameable var5;

                        if (this.getPowered())
                        {
                            if (var4 != this.getOwner() && !((Entity)var4).isDead && this.getDistanceSqToEntity((Entity)var4) < 36.0D && var4 instanceof EntityLiving)
                            {
                                if (var4 instanceof EntityTameable)
                                {
                                    var5 = (EntityTameable)var4;

                                    if (var5.isTamed() && var5.getOwnerName() != this.getOwnerName())
                                    {
                                        continue;
                                    }
                                }

                                ((Entity)var4).attackEntityFrom(DamageSource.causeMobDamage(this), 100);
                            }
                        }
                        else if (var4 != this.getOwner() && !((Entity)var4).isDead && this.getDistanceSqToEntity((Entity)var4) < 18.0D && var4 instanceof EntityLiving)
                        {
                            if (var4 instanceof EntityTameable)
                            {
                                var5 = (EntityTameable)var4;

                                if (var5.isTamed() && var5.getOwnerName() != this.getOwnerName())
                                {
                                    continue;
                                }
                            }

                            ((Entity)var4).attackEntityFrom(DamageSource.causeMobDamage(this), 50);
                        }
                    }

                    this.setDead();
                }
            }
        }

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

    public int func_82143_as()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (this.health - 1);
    }

    public boolean getPowered()
    {
        return this.dataWatcher.getWatchableObjectByte(22) == 1;
    }

    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float var1)
    {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * var1) / (float)(this.fuseTime - 2);
    }

    public int getCreeperState()
    {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public void setCreeperState(int var1)
    {
        this.dataWatcher.updateObject(21, Byte.valueOf((byte)var1));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt var1)
    {
        super.onStruckByLightning(var1);
        this.dataWatcher.updateObject(22, Byte.valueOf((byte)1));
    }
}

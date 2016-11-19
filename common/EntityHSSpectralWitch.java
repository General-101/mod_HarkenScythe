package mod_HarkenScythe.common;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHSSpectralWitch extends EntityTameable implements IRangedAttackMob
{
    private float field_70926_e;
    private float field_70924_f;
    public int soulAge = 0;
    public int petDamageTamed;
    public int petDamageReg;
    private int witchAttackTimer;
    private int witchBuffTimer;
    private int witchAttackTimeNeeded;
    public float moveSpeed = 0.3F;
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 0.25F, 20, 60, 10.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide;
    private EntityAILeapAtTarget aiLeapAtTarget;
    public int isHarbingerMinon;
    public int harbingerToggle;
    private EntityAINearestAttackableTarget harbinerMinon1;
    private static int[] buffPotionList = new int[11];
    private static int[] debuffPotionList;

    public EntityHSSpectralWitch(World var1)
    {
        super(var1);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, this.moveSpeed, true);
        this.aiLeapAtTarget = new EntityAILeapAtTarget(this, 0.4F);
        this.harbinerMinon1 = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, false);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_witch.png";
        this.setSize(0.6F, 1.8F);
        this.moveSpeed = 0.3F;
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAITempt(this, 0.25F, mod_HarkenScythe.HSCreepBall.itemID, false));
        this.tasks.addTask(7, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(8, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
        this.tasks.addTask(11, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.experienceValue = 0;
        this.petDamageReg = 1;
        this.petDamageTamed = 2;

        if (var1 != null && !var1.isRemote)
        {
            this.setCombatTask();
        }

        this.witchAttackTimer = 0;
        this.witchBuffTimer = 0;
        this.witchAttackTimeNeeded = 200;
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
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("SAge", (short)this.soulAge);
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
        this.setCombatTask();
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
        return "mob.witch.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.witch.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.witch.death";
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

        if (this.witchAttackTimer < this.witchAttackTimeNeeded)
        {
            ++this.witchAttackTimer;
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
            if (var2 != null)
            {
                if (this.dataWatcher.getWatchableObjectInt(18) < 20 && !this.isPotionActive(Potion.regeneration) && (var2.itemID == mod_HarkenScythe.HSBloodkeeper.itemID || var2.itemID == mod_HarkenScythe.HSBloodVessel.itemID))
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

                if (var2.itemID == Item.potion.itemID && var3 == null)
                {
                    ItemPotion var4 = (ItemPotion)var2.getItem();

                    if (!ItemPotion.isSplash(var2.getItemDamage()))
                    {
                        if (!this.worldObj.isRemote)
                        {
                            ItemStack var5 = new ItemStack(var2.getItem(), 1, var2.getItemDamage());
                            this.setCurrentItemOrArmor(0, var5);
                            this.setCombatTask();
                        }

                        if (!var1.capabilities.isCreativeMode)
                        {
                            --var2.stackSize;
                        }

                        if (var2.stackSize <= 0)
                        {
                            var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
                        }
                    }
                }
            }
            else if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && var3 != null && !var1.isSneaking() && var3.itemID == Item.potion.itemID)
            {
                ItemStack var6 = new ItemStack(var3.getItem(), 1, var3.getItemDamage());
                var1.setCurrentItemOrArmor(0, var6);
                this.setCurrentItemOrArmor(0, (ItemStack)null);
                this.setCombatTask();
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
     * Initialize this creature.
     */
    public void initCreature()
    {
        this.tasks.addTask(3, this.aiLeapAtTarget);
        this.tasks.addTask(4, this.aiAttackOnCollide);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote && this.isTamed() && this.witchAttackTimer >= this.witchAttackTimeNeeded)
        {
            ItemStack var1 = this.getHeldItem();

            if (var1 != null && var1.itemID == Item.potion.itemID && this.getPotionEffectType(var1) == 1 && this.getOwner() != null && !this.getOwner().isPotionActive(this.getPotionEffectID(var1)))
            {
                if (this.witchBuffTimer >= this.witchAttackTimeNeeded)
                {
                    ItemPotion var2 = (ItemPotion)var1.getItem();
                    EntityPotion var3 = new EntityPotion(this.worldObj, this, var1.getItemDamage());
                    var3.rotationPitch -= -20.0F;
                    double var4 = this.getOwner().posX + this.getOwner().motionX - this.posX;
                    double var6 = this.getOwner().posY + (double)this.getOwner().getEyeHeight() - 1.100000023841858D - this.posY;
                    double var8 = this.getOwner().posZ + this.getOwner().motionZ - this.posZ;
                    float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
                    var3.setThrowableHeading(var4, var6 + (double)(var10 * 0.2F), var8, 0.75F, 8.0F);
                    this.worldObj.spawnEntityInWorld(var3);
                    this.witchAttackTimer = 0;
                    this.witchBuffTimer = 0;
                }
                else
                {
                    ++this.witchBuffTimer;
                }
            }
        }

        super.onLivingUpdate();
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLiving var1, float var2)
    {
        if (this.witchAttackTimer >= this.witchAttackTimeNeeded)
        {
            ItemStack var3 = this.getHeldItem();
            ItemPotion var4 = (ItemPotion)var3.getItem();

            if (!var1.isPotionActive(this.getPotionEffectID(var3)))
            {
                EntityPotion var5 = new EntityPotion(this.worldObj, this, var3.getItemDamage());
                var5.rotationPitch -= -20.0F;
                double var6 = var1.posX + var1.motionX - this.posX;
                double var8 = var1.posY + (double)var1.getEyeHeight() - 1.100000023841858D - this.posY;
                double var10 = var1.posZ + var1.motionZ - this.posZ;
                float var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
                var5.setThrowableHeading(var6, var8 + (double)(var12 * 0.2F), var10, 0.75F, 8.0F);
                this.worldObj.spawnEntityInWorld(var5);
                this.witchAttackTimer = 0;
            }
        }
    }

    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        this.tasks.removeTask(this.aiLeapAtTarget);
        ItemStack var1 = this.getHeldItem();

        if (var1 != null && var1.itemID == Item.potion.itemID)
        {
            if (this.getPotionEffectType(var1) == 2)
            {
                this.tasks.addTask(4, this.aiArrowAttack);
            }
        }
        else
        {
            this.tasks.addTask(3, this.aiLeapAtTarget);
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean var1, int var2)
    {
        ItemStack var3 = this.getHeldItem();

        if (var3 != null && var3.itemID == Item.potion.itemID && var3.getItemDamage() + 50 < var3.getMaxDamage())
        {
            ItemStack var4 = new ItemStack(Item.glassBottle, 1);
            this.entityDropItem(var4, 0.0F);
        }
    }

    public int getPotionEffectType(ItemStack var1)
    {
        if (var1 != null && var1.itemID == Item.potion.itemID)
        {
            List var2 = Item.potion.getEffects(var1);
            Object var3 = null;
            byte var4 = 0;

            if (var2 != null)
            {
                Iterator var5 = var2.iterator();

                while (var5.hasNext())
                {
                    PotionEffect var6 = (PotionEffect)var5.next();
                    int var7;

                    for (var7 = 0; var7 < buffPotionList.length; ++var7)
                    {
                        if (var6.getPotionID() == buffPotionList[var7])
                        {
                            var4 = 1;
                            return var4;
                        }
                    }

                    for (var7 = 0; var7 < debuffPotionList.length; ++var7)
                    {
                        if (var6.getPotionID() == debuffPotionList[var7])
                        {
                            var4 = 2;
                            return var4;
                        }
                    }
                }
            }

            return var4;
        }
        else
        {
            return 0;
        }
    }

    public int getPotionEffectID(ItemStack var1)
    {
        if (var1 != null && var1.itemID == Item.potion.itemID)
        {
            List var2 = Item.potion.getEffects(var1);

            if (var2 != null)
            {
                Iterator var3 = var2.iterator();

                if (var3.hasNext())
                {
                    PotionEffect var4 = (PotionEffect)var3.next();
                    return var4.getPotionID();
                }
            }
        }

        return 0;
    }

    static
    {
        buffPotionList[0] = Potion.damageBoost.id;
        buffPotionList[1] = Potion.digSpeed.id;
        buffPotionList[2] = Potion.fireResistance.id;
        buffPotionList[3] = Potion.heal.id;
        buffPotionList[4] = Potion.invisibility.id;
        buffPotionList[5] = Potion.jump.id;
        buffPotionList[6] = Potion.moveSpeed.id;
        buffPotionList[7] = Potion.nightVision.id;
        buffPotionList[8] = Potion.regeneration.id;
        buffPotionList[9] = Potion.resistance.id;
        buffPotionList[10] = Potion.waterBreathing.id;
        debuffPotionList = new int[9];
        debuffPotionList[0] = Potion.weakness.id;
        debuffPotionList[1] = Potion.digSlowdown.id;
        debuffPotionList[2] = Potion.confusion.id;
        debuffPotionList[3] = Potion.blindness.id;
        debuffPotionList[4] = Potion.harm.id;
        debuffPotionList[5] = Potion.hunger.id;
        debuffPotionList[6] = Potion.moveSlowdown.id;
        debuffPotionList[7] = Potion.poison.id;
        debuffPotionList[8] = Potion.wither.id;
    }
}

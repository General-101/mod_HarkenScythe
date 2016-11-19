package mod_HarkenScythe.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
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
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHSSpectralMiner extends EntityTameable implements IRangedAttackMob
{
    private float field_70926_e;
    private float field_70924_f;
    public int soulAge = 0;
    public int petDamageTamed;
    public int petDamageReg;
    public float moveSpeed = 0.3F;
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 0.25F, 20, 60, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide;
    private EntityAILeapAtTarget aiLeapAtTarget;

    public EntityHSSpectralMiner(World var1)
    {
        super(var1);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, this.moveSpeed, true);
        this.aiLeapAtTarget = new EntityAILeapAtTarget(this, 0.4F);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_miner.png";
        this.setSize(0.8F, 1.8F);
        this.moveSpeed = 0.3F;
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(5, new EntityAIHSSpectralMinerFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
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
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.soulAge = var1.getShort("SAge");
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
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "random.classic_hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "damage.fall";
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

        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);
        ItemStack var4 = this.getHeldItem();

        if (this.worldObj.getBlockId(var1, var2 + 1, var3) != mod_HarkenScythe.HSSoulLight.blockID && this.worldObj.getBlockId(var1, var2 + 1, var3) == 0 && var4 != null && var4.getItem().itemID == Block.torchWood.blockID)
        {
            this.worldObj.setBlock(var1, var2 + 1, var3, mod_HarkenScythe.HSSoulLight.blockID);
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
        if (this.isTamed() && this.getHeldItem() != null && this.getHeldItem().getItem().itemID == Block.torchWood.blockID)
        {
            var1.setFire(2);
        }

        int var2 = this.isTamed() ? this.petDamageTamed : this.petDamageReg;
        return var1.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    public int getAttackStrength(Entity var1)
    {
        ItemStack var2 = this.getHeldItem();
        int var3 = this.isTamed() ? this.petDamageTamed : this.petDamageReg;

        if (var2 != null)
        {
            var3 += var2.getDamageVsEntity(this) / 2;
        }

        return var3;
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
            ItemStack var4;

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

                if (var2.getItem() instanceof ItemBow && var3 == null || var2.getItem() instanceof ItemSword && var3 == null)
                {
                    if (!this.worldObj.isRemote)
                    {
                        var4 = new ItemStack(var2.getItem(), 1, var2.getItemDamage());
                        this.setCurrentItemOrArmor(0, var4);
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

                if (var2.getItem() instanceof ItemArmor && !"mod_AsgardShield.common.ItemAsgardShield".equals(var2.getItem().getClass().getName()) && !this.worldObj.isRemote)
                {
                    var4 = new ItemStack(var2.getItem(), 1, var2.getItemDamage());
                    int var5 = getArmorPosition(var4) - 1;
                    ItemStack var6 = this.getCurrentArmor(var5);

                    if (var6 == null)
                    {
                        this.setCurrentItemOrArmor(var5 + 1, var4.copy());

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

                if (var2.getItem().itemID == Block.torchWood.blockID && var3 == null)
                {
                    if (!this.worldObj.isRemote)
                    {
                        var4 = new ItemStack(var2.getItem(), 1);
                        this.setCurrentItemOrArmor(0, var4);
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
            else if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && var3 != null && !var1.isSneaking() && (var3.getItem() instanceof ItemBow || var3.getItem() instanceof ItemSword))
            {
                var4 = new ItemStack(var3.getItem(), 1, var3.getItemDamage());
                var1.setCurrentItemOrArmor(0, var4);
                this.setCurrentItemOrArmor(0, (ItemStack)null);
                this.setCombatTask();
            }

            if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && var3 != null && !var1.isSneaking() && var3.getItem().itemID == Block.torchWood.blockID)
            {
                Block var9 = Block.blocksList[var3.itemID];
                ItemStack var10 = new ItemStack(var9, 1);

                if (var1.inventory.addItemStackToInventory(var10))
                {
                    ;
                }

                this.setCurrentItemOrArmor(0, (ItemStack)null);
                int var11 = MathHelper.floor_double(this.posX);
                int var7 = MathHelper.floor_double(this.posY);
                int var8 = MathHelper.floor_double(this.posZ);

                if (this.worldObj.getBlockId(var11, var7 + 1, var8) == mod_HarkenScythe.HSSoulLight.blockID)
                {
                    this.worldObj.setBlock(var11, var7 + 1, var8, 0);
                }
            }

            if (var1.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && var1.isSneaking())
            {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
            }
        }
        else if (var2 != null && var2.itemID == mod_HarkenScythe.HSCreepBall.itemID)
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
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLiving var1, float var2)
    {
        EntityArrow var3 = new EntityArrow(this.worldObj, this, var1, 1.6F, (float)(14 - this.worldObj.difficultySetting * 4));
        int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var3.setDamage((double)(var2 * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting * 0.11F));

        if (var4 > 0)
        {
            var3.setDamage(var3.getDamage() + (double)var4 * 0.5D + 0.5D);
        }

        if (var5 > 0)
        {
            var3.setKnockbackStrength(var5);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0)
        {
            var3.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(var3);
    }

    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        this.tasks.removeTask(this.aiLeapAtTarget);
        ItemStack var1 = this.getHeldItem();

        if (var1 != null && var1.getItem() instanceof ItemBow)
        {
            this.tasks.addTask(4, this.aiArrowAttack);
        }
        else if (var1 != null && var1.getItem() instanceof ItemSword)
        {
            this.tasks.addTask(3, this.aiLeapAtTarget);
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
        else
        {
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

        if ((var3 != null && var3.getItem() instanceof ItemBow || var3 != null && var3.getItem() instanceof ItemSword) && var3.getItemDamage() + 50 < var3.getMaxDamage())
        {
            ItemStack var4 = new ItemStack(var3.getItem(), 1, var3.getItemDamage() + 50);
            this.entityDropItem(var4, 0.0F);
        }

        for (int var7 = 0; var7 < 4; ++var7)
        {
            ItemStack var5 = this.getCurrentArmor(var7);

            if (var5 != null && var5.getItemDamage() + 50 < var5.getMaxDamage())
            {
                ItemStack var6 = new ItemStack(var5.getItem(), 1, var5.getItemDamage() + 50);
                this.entityDropItem(var6, 0.0F);
            }
        }
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        int var1 = super.getTotalArmorValue() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }
}

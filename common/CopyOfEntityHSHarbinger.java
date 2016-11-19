package mod_HarkenScythe.common;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CopyOfEntityHSHarbinger extends EntitySkeleton
{
    private int lvl;
    private int teleportDelay = 0;
    private int field_70826_g = 0;
    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;

    public CopyOfEntityHSHarbinger(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/harbinger.png";
        this.moveSpeed = 0.3F;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(3, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIBreakDoor(this));
        this.tasks.addTask(7, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
        this.setSkeletonType(3);
        this.experienceValue = 30;
        this.lvl = 0;
    }

    public int getMaxHealth()
    {
        return 60;
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity var1)
    {
        ItemStack var2 = this.getHeldItem();
        int var3 = 2;

        if (var2 != null)
        {
            var3 += var2.getDamageVsEntity(this);
        }

        return var3;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return mod_HarkenScythe.HSEssenceVessel.itemID;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean var1, int var2)
    {
        int var3 = this.rand.nextInt(3 + var2);

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.bone.itemID, 1);
        }
    }

    protected void dropRareDrop(int var1)
    {
        this.entityDropItem(new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1), 0.0F);
    }

    /**
     * Makes entity wear random armor based on difficulty
     */
    protected void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(mod_HarkenScythe.HSScytheDiamond));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote && this.worldObj.isDaytime())
        {
            float var1 = this.getBrightness(1.0F);

            if (var1 > 0.5F && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
            {
                boolean var2 = true;
                ItemStack var3 = this.getCurrentItemOrArmor(4);

                if (var3 != null)
                {
                    if (var3.isItemStackDamageable())
                    {
                        var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));

                        if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
                        {
                            this.renderBrokenItemStack(var3);
                            this.setCurrentItemOrArmor(4, (ItemStack)null);
                        }
                    }

                    var2 = false;
                }

                if (var2)
                {
                    this.setFire(8);
                }
            }
        }

        if (!this.worldObj.isRemote && this.isEntityAlive() && this.getHealth() <= this.getMaxHealth() / 2)
        {
            if (this.entityToAttack != null)
            {
                if (this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.entityToAttack))
                {
                    this.moveStrafing = this.moveForward = 0.0F;
                    this.moveSpeed = 0.0F;

                    if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0D)
                    {
                        this.teleportRandomly();
                    }

                    this.teleportDelay = 0;
                }
                else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && this.teleportToEntity(this.entityToAttack))
                {
                    this.teleportDelay = 0;
                }
            }
            else
            {
                this.teleportDelay = 0;
            }

            if (this.getHealth() <= this.getMaxHealth() / 3)
            {
                this.addPotionEffect(new PotionEffect(Potion.invisibility.id, 10, 2));
            }
        }

        super.onLivingUpdate();
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving var1)
    {
        super.onKillEntity(var1);
        var1.worldObj.playAuxSFX(2002, (int)Math.round(var1.posX - 0.5D), (int)Math.round(var1.posY + 0.5D), (int)Math.round(var1.posZ), 0);

        if (this.health < this.getMaxHealth())
        {
            this.health += 5;
        }
    }

    protected boolean teleportRandomly()
    {
        double var1 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double var3 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double var5 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(var1, var3, var5);
    }

    protected boolean teleportToEntity(Entity var1)
    {
        Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - var1.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - var1.posY + (double)var1.getEyeHeight(), this.posZ - var1.posZ);
        var2 = var2.normalize();
        double var3 = 16.0D;
        double var5 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - var2.xCoord * var3;
        double var7 = this.posY + (double)(this.rand.nextInt(16) - 8) - var2.yCoord * var3;
        double var9 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - var2.zCoord * var3;
        return this.teleportTo(var5, var7, var9);
    }

    protected boolean teleportTo(double var1, double var3, double var5)
    {
        double var7 = this.posX;
        double var9 = this.posY;
        double var11 = this.posZ;
        this.posX = var1;
        this.posY = var3;
        this.posZ = var5;
        boolean var13 = false;
        int var14 = MathHelper.floor_double(this.posX);
        int var15 = MathHelper.floor_double(this.posY);
        int var16 = MathHelper.floor_double(this.posZ);
        int var17;

        if (this.worldObj.blockExists(var14, var15, var16))
        {
            boolean var18 = false;

            while (!var18 && var15 > 0)
            {
                var17 = this.worldObj.getBlockId(var14, var15 - 1, var16);

                if (var17 != 0 && Block.blocksList[var17].blockMaterial.blocksMovement())
                {
                    var18 = true;
                }
                else
                {
                    --this.posY;
                    --var15;
                }
            }

            if (var18)
            {
                this.setPosition(this.posX, this.posY, this.posZ);

                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
                {
                    var13 = true;
                }
            }
        }

        if (!var13)
        {
            this.setPosition(var7, var9, var11);
            return false;
        }
        else
        {
            short var30 = 128;

            for (var17 = 0; var17 < var30; ++var17)
            {
                double var19 = (double)var17 / ((double)var30 - 1.0D);
                float var21 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float var22 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float var23 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                double var24 = var7 + (this.posX - var7) * var19 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double var26 = var9 + (this.posY - var9) * var19 + this.rand.nextDouble() * (double)this.height;
                double var28 = var11 + (this.posZ - var11) * var19 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                this.worldObj.spawnParticle("portal", var24, var26, var28, (double)var21, (double)var22, (double)var23);
            }

            this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
            this.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    private boolean shouldAttackPlayer(EntityPlayer var1)
    {
        ItemStack var2 = var1.inventory.armorInventory[3];

        if (var2 != null && var2.itemID == Block.pumpkin.blockID)
        {
            return false;
        }
        else
        {
            Vec3 var3 = var1.getLook(1.0F).normalize();
            Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX - var1.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (var1.posY + (double)var1.getEyeHeight()), this.posZ - var1.posZ);
            double var5 = var4.lengthVector();
            var4 = var4.normalize();
            double var7 = var3.dotProduct(var4);
            return var7 > 1.0D - 0.025D / var5 ? var1.canEntityBeSeen(this) : false;
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0D);

        if (var1 != null)
        {
            if (this.shouldAttackPlayer(var1))
            {
                if (this.field_70826_g == 0)
                {
                    this.worldObj.playSoundAtEntity(var1, "mob.endermen.stare", 1.0F, 1.0F);
                }

                if (this.field_70826_g++ == 5)
                {
                    this.field_70826_g = 0;
                    return var1;
                }
            }
            else
            {
                this.field_70826_g = 0;
            }
        }

        return null;
    }
}

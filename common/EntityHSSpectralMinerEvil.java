package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityHSSpectralMinerEvil extends EntityMob
{
    public int soulAge = 0;
    private boolean soul1;
    private boolean soul2;
    private boolean soul3;
    private boolean soul4;
    private boolean soul5;
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    private static Item[] randomPickaxe = new Item[5];
    private static ItemStack[] randomDrops;

    public EntityHSSpectralMinerEvil(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/spectral_miner_evil.png";
        this.moveSpeed = 0.3F;
        this.isImmuneToFire = true;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(2, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(3, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.experienceValue = 5;
        this.soul1 = true;
        this.soul2 = true;
        this.soul3 = true;
        this.soul4 = true;
        this.soul5 = true;
        this.setCurrentItemOrArmor(0, new ItemStack(this.getRandomPickaxe(), 1));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        var1.setShort("Age", (short)this.soulAge);
        var1.setBoolean("Soul1", this.soul1);
        var1.setBoolean("Soul2", this.soul2);
        var1.setBoolean("Soul3", this.soul3);
        var1.setBoolean("Soul4", this.soul4);
        var1.setBoolean("Soul5", this.soul5);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        this.soulAge = var1.getShort("Age");
        this.soul1 = var1.getBoolean("Soul1");
        this.soul2 = var1.getBoolean("Soul2");
        this.soul3 = var1.getBoolean("Soul3");
        this.soul4 = var1.getBoolean("Soul4");
        this.soul5 = var1.getBoolean("Soul5");
    }

    public int getMaxHealth()
    {
        return 50;
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity var1)
    {
        ItemStack var2 = this.getHeldItem();
        int var3 = 5;

        if (var2 != null)
        {
            var3 += var2.getDamageVsEntity(this);
        }

        return var3;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    public boolean canBreatheUnderwater()
    {
        return true;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity var1, float var2)
    {
        if (this.attackTime <= 0 && var2 < 1.2F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(var1);
        }
    }

    public boolean attackEntityAsMob(Entity var1)
    {
        if (super.attackEntityAsMob(var1))
        {
            if (var1 instanceof EntityLiving)
            {
                byte var2 = 0;

                if (this.worldObj.difficultySetting > 1)
                {
                    var2 = 30;

                    if (this.worldObj.difficultySetting == 2)
                    {
                        var2 = 60;
                    }
                    else if (this.worldObj.difficultySetting == 3)
                    {
                        var2 = 120;
                    }
                }

                if (var2 > 0)
                {
                    ((EntityLiving)var1).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, var2 * 20, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
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
        else if (this.isSoulStealingAttack(var1) && super.attackEntityFrom(var1, var2))
        {
            this.soulAge = 0;
            this.soulStolen();
            Entity var3 = var1.getEntity();

            if (this.riddenByEntity != var3 && this.ridingEntity != var3)
            {
                if (var3 != this)
                {
                    this.entityToAttack = var3;
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        ++this.soulAge;

        if (this.soulAge >= 6000 && !this.worldObj.isRemote)
        {
            this.setDead();

            if (!this.worldObj.isRemote)
            {
                EntityHSSoulLost var1 = new EntityHSSoulLost(this.worldObj, (double)((int)Math.round(this.posX - 0.5D)), (double)((int)Math.round(this.posY + 0.5D)), (double)((int)Math.round(this.posZ)), 0);
                this.worldObj.spawnEntityInWorld(var1);
            }
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.zombie.unfect";
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
        return "mob.wither.shoot";
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean var1, int var2)
    {
        int var3 = this.rand.nextInt(2 + var2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            new ItemStack(Item.coal, 1);
            Random var6 = new Random();
            ItemStack var5 = randomDrops[var6.nextInt(randomDrops.length)];
            this.dropItem(var5.itemID, 1);
        }

        var3 = this.rand.nextInt(2 + var2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(mod_HarkenScythe.HSEctoplasm.itemID, 1);
        }
    }

    protected void dropRareDrop(int var1)
    {
        this.dropItem(Item.diamond.itemID, 1);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.coal.itemID;
    }

    protected Item getRandomPickaxe()
    {
        Item var1 = Item.pickaxeWood;
        Random var2 = new Random();
        var1 = randomPickaxe[var2.nextInt(randomPickaxe.length)];
        return var1;
    }

    protected boolean isSoulStealingAttack(DamageSource var1)
    {
        if (var1.getEntity() instanceof EntityLiving)
        {
            EntityLiving var2 = (EntityLiving)var1.getEntity();

            if (var2.getCurrentItemOrArmor(0) != null && var2.getCurrentItemOrArmor(0).getItem() instanceof ItemHSScythe)
            {
                return true;
            }

            if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulstealAug.effectId, var2.getCurrentItemOrArmor(0)) > 0)
            {
                return true;
            }
        }

        return false;
    }

    public void soulStolen()
    {
        if (this.getHealth() < 50 && this.soul1)
        {
            this.createSlimes();
            this.soul1 = false;
        }

        if (this.getHealth() < 40 && this.soul2)
        {
            this.createSlimes();
            this.soul2 = false;
        }

        if (this.getHealth() < 30 && this.soul3)
        {
            this.createSlimes();
            this.soul3 = false;
        }

        if (this.getHealth() < 20 && this.soul4)
        {
            this.createSlimes();
            this.soul4 = false;
        }

        if (this.getHealth() < 10 && this.soul5)
        {
            this.createSlimes();
            this.soul5 = false;
        }

        if (this.getHealth() < 1)
        {
            this.createSlimes();
        }
    }

    public void createSlimes()
    {
        for (int var1 = 0; var1 < 1; ++var1)
        {
            float var2 = ((float)(var1 % 2) - 0.5F) * 2.0F / 4.0F;
            float var3 = ((float)(var1 / 2) - 0.5F) * 2.0F / 4.0F;
            EntityHSSlimeSoul var4 = new EntityHSSlimeSoul(this.worldObj);
            var4.setLocationAndAngles(this.posX + (double)var2, this.posY + 0.5D, this.posZ + (double)var3, this.rand.nextFloat() * 360.0F, 0.0F);
            var4.setSlimeSize(1);
            var4.setResourceWorth(2);
            this.worldObj.spawnEntityInWorld(var4);
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote)
        {
            for (int var1 = 0; var1 < this.worldObj.loadedEntityList.size(); ++var1)
            {
                Object var2 = this;

                if (this.side == Side.CLIENT)
                {
                    var2 = (Entity)this.worldObj.getLoadedEntityList().get(var1);
                }

                if (this.side == Side.SERVER)
                {
                    var2 = (Entity)this.worldObj.loadedEntityList.get(var1);
                }

                if (!((Entity)var2).isDead && this.getDistanceSqToEntity((Entity)var2) < 100.0D && var2 instanceof EntityPlayer && !((EntityPlayer)var2).isPotionActive(Potion.nightVision))
                {
                    ((EntityPlayer)var2).addPotionEffect(new PotionEffect(Potion.blindness.id, 60, 0));
                }
            }
        }
    }

    static
    {
        randomPickaxe[0] = Item.pickaxeWood;
        randomPickaxe[1] = Item.pickaxeStone;
        randomPickaxe[2] = Item.pickaxeIron;
        randomPickaxe[3] = Item.pickaxeGold;
        randomPickaxe[4] = Item.pickaxeDiamond;
        randomDrops = new ItemStack[6];
        randomDrops[0] = new ItemStack(Item.coal, 1);
        randomDrops[1] = new ItemStack(Block.oreIron, 1);
        randomDrops[2] = new ItemStack(Block.oreGold, 1);
        randomDrops[3] = new ItemStack(Block.torchWood, 1);
        randomDrops[4] = new ItemStack(Item.redstone, 1);
        randomDrops[5] = new ItemStack(Item.clay, 1);
    }
}

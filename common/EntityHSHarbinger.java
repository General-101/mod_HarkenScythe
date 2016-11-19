package mod_HarkenScythe.common;

import java.util.Calendar;
import java.util.Random;
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
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityHSHarbinger extends EntityMob
{
    private int lvl;
    public int ReAnimateResource;
    public int summonMinion;
    private int teleportDelay = 0;
    public static String[] randSoulList = new String[15];

    public EntityHSHarbinger(World var1)
    {
        super(var1);
        this.texture = "/mods/mod_HarkenScythe/textures/models/mob/harbinger.png";
        this.setSize(0.8F, 1.8F);
        this.moveSpeed = 0.3F;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityHSSoul.class, this.moveSpeed, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIBreakDoor(this));
        this.tasks.addTask(8, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityHSSoul.class, 16.0F, 0, false));
        this.experienceValue = 30;
        this.lvl = 0;
        this.ReAnimateResource = 5;
        this.summonMinion = 1;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    public int getMaxHealth()
    {
        return 160;
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

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(21, new Byte((byte)0));
    }

    /**
     * Initialize this creature.
     */
    public void initCreature()
    {
        this.addRandomArmor();
        this.func_82162_bC();
        this.setCanPickUpLoot(this.rand.nextFloat() < pickUpLootProability[this.worldObj.difficultySetting]);

        if (this.getCurrentItemOrArmor(4) == null)
        {
            Calendar var1 = this.worldObj.getCurrentDate();

            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("Lvl", (short)this.lvl);
        var1.setShort("ReAnimateResource", (short)this.ReAnimateResource);
        var1.setShort("SummonMinions", (short)this.summonMinion);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.lvl = var1.getShort("Lvl");
        this.ReAnimateResource = var1.getShort("ReAnimateResource");
        this.summonMinion = var1.getShort("SummonMinions");
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.bone.itemID;
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

        this.dropItem(mod_HarkenScythe.HSAbyssFragment.itemID, 1);
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
        this.setCurrentItemOrArmor(0, new ItemStack(mod_HarkenScythe.HSScytheSoulReaper));
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
                    this.worldObj.playSoundAtEntity(this, "mob.zombie.unfect", 1.0F, 1.0F);
                    this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX - 0.5D), (int)Math.round(this.posY + 0.5D), (int)Math.round(this.posZ), 0);
                    this.setDead();
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
        }

        if (!this.worldObj.isRemote && this.isEntityAlive() && this.getHealth() <= this.getMaxHealth() / 3 && this.summonMinion == 1)
        {
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY + 0.5D), (int)Math.round(this.posZ), 0);
            this.worldObj.playSoundAtEntity(this, "mob.zombie.unfect", 1.0F, 1.0F);
            this.summonMinons(this.worldObj, this);
            --this.summonMinion;
            this.ReAnimateResource += 4;
        }

        if (this.getHealth() <= this.getMaxHealth() / 4)
        {
            this.addPotionEffect(new PotionEffect(Potion.invisibility.id, 10, 2));
        }

        super.onLivingUpdate();
    }

    public boolean attackEntityAsMob(Entity var1)
    {
        super.attackEntityAsMob(var1);
        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return true;
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving var1)
    {
        ++this.lvl;

        if (var1 instanceof EntityHSSoul)
        {
            this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0));
        }

        super.onKillEntity(var1);
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

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource var1, int var2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (var1 instanceof EntityDamageSourceIndirect && this.getHealth() <= this.getMaxHealth() / 2)
        {
            for (int var3 = 0; var3 < 64; ++var3)
            {
                if (this.teleportRandomly())
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            return super.attackEntityFrom(var1, var2);
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

    public void summonMinons(World var1, EntityHSHarbinger var2)
    {
        int var3;
        EntityHSSoul var4;

        for (var3 = 4; var3 >= 0; --var3)
        {
            if (var1.isAirBlock((int)Math.round(var2.posX + (double)var3), (int)Math.round(var2.posY + 0.5D), (int)Math.round(var2.posZ + (double)var3)))
            {
                var4 = new EntityHSSoul(var1, (double)((int)Math.round(var2.posX + (double)var3)), (double)((int)Math.round(var2.posY + 0.5D)), (double)((int)Math.round(var2.posZ + (double)var3)), 1, this.getRandomSouls());
                var1.spawnEntityInWorld(var4);
                break;
            }
        }

        for (var3 = 4; var3 >= 0; --var3)
        {
            if (var1.isAirBlock((int)Math.round(var2.posX - (double)var3), (int)Math.round(var2.posY + 0.5D), (int)Math.round(var2.posZ - (double)var3)))
            {
                var4 = new EntityHSSoul(var1, (double)((int)Math.round(var2.posX - (double)var3)), (double)((int)Math.round(var2.posY + 0.5D)), (double)((int)Math.round(var2.posZ - (double)var3)), 1, this.getRandomSouls());
                var1.spawnEntityInWorld(var4);
                break;
            }
        }

        for (var3 = 4; var3 >= 0; --var3)
        {
            if (var1.isAirBlock((int)Math.round(var2.posX - (double)var3), (int)Math.round(var2.posY + 0.5D), (int)Math.round(var2.posZ + (double)var3)))
            {
                var4 = new EntityHSSoul(var1, (double)((int)Math.round(var2.posX - (double)var3)), (double)((int)Math.round(var2.posY + 0.5D)), (double)((int)Math.round(var2.posZ + (double)var3)), 1, this.getRandomSouls());
                var1.spawnEntityInWorld(var4);
                break;
            }
        }

        for (var3 = 4; var3 >= 0; --var3)
        {
            if (var1.isAirBlock((int)Math.round(var2.posX + (double)var3), (int)Math.round(var2.posY + 0.5D), (int)Math.round(var2.posZ - (double)var3)))
            {
                var4 = new EntityHSSoul(var1, (double)((int)Math.round(var2.posX + (double)var3)), (double)((int)Math.round(var2.posY + 0.5D)), (double)((int)Math.round(var2.posZ - (double)var3)), 1, this.getRandomSouls());
                var1.spawnEntityInWorld(var4);
                break;
            }
        }
    }

    public EntityLiving getRandomSouls()
    {
        EntityLiving var1 = this.createSoul();
        return var1;
    }

    public EntityLiving createSoul()
    {
        EntityHSSlimeSoul var1 = new EntityHSSlimeSoul(this.worldObj);
        Random var2 = new Random();
        int var3 = var2.nextInt(randSoulList.length);
        String var4 = randSoulList[var3];

        if ("null".equals(var4))
        {
            return var1;
        }
        else if ("Chicken".equals(var4))
        {
            EntityChicken var19 = new EntityChicken(this.worldObj);
            return var19;
        }
        else if ("Cow".equals(var4))
        {
            EntityCow var18 = new EntityCow(this.worldObj);
            return var18;
        }
        else if ("Sheep".equals(var4))
        {
            EntitySheep var17 = new EntitySheep(this.worldObj);
            return var17;
        }
        else if ("Pig".equals(var4))
        {
            EntityPig var16 = new EntityPig(this.worldObj);
            return var16;
        }
        else if ("Wolf".equals(var4))
        {
            EntityWolf var15 = new EntityWolf(this.worldObj);
            return var15;
        }
        else if ("Ocelot".equals(var4))
        {
            EntityOcelot var14 = new EntityOcelot(this.worldObj);
            return var14;
        }
        else if ("Villager".equals(var4))
        {
            EntityVillager var13 = new EntityVillager(this.worldObj);
            return var13;
        }
        else if ("Creeper".equals(var4))
        {
            EntityCreeper var12 = new EntityCreeper(this.worldObj);
            return var12;
        }
        else if ("Zombie".equals(var4))
        {
            EntityZombie var11 = new EntityZombie(this.worldObj);
            return var11;
        }
        else if ("Skeleton".equals(var4))
        {
            EntitySkeleton var10 = new EntitySkeleton(this.worldObj);
            return var10;
        }
        else if ("Witch".equals(var4))
        {
            EntityWitch var9 = new EntityWitch(this.worldObj);
            return var9;
        }
        else if ("Spider".equals(var4))
        {
            EntitySpider var8 = new EntitySpider(this.worldObj);
            return var8;
        }
        else if ("CaveSpider".equals(var4))
        {
            EntityCaveSpider var7 = new EntityCaveSpider(this.worldObj);
            return var7;
        }
        else if ("Silverfish".equals(var4))
        {
            EntitySilverfish var6 = new EntitySilverfish(this.worldObj);
            return var6;
        }
        else if ("Enderman".equals(var4))
        {
            EntityEnderman var5 = new EntityEnderman(this.worldObj);
            return var5;
        }
        else
        {
            return var1;
        }
    }

    static
    {
        randSoulList[0] = "Chicken";
        randSoulList[1] = "Cow";
        randSoulList[2] = "Sheep";
        randSoulList[3] = "Pig";
        randSoulList[4] = "Wolf";
        randSoulList[5] = "Ocelot";
        randSoulList[6] = "Villager";
        randSoulList[7] = "Creeper";
        randSoulList[8] = "Zombie";
        randSoulList[9] = "Skeleton";
        randSoulList[10] = "Witch";
        randSoulList[11] = "Spider";
        randSoulList[12] = "CaveSpider";
        randSoulList[13] = "Silverfish";
        randSoulList[14] = "Enderman";
    }
}

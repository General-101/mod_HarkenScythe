package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemHSScythe extends ItemSword
{
    private static Random rand = new Random();
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    private int weaponDamage;
    private final EnumToolMaterial toolMaterial;
    private static Block[] blocksEffectiveAgainst = new Block[0];
    public float scytheKnockback;
    public boolean scytheCharged;
    private int specialNumber;

    public ItemHSScythe(int var1, EnumToolMaterial var2, int var3)
    {
        super(var1, var2);
        this.maxStackSize = 1;
        this.toolMaterial = var2;
        this.weaponDamage = 3 + var2.getDamageVsEntity();
        this.setMaxDamage(var2.getMaxUses());
        this.scytheKnockback = 0.1F;
        this.specialNumber = var3;
        this.scytheCharged = false;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        return this.specialNumber == 1 ? EnumRarity.epic : EnumRarity.common;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack var1, Block var2)
    {
        return var2.blockID == Block.web.blockID ? 15.0F : 1.5F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3)
    {
        var2.addVelocity((double)(-MathHelper.sin(var2.rotationYaw * (float)Math.PI / 180.0F) * this.scytheKnockback * 0.5F), 0.1D, (double)(MathHelper.cos(var2.rotationYaw * (float)Math.PI / 180.0F) * this.scytheKnockback * 0.5F));

        if (this.scytheCharged)
        {
            var1.damageItem(3, var3);
            return true;
        }
        else
        {
            var1.damageItem(1, var3);
            return true;
        }
    }

    public boolean onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
    {
        var1.damageItem(3, var6);
        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity var1)
    {
        return this.scytheCharged ? this.weaponDamage * 2 : this.weaponDamage;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack var1)
    {
        return EnumAction.bow;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack var1)
    {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.getCurrentEquippedItem() != null)
        {
            var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
            var3.worldObj.playSoundAtEntity(var3, "random.breath", 1.0F, 0.9F);
        }

        return var1;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        int var5 = this.getMaxItemUseDuration(var1) - var4;

        if (var5 >= 24)
        {
            this.scytheCharged = true;

            for (int var6 = 0; var6 < var2.loadedEntityList.size(); ++var6)
            {
                Object var7 = var3;

                if (this.side == Side.CLIENT)
                {
                    var7 = (Entity)var2.getLoadedEntityList().get(var6);
                }

                if (this.side == Side.SERVER)
                {
                    var7 = (Entity)var2.loadedEntityList.get(var6);
                }

                if (var7 != var3 && !((Entity)var7).isDead && var3.getDistanceSqToEntity((Entity)var7) < 15.0D && var7 instanceof EntityLiving)
                {
                    if (var7 instanceof EntityTameable)
                    {
                        EntityTameable var8 = (EntityTameable)var7;

                        if (var8.isTamed() && var8.getAttackTarget() != var3)
                        {
                            continue;
                        }
                    }

                    var3.attackTargetEntityWithCurrentItem((Entity)var7);
                }
            }

            var3.worldObj.playSoundAtEntity(var3, "mob.irongolem.throw", 1.0F, 1.0F);
            var3.swingItem();
            this.scytheCharged = false;
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (!var2.canPlayerEdit(var4, var5, var6, var7, var1))
        {
            return false;
        }
        else
        {
            UseHoeEvent var11 = new UseHoeEvent(var2, var1, var3, var4, var5, var6);

            if (MinecraftForge.EVENT_BUS.post(var11))
            {
                return false;
            }
            else if (var11.getResult() == Event.Result.ALLOW)
            {
                var1.damageItem(1, var2);
                return true;
            }
            else
            {
                int var12 = var3.getBlockId(var4, var5, var6);
                int var13 = var3.getBlockMetadata(var4, var5, var6);
                int var14 = var3.getBlockId(var4, var5 + 1, var6);
                int var15 = var3.getBlockId(var4, var5 + 2, var6);

                if ((var7 == 0 || var12 != Block.reed.blockID) && var12 != Block.crops.blockID && var12 != mod_HarkenScythe.HSBiomassPlant.blockID)
                {
                    return false;
                }
                else
                {
                    byte var16 = 1;

                    if (var14 == var12)
                    {
                        var16 = 2;
                    }

                    if (var15 == var12)
                    {
                        var16 = 3;
                    }

                    if (var12 == Block.reed.blockID)
                    {
                        var2.dropPlayerItem(new ItemStack(Item.reed, var16));
                    }

                    if (var12 == Block.crops.blockID)
                    {
                        if (var13 == 7)
                        {
                            var2.dropPlayerItem(new ItemStack(Item.wheat, 2));
                        }

                        var2.dropPlayerItem(new ItemStack(Item.seeds, 1));
                    }

                    if (var12 == mod_HarkenScythe.HSBiomassPlant.blockID)
                    {
                        if (var13 == 3)
                        {
                            var2.dropPlayerItem(new ItemStack(mod_HarkenScythe.HSBiomass, 2));
                        }

                        var2.dropPlayerItem(new ItemStack(mod_HarkenScythe.HSBiomassSeed, 1));
                    }

                    for (int var17 = 0; var17 < 10; ++var17)
                    {
                        Random var18 = new Random();
                        Vec3 var19 = Vec3.createVectorHelper(((double)var18.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                        var19.rotateAroundX(-var2.rotationPitch * (float)Math.PI / 180.0F);
                        var19.rotateAroundY(-var2.rotationYaw * (float)Math.PI / 180.0F);
                        Vec3 var20 = Vec3.createVectorHelper(((double)var18.nextFloat() - 0.5D) * 0.3D, (double)(-var18.nextFloat()) * 0.6D - 0.3D, 0.6D);
                        var20.rotateAroundX(-var2.rotationPitch * (float)Math.PI / 180.0F);
                        var20.rotateAroundY(-var2.rotationYaw * (float)Math.PI / 180.0F);
                        var20.addVector(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ);
                        var2.worldObj.spawnParticle("iconcrack_" + Item.wheat.itemID, (double)var4 + 0.5D, (double)var5 + 0.5D, (double)var6 + 0.5D, var19.xCoord, var19.yCoord + 0.05D, var19.zCoord);
                    }

                    var2.swingItem();
                    Block var21 = Block.reed;
                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var21.stepSound.getStepSound(), (var21.stepSound.getVolume() + 1.0F) / 2.0F, var21.stepSound.getPitch() * 0.8F);

                    if (var3.isRemote)
                    {
                        return true;
                    }
                    else
                    {
                        var3.setBlock(var4, var5, var6, 0);
                        var1.damageItem(var16 * 3, var2);
                        return true;
                    }
                }
            }
        }
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block var1)
    {
        return false;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack var1, ItemStack var2)
    {
        return this.specialNumber != 0 && this.specialNumber != 1 ? false : (this.toolMaterial.getToolCraftingMaterial() == var2.itemID ? true : super.getIsRepairable(var1, var2));
    }

    static
    {
        blocksEffectiveAgainst = new Block[0];
    }
}

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
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemHSGlaive extends ItemSword
{
    private static Random rand = new Random();
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    private int weaponDamage;
    private final EnumToolMaterial toolMaterial;
    private static Block[] blocksEffectiveAgainst = new Block[0];
    public float glaiveKnockback;
    public boolean glaiveCharged;
    private int specialNumber;
    public int bloodPoolChance;

    public ItemHSGlaive(int var1, EnumToolMaterial var2, int var3, int var4)
    {
        super(var1, var2);
        this.maxStackSize = 1;
        this.toolMaterial = var2;
        this.weaponDamage = 3 + var2.getDamageVsEntity();
        this.setMaxDamage(var2.getMaxUses());
        this.glaiveKnockback = 0.2F;
        this.specialNumber = var3;
        this.bloodPoolChance = var4;
        this.glaiveCharged = false;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
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
        var2.addVelocity((double)(-MathHelper.sin(var2.rotationYaw * (float)Math.PI / 180.0F) * this.glaiveKnockback * 0.5F), 0.1D, (double)(MathHelper.cos(var2.rotationYaw * (float)Math.PI / 180.0F) * this.glaiveKnockback * 0.5F));

        if (this.glaiveCharged)
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
        return this.glaiveCharged ? this.weaponDamage * 2 : this.weaponDamage;
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
            this.glaiveCharged = true;

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

                if (var7 != var3 && !((Entity)var7).isDead && var3.getDistanceSqToEntity((Entity)var7) < 25.0D && var7 instanceof EntityLiving)
                {
                    if (var7 instanceof EntityTameable)
                    {
                        EntityTameable var8 = (EntityTameable)var7;

                        if (var8.isTamed() && var8.getAttackTarget() != var3)
                        {
                            continue;
                        }
                    }

                    EntityLiving var15 = (EntityLiving)var7;
                    Vec3 var9 = var3.getLook(1.0F).normalize();
                    Vec3 var10 = ((Entity)var7).worldObj.getWorldVec3Pool().getVecFromPool(((Entity)var7).posX - var3.posX, ((Entity)var7).boundingBox.minY + (double)(((Entity)var7).height / 2.0F) - (var3.posY + (double)var3.getEyeHeight()), ((Entity)var7).posZ - var3.posZ);
                    double var11 = var10.lengthVector();
                    var10 = var10.normalize();
                    double var13 = var9.dotProduct(var10);

                    if (var13 > 0.9D - 0.025D / var11)
                    {
                        var3.attackTargetEntityWithCurrentItem(var15);
                    }
                }
            }

            var3.worldObj.playSoundAtEntity(var3, "mob.irongolem.throw", 1.0F, 1.0F);
            var3.swingItem();
            this.glaiveCharged = false;
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
        return this.specialNumber == 0 ? (this.toolMaterial.getToolCraftingMaterial() == var2.itemID ? true : super.getIsRepairable(var1, var2)) : false;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (var7 != 1)
        {
            return false;
        }
        else if (var2.canPlayerEdit(var4, var5, var6, var7, var1) && var2.canPlayerEdit(var4, var5, var6, var7, var1))
        {
            int var11 = var3.getBlockId(var4, var5, var6);
            int var12 = var3.getBlockMetadata(var4, var5, var6);

            if (var11 == mod_HarkenScythe.HSCreepBlock.blockID && var12 == 0)
            {
                var3.setBlockMetadataWithNotify(var4, var5, var6, 1, 2);
                Block var13 = Block.tilledField;
                var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);
                var1.damageItem(1, var2);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    static
    {
        blocksEffectiveAgainst = new Block[0];
    }
}

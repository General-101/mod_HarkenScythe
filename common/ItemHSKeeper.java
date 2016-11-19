package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemHSKeeper extends Item
{
    public static final String[] keeperFillIconNameArray = new String[] {"_0", "_1", "_2", "_3"};
    private Icon[] iconArray;
    private int specialNumber;
    private String keeperType;
    public int flaskDrinkCount = 0;
    private static int[] soulBuffs = new int[11];
    private static int[] soulDebuffs;

    public ItemHSKeeper(int var1, int var2, String var3)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(20);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setNoRepair();
        this.specialNumber = var2;
        this.keeperType = var3;
        this.flaskDrinkCount = 0;

        if (var2 == 2 || var2 == 3 || var2 == 4)
        {
            this.setMaxDamage(40);
        }

        if (var2 == 1 || var2 == 2)
        {
            this.setContainerItem(mod_HarkenScythe.HSEssenceKeeper);
        }

        if (var2 == 5)
        {
            this.setMaxDamage(80);
        }

        if (var2 == 4 || var2 == 5)
        {
            this.setContainerItem(mod_HarkenScythe.HSEssenceVessel);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        if (this.specialNumber != 0 && this.specialNumber != 3)
        {
            this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + keeperFillIconNameArray[3]);
            this.iconArray = new Icon[keeperFillIconNameArray.length];

            for (int var2 = 0; var2 < this.iconArray.length; ++var2)
            {
                this.iconArray[var2] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + keeperFillIconNameArray[var2]);
            }
        }
        else
        {
            this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int var1)
    {
        if (this.specialNumber != 1 && this.specialNumber != 4 && this.specialNumber != 2 && this.specialNumber != 5)
        {
            return this.itemIcon;
        }
        else
        {
            int var2 = this.getMaxDamage() / 4;
            return var1 == 0 ? (this.itemIcon = this.iconArray[3]) : (var1 > var2 * 2 ? (this.itemIcon = this.iconArray[0]) : (var1 > var2 ? (this.itemIcon = this.iconArray[1]) : (this.itemIcon = this.iconArray[2])));
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack var1)
    {
        return this.specialNumber != 0 && this.specialNumber != 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        return this.specialNumber != 0 && this.specialNumber != 3 ? EnumRarity.epic : EnumRarity.common;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        if (this.specialNumber != 0 && this.specialNumber != 3)
        {
            int var5 = var1.getMaxDamage() - var1.getItemDamage();
            int var6 = var1.getMaxDamage();
            var3.add("Stored " + this.keeperType + ": " + var5 + " / " + var6);
        }
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack var1)
    {
        return this.specialNumber != 0 && this.specialNumber != 3 ? EnumAction.drink : EnumAction.none;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack var1)
    {
        return 32;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (this.specialNumber != 0 && this.specialNumber != 3)
        {
            if ((this.specialNumber != 1 || this.flaskDrinkCount < var1.getMaxDamage() - var1.getItemDamage()) && (this.specialNumber != 2 || this.flaskDrinkCount * 2 < var1.getMaxDamage() - var1.getItemDamage()) && (this.specialNumber != 4 || this.flaskDrinkCount < var1.getMaxDamage() - var1.getItemDamage()) && (this.specialNumber != 5 || this.flaskDrinkCount * 2 < var1.getMaxDamage() - var1.getItemDamage()))
            {
                var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
                return var1;
            }
            else
            {
                var3.stopUsingItem();
                this.onPlayerStoppedUsing(var1, var2, var3, var1.getMaxItemUseDuration());
                return var1;
            }
        }
        else
        {
            return var1;
        }
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        if (!var2.isRemote && (this.specialNumber == 1 && this.flaskDrinkCount != 0 || this.specialNumber == 2 && this.flaskDrinkCount != 0 || this.specialNumber == 4 && this.flaskDrinkCount != 0 || this.specialNumber == 5 && this.flaskDrinkCount != 0))
        {
            if (this.specialNumber == 1 || this.specialNumber == 4)
            {
                var1.setItemDamage(var1.getItemDamage() + this.flaskDrinkCount);
                this.soulFeastingEffect(var3, this.flaskDrinkCount);
            }

            if (this.specialNumber == 2 || this.specialNumber == 5)
            {
                var1.setItemDamage(var1.getItemDamage() + this.flaskDrinkCount * 2);
                this.bloodDrinkingEffect(var3, this.flaskDrinkCount);
            }

            this.flaskDrinkCount = 0;

            if (var1.getItemDamage() >= var1.getMaxDamage())
            {
                ItemStack var6;

                if (this.specialNumber == 1 || this.specialNumber == 2)
                {
                    new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1);
                    var6 = var3.inventory.getCurrentItem();
                    var6.itemID = mod_HarkenScythe.HSEssenceKeeper.itemID;
                    var6.setItemDamage(0);
                }

                if (this.specialNumber == 4 || this.specialNumber == 5)
                {
                    new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1);
                    var6 = var3.inventory.getCurrentItem();
                    var6.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
                    var6.setItemDamage(0);
                }
            }
        }
    }

    public ItemStack onEaten(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (!var2.isRemote)
        {
            ++this.flaskDrinkCount;

            if (this.specialNumber == 2 || this.specialNumber == 5)
            {
                this.bloodDrinkingHealth(var3, 1);
            }
        }

        return var1;
    }

    public ItemStack getContainerItemStack(ItemStack var1)
    {
        if (this.specialNumber != 1 && this.specialNumber != 2)
        {
            if (this.specialNumber == 4 || this.specialNumber == 5)
            {
                if (var1.getItemDamage() >= var1.getMaxDamage() / 2)
                {
                    return new ItemStack(mod_HarkenScythe.HSEssenceVessel);
                }

                var1.setItemDamage(var1.getItemDamage() + var1.getMaxDamage() / 2);
            }

            return var1;
        }
        else
        {
            return new ItemStack(mod_HarkenScythe.HSEssenceKeeper);
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (this.specialNumber == 2 || this.specialNumber == 5)
        {
            if (var7 != 1)
            {
                return false;
            }

            if (var2.canPlayerEdit(var4, var5, var6, var7, var1) && var2.canPlayerEdit(var4, var5, var6, var7, var1))
            {
                int var11 = var3.getBlockId(var4, var5, var6);
                int var12 = var3.getBlockMetadata(var4, var5, var6);

                if (var11 == mod_HarkenScythe.HSCreepBlock.blockID && var12 == 1)
                {
                    var3.setBlockMetadataWithNotify(var4, var5, var6, 2, 2);
                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), "liquid.swim", 0.05F, 0.05F);
                    var1.damageItem(1, var2);
                    ItemStack var13 = var2.inventory.getCurrentItem();

                    if (var13.itemID == mod_HarkenScythe.HSBloodkeeper.itemID)
                    {
                        if (var13.getMaxDamage() - var13.getItemDamage() == 0)
                        {
                            var13.itemID = mod_HarkenScythe.HSEssenceKeeper.itemID;
                            var13.setItemDamage(-var13.getMaxDamage());
                        }

                        return true;
                    }

                    if (var13.itemID == mod_HarkenScythe.HSBloodVessel.itemID)
                    {
                        if (var13.getMaxDamage() - var13.getItemDamage() == 0)
                        {
                            var13.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
                            var13.setItemDamage(-var13.getMaxDamage());
                        }

                        return true;
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public void soulFeastingEffect(EntityPlayer var1, int var2)
    {
        int var3 = ItemHSSoulweaveArmor.soulweaveArmorCheck(var1);
        byte var4 = 0;
        int var10 = var4 + var3;

        if (var10 < 4 && var2 >= 5)
        {
            var10 += var2 / 5;
        }

        if (var10 > 3)
        {
            var10 = 3;
        }

        short var5 = 410;
        int var6 = 300 * var3;
        int var11 = var5 + 205 * var2 + var6;
        int var7 = 0;

        if (var2 >= 10)
        {
            var7 += var2 / 10;
        }

        if (var7 > 3)
        {
            var7 = 3;
        }

        short var8 = 205;
        int var12 = var8 + 102 * var2;
        int var9 = this.soulFeastingRandomEffect(var3);
        var1.clearActivePotions();
        var1.addPotionEffect(new PotionEffect(var9, var11, var10));
        var1.addPotionEffect(new PotionEffect(Potion.hunger.id, var12, var7));
    }

    public int soulFeastingRandomEffect(int var1)
    {
        Random var2 = new Random();
        Random var3 = new Random();
        int var4 = var2.nextInt(100) + 1;
        boolean var5 = false;
        int var6;
        int var7;

        if (var4 >= 50 && var1 < 4)
        {
            var6 = var3.nextInt(soulDebuffs.length);
            var7 = soulDebuffs[var6];
        }
        else
        {
            var6 = var3.nextInt(soulBuffs.length);
            var7 = soulBuffs[var6];
        }

        return var7;
    }

    public void bloodDrinkingEffect(EntityPlayer var1, int var2)
    {
        int var3 = ItemHSBloodweaveArmor.bloodweaveArmorCheck(var1);
        byte var4 = 0;
        int var9 = var4 + var3;

        if (var9 < 4 && var2 >= 5)
        {
            var9 += var2 / 5;
        }

        if (var9 > 3)
        {
            var9 = 3;
        }

        byte var5 = 103;
        int var6 = 100 * var3;
        int var10 = var5 + 103 * var2 + var6;
        int var7 = 0;

        if (var2 >= 10)
        {
            var7 += var2 / 10;
        }

        if (var7 > 3)
        {
            var7 = 3;
        }

        byte var8 = 43;
        int var11 = var8 + 43 * var2;
        var1.addPotionEffect(new PotionEffect(Potion.damageBoost.id, var10, var9));

        if (var3 >= 4)
        {
            var1.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, var10, 0));
        }

        var1.addPotionEffect(new PotionEffect(Potion.hunger.id, var11, var7));
    }

    public void bloodDrinkingHealth(EntityPlayer var1, int var2)
    {
        int var3 = var1.getMaxHealth() - var1.getHealth();
        byte var4 = 0;

        if (var3 > 0)
        {
            if (var3 >= var2 + var4)
            {
                var1.heal(var2 + var4);
            }
            else
            {
                var1.heal(var3);
            }
        }
    }

    public static boolean soulkeeperFillCheck(EntityPlayer var0, int var1)
    {
        if (var1 == 0)
        {
            return false;
        }
        else
        {
            if (var0.inventory.hasItem(mod_HarkenScythe.HSSoulkeeper.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSEssenceKeeper.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSSoulVessel.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSEssenceVessel.itemID))
            {
                int var2 = var1;
                ItemStack var3 = var0.inventory.getCurrentItem();
                int var4;

                if (var3.itemID == mod_HarkenScythe.HSSoulVessel.itemID)
                {
                    var4 = var3.getItemDamage();

                    if (var4 >= var1)
                    {
                        var3.damageItem(-var1, var0);
                        return true;
                    }

                    var2 = var1 - var4;
                    var3.damageItem(-var4, var0);
                }

                int var5;
                ItemStack var6;

                if (var3.itemID == mod_HarkenScythe.HSEssenceVessel.itemID)
                {
                    var0.inventory.mainInventory[var0.inventory.currentItem] = new ItemStack(mod_HarkenScythe.HSSoulVessel, 1);
                    var6 = var0.inventory.getCurrentItem();
                    var5 = var6.getMaxDamage();

                    if (var5 >= var2)
                    {
                        var6.damageItem(var5 - var2, var0);
                        return true;
                    }

                    var2 -= var5;
                    var6.damageItem(var2 - var5, var0);
                }

                if (var3.itemID == mod_HarkenScythe.HSSoulkeeper.itemID)
                {
                    var4 = var3.getItemDamage();

                    if (var4 >= var2)
                    {
                        var3.damageItem(-var2, var0);
                        return true;
                    }

                    var2 -= var4;
                    var3.damageItem(-var4, var0);
                }

                if (var3.itemID == mod_HarkenScythe.HSEssenceKeeper.itemID)
                {
                    var0.inventory.mainInventory[var0.inventory.currentItem] = new ItemStack(mod_HarkenScythe.HSSoulkeeper, 1);
                    var6 = var0.inventory.getCurrentItem();
                    var5 = var6.getMaxDamage();

                    if (var5 >= var2)
                    {
                        var6.damageItem(var5 - var2, var0);
                        return true;
                    }

                    var2 -= var5;
                    var6.damageItem(var2 - var5, var0);
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSSoulVessel".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var5 = var0.inventory.mainInventory[var4].getItemDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(-var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(-var5, var0);
                    }
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSEssenceVessel".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var0.inventory.mainInventory[var4] = new ItemStack(mod_HarkenScythe.HSSoulVessel, 1);
                        var5 = var0.inventory.mainInventory[var4].getMaxDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(var5 - var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(var2 - var5, var0);
                    }
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSSoulkeeper".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var5 = var0.inventory.mainInventory[var4].getItemDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(-var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(-var5, var0);
                    }
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSEssenceKeeper".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var0.inventory.mainInventory[var4] = new ItemStack(mod_HarkenScythe.HSSoulkeeper, 1);
                        var5 = var0.inventory.mainInventory[var4].getMaxDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(var5 - var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(var2 - var5, var0);
                    }
                }
            }

            return false;
        }
    }

    public static boolean bloodkeeperFillCheck(EntityPlayer var0, int var1)
    {
        if (var1 == 0)
        {
            return false;
        }
        else
        {
            if (var0.inventory.hasItem(mod_HarkenScythe.HSBloodkeeper.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSEssenceKeeper.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSBloodVessel.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSEssenceVessel.itemID))
            {
                int var2 = var1;
                ItemStack var3 = var0.inventory.getCurrentItem();
                int var4;

                if (var3.itemID == mod_HarkenScythe.HSBloodVessel.itemID)
                {
                    var4 = var3.getItemDamage();

                    if (var4 >= var1)
                    {
                        var3.damageItem(-var1, var0);
                        return true;
                    }

                    var2 = var1 - var4;
                    var3.damageItem(-var4, var0);
                }

                int var5;
                ItemStack var6;

                if (var3.itemID == mod_HarkenScythe.HSEssenceVessel.itemID)
                {
                    var0.inventory.mainInventory[var0.inventory.currentItem] = new ItemStack(mod_HarkenScythe.HSBloodVessel, 1);
                    var6 = var0.inventory.getCurrentItem();
                    var5 = var6.getMaxDamage();

                    if (var5 >= var2)
                    {
                        var6.damageItem(var5 - var2, var0);
                        return true;
                    }

                    var2 -= var5;
                    var6.damageItem(var2 - var5, var0);
                }

                if (var3.itemID == mod_HarkenScythe.HSBloodkeeper.itemID)
                {
                    var4 = var3.getItemDamage();

                    if (var4 >= var2)
                    {
                        var3.damageItem(-var2, var0);
                        return true;
                    }

                    var2 -= var4;
                    var3.damageItem(-var4, var0);
                }

                if (var3.itemID == mod_HarkenScythe.HSEssenceKeeper.itemID)
                {
                    var0.inventory.mainInventory[var0.inventory.currentItem] = new ItemStack(mod_HarkenScythe.HSBloodkeeper, 1);
                    var6 = var0.inventory.getCurrentItem();
                    var5 = var6.getMaxDamage();

                    if (var5 >= var2)
                    {
                        var6.damageItem(var5 - var2, var0);
                        return true;
                    }

                    var2 -= var5;
                    var6.damageItem(var2 - var5, var0);
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSBloodVessel".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var5 = var0.inventory.mainInventory[var4].getItemDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(-var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(-var5, var0);
                    }
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSEssenceVessel".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var0.inventory.mainInventory[var4] = new ItemStack(mod_HarkenScythe.HSBloodVessel, 1);
                        var5 = var0.inventory.mainInventory[var4].getMaxDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(var5 - var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(var2 - var5, var0);
                    }
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSBloodkeeper".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var5 = var0.inventory.mainInventory[var4].getItemDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(-var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(-var5, var0);
                    }
                }

                for (var4 = 0; var4 < var0.inventory.mainInventory.length; ++var4)
                {
                    if (var0.inventory.mainInventory[var4] != null && "item.HSEssenceKeeper".equals(var0.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
                    {
                        var0.inventory.mainInventory[var4] = new ItemStack(mod_HarkenScythe.HSBloodkeeper, 1);
                        var5 = var0.inventory.mainInventory[var4].getMaxDamage();

                        if (var5 >= var2)
                        {
                            var0.inventory.mainInventory[var4].damageItem(var5 - var2, var0);
                            return true;
                        }

                        var2 -= var5;
                        var0.inventory.mainInventory[var4].damageItem(var2 - var5, var0);
                    }
                }
            }

            return false;
        }
    }

    public static boolean soulkeeperCheck(EntityPlayer var0, int var1, boolean var2)
    {
        if (var1 == 0)
        {
            return true;
        }
        else
        {
            if (var0.inventory.hasItem(mod_HarkenScythe.HSSoulkeeper.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSSoulVessel.itemID))
            {
                int var3;
                int var4;

                if (var0.inventory.hasItem(mod_HarkenScythe.HSSoulkeeper.itemID) && var2)
                {
                    for (var3 = 0; var3 < var0.inventory.mainInventory.length; ++var3)
                    {
                        if (var0.inventory.mainInventory[var3] != null && "item.HSSoulkeeper".equals(var0.inventory.mainInventory[var3].getItem().getUnlocalizedName()))
                        {
                            var4 = var0.inventory.mainInventory[var3].getMaxDamage() - var0.inventory.mainInventory[var3].getItemDamage();

                            if (var4 >= var1)
                            {
                                return true;
                            }
                        }
                    }
                }

                if (var0.inventory.hasItem(mod_HarkenScythe.HSSoulVessel.itemID))
                {
                    for (var3 = 0; var3 < var0.inventory.mainInventory.length; ++var3)
                    {
                        if (var0.inventory.mainInventory[var3] != null && "item.HSSoulVessel".equals(var0.inventory.mainInventory[var3].getItem().getUnlocalizedName()))
                        {
                            var4 = var0.inventory.mainInventory[var3].getMaxDamage() - var0.inventory.mainInventory[var3].getItemDamage();

                            if (var4 >= var1)
                            {
                                return true;
                            }
                        }
                    }
                }

                if (var0.inventory.hasItem(mod_HarkenScythe.HSSoulkeeper.itemID) && !var2)
                {
                    for (var3 = 0; var3 < var0.inventory.mainInventory.length; ++var3)
                    {
                        if (var0.inventory.mainInventory[var3] != null && "item.HSSoulkeeper".equals(var0.inventory.mainInventory[var3].getItem().getUnlocalizedName()))
                        {
                            var4 = var0.inventory.mainInventory[var3].getMaxDamage() - var0.inventory.mainInventory[var3].getItemDamage();

                            if (var4 >= var1)
                            {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }
    }

    public static boolean bloodkeeperCheck(EntityPlayer var0, int var1, boolean var2)
    {
        if (var1 == 0)
        {
            return true;
        }
        else
        {
            if (var0.inventory.hasItem(mod_HarkenScythe.HSBloodkeeper.itemID) || var0.inventory.hasItem(mod_HarkenScythe.HSBloodVessel.itemID))
            {
                int var3;
                int var4;

                if (var0.inventory.hasItem(mod_HarkenScythe.HSBloodkeeper.itemID) && var2)
                {
                    for (var3 = 0; var3 < var0.inventory.mainInventory.length; ++var3)
                    {
                        if (var0.inventory.mainInventory[var3] != null && "item.HSBloodkeeper".equals(var0.inventory.mainInventory[var3].getItem().getUnlocalizedName()))
                        {
                            var4 = var0.inventory.mainInventory[var3].getMaxDamage() - var0.inventory.mainInventory[var3].getItemDamage();

                            if (var4 >= var1)
                            {
                                return true;
                            }
                        }
                    }
                }

                if (var0.inventory.hasItem(mod_HarkenScythe.HSBloodVessel.itemID))
                {
                    for (var3 = 0; var3 < var0.inventory.mainInventory.length; ++var3)
                    {
                        if (var0.inventory.mainInventory[var3] != null && "item.HSBloodVessel".equals(var0.inventory.mainInventory[var3].getItem().getUnlocalizedName()))
                        {
                            var4 = var0.inventory.mainInventory[var3].getMaxDamage() - var0.inventory.mainInventory[var3].getItemDamage();

                            if (var4 >= var1)
                            {
                                return true;
                            }
                        }
                    }
                }

                if (var0.inventory.hasItem(mod_HarkenScythe.HSBloodkeeper.itemID) && !var2)
                {
                    for (var3 = 0; var3 < var0.inventory.mainInventory.length; ++var3)
                    {
                        if (var0.inventory.mainInventory[var3] != null && "item.HSBloodkeeper".equals(var0.inventory.mainInventory[var3].getItem().getUnlocalizedName()))
                        {
                            var4 = var0.inventory.mainInventory[var3].getMaxDamage() - var0.inventory.mainInventory[var3].getItemDamage();

                            if (var4 >= var1)
                            {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }
    }

    static
    {
        soulBuffs[0] = 1;
        soulBuffs[1] = 3;
        soulBuffs[2] = 5;
        soulBuffs[3] = 6;
        soulBuffs[4] = 8;
        soulBuffs[5] = 10;
        soulBuffs[6] = 11;
        soulBuffs[7] = 12;
        soulBuffs[8] = 13;
        soulBuffs[9] = 14;
        soulBuffs[10] = 16;
        soulDebuffs = new int[8];
        soulDebuffs[0] = 2;
        soulDebuffs[1] = 4;
        soulDebuffs[2] = 7;
        soulDebuffs[3] = 9;
        soulDebuffs[4] = 15;
        soulDebuffs[5] = 18;
        soulDebuffs[6] = 19;
        soulDebuffs[7] = 20;
    }
}

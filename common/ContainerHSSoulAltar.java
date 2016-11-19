package mod_HarkenScythe.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHSSoulAltar extends Container
{
    protected TileEntityHSSoulAltar tile_entity;
    public int[] enchantLevels = new int[1];

    public ContainerHSSoulAltar(TileEntityHSSoulAltar var1, InventoryPlayer var2)
    {
        this.tile_entity = var1;
        this.addSlotToContainer(new Slot(var1, 0, 25, 47));
        this.bindPlayerInventory(var2);
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.tile_entity.isUseableByPlayer(var1);
    }

    protected void bindPlayerInventory(InventoryPlayer var1)
    {
        int var2;

        for (var2 = 0; var2 < 3; ++var2)
        {
            for (int var3 = 0; var3 < 9; ++var3)
            {
                this.addSlotToContainer(new Slot(var1, var3 + var2 * 9 + 9, 8 + var3 * 18, 84 + var2 * 18));
            }
        }

        for (var2 = 0; var2 < 9; ++var2)
        {
            this.addSlotToContainer(new Slot(var1, var2, 8 + var2 * 18, 142));
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer var1, int var2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(var2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (var2 == 0)
            {
                if (!this.mergeItemStack(var5, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(var5))
                {
                    return null;
                }

                if (var5.hasTagCompound() && var5.stackSize == 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(var5.copy());
                    var5.stackSize = 0;
                }
                else if (var5.stackSize >= 1)
                {
                    ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(var5.itemID, var5.stackSize, var5.getItemDamage()));
                    var5.stackSize -= var5.stackSize;
                }
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(var1, var5);
        }

        return var3;
    }

    public boolean soulAlterItem(int var1, int var2)
    {
        this.tile_entity.soulAlterItem(var1, var2);
        return true;
    }

    public boolean soulDrain(int var1)
    {
        this.tile_entity.soulAltarDrain(var1);
        return true;
    }

    public boolean soulAlterAugment(int var1, int var2)
    {
        this.tile_entity.soulAlterAugment(var1, var2);
        return true;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer var1)
    {
        super.onCraftGuiClosed(var1);
        ItemStack var2 = this.tile_entity.getStackInSlotOnClosing(0);

        if (var2 != null)
        {
            this.tile_entity.getStackInSlot(0);
            var1.dropPlayerItem(var2);
        }
    }
}

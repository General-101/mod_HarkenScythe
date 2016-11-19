package mod_HarkenScythe.common;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    public Object getServerGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        TileEntity var7 = var3.getBlockTileEntity(var4, var5, var6);
        return var7 instanceof TileEntityHSSoulAltar ? new ContainerHSSoulAltar((TileEntityHSSoulAltar)var7, var2.inventory) : (var7 instanceof TileEntityHSBloodAltar ? new ContainerHSBloodAltar((TileEntityHSBloodAltar)var7, var2.inventory) : null);
    }

    public Object getClientGuiElement(int var1, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        TileEntity var7 = var3.getBlockTileEntity(var4, var5, var6);
        return var7 instanceof TileEntityHSSoulAltar ? new GuiHSSoulAltar(var2.inventory, (TileEntityHSSoulAltar)var7) : (var7 instanceof TileEntityHSBloodAltar ? new GuiHSBloodAltar(var2.inventory, (TileEntityHSBloodAltar)var7) : null);
    }
}

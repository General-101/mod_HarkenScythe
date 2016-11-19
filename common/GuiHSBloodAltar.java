package mod_HarkenScythe.common;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class GuiHSBloodAltar extends GuiContainer
{
    private static ModelBook bookModel = new ModelBook();
    public int[] SARL = new int[11];
    public int[] SAAL = new int[8];
    private Random rand = new Random();
    private ContainerHSBloodAltar containerHSBloodAltar;
    public int field_74214_o;
    public float field_74213_p;
    public float field_74212_q;
    public float field_74211_r;
    public float field_74210_s;
    public float field_74209_t;
    public float field_74208_u;
    ItemStack theItemStack;

    public GuiHSBloodAltar(InventoryPlayer var1, TileEntityHSBloodAltar var2)
    {
        super(new ContainerHSBloodAltar(var2, var1));
        this.containerHSBloodAltar = (ContainerHSBloodAltar)this.inventorySlots;
        int var3;

        for (var3 = 0; var3 < this.SARL.length; ++var3)
        {
            this.SARL[var3] = 0;
        }

        for (var3 = 0; var3 < this.SAAL.length; ++var3)
        {
            this.SAAL[var3] = 0;
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int var1, int var2)
    {
        this.fontRenderer.drawString("Altar of Blood", 6, 6, 4210752);
        this.fontRenderer.drawString("Collected Blood", 94, 6, 4210752);
        byte var3 = 0;

        if (BlockHSBloodAltar.bloodCountTotal > 999)
        {
            var3 = -4;
        }

        this.fontRenderer.drawString("" + BlockHSBloodAltar.bloodCountTotal, 150 + var3, 24, 16777215);

        if (this.inventorySlots.getSlot(0).getStack() != null)
        {
            if (this.SARL[1] != 0)
            {
                this.fontRenderer.drawString("" + this.SARL[1] * this.inventorySlots.getSlot(0).getStack().stackSize, 150, 51, 16777215);
            }

            if (this.SAAL[1] != 0)
            {
                this.fontRenderer.drawString("" + this.SAAL[1] * this.inventorySlots.getSlot(0).getStack().stackSize, 150, 51, 16777215);
            }

            if (this.SARL[1] == 0 && this.SAAL[1] == 0)
            {
                this.fontRenderer.drawString("0", 150, 51, 16777215);
            }
        }

        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 6, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        this.func_74205_h();
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        String var4 = "/mods/mod_HarkenScythe/textures/gui/guiBloodAlter.png";
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution var7 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((var7.getScaledWidth() - 320) / 2 * var7.getScaleFactor(), (var7.getScaledHeight() - 240) / 2 * var7.getScaleFactor(), 320 * var7.getScaleFactor(), 240 * var7.getScaleFactor());
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        GLU.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        float var8 = 1.0F;
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0F, 3.3F, -16.0F);
        GL11.glScalef(var8, var8, var8);
        float var9 = 5.0F;
        GL11.glScalef(var9, var9, var9);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/mod_HarkenScythe/textures/gui/bookCarnage.png");
        GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
        float var10 = this.field_74208_u + (this.field_74209_t - this.field_74208_u) * var1;
        GL11.glTranslatef((1.0F - var10) * 0.2F, (1.0F - var10) * 0.1F, (1.0F - var10) * 0.25F);
        GL11.glRotatef(-(1.0F - var10) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        float var11 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * var1 + 0.25F;
        float var12 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * var1 + 0.75F;
        var11 = (var11 - (float)MathHelper.truncateDoubleToInt((double)var11)) * 1.6F - 0.3F;
        var12 = (var12 - (float)MathHelper.truncateDoubleToInt((double)var12)) * 1.6F - 0.3F;

        if (var11 < 0.0F)
        {
            var11 = 0.0F;
        }

        if (var12 < 0.0F)
        {
            var12 = 0.0F;
        }

        if (var11 > 1.0F)
        {
            var11 = 1.0F;
        }

        if (var12 > 1.0F)
        {
            var12 = 1.0F;
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        bookModel.render((Entity)null, 0.0F, var11, var12, var10, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        this.bloodAltarRecipeCheck();

        if (this.SARL[0] == 0)
        {
            this.buttonList.clear();
        }

        if (this.SAAL[0] == 0)
        {
            this.buttonList.clear();
        }

        if (this.SARL[0] != 0)
        {
            if (this.SARL[0] == 1 || this.SARL[0] == 2)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(1, var5 + 59, var6 + 45, 86, 19, "Enthrall"));
            }

            if (this.SARL[0] == 3 || this.SARL[0] == 4)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(4, var5 + 59, var6 + 45, 86, 19, "Enthrall"));
            }

            if (this.SARL[0] == 5)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(5, var5 + 59, var6 + 45, 86, 19, "Entwine"));
            }

            if (this.SARL[0] == 6)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(6, var5 + 59, var6 + 45, 86, 19, "Germinate"));
            }
        }

        if (this.SAAL[0] != 0)
        {
            if (this.SAAL[0] == 20)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(20, var5 + 59, var6 + 45, 86, 19, "Augment"));
            }

            if (this.SAAL[0] == 21)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(21, var5 + 59, var6 + 45, 86, 19, "Augment"));
            }

            if (this.SAAL[0] == 22)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(22, var5 + 59, var6 + 45, 86, 19, "Augment"));
            }

            if (this.SAAL[0] == 23)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(23, var5 + 59, var6 + 45, 86, 19, "Augment"));
            }

            if (this.SAAL[0] == 24)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(24, var5 + 59, var6 + 45, 86, 19, "Augment"));
            }

            if (this.SAAL[0] == 25)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(25, var5 + 59, var6 + 45, 86, 19, "Augment"));
            }

            if (this.SAAL[0] == 26)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(26, var5 + 59, var6 + 45, 86, 19, "Attune"));
            }

            if (this.SAAL[0] == 27)
            {
                this.buttonList.clear();
                this.buttonList.add(new GuiButton(27, var5 + 59, var6 + 45, 86, 19, "Attune"));
            }
        }
    }

    public void bloodAltarRecipeCheck()
    {
        ItemStack var1 = this.inventorySlots.getSlot(0).getStack();

        if (var1 == null)
        {
            int var2;

            for (var2 = 0; var2 < this.SARL.length; ++var2)
            {
                this.SARL[var2] = 0;
            }

            for (var2 = 0; var2 < this.SAAL.length; ++var2)
            {
                this.SAAL[var2] = 0;
            }
        }

        if (var1 != null)
        {
            this.SARL = RecipeHSBloodAltar.bloodAltarRecipeList(var1);
            this.SAAL = RecipeHSBloodAltar.bloodAltarAugmentList(var1);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    public void actionPerformed(GuiButton var1)
    {
        int var2 = this.inventorySlots.getSlot(0).getStack().stackSize;
        int var3 = this.SARL[1] * this.inventorySlots.getSlot(0).getStack().stackSize / 10;
        int var4 = this.SAAL[1] * this.inventorySlots.getSlot(0).getStack().stackSize / 10;
        int var5 = BlockHSBloodAltar.bloodCountTotal / 10;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;

        if (var3 <= var5)
        {
            switch (var1.id)
            {
                case 0:
                case 2:
                case 3:
                default:
                    break;

                case 1:
                    var6 = true;
                    this.itemStackButtonPress(var3, var1.id, var2);
                    break;

                case 4:
                    var6 = true;
                    this.itemStackButtonPress(var3, var1.id, var2);
                    break;

                case 5:
                    var7 = true;

                    if (!this.mc.isIntegratedServerRunning())
                    {
                        this.containerHSBloodAltar.bloodDrain(var3);
                    }

                    this.containerHSBloodAltar.bloodAlterItem(var1.id, var2);
                    break;

                case 6:
                    var6 = true;

                    if (!this.mc.isIntegratedServerRunning())
                    {
                        this.containerHSBloodAltar.bloodDrain(var3);
                    }

                    this.containerHSBloodAltar.bloodAlterItem(var1.id, var2);
            }

            if (var4 <= var5)
            {
                switch (var1.id)
                {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    default:
                        break;

                    case 20:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 21:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 22:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 23:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 24:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 25:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 26:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }

                        break;

                    case 27:
                        var8 = true;

                        if (!this.inventorySlots.getSlot(0).getStack().isItemEnchanted())
                        {
                            if (!this.mc.isIntegratedServerRunning())
                            {
                                this.containerHSBloodAltar.bloodDrain(var4);
                            }

                            this.containerHSBloodAltar.bloodAlterAugment(var1.id, var2);
                        }
                }
            }

            byte var9 = 2;
            boolean var10 = false;
            boolean var11 = false;
            boolean var12 = false;
            byte var13 = 0;
            byte var14 = 0;
            byte var15 = 0;
            byte var16 = 0;
            byte var17 = 0;
            boolean var18 = var6;
            boolean var19 = var7;
            boolean var20 = var8;
            int var21 = var2;
            int var22 = var1.id;
            int var23 = var3 + var4;
            int var24 = this.inventorySlots.getSlot(0).getStack().itemID;
            int var25 = this.inventorySlots.getSlot(0).getStack().getItemDamage();
            boolean var26 = false;
            byte var27 = 0;
            ByteArrayOutputStream var28 = new ByteArrayOutputStream(8);
            DataOutputStream var29 = new DataOutputStream(var28);

            try
            {
                var29.writeInt(var9);
                var29.writeBoolean(var10);
                var29.writeBoolean(var11);
                var29.writeBoolean(var12);
                var29.writeInt(var13);
                var29.writeInt(var14);
                var29.writeInt(var15);
                var29.writeInt(var16);
                var29.writeInt(var17);
                var29.writeBoolean(var18);
                var29.writeBoolean(var19);
                var29.writeBoolean(var20);
                var29.writeInt(var21);
                var29.writeInt(var22);
                var29.writeInt(var23);
                var29.writeInt(var24);
                var29.writeInt(var25);
                var29.writeBoolean(var26);
                var29.writeInt(var27);
            }
            catch (Exception var32)
            {
                var32.printStackTrace();
            }

            Packet250CustomPayload var30 = new Packet250CustomPayload();
            var30.channel = "HSPacket";
            var30.data = var28.toByteArray();
            var30.length = var28.size();
            EntityClientPlayerMP var31 = this.mc.thePlayer;
            var31.sendQueue.addToSendQueue(var30);
            PacketDispatcher.sendPacketToServer(var30);
        }
    }

    public void func_74205_h()
    {
        ItemStack var1 = this.inventorySlots.getSlot(0).getStack();

        if (!ItemStack.areItemStacksEqual(var1, this.theItemStack))
        {
            this.theItemStack = var1;

            do
            {
                this.field_74211_r += (float)(this.rand.nextInt(4) - this.rand.nextInt(4));
            }
            while (this.field_74213_p <= this.field_74211_r + 1.0F && this.field_74213_p >= this.field_74211_r - 1.0F);
        }

        ++this.field_74214_o;
        this.field_74212_q = this.field_74213_p;
        this.field_74208_u = this.field_74209_t;
        boolean var2 = false;

        if (this.SARL[0] != 0)
        {
            var2 = true;
        }

        if (this.SAAL[0] != 0)
        {
            var2 = true;
        }

        if (var2)
        {
            this.field_74209_t += 0.2F;
        }
        else
        {
            this.field_74209_t -= 0.2F;
        }

        if (this.field_74209_t < 0.0F)
        {
            this.field_74209_t = 0.0F;
        }

        if (this.field_74209_t > 1.0F)
        {
            this.field_74209_t = 1.0F;
        }

        float var3 = (this.field_74211_r - this.field_74213_p) * 0.4F;
        float var4 = 0.2F;

        if (var3 < -var4)
        {
            var3 = -var4;
        }

        if (var3 > var4)
        {
            var3 = var4;
        }

        this.field_74210_s += (var3 - this.field_74210_s) * 0.9F;
        this.field_74213_p += this.field_74210_s;
    }

    public void itemStackButtonPress(int var1, int var2, int var3)
    {
        if (!this.mc.isIntegratedServerRunning())
        {
            this.containerHSBloodAltar.bloodDrain(var1);
        }

        this.containerHSBloodAltar.bloodAlterItem(var2, var3);
    }
}

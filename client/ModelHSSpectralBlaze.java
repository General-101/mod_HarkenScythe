package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelHSSpectralBlaze extends ModelBase
{
    private ModelRenderer[] blazeSticks = new ModelRenderer[12];
    private ModelRenderer blazeHead;

    public ModelHSSpectralBlaze()
    {
        for (int var1 = 0; var1 < this.blazeSticks.length; ++var1)
        {
            this.blazeSticks[var1] = new ModelRenderer(this, 0, 16);
            this.blazeSticks[var1].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
        }

        this.blazeHead = new ModelRenderer(this, 0, 0);
        this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }

    public int func_78104_a()
    {
        return 8;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7)
    {
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.setRotationAngles(var2, var3, var4, var5, var6, var7, var1);
        this.blazeHead.render(var7);

        for (int var8 = 0; var8 < this.blazeSticks.length; ++var8)
        {
            this.blazeSticks[var8].render(var7);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7)
    {
        float var8 = var3 * (float)Math.PI * -0.1F;
        int var9;

        for (var9 = 0; var9 < 4; ++var9)
        {
            this.blazeSticks[var9].rotationPointY = -2.0F + MathHelper.cos(((float)(var9 * 2) + var3) * 0.25F);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 9.0F;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 9.0F;
            ++var8;
        }

        var8 = ((float)Math.PI / 4F) + var3 * (float)Math.PI * 0.03F;

        for (var9 = 4; var9 < 8; ++var9)
        {
            this.blazeSticks[var9].rotationPointY = 2.0F + MathHelper.cos(((float)(var9 * 2) + var3) * 0.25F);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 7.0F;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 7.0F;
            ++var8;
        }

        var8 = 0.47123894F + var3 * (float)Math.PI * -0.05F;

        for (var9 = 8; var9 < 12; ++var9)
        {
            this.blazeSticks[var9].rotationPointY = 11.0F + MathHelper.cos(((float)var9 * 1.5F + var3) * 0.5F);
            this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 5.0F;
            this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 5.0F;
            ++var8;
        }

        this.blazeHead.rotateAngleY = var4 / (180F / (float)Math.PI);
        this.blazeHead.rotateAngleX = var5 / (180F / (float)Math.PI);
    }
}

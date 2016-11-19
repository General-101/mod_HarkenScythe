package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ModelHSSpectralGhast extends ModelBase
{
    ModelRenderer body;
    ModelRenderer[] tentacles = new ModelRenderer[9];

    public ModelHSSpectralGhast()
    {
        byte var1 = -16;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
        this.body.rotationPointY += (float)(24 + var1);
        Random var2 = new Random(1660L);

        for (int var3 = 0; var3 < this.tentacles.length; ++var3)
        {
            this.tentacles[var3] = new ModelRenderer(this, 0, 0);
            float var4 = (((float)(var3 % 3) - (float)(var3 / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
            float var5 = ((float)(var3 / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
            int var6 = var2.nextInt(7) + 8;
            this.tentacles[var3].addBox(-1.0F, 0.0F, -1.0F, 2, var6, 2);
            this.tentacles[var3].rotationPointX = var4;
            this.tentacles[var3].rotationPointZ = var5;
            this.tentacles[var3].rotationPointY = (float)(31 + var1);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7)
    {
        for (int var8 = 0; var8 < this.tentacles.length; ++var8)
        {
            this.tentacles[var8].rotateAngleX = 0.2F * MathHelper.sin(var3 * 0.3F + (float)var8) + 0.4F;
        }
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
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.6F, 0.0F);
        this.body.render(var7);
        ModelRenderer[] var8 = this.tentacles;
        int var9 = var8.length;

        for (int var10 = 0; var10 < var9; ++var10)
        {
            ModelRenderer var11 = var8[var10];
            var11.render(var7);
        }

        GL11.glPopMatrix();
    }
}

package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralGhast;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralGhast extends RenderLiving
{
    public RenderHSSpectralGhast()
    {
        super(new ModelHSSpectralGhast(), 0.5F);
    }

    protected void preRenderGhast(EntityHSSpectralGhast var1, float var2)
    {
        float var3 = ((float)var1.prevAttackCounter + (float)(var1.attackCounter - var1.prevAttackCounter) * var2) / 20.0F;

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        var3 = 1.0F / (var3 * var3 * var3 * var3 * var3 * 2.0F + 1.0F);
        float var4 = (8.0F + var3) / 2.0F;
        float var5 = (8.0F + 1.0F / var3) / 2.0F;
        GL11.glScalef(var5, var4, var5);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.preRenderGhast((EntityHSSpectralGhast)var1, var2);
    }
}

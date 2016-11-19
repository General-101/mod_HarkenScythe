package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralSkeleton;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralSkeleton extends RenderHSSpectralBiped
{
    public RenderHSSpectralSkeleton()
    {
        super(new ModelHSSpectralSkeleton(), 0.5F);
    }

    protected void func_82438_a(EntityHSSpectralSkeleton var1, float var2) {}

    protected void func_82422_c()
    {
        GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.func_82438_a((EntityHSSpectralSkeleton)var1, var2);
    }
}

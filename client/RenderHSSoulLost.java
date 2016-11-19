package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSoulLost;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSoulLost extends Render
{
    private int field_76996_a = -1;
    private ModelBase field_76995_b;

    public RenderHSSoulLost()
    {
        this.shadowSize = 0.3F;
    }

    public void doRenderSoulCube(EntityHSSoulLost var1, double var2, double var4, double var6, float var8, float var9)
    {
        if (this.field_76996_a != 1)
        {
            this.field_76995_b = new ModelHSSoul(0.0F, true);
            this.field_76996_a = 1;
        }

        float var10 = (float)var1.innerRotation + var9;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2, (float)var4, (float)var6);
        this.loadTexture(var1.getSoulTexture());
        float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
        var11 += var11 * var11;
        this.field_76995_b.render(var1, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.doRenderSoulCube((EntityHSSoulLost)var1, var2, var4, var6, var8, var9);
    }
}

package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import mod_HarkenScythe.common.EntityHSSpectralEnderman;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralEnderman extends RenderLiving
{
    private ModelHSSpectralEnderman endermanModel;
    private Random rnd = new Random();

    public RenderHSSpectralEnderman()
    {
        super(new ModelHSSpectralEnderman(), 0.5F);
        this.endermanModel = (ModelHSSpectralEnderman)super.mainModel;
        this.setRenderPassModel(this.endermanModel);
    }

    public void renderEnderman(EntityHSSpectralEnderman var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.endermanModel.isCarrying = var1.getCarried() > 0;
        this.endermanModel.isAttacking = var1.isScreaming();

        if (var1.isScreaming())
        {
            double var10 = 0.02D;
            var2 += this.rnd.nextGaussian() * var10;
            var6 += this.rnd.nextGaussian() * var10;
        }

        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected void renderCarrying(EntityHSSpectralEnderman var1, float var2)
    {
        super.renderEquippedItems(var1, var2);

        if (var1.getCarried() > 0)
        {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glPushMatrix();
            float var3 = 0.5F;
            GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
            var3 *= 1.0F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-var3, -var3, var3);
            int var4 = var1.getBrightnessForRender(var2);
            int var5 = var4 % 65536;
            int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0F, (float)var6 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.loadTexture("/terrain.png");
            this.renderBlocks.renderBlockAsItem(Block.blocksList[var1.getCarried()], var1.getCarryingData(), 1.0F);
            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
    }

    protected int renderEyes(EntityHSSpectralEnderman var1, int var2, float var3)
    {
        if (var2 != 0)
        {
            return -1;
        }
        else
        {
            this.loadTexture("/mods/mod_HarkenScythe/textures/models/mob/spectral_enderman_eyes.png");
            float var4 = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_LIGHTING);

            if (var1.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            char var5 = 61680;
            int var6 = var5 % 65536;
            int var7 = var5 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
            return 1;
        }
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.renderEyes((EntityHSSpectralEnderman)var1, var2, var3);
    }

    protected void renderEquippedItems(EntityLiving var1, float var2)
    {
        this.renderCarrying((EntityHSSpectralEnderman)var1, var2);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderEnderman((EntityHSSpectralEnderman)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderEnderman((EntityHSSpectralEnderman)var1, var2, var4, var6, var8, var9);
    }
}

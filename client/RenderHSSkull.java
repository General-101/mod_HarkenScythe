package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.TileEntityHSSkull;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderHSSkull extends TileEntitySpecialRenderer
{
    public static RenderHSSkull skullRenderer;
    private ModelHSSkull field_82396_c = new ModelHSSkull(0, 0, 64, 32);
    private ModelHSSkull field_82395_d = new ModelHSSkull(0, 0, 64, 64);
    private ModelHSSkull dodads = new ModelHSSkull(0, 0, 64, 32);

    public void renderTileEntityHSSkullAt(TileEntityHSSkull var1, double var2, double var4, double var6, float var8)
    {
        this.func_82393_a((float)var2, (float)var4, (float)var6, var1.getBlockMetadata() & 7, (float)(var1.func_82119_b() * 360) / 16.0F, var1.getSkullType(), var1.getExtraType());
    }

    /**
     * Associate a TileEntityRenderer with this TileEntitySpecialRenderer
     */
    public void setTileEntityRenderer(TileEntityRenderer var1)
    {
        super.setTileEntityRenderer(var1);
        skullRenderer = this;
    }

    public void func_82393_a(float var1, float var2, float var3, int var4, float var5, int var6, String var7)
    {
        ModelHSSkull var8 = this.field_82396_c;
        float var9 = 0.0F;
        float var10 = 0.0F;

        switch (var6)
        {
            case 0:
            default:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/spider.png");
                break;

            case 1:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/cavespider.png");
                break;

            case 2:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderman.png");
                break;

            case 3:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/pigmanzombie.png");
                break;

            case 4:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/villagerzombie.png");
                break;

            case 5:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/villager.png");
                break;

            case 6:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/cow.png");
                break;

            case 7:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/mooshroom.png");
                break;

            case 8:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/pig.png");
                break;

            case 9:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/squid.png");
                break;

            case 10:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/sheep.png");
                break;

            case 11:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/wolf.png");
                var9 = 0.1F;
                break;

            case 12:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/ocelot.png");
                var9 = 0.1F;
                break;

            case 13:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderdragon.png");
                var9 = -0.24F;
                var10 = -0.24F;
                break;

            case 14:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/bat.png");
                var9 = 0.16F;
                break;

            case 15:
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/chicken.png");
                var9 = 0.1F;
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (var4 != 1)
        {
            switch (var4)
            {
                case 2:
                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var10, var3 + 0.74F + var9);
                    break;

                case 3:
                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var10, var3 + 0.26F - var9);
                    var5 = 180.0F;
                    break;

                case 4:
                    GL11.glTranslatef(var1 + 0.74F + var9, var2 + 0.25F + var10, var3 + 0.5F);
                    var5 = 270.0F;
                    break;

                case 5:
                default:
                    GL11.glTranslatef(var1 + 0.26F - var9, var2 + 0.25F + var10, var3 + 0.5F);
                    var5 = 90.0F;
            }
        }
        else
        {
            GL11.glTranslatef(var1 + 0.5F, var2, var3 + 0.5F);
        }

        float var11 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        switch (var6)
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
            default:
                GL11.glScalef(-1.0F, -1.0F, 1.0F);
                break;

            case 11:
                GL11.glScalef(-0.8F, -0.8F, 0.8F);
                break;

            case 12:
                GL11.glScalef(-0.7F, -0.7F, 0.7F);
                break;

            case 13:
                GL11.glScalef(-2.0F, -2.0F, 2.0F);
                break;

            case 14:
                GL11.glScalef(-0.35F, -0.35F, 0.35F);
                break;

            case 15:
                GL11.glScalef(-0.6F, -0.65F, 0.65F);
        }

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        var8.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var11);
        GL11.glPopMatrix();

        switch (var6)
        {
            case 13:
                this.enderDragonAcc(var1, var2, var3, var4, var5, var6, var7, var9, var10);

            case 0:
            default:
        }
    }

    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8)
    {
        this.renderTileEntityHSSkullAt((TileEntityHSSkull)var1, var2, var4, var6, var8);
    }

    public void enderDragonAcc(float var1, float var2, float var3, int var4, float var5, int var6, String var7, float var8, float var9)
    {
        ModelHSSkull var10 = this.dodads;
        this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderdragonsnout.png");
        var8 = -1.0F;
        var9 = -0.24F;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (var4 != 1)
        {
            switch (var4)
            {
                case 2:
                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.74F + var8);
                    break;

                case 3:
                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.26F - var8);
                    var5 = 180.0F;
                    break;

                case 4:
                    GL11.glTranslatef(var1 + 0.74F + var8, var2 + 0.25F + var9, var3 + 0.5F);
                    var5 = 270.0F;
                    break;

                case 5:
                default:
                    GL11.glTranslatef(var1 + 0.26F - var8, var2 + 0.25F + var9, var3 + 0.5F);
                    var5 = 90.0F;
            }
        }
        else if (var5 == 0.0F)
        {
            GL11.glTranslatef(var1 + 0.5F, var2, var3 + 0.5F - 0.76F);
        }
        else if ((double)var5 == 22.5D)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.3F, var2, var3 + 0.5F - 0.7F);
        }
        else if (var5 == 45.0F)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.54F, var2, var3 + 0.5F - 0.54F);
        }
        else if ((double)var5 == 67.5D)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.7F, var2, var3 + 0.5F - 0.3F);
        }
        else if (var5 == 90.0F)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.76F, var2, var3 + 0.5F);
        }
        else if ((double)var5 == 112.5D)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.7F, var2, var3 + 0.5F + 0.3F);
        }
        else if (var5 == 135.0F)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.54F, var2, var3 + 0.5F + 0.54F);
        }
        else if ((double)var5 == 157.5D)
        {
            GL11.glTranslatef(var1 + 0.5F + 0.3F, var2, var3 + 0.5F + 0.7F);
        }
        else if (var5 == 180.0F)
        {
            GL11.glTranslatef(var1 + 0.5F, var2, var3 + 0.5F + 0.76F);
        }
        else if ((double)var5 == 202.5D)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.3F, var2, var3 + 0.5F + 0.7F);
        }
        else if (var5 == 225.0F)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.54F, var2, var3 + 0.5F + 0.54F);
        }
        else if ((double)var5 == 247.5D)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.7F, var2, var3 + 0.5F + 0.3F);
        }
        else if (var5 == 270.0F)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.76F, var2, var3 + 0.5F);
        }
        else if ((double)var5 == 292.5D)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.7F, var2, var3 + 0.5F - 0.3F);
        }
        else if (var5 == 315.0F)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.54F, var2, var3 + 0.5F - 0.54F);
        }
        else if ((double)var5 == 337.5D)
        {
            GL11.glTranslatef(var1 + 0.5F - 0.3F, var2, var3 + 0.5F - 0.7F);
        }
        else
        {
            GL11.glTranslatef(var1 + 0.5F, var2, var3 + 0.5F);
        }

        float var11 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        switch (var6)
        {
            case 0:
            case 13:
            default:
                GL11.glScalef(-1.2F, -1.2F, 1.2F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                var10.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var11);
                GL11.glPopMatrix();
                ModelHSSkull var12 = this.dodads;
                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderdragonnose.png");
                var8 = -1.1F;
                var9 = 0.3F;
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_CULL_FACE);

                if (var4 != 1)
                {
                    switch (var4)
                    {
                        case 2:
                            GL11.glTranslatef(var1 + 0.5F + 0.15F, var2 + 0.25F + var9, var3 + 0.74F + var8);
                            break;

                        case 3:
                            GL11.glTranslatef(var1 + 0.5F - 0.15F, var2 + 0.25F + var9, var3 + 0.26F - var8);
                            var5 = 180.0F;
                            break;

                        case 4:
                            GL11.glTranslatef(var1 + 0.74F + var8, var2 + 0.25F + var9, var3 + 0.5F - 0.15F);
                            var5 = 270.0F;
                            break;

                        case 5:
                        default:
                            GL11.glTranslatef(var1 + 0.26F - var8, var2 + 0.25F + var9, var3 + 0.5F + 0.15F);
                            var5 = 90.0F;
                    }
                }
                else if (var5 == 0.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.86F);
                }
                else if ((double)var5 == 22.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.3F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.7F);
                }
                else if (var5 == 45.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.54F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F + 0.05F);
                }
                else if ((double)var5 == 67.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.7F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F + 0.1F);
                }
                else if (var5 == 90.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.86F, var2 + 0.25F + var9, var3 + 0.5F + 0.15F);
                }
                else if ((double)var5 == 112.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.7F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F + 0.15F);
                }
                else if (var5 == 135.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.54F - 0.05F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F + 0.15F);
                }
                else if ((double)var5 == 157.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F + 0.3F - 0.1F, var2 + 0.25F + var9, var3 + 0.5F + 0.7F + 0.15F);
                }
                else if (var5 == 180.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.86F);
                }
                else if ((double)var5 == 202.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.3F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.7F);
                }
                else if (var5 == 225.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.54F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F - 0.05F);
                }
                else if ((double)var5 == 247.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.7F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F - 0.1F);
                }
                else if (var5 == 270.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.86F, var2 + 0.25F + var9, var3 + 0.5F - 0.15F);
                }
                else if ((double)var5 == 292.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.7F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F - 0.15F);
                }
                else if (var5 == 315.0F)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.54F + 0.05F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F - 0.15F);
                }
                else if ((double)var5 == 337.5D)
                {
                    GL11.glTranslatef(var1 + 0.5F - 0.3F + 0.1F, var2 + 0.25F + var9, var3 + 0.5F - 0.7F - 0.15F);
                }
                else
                {
                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F);
                }

                float var13 = 0.0625F;
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);

                switch (var6)
                {
                    case 0:
                    case 13:
                    default:
                        GL11.glScalef(-0.3F, -0.3F, 0.3F);
                        GL11.glEnable(GL11.GL_ALPHA_TEST);
                        var12.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var13);
                        GL11.glPopMatrix();
                        ModelHSSkull var14 = this.dodads;
                        this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderdragonnose.png");
                        var8 = -1.1F;
                        var9 = 0.3F;
                        GL11.glPushMatrix();
                        GL11.glDisable(GL11.GL_CULL_FACE);

                        if (var4 != 1)
                        {
                            switch (var4)
                            {
                                case 2:
                                    GL11.glTranslatef(var1 + 0.5F - 0.15F, var2 + 0.25F + var9, var3 + 0.74F + var8);
                                    break;

                                case 3:
                                    GL11.glTranslatef(var1 + 0.5F + 0.15F, var2 + 0.25F + var9, var3 + 0.26F - var8);
                                    var5 = 180.0F;
                                    break;

                                case 4:
                                    GL11.glTranslatef(var1 + 0.74F + var8, var2 + 0.25F + var9, var3 + 0.5F + 0.15F);
                                    var5 = 270.0F;
                                    break;

                                case 5:
                                default:
                                    GL11.glTranslatef(var1 + 0.26F - var8, var2 + 0.25F + var9, var3 + 0.5F - 0.15F);
                                    var5 = 90.0F;
                            }
                        }
                        else if (var5 == 0.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.86F);
                        }
                        else if ((double)var5 == 22.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.3F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.84F);
                        }
                        else if (var5 == 45.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.54F - 0.05F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F - 0.15F);
                        }
                        else if ((double)var5 == 67.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.7F + 0.05F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F - 0.15F);
                        }
                        else if (var5 == 90.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.86F, var2 + 0.25F + var9, var3 + 0.5F - 0.15F);
                        }
                        else if ((double)var5 == 112.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.84F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F - 0.15F);
                        }
                        else if (var5 == 135.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.54F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F - 0.05F);
                        }
                        else if ((double)var5 == 157.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.3F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.7F + 0.05F);
                        }
                        else if (var5 == 180.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.86F);
                        }
                        else if ((double)var5 == 202.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.3F + 0.15F, var2 + 0.25F + var9, var3 + 0.5F + 0.84F);
                        }
                        else if (var5 == 225.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.54F + 0.05F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F + 0.15F);
                        }
                        else if ((double)var5 == 247.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.7F - 0.05F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F + 0.15F);
                        }
                        else if (var5 == 270.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.86F, var2 + 0.25F + var9, var3 + 0.5F + 0.15F);
                        }
                        else if ((double)var5 == 292.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.84F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F + 0.15F);
                        }
                        else if (var5 == 315.0F)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.54F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F + 0.05F);
                        }
                        else if ((double)var5 == 337.5D)
                        {
                            GL11.glTranslatef(var1 + 0.5F - 0.3F - 0.15F, var2 + 0.25F + var9, var3 + 0.5F - 0.7F - 0.05F);
                        }
                        else
                        {
                            GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F);
                        }

                        float var15 = 0.0625F;
                        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

                        switch (var6)
                        {
                            case 0:
                            case 13:
                            default:
                                GL11.glScalef(-0.3F, -0.3F, 0.3F);
                                GL11.glEnable(GL11.GL_ALPHA_TEST);
                                var14.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var15);
                                GL11.glPopMatrix();
                                ModelHSSkull var16 = this.dodads;
                                this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderdragonhorn.png");
                                var8 = -0.3F;
                                var9 = 0.75F;
                                GL11.glPushMatrix();
                                GL11.glDisable(GL11.GL_CULL_FACE);

                                if (var4 != 1)
                                {
                                    switch (var4)
                                    {
                                        case 2:
                                            GL11.glTranslatef(var1 + 0.5F + 0.2F, var2 + 0.25F + var9, var3 + 0.74F + var8);
                                            break;

                                        case 3:
                                            GL11.glTranslatef(var1 + 0.5F - 0.2F, var2 + 0.25F + var9, var3 + 0.26F - var8);
                                            var5 = 180.0F;
                                            break;

                                        case 4:
                                            GL11.glTranslatef(var1 + 0.74F + var8, var2 + 0.25F + var9, var3 + 0.5F - 0.2F);
                                            var5 = 270.0F;
                                            break;

                                        case 5:
                                        default:
                                            GL11.glTranslatef(var1 + 0.26F - var8, var2 + 0.25F + var9, var3 + 0.5F + 0.2F);
                                            var5 = 90.0F;
                                    }
                                }
                                else if (var5 == 0.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.2F, var2 + 0.25F + var9, var3 + 0.5F);
                                }
                                else if ((double)var5 == 22.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.3F - 0.1F, var2 + 0.25F + var9, var3 + 0.5F + 0.1F);
                                }
                                else if (var5 == 45.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.54F - 0.4F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F + 0.7F);
                                }
                                else if ((double)var5 == 67.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.7F - 0.6F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F + 0.5F);
                                }
                                else if (var5 == 90.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F + 0.2F);
                                }
                                else if ((double)var5 == 112.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.1F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F - 0.1F);
                                }
                                else if (var5 == 135.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.54F - 0.7F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F - 0.4F);
                                }
                                else if ((double)var5 == 157.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.3F - 0.5F, var2 + 0.25F + var9, var3 + 0.5F + 0.7F - 0.6F);
                                }
                                else if (var5 == 180.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.2F, var2 + 0.25F + var9, var3 + 0.5F);
                                }
                                else if ((double)var5 == 202.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.3F + 0.1F, var2 + 0.25F + var9, var3 + 0.5F - 0.1F);
                                }
                                else if (var5 == 225.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.54F + 0.4F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F - 0.7F);
                                }
                                else if ((double)var5 == 247.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.7F + 0.6F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F - 0.5F);
                                }
                                else if (var5 == 270.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F - 0.2F);
                                }
                                else if ((double)var5 == 292.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F + 0.1F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F + 0.1F);
                                }
                                else if (var5 == 315.0F)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.54F + 0.7F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F + 0.4F);
                                }
                                else if ((double)var5 == 337.5D)
                                {
                                    GL11.glTranslatef(var1 + 0.5F - 0.3F + 0.5F, var2 + 0.25F + var9, var3 + 0.5F - 0.7F + 0.6F);
                                }
                                else
                                {
                                    GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F);
                                }

                                float var17 = 0.0625F;
                                GL11.glEnable(GL12.GL_RESCALE_NORMAL);

                                switch (var6)
                                {
                                    case 0:
                                    case 13:
                                    default:
                                        GL11.glScalef(-0.4F, -0.4F, 0.4F);
                                        GL11.glEnable(GL11.GL_ALPHA_TEST);
                                        var12.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var17);
                                        GL11.glPopMatrix();
                                        ModelHSSkull var18 = this.dodads;
                                        this.bindTextureByName("/mods/mod_HarkenScythe/textures/models/mob/skulls/enderdragonhorn.png");
                                        var8 = -0.3F;
                                        var9 = 0.75F;
                                        GL11.glPushMatrix();
                                        GL11.glDisable(GL11.GL_CULL_FACE);

                                        if (var4 != 1)
                                        {
                                            switch (var4)
                                            {
                                                case 2:
                                                    GL11.glTranslatef(var1 + 0.5F - 0.2F, var2 + 0.25F + var9, var3 + 0.74F + var8);
                                                    break;

                                                case 3:
                                                    GL11.glTranslatef(var1 + 0.5F + 0.2F, var2 + 0.25F + var9, var3 + 0.26F - var8);
                                                    var5 = 180.0F;
                                                    break;

                                                case 4:
                                                    GL11.glTranslatef(var1 + 0.74F + var8, var2 + 0.25F + var9, var3 + 0.5F + 0.2F);
                                                    var5 = 270.0F;
                                                    break;

                                                case 5:
                                                default:
                                                    GL11.glTranslatef(var1 + 0.26F - var8, var2 + 0.25F + var9, var3 + 0.5F - 0.2F);
                                                    var5 = 90.0F;
                                            }
                                        }
                                        else if (var5 == 0.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.2F, var2 + 0.25F + var9, var3 + 0.5F);
                                        }
                                        else if ((double)var5 == 22.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.3F - 0.5F, var2 + 0.25F + var9, var3 + 0.5F - 0.06F);
                                        }
                                        else if (var5 == 45.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.54F - 0.7F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F + 0.4F);
                                        }
                                        else if ((double)var5 == 67.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.7F - 0.76F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F + 0.07F);
                                        }
                                        else if (var5 == 90.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F - 0.2F);
                                        }
                                        else if ((double)var5 == 112.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.05F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F - 0.5F);
                                        }
                                        else if (var5 == 135.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.54F - 0.4F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F - 0.7F);
                                        }
                                        else if ((double)var5 == 157.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.3F - 0.1F, var2 + 0.25F + var9, var3 + 0.5F + 0.7F - 0.75F);
                                        }
                                        else if (var5 == 180.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F + 0.2F, var2 + 0.25F + var9, var3 + 0.5F);
                                        }
                                        else if ((double)var5 == 202.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.3F + 0.5F, var2 + 0.25F + var9, var3 + 0.5F + 0.06F);
                                        }
                                        else if (var5 == 225.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.54F + 0.7F, var2 + 0.25F + var9, var3 + 0.5F + 0.54F - 0.4F);
                                        }
                                        else if ((double)var5 == 247.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.7F + 0.76F, var2 + 0.25F + var9, var3 + 0.5F + 0.3F - 0.07F);
                                        }
                                        else if (var5 == 270.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F + 0.2F);
                                        }
                                        else if ((double)var5 == 292.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.05F, var2 + 0.25F + var9, var3 + 0.5F - 0.3F + 0.5F);
                                        }
                                        else if (var5 == 315.0F)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.54F + 0.4F, var2 + 0.25F + var9, var3 + 0.5F - 0.54F + 0.7F);
                                        }
                                        else if ((double)var5 == 337.5D)
                                        {
                                            GL11.glTranslatef(var1 + 0.5F - 0.3F + 0.1F, var2 + 0.25F + var9, var3 + 0.5F - 0.7F + 0.75F);
                                        }
                                        else
                                        {
                                            GL11.glTranslatef(var1 + 0.5F, var2 + 0.25F + var9, var3 + 0.5F);
                                        }

                                        float var19 = 0.0625F;
                                        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

                                        switch (var6)
                                        {
                                            case 0:
                                            case 13:
                                            default:
                                                GL11.glScalef(-0.4F, -0.4F, 0.4F);
                                                GL11.glEnable(GL11.GL_ALPHA_TEST);
                                                var12.render((Entity)null, 0.0F, 0.0F, 0.0F, var5, 0.0F, var19);
                                                GL11.glPopMatrix();
                                        }
                                }
                        }
                }
        }
    }
}

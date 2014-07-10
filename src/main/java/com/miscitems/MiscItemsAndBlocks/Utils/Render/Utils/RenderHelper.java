package com.miscitems.MiscItemsAndBlocks.Utils.Render.Utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderHelper {


    static final boolean testflagColour = false;
    static final boolean testflagIsFull3D = false;
    static boolean wrongRendererMsgWritten = false;

    public static void RenderInventoryBlockWithColor(IItemRenderer.ItemRenderType renderType, ItemStack stack, Color color){

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();


        boolean mustundotranslate = false;
        switch (renderType) {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON: {
                break;
            }
            case ENTITY:
            case INVENTORY: {

                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                mustundotranslate = true;
                break;
            }
            default:
                break;
        }




        IIcon icon = stack.getItem().getIconFromDamage(5);
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        icon = stack.getItem().getIconFromDamage(4);
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());


        icon = stack.getItem().getIconFromDamage(2);
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());


        icon = stack.getItem().getIconFromDamage(3);
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        icon = stack.getItem().getIconFromDamage(1);
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        icon = stack.getItem().getIconFromDamage(0);
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
         tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
        tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV());

        tessellator.draw();

        if (mustundotranslate) GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }


    public static void RenderInventoryItem(IItemRenderer.ItemRenderType type, ItemStack item) {

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        IIcon icon1 = item.getItem().getIconFromDamage(0);

        double minU1 = (double)icon1.getMinU();
        double minV1 = (double)icon1.getMinV();
        double maxU1 = (double)icon1.getMaxU();
        double maxV1 = (double)icon1.getMaxV();

        tessellator.addVertexWithUV(16.0, 16.0, 0.0, maxU1, maxV1);
        tessellator.addVertexWithUV(16.0, 0.0, 0.0, maxU1, minV1);
        tessellator.addVertexWithUV( 0.0, 0.0, 0.0, minU1, minV1);
        tessellator.addVertexWithUV( 0.0, 16.0, 0.0, minU1, maxV1);
        tessellator.draw();
    }


}
package com.miscitems.MiscItemsAndBlocks.Utils.Render.TileEntityRenderer;

import MiscItemsApi.Electric.IPowerGeneration;
import MiscItemsApi.Electric.IPowerTile;
import com.miscitems.MiscItemsAndBlocks.Models.ModelPowerCable;
import com.miscitems.MiscItemsAndBlocks.TileEntity.Electric.TileEntityPowerCable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityPowerCableRender extends TileEntitySpecialRenderer {
    
    private final ModelPowerCable model;
   
    public TileEntityPowerCableRender() {
            this.model = new ModelPowerCable();
    }
   
    private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
            int meta = world.getBlockMetadata(x, y, z);
            GL11.glPushMatrix();
            GL11.glRotatef(meta * (- 90), 0.0F, 0.0F, 1.0F);
            GL11.glPopMatrix();
    }
   
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            
            
            bindTexture(new ResourceLocation("miscitems" , "textures/models/PowerCable.png"));
            
            if(te.hasWorldObj()){
            
            World world = te.getWorldObj();
            int X = te.xCoord;
            int Y = te.yCoord;
            int Z = te.zCoord;
            
            
            boolean top, bottom, front, back, right, left;
            int Meta = world.getBlockMetadata(X, Y, Z);
            
            bottom = IsPowerBlock(world, X, Y - 1, Z, Meta);
            
            top = IsPowerBlock(world, X, Y + 1, Z, Meta);
            
            front = IsPowerBlock(world, X, Y, Z + 1, Meta);
            
            back = IsPowerBlock(world, X, Y, Z - 1, Meta);
            
            right = IsPowerBlock(world, X - 1, Y, Z, Meta);
            
            left = IsPowerBlock(world, X + 1, Y, Z, Meta);
            
         GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            
            this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, bottom, top, left, right, front, back);
            
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            }else{
                World world = te.getWorldObj();
                int X = te.xCoord;
                int Y = te.yCoord;
                int Z = te.zCoord;
                
           
                
             GL11.glPushMatrix();
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                
                this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, true, true, true, true, true, true);
                
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
    }
     


    
    public boolean IsPowerBlock(World world, int x, int y, int z, int Meta){
    	
    	Block BlockID = world.getBlock(x, y, z);
    	TileEntity tile = world.getTileEntity(x, y, z);
    	int Meta2 = world.getBlockMetadata(x, y, z);
    	
    	Block block = BlockID;

    	if(tile instanceof IPowerTile && ((IPowerTile)tile).ConnectsToTile(new TileEntityPowerCable()))return true;
    	
    	else if(tile instanceof IPowerGeneration)return true;

    	else


    	
    	return false;
    }
}


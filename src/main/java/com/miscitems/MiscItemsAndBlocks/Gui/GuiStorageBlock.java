package com.miscitems.MiscItemsAndBlocks.Gui;

import com.miscitems.MiscItemsAndBlocks.Container.ContainerStorageBlock;
import com.miscitems.MiscItemsAndBlocks.Container.Utils.ModGuiContainer;
import com.miscitems.MiscItemsAndBlocks.TileEntity.Inventories.TileEntityStorageBlock;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiStorageBlock extends ModGuiContainer {


	private final ResourceLocation Texture = new ResourceLocation("miscitems" , "textures/gui/StorageBlockGui.png");
	
	    String username;
	    boolean isScrolling = false;
	    boolean wasClicking;
	    float currentScroll = 0.0F;
	    int slotPos = 0;
	    int prevSlotPos = 0;
	    
	    
	    TileEntityStorageBlock tile;
	    
		public GuiStorageBlock(InventoryPlayer InvPlayer, TileEntityStorageBlock tile) {
			super(new ContainerStorageBlock(InvPlayer, tile));
			this.xSize = 176;
			this.ySize = 235;
			
			this.tile = tile;
	        username = InvPlayer.player.getCommandSenderName();
			
	     
		}


	    @Override
	    public void drawScreen (int mouseX, int mouseY, float par3)
	    {
	        super.drawScreen(mouseX, mouseY, par3);
	        updateScrollbar(mouseX, mouseY, par3);
	    }

	    protected void updateScrollbar (int mouseX, int mouseY, float par3)
	    {
	        if (tile.getSizeInventory() > 64)
	        {
	            boolean mouseDown = Mouse.isButtonDown(0);
	            int lefto = this.guiLeft;
	            int topo = this.guiTop;
	            int xScroll = lefto + 157;
	            int yScroll = topo + 6;
	            int scrollWidth = xScroll + 14;
	            int scrollHeight = yScroll + 144;

	            if (!this.wasClicking && mouseDown && mouseX >= xScroll && mouseY >= yScroll && mouseX < scrollWidth && mouseY < scrollHeight)
	            {
	                this.isScrolling = true;
	            }

	            if (!mouseDown)
	            {
	                this.isScrolling = false;
	            }

	            if (wasClicking && !isScrolling && slotPos != prevSlotPos)
	            {
	                prevSlotPos = slotPos;
	            }

	            this.wasClicking = mouseDown;

	            if (this.isScrolling)
	            {
	                this.currentScroll = (mouseY - yScroll - 7.5F) / (scrollHeight - yScroll - 15.0F);

	                if (this.currentScroll < 0.0F)
	                {
	                    this.currentScroll = 0.0F;
	                }

	                if (this.currentScroll > 1.0F)
	                {
	                    this.currentScroll = 1.0F;
	                }

	                int s = ((ContainerStorageBlock) this.container).scrollTo(this.currentScroll);
	                if (s != -1)
	                    slotPos = s;
	            }
	        }
	    }
	    


	    @Override
	    protected void drawGuiContainerBackgroundLayer (float f, int mouseX, int mouseY)
	    {
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(Texture);
	        int cornerX = (width - xSize) / 2;
	        int cornerY = (height - ySize) / 2;
	        drawTexturedModalRect(cornerX, cornerY, 0, 0, 176, ySize);

	            
	            drawTexturedModalRect(cornerX + 157, (int) (cornerY + 6 + 127 * currentScroll), 176, 0, 12, 15);
	        

	
	    }







	}

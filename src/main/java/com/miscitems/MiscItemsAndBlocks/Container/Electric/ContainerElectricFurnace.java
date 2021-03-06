package com.miscitems.MiscItemsAndBlocks.Container.Electric;

import MiscUtils.GuiObjects.Slots.SlotOutput;
import MiscUtils.Utils.ContainerBase;
import com.miscitems.MiscItemsAndBlocks.GuiObjects.Slots.SlotPowerStorage;
import com.miscitems.MiscItemsAndBlocks.TileEntity.Electric.TileEntityElectricFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerElectricFurnace  extends ContainerBase {

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tile.isUseableByPlayer(entityplayer);
	}
	
    private TileEntityElectricFurnace tile;
    
    int LastPower;
    int LastWorkTime;
    int LastMaxPower;
	
    public ContainerElectricFurnace(InventoryPlayer InvPlayer, TileEntityElectricFurnace tile)
    {
    	this.tile = tile;
    	
    	for(int x = 0; x < 9; x++){
    		
    		addSlotToContainer(new Slot(InvPlayer, x, 8 + 18 * x, 142));
    	}
    	
    	for(int y = 0; y < 3; y++){
    		for(int x = 0; x < 9; x++){
    			
    			addSlotToContainer(new Slot(InvPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
    		}
    		
    		addSlotToContainer(new Slot(tile, 0, 56, 17));
    		addSlotToContainer(new SlotPowerStorage(tile, 1, 56, 53));
    		addSlotToContainer(new SlotOutput(tile, 2, 116, 35));
    }

}


    @Override
    public IInventory getTile() {
        return tile;
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, (int)this.tile.GetPower());
        par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.GetWorkTime());
        par1ICrafting.sendProgressBarUpdate(this, 2, (int)this.tile.GetMaxPower());
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.LastPower != this.tile.GetPower())
            {
                icrafting.sendProgressBarUpdate(this, 0, (int)this.tile.GetPower());
            }
            
            if (this.LastWorkTime != this.tile.GetWorkTime())
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tile.GetWorkTime());
            }
            
            if (this.LastMaxPower != this.tile.GetMaxPower())
            {
                icrafting.sendProgressBarUpdate(this, 2, (int)this.tile.GetMaxPower());
            }
            
            
            
        }

        this.LastPower = (int)this.tile.GetPower();
        this.LastWorkTime = this.tile.GetWorkTime();
        this.LastMaxPower = (int)this.tile.GetMaxPower();
        
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tile.SetPower(par2);
        }
        
        if (par1 == 1)
        {
            this.tile.SetWorkTime(par2);
        }
        
        if (par1 == 2)
        {
            this.tile.SetMaxPower(par2);
        }
        
        

    }
	  

}

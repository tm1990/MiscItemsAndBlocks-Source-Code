package com.miscitems.MiscItemsAndBlocks.GuiObjects.Slots;

import com.miscitems.MiscItemsAndBlocks.TileEntity.Machines.TileEntityWorktable;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class SlotCraftingInv extends SlotCrafting
{
private EntityPlayer thePlayer;
private final IInventory craftMatrix;
private IInventory craftResultMatrix;
private IInventory craftSupplyMatrix;

TileEntityWorktable tile;

public SlotCraftingInv(Container parent, EntityPlayer player, IInventory craftingMatrix,
IInventory craftingResultMatrix, IInventory craftingSupplyMatrix,
int slotID, int xDisplay, int yDisplay, TileEntityWorktable tile)
{
super(player, craftingMatrix, craftingResultMatrix, slotID, xDisplay, yDisplay);
this.tile = tile;
thePlayer = player;
craftMatrix = craftingMatrix;
craftResultMatrix = craftingResultMatrix;
craftSupplyMatrix = craftingSupplyMatrix;
}

@Override
public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
    {
	tile.OnInvChanged();
	
boolean found = false;
boolean metaSens = false;
        MinecraftForge.EVENT_BUS.post(new PlayerEvent.ItemCraftedEvent(thePlayer, stack, craftMatrix));
        this.onCrafting(stack);
        
        for(int invIndex = 0; invIndex < 9; invIndex++)
        {
         found = false;
         ItemStack craftComponentStack = craftSupplyMatrix.getStackInSlot(invIndex);
         if(craftComponentStack != null)
         {
        	 
        	 
         if(!craftComponentStack.isItemStackDamageable() && craftComponentStack.getMaxDamage() == 0
         && craftComponentStack!= new ItemStack(Blocks.planks)
         && craftComponentStack != new ItemStack(Blocks.wool)
         && craftComponentStack != new ItemStack(Blocks.leaves))
         {
         

metaSens = true;
}
     for(int supplyInv = 9; supplyInv < 27; supplyInv++)
{
ItemStack supplyMatrixStack = craftSupplyMatrix.getStackInSlot(supplyInv);
if(supplyMatrixStack != null)
{
if(supplyMatrixStack.getItem().equals(craftComponentStack.getItem()))
{
if(metaSens)
{
if(craftComponentStack.getItemDamage() != supplyMatrixStack.getItemDamage())
{
continue;
} else
{
craftSupplyMatrix.decrStackSize(supplyInv, 1);
found = true;
}
}
else
{
craftSupplyMatrix.decrStackSize(supplyInv, 1);
found = true;
}
if (supplyMatrixStack.getItem().hasContainerItem(supplyMatrixStack))
{
ItemStack contStack = supplyMatrixStack.getItem().getContainerItem(supplyMatrixStack);

if (contStack.isItemStackDamageable() && contStack.getItemDamage() > contStack.getMaxDamage())
{
MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, contStack));
contStack = null;
}

if (contStack != null && (!supplyMatrixStack.getItem().doesContainerItemLeaveCraftingGrid(supplyMatrixStack) || !this.thePlayer.inventory.addItemStackToInventory(contStack)))
{
if (this.craftMatrix.getStackInSlot(supplyInv) == null)
{
this.craftMatrix.setInventorySlotContents(supplyInv, contStack);
}
else
{
contStack.getItem().onDroppedByPlayer(contStack, player);
}
}
}
break;
}
}
}
             if(!found)
         {
         craftSupplyMatrix.decrStackSize(invIndex, 1);
         if (craftComponentStack.getItem().hasContainerItem(craftComponentStack))
                    {
                        ItemStack conStack = craftComponentStack.getItem().getContainerItem(craftComponentStack);
                        
                        if (conStack.isItemStackDamageable() && conStack.getItemDamage() > conStack.getMaxDamage())
                        {
                            MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, conStack));
                            conStack = null;
                        }

                        if (conStack != null && (!craftComponentStack.getItem().doesContainerItemLeaveCraftingGrid(craftComponentStack) || !this.thePlayer.inventory.addItemStackToInventory(conStack)))
                        {
                            if (this.craftMatrix.getStackInSlot(invIndex) == null)
                            {
                                this.craftMatrix.setInventorySlotContents(invIndex, conStack);
                            }
                            else
                            {
                            	conStack.getItem().onDroppedByPlayer(conStack, player);
                            }
                        }
                    }
         }
         }
         }
        
    }

@Override
public ItemStack decrStackSize(int amount) {
return this.inventory.getStackInSlot(this.slotNumber);
}

public void onSlotChange(ItemStack par1ItemStack, ItemStack par2ItemStack)
{
	super.onSlotChange(par1ItemStack, par2ItemStack);
	tile.OnInvChanged();
}
}
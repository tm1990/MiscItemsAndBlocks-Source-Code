package com.miscitems.MiscItemsAndBlocks.Block;

import MiscUtils.Block.ModBlockContainer;
import com.miscitems.MiscItemsAndBlocks.Gui.GuiHandler;
import com.miscitems.MiscItemsAndBlocks.Main.Main;
import com.miscitems.MiscItemsAndBlocks.TileEntity.TileEntityComputer;
import com.miscitems.MiscItemsAndBlocks.Utils.References.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ModBlockComputer extends ModBlockContainer {

    public ModBlockComputer() {
		super(Material.iron);
		this.setHardness(1);
		this.setBlockBounds(0, 0, 0, 1, 0.84F, 1);

	}
	
   


	@Override
	public TileEntity createNewTileEntity(World world, int i) {

		return new TileEntityComputer();
	}
	
	   public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9)
	    {
			player.openGui(Main.instance, GuiHandler.ComputerID, world, par2, par3, par4);
			
			return true;
	    }

	   
	    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	    {

	    	return par1World.isSideSolid(par2, par3 - 1, par4, ForgeDirection.UP);
	    }
	   
	    public void registerBlockIcons(IIconRegister icon) {
	        this.blockIcon = icon.registerIcon(Reference.Mod_Id + ":Computer");
	}
	   
		public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
		{
		   return false;
		}

		public boolean isOpaqueCube()
		{
		   return false;
		}

		
	    @Override
	    public int getRenderType() {
	            return -1;
	    }
	    
	    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	    {
	        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        if (l == 0)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
	        }

	        if (l == 1)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
	        }

	        if (l == 2)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
	        }

	        if (l == 3)
	        {
	            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
	        }

	    }


}

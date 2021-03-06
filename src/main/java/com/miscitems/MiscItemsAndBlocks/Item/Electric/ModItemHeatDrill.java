package com.miscitems.MiscItemsAndBlocks.Item.Electric;

import MiscUtils.Handlers.ParticleHelper;
import com.google.common.collect.Sets;
import com.miscitems.MiscItemsAndBlocks.Main.Main;
import com.miscitems.MiscItemsAndBlocks.Utils.References.Reference;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

public class ModItemHeatDrill extends ModItemPowerTool{

	
    private static final Set field_150916_c = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
    
    private static final Set field_150915_c = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	
    static Set Mineable = Sets.newHashSet(field_150915_c, field_150916_c);
    
	
	
	public ModItemHeatDrill() {
		super(0.1F, ToolMaterial.EMERALD, Mineable);
		this.efficiencyOnProperMaterial = 8;
        ReflectionHelper.setPrivateValue(ItemTool.class, this, "pickaxe", "toolClass");
	}
	
	 public boolean canHarvestBlock(Block par1Block)
	    {
	        return par1Block == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (par1Block != Blocks.diamond_block && par1Block != Blocks.diamond_ore ? (par1Block != Blocks.emerald_ore && par1Block != Blocks.emerald_block ? (par1Block != Blocks.gold_block && par1Block != Blocks.gold_ore ? (par1Block != Blocks.iron_ore ? (par1Block != Blocks.lapis_block && par1Block != Blocks.lapis_ore ? (par1Block != Blocks.redstone_ore ? (par1Block.getMaterial() == Material.rock ? true : (par1Block.getMaterial() == Material.iron ? true : par1Block.getMaterial() == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
	    }

		
	    @Override
	    public float getDigSpeed(ItemStack par1ItemStack, Block par2Blocks, int metadata)
	    {
	    	if(CurrentPower(par1ItemStack) <= 0)
	    		return 0;
	    	
	        return par2Blocks != null && (par2Blocks.getMaterial() == Material.iron || par2Blocks.getMaterial() == Material.anvil || par2Blocks.getMaterial() == Material.rock || par2Blocks.getMaterial() == Material.wood || par2Blocks.getMaterial() == Material.plants || par2Blocks.getMaterial() == Material.vine) ? this.efficiencyOnProperMaterial : super.getDigSpeed(par1ItemStack, par2Blocks, metadata);
	    }
	    
	    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
	    {
	        if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
	        {
                if(!((EntityPlayer)p_150894_7_).capabilities.isCreativeMode)
                RemovePower(p_150894_1_, 1);
	        }

	        return true;
	    }
	    
    
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        ParticleHelper particleHelper = new ParticleHelper(player.worldObj, Main.config.SpawnParticles);



    	int Luck = EnchantmentHelper.getEnchantmentLevel(35, player.inventory.getCurrentItem());
		Block block = player.worldObj.getBlock(x, y, z);
		
		if(block != null) {


                List<ItemStack> stacks = block.getDrops(player.worldObj, x, y, z, player.worldObj.getBlockMetadata(x, y, z), Luck);

                if (stacks != null) {
                    for (ItemStack s : stacks) {
                        if (s != null) {

                            Smelt(s, x, y, z, player.worldObj, player);

                            for(int i = 0; i < 4; i++){
                                particleHelper.SpawnParticleAroundBlock("flame", x, y, z, 0);
                            }
                        }
                    }
                }
            }




            if (!player.worldObj.isRemote)
                player.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(player.worldObj.getBlock(x, y, z)) + (player.worldObj.getBlockMetadata(x, y, z) << 12));



            player.worldObj.setBlock(x, y, z, Blocks.air);
		this.onBlockDestroyed(itemstack,  player.worldObj, block, x, y, z, player);

		return true;
    }
    
    
	   @SideOnly(Side.CLIENT)
	   public void registerIcons(IIconRegister par1IconRegister)
	   {
		   this.itemIcon = par1IconRegister.registerIcon(Reference.Mod_Id + ":HeatDrill");
		   
	   }

	   
	public void Smelt(ItemStack stack, int x, int y, int z, World world, EntityPlayer player){

        if(!world.isRemote){
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(stack);
			if(result != null){
				ItemStack smeltingStack;
				if(result.stackSize > 1){
					   smeltingStack = new ItemStack(result.getItem(), result.stackSize, result.getItemDamage());
				}else{
			   smeltingStack = new ItemStack(result.getItem(), 1, result.getItemDamage());
				}
				
				EntityItem item = new EntityItem(world, x, y, z, smeltingStack);
				world.spawnEntityInWorld(item);
			}else {

                EntityItem item = new EntityItem(world, x, y, z, stack);
                world.spawnEntityInWorld(item);


            }
			}
	   }
	   
	    @SuppressWarnings({ "unused", "null" })
		public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	    {
	    	int Luck = EnchantmentHelper.getEnchantmentLevel(35, player.inventory.getCurrentItem());

            ParticleHelper particleHelper = new ParticleHelper(world, Main.config.SpawnParticles);
	    	
  	        final Block block = world.getBlock(x, y, z);
  	        final int meta = world.getBlockMetadata(x, y, z);
  	      float blockHardness = block.getBlockHardness(world, x, y, z);
	    	
	    	if(block != null){
		        List<ItemStack> stacks = block.getDrops(player.worldObj, x, y, z, player.worldObj.getBlockMetadata(x, y, z), Luck);

		        ItemStack stack1 = new ItemStack(block);
    			ItemStack result1 = FurnaceRecipes.smelting().getSmeltingResult(stack1);


                    if(result1 != null){
                        if(result1.getItem() instanceof ItemBlock){

                            ItemBlock t = (ItemBlock)result1.getItem();

                            if (!world.isRemote)
                                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z)) + (world.getBlockMetadata(x, y, z) << 12));

                            for(int i = 0; i < 8; i++){
                                particleHelper.SpawnParticleAroundBlock("flame", x, y, z, 0);
                            }

                            world.setBlock(x, y, z, Block.getBlockById(Item.getIdFromItem(t)));
                            if(!player.capabilities.isCreativeMode)
                            RemovePower(player.inventory.getCurrentItem(), 1);

                        }
                    }else  if (stacks != null) {
		                for (ItemStack s : stacks) {
		                        if (s != null) {

	                    			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(s);
	                    			if(result != null){
		                        	if(result.getItem() instanceof ItemBlock){
		                        		ItemBlock t = (ItemBlock)result.getItem();

                                        if (!world.isRemote)
                                            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z)) + (world.getBlockMetadata(x, y, z) << 12));

                                        for(int i = 0; i < 8; i++){
                                            particleHelper.SpawnParticleAroundBlock("flame", x, y, z, 0);
                                        }

		                    				world.setBlock(x, y, z, Block.getBlockById(Item.getIdFromItem(t)));
                                        if(!player.capabilities.isCreativeMode)
		                    				RemovePower(player.inventory.getCurrentItem(), 1);

		                    			}


	                    			}

		    		}

		                        
            
		                }

	    	}



                return true;
	    	}

	        return true;
	    }


		@Override
		public double MaxPower(ItemStack stack) {
			return 930;
		}


		@Override
		public double ChargeAmount(ItemStack stack) {
			return 1;
		}
	    
	    
		@Override
		public boolean CanBackpackRecharge(ItemStack stack) {
			return true;
		}


}

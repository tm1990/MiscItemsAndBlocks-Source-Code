package com.miscitems.MiscItemsAndBlocks.Items;

import com.miscitems.MiscItemsAndBlocks.Proxies.ClientProxy;
import com.miscitems.MiscItemsAndBlocks.Utils.InvisibilityUtils;
import com.miscitems.MiscItemsAndBlocks.Utils.Refrence;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.omg.PortableInterceptor.ACTIVE;

import java.util.List;

public class ModItemInvisibilityCore extends ModItemWithDamage {


    protected ModItemInvisibilityCore() {
        super(100);
    }

    public boolean hasEffect(ItemStack par1ItemStack) {
        return true;
    }


    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {

        if(world.isRemote) {
            if(ClientProxy.HasValidInvisibilityArmor) {
                if (InvisibilityUtils.GetList().contains(player))
                    InvisibilityUtils.RemoveInvisiblePlayer(player, true);
                else if (!InvisibilityUtils.GetList().contains(player))
                    InvisibilityUtils.AddInvisiblePlayer(player, true);

            }else{
                InvisibilityUtils.RemoveInvisiblePlayer(player, true);
            }
        }

        return stack;
    }

    IIcon Active, InActive;

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {

        Active = par1IconRegister.registerIcon(Refrence.Mod_Id + ":InvisibilityCoreActive");
        InActive = par1IconRegister.registerIcon(Refrence.Mod_Id + ":InvisibilityCoreInActive");

        itemIcon = InActive;
    }

    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if(InvisibilityUtils.GetList().contains(player)) {
            itemIcon = Active;
            return Active;
        }

        itemIcon = InActive;
        return InActive;
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {

        int DamageLeft = itemstack.getMaxDamage() - itemstack.getItemDamage();

        list.add(StatCollector.translateToLocal("items.desc.invisCharged")+ " " + DamageLeft + "%");
    }

}

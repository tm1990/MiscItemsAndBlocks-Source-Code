package com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatPackets;

import MiscUtils.Network.AbstractPacket;
import MiscUtils.Network.PacketHandler;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChannelUtils;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatChannel;
import com.miscitems.MiscItemsAndBlocks.Main.Main;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class CloseChannel extends AbstractPacket {

    String name;

    public CloseChannel(){}
    public CloseChannel(String name){
        this.name = name;
    }

    @Override
    public void toBytes(ByteBuf buffer, Side side) {
        ByteBufUtils.writeUTF8String(buffer, name);

    }

    @Override
    public void fromBytes(ByteBuf buffer, Side side) {
        name = ByteBufUtils.readUTF8String(buffer);

    }

    @Override
    public void onMessage(Side side, EntityPlayer player) {

        ChatChannel channel = ChannelUtils.GetChannel(name);

        if(channel.Close){
            ChannelUtils.Channels.remove(channel);
            ChannelUtils.ChannelIds.remove(channel.ChannelId);
        }

        if(side == Side.SERVER){
            PacketHandler.sendToAll(new CloseChannel(name), Main.Utils.channels);
        }

    }
}

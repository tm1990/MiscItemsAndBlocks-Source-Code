package com.miscitems.MiscItemsAndBlocks.Network;

import net.minecraft.entity.player.EntityPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestEvent extends IPacket {

    public byte eventType;
    public int originX, originY, originZ;
    public byte sideHit;
    public byte rangeX, rangeY, rangeZ;
    public String data;

    public PacketRequestEvent() {}

    public PacketRequestEvent(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {

        this.eventType = eventType;
        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;
        this.sideHit = sideHit;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        this.data = data;
    }

    @Override
    public void write(DataOutputStream data) throws IOException {

        data.writeByte(eventType);
        data.writeInt(originX);
        data.writeInt(originY);
        data.writeInt(originZ);
        data.writeByte(sideHit);
        data.writeByte(rangeX);
        data.writeByte(rangeY);
        data.writeByte(rangeZ);
        data.writeUTF(this.data);
    }

    @Override
    public void read(DataInputStream data) throws IOException {

        eventType = data.readByte();
        originX = data.readInt();
        originY = data.readInt();
        originZ = data.readInt();
        sideHit = data.readByte();
        rangeX = data.readByte();
        rangeY = data.readByte();
        rangeZ = data.readByte();
        this.data = data.readUTF();
    }

    @Override
    public void execute(EntityPlayer player) {

    }


}
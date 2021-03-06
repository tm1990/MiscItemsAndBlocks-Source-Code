package com.miscitems.MiscItemsAndBlocks.Main;

import MiscUtils.GuideBase.Registry.GuideModRegistry;
import MiscUtils.Network.ChannelUtils;
import MiscUtils.Utils.LocalizationUpdater;
import MiscUtils.GuideBase.Recipe.RecipeUtils;
import com.google.common.collect.Sets;
import com.miscitems.MiscItemsAndBlocks.Entity.EntityPowerArrow;
import com.miscitems.MiscItemsAndBlocks.Entity.EntitySilverArrow;
import com.miscitems.MiscItemsAndBlocks.Event.BoneMealEvent;
import com.miscitems.MiscItemsAndBlocks.Event.DisarmStickEvent;
import com.miscitems.MiscItemsAndBlocks.Event.GhostBlockBreakEvent;
import com.miscitems.MiscItemsAndBlocks.Event.GuiListener;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.ProgramList;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatPackets.AddChannel;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatPackets.CloseChannel;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatPackets.ConnectToChannel;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatPackets.CreateChannel;
import com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChatPackets.DisconnectFromChannel;
import com.miscitems.MiscItemsAndBlocks.Gui.GuiHandler;
import com.miscitems.MiscItemsAndBlocks.Main.Guide.MillGuideRecipeType;
import com.miscitems.MiscItemsAndBlocks.Main.Guide.MiscItemsGuideIntegration;
import com.miscitems.MiscItemsAndBlocks.Main.Guide.SquezerGuideRecipeType;
import com.miscitems.MiscItemsAndBlocks.Network.Client.ClientGamePacketBegin;
import com.miscitems.MiscItemsAndBlocks.Network.Client.ClientGamePacketChange;
import com.miscitems.MiscItemsAndBlocks.Network.Client.ClientGamePacketInviteRecived;
import com.miscitems.MiscItemsAndBlocks.Network.Client.ClientGamePacketRestart;
import com.miscitems.MiscItemsAndBlocks.Network.Client.ClientGhostBlockPacket;
import com.miscitems.MiscItemsAndBlocks.Network.Client.ClientMetalPressPacketUpdate;
import com.miscitems.MiscItemsAndBlocks.Network.ComputerPrograms.Chat.PacketAddPlayerNew;
import com.miscitems.MiscItemsAndBlocks.Network.ComputerPrograms.Chat.PacketAddPlayerOld;
import com.miscitems.MiscItemsAndBlocks.Network.ComputerPrograms.Chat.PacketAddPlayerServerToClient;
import com.miscitems.MiscItemsAndBlocks.Network.ComputerPrograms.Chat.PacketChatMessagetoServer;
import com.miscitems.MiscItemsAndBlocks.Network.PacketTileUpdate;
import com.miscitems.MiscItemsAndBlocks.Network.PacketTileWithItemUpdate;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerButtonPacket;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerGamePacketAccept;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerGamePacketChange;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerGamePacketClosed;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerGamePacketInvite;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerLensBenchPacketDone;
import com.miscitems.MiscItemsAndBlocks.Network.Server.ServerPaintBrushChangePacket;
import com.miscitems.MiscItemsAndBlocks.Utils.Config;
import com.miscitems.MiscItemsAndBlocks.Utils.Crafting;
import com.miscitems.MiscItemsAndBlocks.Utils.Laser.Events.LaserDefaultActionEvent;
import com.miscitems.MiscItemsAndBlocks.Utils.PillarUtils;
import com.miscitems.MiscItemsAndBlocks.Utils.Proxies.ServerProxy;
import com.miscitems.MiscItemsAndBlocks.Utils.References.Messages;
import com.miscitems.MiscItemsAndBlocks.Utils.References.Reference;
import com.miscitems.MiscItemsAndBlocks.WorldGen.ModWorldGenerator;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;

import java.util.Set;


	@Mod(modid = Reference.Mod_Id, name = Reference.Mod_Name, version = Reference.Version, dependencies = "after:NEI;required-after:MiscUtils")
	public class Main 
	{

	
		@Instance(Reference.Mod_Id)
		public static Main instance = new Main();
    
		@SidedProxy(clientSide = "com.miscitems.MiscItemsAndBlocks.Utils.Proxies.ClientProxy", serverSide = "com.miscitems.MiscItemsAndBlocks.Utils.Proxies.ServerProxy")
		public static ServerProxy proxy;


        public static Set EmptyToolSet = Sets.newHashSet();

        public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("MiscItems");

        public static Config config;

        public static LocalizationUpdater localizationUpdater;

        public static CreativeTabs MiscTab = new CreativeTabs("tabMiscMisc")
        {


            @Override
            @SideOnly(Side.CLIENT)
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(config.GetCheckedBlock(ModBlocks.ItemPedestal));
            }

        };

        public static CreativeTabs DecorativeTab = (new CreativeTabs("tabMiscDeco")
        {


            @Override
            @SideOnly(Side.CLIENT)
            public Item getTabIconItem()
            {

                return ItemBlock.getItemFromBlock(config.GetCheckedBlock(ModBlocks.GamePart));

            }


        });


        public static CreativeTabs ElectricTab = new CreativeTabs("tabMiscElectric")
        {


            @Override
            @SideOnly(Side.CLIENT)
            public Item getTabIconItem()
            {
                return ItemBlock.getItemFromBlock(config.GetCheckedBlock(ModBlocks.Charger));
            }

        };




        public static ChannelUtils Utils;

	
        @EventHandler
        public void preInit(FMLPreInitializationEvent event)
        {

            config = new Config(event.getModConfigurationDirectory() + "");




            Utils = new ChannelUtils(Reference.Channel, Reference.Mod_Id);
            RegisterPackets();

            localizationUpdater = new LocalizationUpdater("tm1990", "MiscItemsAndBlocks-Source-Code", "master", "src/main/resources/assets/miscitems/lang/");
            localizationUpdater.initializeThread(config.GetConfigFile());

            proxy.PreInt();


            //Guide Integration
            GuideModRegistry.RegisterModToGuide(new MiscItemsGuideIntegration());
            RecipeUtils.RecipeTypeRenders.add(new MillGuideRecipeType());
            RecipeUtils.RecipeTypeRenders.add(new SquezerGuideRecipeType());




            if(config.AllowCustomPillars) {
                PillarUtils.RegisterBlackList();
                PillarUtils.RegisterBlocks();
            }

        	ModBlocks.Init();
        	ModItems.Init();

        	Messages.Init();

        	Crafting.RegisterRecipes();

            if(event.getSide() == Side.CLIENT)
            ProgramList.RegisterPrograms();

            com.miscitems.MiscItemsAndBlocks.Gui.Computer.Programs.Utils.ChannelUtils.AddChannel("Default", "Default", false, config.GetConfigFile().getBoolean("Op gives admin in all chat channels", Config.CATEGORY_SERVER_SETTINGS, true, "Should players with op get admin in all chat channels?"));



        	proxy.RegisterListeners();

        	proxy.registerRenderThings();

        	proxy.RegisterClientTickhandler();
        	proxy.RegisterServerTickhandler();


            EntityRegistry.registerGlobalEntityID(EntitySilverArrow.class, "SilverArrow", EntityRegistry.findGlobalUniqueEntityId());
            EntityRegistry.registerModEntity(EntitySilverArrow.class, "SilverArrow", 0, this, 128, 1, true);

            EntityRegistry.registerGlobalEntityID(EntityPowerArrow.class, "PowerArrow", EntityRegistry.findGlobalUniqueEntityId());
            EntityRegistry.registerModEntity(EntityPowerArrow.class, "PowerArrow", 1, this, 128, 1, true);


            MinecraftForge.EVENT_BUS.register(new LaserDefaultActionEvent());




        	//Register Events
        	if(event.getSide() == Side.SERVER)
        		RegisterServerEvents();

        	if(event.getSide() == Side.CLIENT)
        		RegisterClientEvents();


        }

        public void RegisterClientEvents()
        {



        	MinecraftForge.EVENT_BUS.register(new GuiListener());


            FMLCommonHandler.instance().bus().register(ServerProxy.tickHandlerClient);
        }


        public void RegisterServerEvents()
        {
	


	
        	MinecraftForge.EVENT_BUS.register(new BoneMealEvent());
        	MinecraftForge.EVENT_BUS.register(new DisarmStickEvent());
        	MinecraftForge.EVENT_BUS.register(new GhostBlockBreakEvent());

            FMLCommonHandler.instance().bus().register(ServerProxy.tickHandlerServer);

        }
            
        @EventHandler
        public void Init(FMLInitializationEvent event){

        	proxy.registerRenderers();



        
        	GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 3);
        
        	MinecraftForge.addGrassSeed(new ItemStack(ModItems.TomatoSeeds), 10);
        
        

        	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        

        }


        public static void RegisterPackets(){



            Utils.handler.RegisterPacket(PacketChatMessagetoServer.class);
            Utils.handler.RegisterPacket(PacketAddPlayerOld.class);
            Utils.handler.RegisterPacket(PacketAddPlayerNew.class);
            Utils.handler.RegisterPacket(PacketAddPlayerServerToClient.class);

            Utils.handler.RegisterPacket(ClientGamePacketBegin.class);
            Utils.handler.RegisterPacket(ClientGamePacketChange.class);
            Utils.handler.RegisterPacket(ClientGamePacketInviteRecived.class);
            Utils.handler.RegisterPacket(ClientGamePacketRestart.class);
            Utils.handler.RegisterPacket(ClientGhostBlockPacket.class);
            Utils.handler.RegisterPacket(ClientMetalPressPacketUpdate.class);

            Utils.handler.RegisterPacket(ServerButtonPacket.class);
            Utils.handler.RegisterPacket(ServerGamePacketAccept.class);
            Utils.handler.RegisterPacket(ServerGamePacketChange.class);
            Utils.handler.RegisterPacket(ServerGamePacketClosed.class);
            Utils.handler.RegisterPacket(ServerGamePacketInvite.class);
            Utils.handler.RegisterPacket(ServerLensBenchPacketDone.class);
            Utils.handler.RegisterPacket(ServerPaintBrushChangePacket.class);

            Utils.handler.RegisterPacket(PacketTileUpdate.class);
            Utils.handler.RegisterPacket(PacketTileWithItemUpdate.class);

            //Computer packets

            //ChatPackets
            Utils.handler.RegisterPacket(AddChannel.class);
            Utils.handler.RegisterPacket(CloseChannel.class);
            Utils.handler.RegisterPacket(CreateChannel.class);
            Utils.handler.RegisterPacket(ConnectToChannel.class);
            Utils.handler.RegisterPacket(DisconnectFromChannel.class);


        }



        @EventHandler
        public void PostInit(FMLPostInitializationEvent event)
        {

        }




	}

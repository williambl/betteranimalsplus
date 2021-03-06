package its_meow.betteranimalsplus.init;

import java.util.HashMap;
import java.util.Map;

import its_meow.betteranimalsplus.BetterAnimalsPlusMod;
import its_meow.betteranimalsplus.Ref;
import its_meow.betteranimalsplus.common.entity.projectile.EntityPheasantEgg;
import its_meow.betteranimalsplus.common.entity.projectile.EntityTurkeyEgg;
import its_meow.betteranimalsplus.common.item.ItemAdvancementIcon;
import its_meow.betteranimalsplus.common.item.ItemBearCape;
import its_meow.betteranimalsplus.common.item.ItemBetterFood;
import its_meow.betteranimalsplus.common.item.ItemHirschgeistSkullWearable;
import its_meow.betteranimalsplus.common.item.ItemNamedSimple;
import its_meow.betteranimalsplus.common.item.ItemThrowableCustomEgg;
import its_meow.betteranimalsplus.common.item.ItemWolfCape;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;

public class ModItems {

    public static final ItemBetterFood VENISON_RAW = new ItemBetterFood("venisonraw", 4, 0, 32, true);
    public static final ItemBetterFood VENISON_COOKED = new ItemBetterFood("venisoncooked", 8, 1.2F, 32, true);
    public static final ItemHirschgeistSkullWearable HIRSCHGEIST_SKULL_WEARABLE = new ItemHirschgeistSkullWearable();
    public static final Item ANTLER = new ItemNamedSimple("antler");
    public static final Item GOAT_MILK = new MilkBucketItem(
            new Properties().containerItem(Items.BUCKET).group(BetterAnimalsPlusMod.group).maxStackSize(1))
                    .setRegistryName("goatmilk");
    public static final ItemBetterFood GOAT_CHEESE = new ItemBetterFood("goatcheese", 3, 1, 15, false);
    public static final ItemBetterFood PHEASANT_RAW = new ItemBetterFood("pheasantraw", 3, 0, 32, true);
    public static final ItemBetterFood PHEASANT_COOKED = new ItemBetterFood("pheasantcooked", 7, 1.2F, 32, true);
    
    public static final ItemBetterFood CRAB_MEAT_RAW = new ItemBetterFood("crab_meat_raw", 2, 1, 16, true);
    public static final ItemBetterFood CRAB_MEAT_COOKED = new ItemBetterFood("crab_meat_cooked", 5, 1.2F, 16, true);

    public static final BlockItem ITEMBLOCK_TRILLIUM = new BlockItem(ModBlocks.TRILLIUM,
            new Properties().group(BetterAnimalsPlusMod.group));
    public static final BlockItem ITEMBLOCK_HAND_OF_FATE = new BlockItem(ModBlocks.HAND_OF_FATE,
            new Properties().group(BetterAnimalsPlusMod.group));
    
    public static final Item WOLF_PELT_SNOWY = new ItemNamedSimple("wolf_pelt_snowy");
    public static final Item WOLF_PELT_TIMBER = new ItemNamedSimple("wolf_pelt_timber");
    public static final Item WOLF_PELT_BLACK = new ItemNamedSimple("wolf_pelt_black");

    public static ItemWolfCape WOLF_CAPE_CLASSIC = new ItemWolfCape("classic", WOLF_PELT_SNOWY);
    public static ItemWolfCape WOLF_CAPE_TIMBER = new ItemWolfCape("timber", WOLF_PELT_TIMBER);
    public static ItemWolfCape WOLF_CAPE_BLACK = new ItemWolfCape("black", WOLF_PELT_BLACK);
    
    public static final Item BEAR_SKIN_BROWN = new ItemNamedSimple("bear_skin_brown");
    public static final Item BEAR_SKIN_BLACK = new ItemNamedSimple("bear_skin_black");
    public static final Item BEAR_SKIN_KERMODE = new ItemNamedSimple("bear_skin_kermode");
    
    public static ItemBearCape BEAR_CAPE_BROWN = new ItemBearCape("brown", BEAR_SKIN_BROWN);
    public static ItemBearCape BEAR_CAPE_BLACK = new ItemBearCape("black", BEAR_SKIN_BLACK);
    public static ItemBearCape BEAR_CAPE_KERMODE = new ItemBearCape("kermode", BEAR_SKIN_KERMODE);

    public static final MusicDiscItem RECORD_CRAB_RAVE = new MusicDiscItem(15, ModSoundEvents.CRAB_RAVE, new Item.Properties().maxStackSize(1).rarity(Rarity.RARE)) {}; static {
        RECORD_CRAB_RAVE.setRegistryName(new ResourceLocation(Ref.MOD_ID, "record_crab_rave"));
    }
    
    public static final ItemThrowableCustomEgg PHEASANT_EGG = new ItemThrowableCustomEgg("pheasant_egg", player -> new EntityPheasantEgg(player.world, player));
    public static final ItemThrowableCustomEgg TURKEY_EGG = new ItemThrowableCustomEgg("turkey_egg", player -> new EntityTurkeyEgg(player.world, player));
    
    public static Map<String, ItemAdvancementIcon> ADVANCEMENT_ICONS = new HashMap<String, ItemAdvancementIcon>();
}

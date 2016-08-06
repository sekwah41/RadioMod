package com.sekwah.radiomod.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by on 06/08/2016.
 *
 * @author sekwah41
 */
public class RadioItems {

    public static ItemMobile MOBILE;

    public static void registerItems(){

        MOBILE = new ItemMobile();
        registerItem(MOBILE, "mobile_player", "ItemMobilePlayer");

    }

    private static void registerItem(Item item, String unlocalisedName, String registryName) {
        GameRegistry.register(item.setUnlocalizedName(unlocalisedName).setRegistryName(registryName));
    }

}

/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.morestats.utils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Utils {

    public static List<String> dynamicTab(List<String> list, String arg) {
        List<String> finalList = new ArrayList<>(list);
        for (String s : list) {
            if (!s.toLowerCase().startsWith(arg.toLowerCase())) {
                finalList.remove(s);
            }
        }
        Collections.sort(finalList);
        return finalList;
    }

    public static boolean isFood(Material type) {
        List<Material> food = new ArrayList<>();
        food.add(Material.COOKED_BEEF);
        food.add(Material.COOKED_CHICKEN);
        food.add(Material.COOKED_COD);
        food.add(Material.COOKED_MUTTON);
        food.add(Material.COOKED_PORKCHOP);
        food.add(Material.COOKED_RABBIT);
        food.add(Material.COOKED_SALMON);
        food.add(Material.BEEF);
        food.add(Material.CHICKEN);
        food.add(Material.COD);
        food.add(Material.MUTTON);
        food.add(Material.PORKCHOP);
        food.add(Material.RABBIT);
        food.add(Material.SALMON);

        return food.contains(type);


    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException ignored) {
            return false;
        }
        return true;
    }

    public static int getEmptySlots(Player player) {
        int i = 0;
        for (ItemStack is : player.getInventory().getContents()) {
            if (!(is == null))
                continue;
            i++;
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (!(item == null))
                continue;
            i--;
        }
        return i;
    }

    public static void safeGive(Player player, ItemStack item) {
        if (getEmptySlots(player) < 2)
            player.getWorld().dropItem(player.getLocation(), item);
        else
            player.getInventory().addItem(item);
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

}

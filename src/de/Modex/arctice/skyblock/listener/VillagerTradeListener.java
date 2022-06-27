package de.Modex.arctice.skyblock.listener;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VillagerTradeListener implements Listener {

    @EventHandler
    public void on(InventoryOpenEvent e) {
        if (!e.getInventory().getType().equals(InventoryType.MERCHANT) || !(e.getInventory().getHolder() instanceof Villager villager))
            return;

        List<MerchantRecipe> trades = new ArrayList<>(villager.getRecipes());

        for (int i = 0; i < trades.size(); i++) {
            if (trades.get(i).getResult().getType().equals(Material.ENCHANTED_BOOK)) {
                MerchantRecipe recipe = trades.get(i);
                MerchantRecipe newRecipe = new MerchantRecipe(new ItemStack(Material.BOOK), recipe.getUses(), recipe.getMaxUses(), true, recipe.getVillagerExperience(), recipe.getPriceMultiplier(), recipe.getDemand(), recipe.getSpecialPrice());
                newRecipe.setIngredients(recipe.getIngredients());

                trades.set(i, newRecipe);
            }
        }

        trades = trades.stream().filter(recipe -> !recipe.getResult().getType().equals(Material.ENCHANTED_BOOK)).collect(Collectors.toList());
        List<MerchantRecipe> temp = trades.stream().filter(recipe -> !recipe.getResult().getType().name().contains("NETHERITE_") && !recipe.getResult().getType().name().contains("DIAMOND_")).collect(Collectors.toList());

        trades = trades.stream().filter(recipe -> !recipe.getResult().getType().name().contains("BLOCK") && !recipe.getResult().getType().name().contains("INGOT") && (recipe.getResult().getType().name().contains("NETHERITE_") || recipe.getResult().getType().name().contains("DIAMOND_"))).collect(Collectors.toList());
        List<MerchantRecipe> toRemove = new ArrayList<>();
        for (MerchantRecipe recipe : trades) {
            ItemStack old = recipe.getResult();

            ItemStack result = new ItemStack(Material.valueOf("IRON_" + old.getType().name().split("_")[1]));
            result.setItemMeta(old.getItemMeta());
            result.setAmount(old.getAmount());
            toRemove.add(recipe);

            MerchantRecipe newRecipe = new MerchantRecipe(result, recipe.getUses(), recipe.getMaxUses(), true, recipe.getVillagerExperience(), recipe.getPriceMultiplier(), recipe.getDemand(), recipe.getSpecialPrice());
            newRecipe.setIngredients(recipe.getIngredients());
            temp.add(newRecipe);
        }
        trades.removeAll(toRemove);
        trades.addAll(temp);

        villager.setRecipes(trades);
    }
}

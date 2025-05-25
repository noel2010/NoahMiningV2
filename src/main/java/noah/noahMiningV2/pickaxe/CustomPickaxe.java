package noah.noahMiningV2.pickaxe;

import lombok.Getter;
import noah.noahMiningV2.NoahMiningV2;
import noah.noahMiningV2.data.PlayerData;
import noah.noahMiningV2.pickaxe.enchants.ConfiguredEnchant;
import noah.noahMiningV2.pickaxe.enchants.EnchantManager;
import noah.noahMiningV2.pickaxe.enchants.infr.Enchant;
import noah.noahMiningV2.pickaxe.enchants.infr.ReturnEnchant;
import noah.noahMiningV2.pickaxe.enchants.infr.VoidEnchant;
import noah.noahMiningV2.pickaxe.runes.ConfiguredRune;
import noah.noahMiningV2.pickaxe.runes.RuneManager;
import noah.noahMiningV2.pickaxe.runes.RuneTypes;
import noah.noahMiningV2.pickaxe.runes.impl.BlankRune;
import noah.noahMiningV2.pickaxe.runes.infr.ReturnRune;
import noah.noahMiningV2.pickaxe.runes.infr.Rune;
import noah.noahMiningV2.pickaxe.runes.infr.VoidRune;
import noah.noahMiningV2.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import poa.poalib.economy.Economy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CustomPickaxe {
    @Getter
    private ItemStack item;

    private static final NamespacedKey enchantKey = new NamespacedKey(NoahMiningV2.INSTANCE, "enchant");
    private static final NamespacedKey runeKey = new NamespacedKey(NoahMiningV2.INSTANCE, "rune");
    private final Config conf = new Config();
    private final EnchantManager enchManager = new EnchantManager();
    private final RuneManager runeManager = new RuneManager();

    private CustomPickaxe(ItemStack item){
        if (hasProperData(item))
            this.item = item;
    }

    private CustomPickaxe(ItemStack item, boolean setData){
        if (hasProperData(item))
            this.item = item;

        if (setData)
            this.item = setProperData(item);
        else
            this.item = item;
    }

    public static CustomPickaxe of(ItemStack item){return new CustomPickaxe(item);}
    public static CustomPickaxe of(ItemStack item,boolean setData){return new CustomPickaxe(item,setData);}

    public static boolean isPickaxe(ItemStack item){
        return hasProperData(item) && !isNull(item);
    }

    public Map<Enchant, Integer> getEnchants(){
        if (item == null || item.getType().isAir()) return new HashMap<>();
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if (!data.has(enchantKey)) return new HashMap<>();
        String extracted = data.get(enchantKey, PersistentDataType.STRING);
        return parseEnchant(extracted);
    }

    public ConfiguredEnchant getSpecificEnchant(String id){
        if(item == null || item.getType().isAir()) return null;
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if(!data.has(enchantKey)) return null;
        for(ConfiguredEnchant enchant : getConfiguredEnchants()){
            if(enchant.getEnchant().getID() == id) return enchant;
            else continue;
        }
        return null;
    }

    public ConfiguredRune getRune(){
        if (item==null || item.getType().isAir()) return null;
        Map<String, Rune> runes = runeManager.getRunes();
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        if (!data.has(runeKey)) return null;
        String extracted = data.get(runeKey, PersistentDataType.STRING);
        if (extracted.equals("N/A")) return ConfiguredRune.of(new BlankRune());
        for (Map.Entry<String, Rune> entry : runes.entrySet()){
            if (entry.getKey().equals(extracted))
                return ConfiguredRune.of(entry.getValue());
        }
        return null;
    }

    public List<ConfiguredEnchant> getConfiguredEnchants(){
        if (item == null || item.getType().isAir()) return new ArrayList<>();
        List<ConfiguredEnchant> enchants = new ArrayList<>();
        for (Map.Entry<Enchant, Integer> entry : fixEnchantList().entrySet()){
            enchants.add(ConfiguredEnchant.of(entry.getKey(), entry.getValue()));
        }
        return enchants;
    }

    public void upgradeEnchant(String id){
        if (item == null ||item.getType().isAir()) return;
        List<ConfiguredEnchant> enchants = getConfiguredEnchants();
        for (ConfiguredEnchant ench : enchants){
            if (ench.getEnchant().getID() == id) {
                ench.upgrade();
                break;
            }
        }
        Map<Enchant, Integer> enchs = new HashMap<>();
        for (ConfiguredEnchant ench : enchants){
            enchs.put(ench.getEnchant(), ench.getLevel());
        }
        item = updatePickaxeLore(enchants);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(enchantKey, PersistentDataType.STRING, MapToString(enchs));
        item.setItemMeta(meta);
    }

    private Map<Enchant, Integer> fixEnchantList() {
        Map<String, Enchant> registeredEnchants = enchManager.getEnchants();
        Map<Enchant, Integer> appliedEnchants = getEnchants();
        for (Enchant registered : registeredEnchants.values()) {
            appliedEnchants.putIfAbsent(registered, 0);
        }
        return appliedEnchants;
    }

    private Map<Enchant, Integer> parseEnchant(String data){
        if (data==null || data.isEmpty()) return new HashMap<>();
        Map<String, Integer> enchants = new HashMap<>();
        Map<String, Enchant> registeredEnch = enchManager.getEnchants();
        String[] pairs = data.split(";");
        for (String pair : pairs){
            String[] parts = pair.split(":");
            if (parts.length==2) enchants.put(parts[0], Integer.parseInt(parts[1]));
        }
        Map<Enchant, Integer> finalEnch = new HashMap<>();
        for (Map.Entry<String, Integer> entry : enchants.entrySet()){
            if (registeredEnch.get(entry.getKey()) != null)
                finalEnch.put(registeredEnch.get(entry.getKey()), entry.getValue());
        }
        return finalEnch;
    }

    private String MapToString(Map<Enchant, Integer> enchants){
        return enchants.entrySet().stream()
                .map(entry->entry.getKey().getID()+":"+entry.getValue())
                .collect(Collectors.joining(";"));
    }

    public void triggerPickaxe(Player p, Location loc){
        double chanceBuff = 0.0;
        ConfiguredRune rune = getRune();
        if (rune != null && !rune.isBlank()) {
            if (rune.getRune() instanceof VoidRune vRune)
                vRune.activate(p, loc);
            else if (rune.getRune() instanceof ReturnRune rRune) {
                switch (rRune.getType()){
                    case LUCK: chanceBuff = rRune.activate(p);
                    case SOULS: rRune.activate(p);
                    case SPEED: /* HANDLE EFFECT STUFF*/ rRune.activate(p);
                    case GREED: Economy.depositPlayer(p, rRune.activate(p));
                }
            }
        }

        List<ConfiguredEnchant> enchants = getConfiguredEnchants();
        PlayerData playerData = new PlayerData(p.getUniqueId());
        int souls = 0;
        for (ConfiguredEnchant ench : enchants){
            if (!ench.hasUpgradedOnce()) continue;
            Enchant enchant = ench.getEnchant();
            if (enchant instanceof VoidEnchant vEnch){
                vEnch.trigger(loc,p,chanceBuff, ench);
            } else if (enchant instanceof ReturnEnchant rEnch) {
                souls += rEnch.trigger(loc,p,chanceBuff, ench);
            }
        }
        String breakMessage = conf.getBreakMessage();
        breakMessage = breakMessage.replace("{souls}", ""+souls);
        p.sendActionBar(conf.getColoredMessage(breakMessage));
        playerData.addSouls(souls);
    }

    public boolean hasEnchants(){
        return item.getItemMeta().getPersistentDataContainer().has(enchantKey);
    }

    public boolean hasProperData(){
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        return data.has(enchantKey) && data.has(runeKey);
    }

    private static boolean hasProperData(ItemStack item){
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        return data.has(enchantKey) && data.has(runeKey);
    }

    private ItemStack setProperData(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        Map<String, Enchant> enchants = enchManager.getEnchants();
        Map<Enchant, Integer> enchs = new HashMap<>();
        for (Map.Entry<String, Enchant> entry : enchants.entrySet())
            enchs.put(entry.getValue(), 0);
        data.set(enchantKey, PersistentDataType.STRING, MapToString(enchs));
        data.set(runeKey, PersistentDataType.STRING, "test02");
        item.setItemMeta(meta);
        return item;
    }

    public boolean isNull(){ return item == null; }
    private static boolean isNull(ItemStack item){ return item == null; }

    public ItemStack updatePickaxeLore(List<ConfiguredEnchant> enchants){
        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = conf.getPickaxeLore();
        for(ConfiguredEnchant enchant : enchants){
            String replaceMessage = enchant.getEnchant().getID()+"Lvl";
            lore.replaceAll(str->str.replace("{"+replaceMessage+"}", ""+enchant.getLevel()));
            NoahMiningV2.INSTANCE.getLogger().warning(replaceMessage+" | "+"{"+replaceMessage+"}");
        }
        meta.lore(conf.getColoredLore(lore));
        item.setItemMeta(meta);
        return item;
    }

}

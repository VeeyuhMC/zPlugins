package com.zp4rker.zconomy.commands;

import com.zp4rker.zconomy.PlayerData;
import com.zp4rker.zconomy.zConomy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class PayCommand implements CommandExecutor {

    zConomy plugin;

    public PayCommand(zConomy plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pay")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage("§4You must be a player to run that command!");
                return true;
            } else if (sender instanceof Player) {
                Player payer = (Player) sender;
                PlayerData payerData = plugin.getDatabase().find(PlayerData.class)
                        .where().ieq("playerUUID", payer.getUniqueId().toString()).findUnique();
                if (payer.hasPermission("zconomy.pay")) {
                    if (args.length == 2) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player receiver = Bukkit.getPlayer(args[0]);
                            PlayerData receiverData = plugin.getDatabase().find(PlayerData.class)
                                    .where().ieq("playerUUID", receiver.getUniqueId().toString()).findUnique();
                            long payerMoney = payerData.getMoney();
                            long receiverMoney = receiverData.getMoney();
                            long amount;
                            try {
                                amount = Long.parseLong(args[1]);
                            } catch (Exception e) {
                                payer.sendMessage("§4Invalid Arguments!");
                                return false;
                            }
                            if (payerMoney - amount < 0) {
                                payerMoney -= amount;
                                receiverMoney += amount;
                            } else {
                                payer.sendMessage("§4You don't have enough money!");
                                return true;
                            }
                            payerData.setMoney(payerMoney);
                            receiverData.setMoney(receiverMoney);
                            plugin.getDatabase().save(payerData);
                            plugin.getDatabase().save(receiverData);
                            payer.sendMessage("§6You payed §2$" + amount + " §6to §2" + receiver.getName());
                            receiver.sendMessage("§6You were payed §2$" + amount + " §6by §2" + payer.getName());
                            return true;
                        } else if (Bukkit.getOfflinePlayer(args[0]) != null) {
                            OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[0]);
                            PlayerData receiverData = plugin.getDatabase().find(PlayerData.class)
                                    .where().ieq("playerUUID", receiver.getUniqueId().toString()).findUnique();
                            long payerMoney = payerData.getMoney();
                            long receiverMoney = receiverData.getMoney();
                            long amount;
                            try {
                                amount = Long.parseLong(args[1]);
                            } catch (Exception e) {
                                payer.sendMessage("§4Invalid Arguments!");
                                return false;
                            }
                            if (payerMoney - amount < 0) {
                                payerMoney -= amount;
                                receiverMoney += amount;
                            } else {
                                payer.sendMessage("§4You don't have enough money!");
                                return true;
                            }
                            payerData.setMoney(payerMoney);
                            receiverData.setMoney(receiverMoney);
                            plugin.getDatabase().save(payerData);
                            plugin.getDatabase().save(receiverData);
                            payer.sendMessage("§6You payed §2$" + amount + " §6to §2" + receiver.getName());
                            return true;
                        } else {
                            payer.sendMessage("§4That player has never joined the server!");
                            return true;
                        }
                    } else {
                        payer.sendMessage("§4Invalid Arguments!");
                    }
                } else {
                    payer.sendMessage("§4You do not have permission to do that!");
                    return true;
                }
            }
        }
        return false;
    }

}

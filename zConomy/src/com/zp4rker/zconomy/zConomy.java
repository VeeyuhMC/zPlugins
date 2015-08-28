package com.zp4rker.zconomy;

import com.zp4rker.zconomy.commands.BalanceCommand;
import com.zp4rker.zconomy.commands.GiveMoneyCommand;
import com.zp4rker.zconomy.commands.PayCommand;
import com.zp4rker.zconomy.commands.SetMoneyCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class zConomy extends JavaPlugin {

    public void onEnable() {

        setupDatabase();

        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("setmoney").setExecutor(new SetMoneyCommand(this));
        getCommand("givemoney").setExecutor(new GiveMoneyCommand(this));

    }

    public void setupDatabase() {
        try {
            getDatabase().find(PlayerData.class).findRowCount();
        } catch (Exception e) {
            getLogger().info("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<>();
        list.add(PlayerData.class);
        return list;
    }

}

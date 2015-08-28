package com.zp4rker.zconomy;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity()
@Table(name = "playerData")
public class PlayerData {

    @Id
    private int id;

    @NotEmpty
    private String playerUUID;

    @NotNull
    private long money = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

}

package com.theretronix.hubp.api;

import org.bukkit.inventory.ItemStack;

public class GUIPages {

    public ItemStack[] firstPage = new ItemStack[54];
    public ItemStack[] staffSelect = new ItemStack[54];
    public ItemStack[] fStaff = new ItemStack[54];
    public ItemStack[] pStaff = new ItemStack[54];
    public ItemStack[] aStaff = new ItemStack[54];
    public ItemStack[] gStaff = new ItemStack[54];

    public GUIPages() {

        initFirstPage();
        initStaffSelect();

    }

    private void initFirstPage() {

        for (int i = 0; i < 54; i++) {

            if (i == 4) {
                firstPage[i] = GUIItems.getWebsite();
            } else if (i == 20) {
                firstPage[i] = GUIItems.getArcade();
            } else if (i == 22) {
                firstPage[i] = GUIItems.getPrison();
            } else if (i == 24) {
                firstPage[i] = GUIItems.getFactions();
            } else {
                firstPage[i] = GUIItems.getPane();
            }

        }

    }

    private void initStaffSelect() {

        for (int i = 0; i < 54; i++) {

            if (i == 4) {
                staffSelect[i] = GUIItems.getStaff();
            } else if (i == 20) {
                staffSelect[i] = GUIItems.getArcade();
            } else if (i == 22) {
                staffSelect[i] = GUIItems.getPrison();
            } else if (i == 24) {
                staffSelect[i] = GUIItems.getFactions();
            } else if (i == 40) {
                staffSelect[i] = GUIItems.getGlobal();
            } else {
                staffSelect[i] = GUIItems.getPane();
            }

        }

    }

}

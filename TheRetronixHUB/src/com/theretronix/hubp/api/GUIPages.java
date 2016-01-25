package com.theretronix.hubp.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GUIPages {
	
	public ItemStack[] firstPage, staffSelect = new ItemStack[54];
	public ItemStack[] hFactions, mFactions, aFactions = new ItemStack[54];
	public ItemStack[] hPrison, mPrison, aPrison = new ItemStack[54];
	public ItemStack[] hArcade, mArcade, aArcade = new ItemStack[54];
	
	public GUIPages() {
		// First Page Setup
		for (int i = 0; i < 54; i++) {
			
			switch(i) {
			case 5:
				firstPage[i] = GUIItems.getWebsite();
				break;
			case 21:
				firstPage[i] = GUIItems.getArcade();
				break;
			case 23:
				firstPage[i] = GUIItems.getPrison();
				break;
			case 25:
				firstPage[i] = GUIItems.getFactions();
				break;
			case 41:
				firstPage[i] = GUIItems.getStaff();
				break;
			default:
				firstPage[i] = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			}
			
		}
		
		// Staff Server Select
		for (int i = 0; i < 54; i++) {
			
			switch(i) {
			case 5:
				staffSelect[i] = GUIItems.getStaff();
			default:
				staffSelect[i] = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			}
			
		}
		
	}

}

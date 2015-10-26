package cf.bluebracket.znexusfactions.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cf.bluebracket.znexusfactions.zNexusFactions;

import java.util.ArrayList;
import java.util.List;

public class Methods {

	private zNexusFactions plugin;
	private static zNexusFactions sPlugin;

	public Methods(zNexusFactions plugin) {
		this.plugin = plugin;
		sPlugin = plugin;
	}

	public void giveNexus(Player player) {
		ItemStack itemStack = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("§5Nexus");
		itemStack.setItemMeta(itemMeta);

		if (player.getItemInHand().getType() != Material.AIR) {
			player.sendMessage("§4Make sure there is nothing in your hand!");
		} else {
			player.getInventory().addItem(itemStack);
			player.sendMessage("§3Place this block to make your faction!");
		}

	}

	public boolean factionExists(String name) {

		FactionData factionData = plugin.getDatabase().find(FactionData.class).where().ieq("name", name).findUnique();

		if (factionData != null) {
			return true;
		}

		return false;

	}

	public boolean invalidPerms(CommandSender sender) {
		sender.sendMessage("§4You do not permission to do that!");
		return true;
	}

	public boolean inFactionBase(Location location) {
		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
			if (location.getBlockX() >= factionData.getXOne() && location.getBlockX() <= factionData.getXTwo()) {
				if (location.getBlockY() >= factionData.getYOne() && location.getBlockY() <= factionData.getYTwo()) {
					if (location.getBlockZ() >= factionData.getZOne()
							&& location.getBlockZ() <= factionData.getZTwo()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public FactionData getFactionAtLocation(Location location) {
		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
			if (location.getBlockX() >= factionData.getXOne() && location.getBlockX() <= factionData.getXTwo()) {
				if (location.getBlockY() >= factionData.getYOne() && location.getBlockY() <= factionData.getYTwo()) {
					if (location.getBlockZ() >= factionData.getZOne()
							&& location.getBlockZ() <= factionData.getZTwo()) {
						return factionData;
					}
				}
			}
		}
		return null;
	}

	public boolean isInFaction(Player player) {

		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
			if (factionData.getBukkitPlayers().contains(player)) {
				return true;
			}
		}

		return false;

	}

	public FactionData getFaction(OfflinePlayer player) {

		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {

			if (factionData.getBukkitPlayers().contains(player)) {
				return factionData;
			}

		}

		return null;

	}

	public boolean isOwner(Player player) {
		Faction faction = getFaction(player).getFaction();
		if (faction.getOwner().getUniqueId() == player.getUniqueId()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isStaff(Player player) {
		Faction faction = getFaction(player).getFaction();
		if (faction.getStaff().contains(player)) {
			return true;
		} else {
			return false;
		}
	}

	public static zNexusFactions getPlugin() {
		return sPlugin;
	}

	public boolean isNearFaction(Player player, double range) {

		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {

			double nearestAmount = getNearestAmount(factionData, player);

			if (nearestAmount <= range) {

				return true;

			}

		}

		return false;

	}

	public String getNearestDimension(FactionData factionData, Player player) {

		String nearestDimension = null;
		double nearestAmount = -1;

		if (player.getLocation().getX() > factionData.getXOne()) {

			if (nearestAmount == -1) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();
				nearestDimension = "X-";

			}

			if (nearestAmount > player.getLocation().getX() - factionData.getXOne()) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();
				nearestDimension = "X-";

			}

		} else if (player.getLocation().getX() < factionData.getXTwo()) {

			if (nearestAmount == -1) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();
				nearestDimension = "X+";

			}

			if (nearestAmount > factionData.getXTwo() - player.getLocation().getX()) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();
				nearestDimension = "X+";

			}

		}

		if (player.getLocation().getY() > factionData.getYOne()) {

			if (nearestAmount == -1) {

				nearestAmount = player.getLocation().getY() - factionData.getYOne();
				nearestDimension = "Y-";

			}

			if (nearestAmount > player.getLocation().getY() - factionData.getYOne()) {

				nearestAmount = player.getLocation().getY() - factionData.getYOne();
				nearestDimension = "Y-";

			}

		} else if (player.getLocation().getY() < factionData.getYTwo()) {

			if (nearestAmount == -1) {

				nearestAmount = factionData.getYTwo() - player.getLocation().getY();
				nearestDimension = "Y+";

			}

			if (nearestAmount > factionData.getYTwo() - player.getLocation().getY()) {

				nearestAmount = factionData.getYTwo() - player.getLocation().getY();
				nearestDimension = "Y+";

			}

		}

		if (player.getLocation().getX() > factionData.getXOne()) {

			if (nearestAmount == -1) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();
				nearestDimension = "X-";

			}

			if (nearestAmount > player.getLocation().getX() - factionData.getXOne()) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();
				nearestDimension = "X-";

			}

		} else if (player.getLocation().getX() < factionData.getXTwo()) {

			if (nearestAmount == -1) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();
				nearestDimension = "X+";

			}

			if (nearestAmount > factionData.getXTwo() - player.getLocation().getX()) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();
				nearestDimension = "X+";

			}

		}

		return nearestDimension;

	}

	public double getNearestAmount(FactionData factionData, Player player) {

		double nearestAmount = -1;

		if (player.getLocation().getX() > factionData.getXOne()) {

			if (nearestAmount == -1) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();

			}

			if (nearestAmount > player.getLocation().getX() - factionData.getXOne()) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();

			}

		} else if (player.getLocation().getX() < factionData.getXTwo()) {

			if (nearestAmount == -1) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();

			}

			if (nearestAmount > factionData.getXTwo() - player.getLocation().getX()) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();

			}

		}

		if (player.getLocation().getY() > factionData.getYOne()) {

			if (nearestAmount == -1) {

				nearestAmount = player.getLocation().getY() - factionData.getYOne();

			}

			if (nearestAmount > player.getLocation().getY() - factionData.getYOne()) {

				nearestAmount = player.getLocation().getY() - factionData.getYOne();

			}

		} else if (player.getLocation().getY() < factionData.getYTwo()) {

			if (nearestAmount == -1) {

				nearestAmount = factionData.getYTwo() - player.getLocation().getY();

			}

			if (nearestAmount > factionData.getYTwo() - player.getLocation().getY()) {

				nearestAmount = factionData.getYTwo() - player.getLocation().getY();

			}

		}

		if (player.getLocation().getX() > factionData.getXOne()) {

			if (nearestAmount == -1) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();

			}

			if (nearestAmount > player.getLocation().getX() - factionData.getXOne()) {

				nearestAmount = player.getLocation().getX() - factionData.getXOne();

			}

		} else if (player.getLocation().getX() < factionData.getXTwo()) {

			if (nearestAmount == -1) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();

			}

			if (nearestAmount > factionData.getXTwo() - player.getLocation().getX()) {

				nearestAmount = factionData.getXTwo() - player.getLocation().getX();

			}

		}

		return nearestAmount;

	}

	public Faction getNearestFaction(Player player, double range) {

		List<FactionData> list = new ArrayList<>();

		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {

			if (player.getLocation().getX() > factionData.getXOne()) {

				if (player.getLocation().getX() - factionData.getXOne() <= range) {

					if (player.getLocation().getY() > factionData.getYOne()) {

						if (player.getLocation().getY() - factionData.getYOne() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					} else if (player.getLocation().getY() > factionData.getYTwo()) {

						// IGNORE

					} else {

						if (factionData.getYTwo() - player.getLocation().getY() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					}

				}

			} else if (player.getLocation().getX() > factionData.getXTwo()) {

				// IGNORE

			} else {

				if (factionData.getXTwo() - player.getLocation().getX() <= range) {

					if (player.getLocation().getY() > factionData.getYOne()) {

						if (player.getLocation().getY() - factionData.getYOne() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					} else if (player.getLocation().getY() > factionData.getYTwo()) {

						// IGNORE

					} else {

						if (factionData.getYTwo() - player.getLocation().getY() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					}

				}

			}

		}

		plugin.getLogger().info("Size: " + list.size());

		Faction nearest = null;

		if (list.size() == 1) {

			nearest = list.get(0).getFaction();

		}

		FactionData current = null;
		@SuppressWarnings("unused")
		String nearestDimension = "";
		double nearestAmount = -1;

		for (FactionData factionData : list) {

			if (current == null) {

				current = factionData;

				nearestAmount = getNearestAmount(current, player);
				nearestDimension = getNearestDimension(current, player);

			} else {

				if (getNearestAmount(factionData, player) < nearestAmount) {

					nearestAmount = getNearestAmount(factionData, player);
					nearestDimension = getNearestDimension(factionData, player);
					current = factionData;

				}

			}

		}

		nearest = current.getFaction();

		return nearest;

	}

	public FactionData getNearestFactionData(Player player, double range) {

		List<FactionData> list = new ArrayList<>();

		for (FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {

			if (player.getLocation().getX() > factionData.getXOne()) {

				if (player.getLocation().getX() - factionData.getXOne() <= range) {

					if (player.getLocation().getY() > factionData.getYOne()) {

						if (player.getLocation().getY() - factionData.getYOne() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					} else if (player.getLocation().getY() > factionData.getYTwo()) {

						// IGNORE

					} else {

						if (factionData.getYTwo() - player.getLocation().getY() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					}

				}

			} else if (player.getLocation().getX() > factionData.getXTwo()) {

				// IGNORE

			} else {

				if (factionData.getXTwo() - player.getLocation().getX() <= range) {

					if (player.getLocation().getY() > factionData.getYOne()) {

						if (player.getLocation().getY() - factionData.getYOne() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					} else if (player.getLocation().getY() > factionData.getYTwo()) {

						// IGNORE

					} else {

						if (factionData.getYTwo() - player.getLocation().getY() <= range) {

							if (player.getLocation().getZ() > factionData.getZOne()) {

								if (player.getLocation().getZ() - factionData.getZOne() <= range) {

									list.add(factionData);

								}
							} else if (player.getLocation().getZ() > factionData.getZTwo()) {

								// IGNORE

							} else {

								if (factionData.getZTwo() - player.getLocation().getZ() <= range) {

									list.add(factionData);

								}

							}

						}

					}

				}

			}

		}

		FactionData current = null;

		if (list.size() == 1) {

			current = list.get(0);

		}

		@SuppressWarnings("unused")
		String nearestDimension = "";
		double nearestAmount = -1;

		for (FactionData factionData : list) {

			if (current == null) {

				current = factionData;

				nearestAmount = getNearestAmount(current, player);
				nearestDimension = getNearestDimension(current, player);

			} else {

				if (getNearestAmount(factionData, player) < nearestAmount) {

					nearestAmount = getNearestAmount(factionData, player);
					nearestDimension = getNearestDimension(factionData, player);
					current = factionData;

				}

			}

		}

		return current;

	}

	public int getPages() {
    	if (plugin.getDatabase().find(FactionData.class).findSet().size() % 5 == 0) {
    		return plugin.getDatabase().find(FactionData.class).findSet().size() / 5;
    	} else {
    		return (plugin.getDatabase().find(FactionData.class).findSet().size() / 5) + 1;
    	}
    }
	
	public int getWholePages() {
		return plugin.getDatabase().find(FactionData.class).findSet().size() / 5;
	}
	
	public List<FactionData> getPage(int index) {
		List<FactionData> list = new ArrayList<>();
		int count = 0;
		for(FactionData factionData : plugin.getDatabase().find(FactionData.class).findSet()) {
			count++;
			if (count >= ((index * 5) + 1) && count <= (((index * 5) + 1) + 5)) {
				list.add(factionData);
			}
		}
		return list;
	}

}
package fr.frivec.armorstandsmodels.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class DevCommand implements CommandExecutor {

    private ArmorStand origin, point;
    private Vector originToPoint;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof final Player player) {

            final String subCommand = args[0];

            if(subCommand != null) {

                if(subCommand.equalsIgnoreCase("rotation")) {

                    if(args[1].equalsIgnoreCase("spawn")) {

                        this.origin = (ArmorStand) Bukkit.getWorld("world").spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                        this.point = (ArmorStand) Bukkit.getWorld("world").spawnEntity(player.getLocation().clone().add(0, 0, 2), EntityType.ARMOR_STAND);

                        this.originToPoint = new Vector(this.origin.getLocation().getX() - this.point.getLocation().getX(), 0, this.origin.getLocation().getZ() - this.point.getLocation().getZ());

                    }else if(args[1].equalsIgnoreCase("rotate")) {

                        final float angle = Float.parseFloat(args[2]);
                        final double radiantAngle = Math.toRadians(angle);

                        player.sendMessage(Component.text("Angle: " + angle + "° | Radiant: " + radiantAngle));
                        player.sendMessage(Component.text("§9Start Location : " + this.point.getLocation().getX() + " | " + this.point.getLocation().getZ()));

                        //Vector from 0 0 to Origin of system
                        final Vector centerToOrigin = new Vector(this.origin.getLocation().getX(), this.origin.getLocation().getY(), this.origin.getLocation().getZ()),
                                    invertedCenterToOrigin = centerToOrigin.clone().multiply(-1);

                        //Phantom point based on 0 0
                        final Location pointOnCenter = new Location(Bukkit.getWorld("world"), roundTo2Decimals(this.point.getLocation().getX() + invertedCenterToOrigin.getX()), this.point.getLocation().getY(), roundTo2Decimals(this.point.getLocation().getZ() + invertedCenterToOrigin.getZ()));

                        player.sendMessage(Component.text("§cPoint on center : x: " + pointOnCenter.getX() + " | z: " + pointOnCenter.getZ()));

                        //Matrix [nbRows][nbColums]
                        final double[][] matrixA = new double[2][2],
                                            matrixB = new double[1][2];

                        //Register values for first matrix
                        matrixA[0][0] = roundTo2Decimals(Math.cos(radiantAngle));
                        matrixA[0][1] = roundTo2Decimals((double) -1 * Math.sin(radiantAngle));
                        matrixA[1][0] = roundTo2Decimals(Math.sin(radiantAngle));
                        matrixA[1][1] = roundTo2Decimals(matrixA[0][0]);

                        //Register values for second matrix
                        //+x is on +y axis on cartesian plan, +z is on +x axis on cartesian plan
                        matrixB[0][0] = roundTo2Decimals(pointOnCenter.getZ());
                        matrixB[0][1] = roundTo2Decimals(pointOnCenter.getX());

                        final double[][] xMatrix = new double[1][2];

                        xMatrix[0][0] = roundTo2Decimals(matrixA[0][1] * matrixB[0][1] + matrixA[0][0] * matrixB[0][0]);
                        xMatrix[0][1] = roundTo2Decimals(matrixA[1][1] * matrixB[0][1] + matrixA[1][0] * matrixB[0][0]);

                        player.sendMessage(Component.text("§bAfter rotation: x: " + xMatrix[0][1] + " | z:" + xMatrix[0][0]));

                        final Location newLocation = new Location(pointOnCenter.getWorld(), xMatrix[0][1] + centerToOrigin.getX(), pointOnCenter.getY(), xMatrix[0][0] + centerToOrigin.getZ());

                        this.point.teleport(newLocation);

                        player.sendMessage(Component.text("§aNew Location: x: " + newLocation.getX() + " | z:" + newLocation.getZ()));
                        player.sendMessage(Component.text("§dNew Origin: x: " + this.origin.getLocation().getX() + " | z:" + this.origin.getLocation().getZ()));

                        player.sendMessage(Component.text("§aPoint teleported with angle of §6" + angle + "°§a."));

                    }

                }

            }

        }

        return false;
    }

    private double roundTo2Decimals(final double a) {

        return (double) Math.round(a * 100) / 100;
    }

}

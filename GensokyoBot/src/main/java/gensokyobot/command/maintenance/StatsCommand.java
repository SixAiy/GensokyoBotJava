/*
 * MIT License
 *
 * Copyright (c) 2017 Frederik Ar. Mikkelsen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package gensokyobot.command.maintenance;

import gensokyobot.Config;
import gensokyobot.GensokyoBot;
import gensokyobot.audio.PlayerRegistry;
import gensokyobot.commandmeta.CommandManager;
import gensokyobot.commandmeta.abs.Command;
import gensokyobot.commandmeta.abs.IMaintenanceCommand;
import gensokyobot.feature.I18n;
import gensokyobot.util.TextUtils;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.text.MessageFormat;

public class StatsCommand extends Command implements IMaintenanceCommand {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args) {
        long totalSecs = (System.currentTimeMillis() - GensokyoBot.START_TIME) / 1000;
        int days = (int) (totalSecs / (60 * 60 * 24));
        int hours = (int) ((totalSecs / (60 * 60)) % 24);
        int mins = (int) ((totalSecs / 60) % 60);
        int secs = (int) (totalSecs % 60);
        
        String str = MessageFormat.format(
                I18n.get().getString("statsParagraph"),
                days, hours, mins, secs, CommandManager.commandsExecuted - 1)
                + "\n";

        str = MessageFormat.format(I18n.get().getString("statsRate"), str, (float) (CommandManager.commandsExecuted - 1) / ((float) totalSecs / (float) (60 * 60)));

        str = str + "\n\n```";

        str = str + "Reserved memory:                " + Runtime.getRuntime().totalMemory() / 1000000 + "MB\n";
        str = str + "-> Of which is used:            " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000 + "MB\n";
        str = str + "-> Of which is free:            " + Runtime.getRuntime().freeMemory() / 1000000 + "MB\n";
        str = str + "Max reservable:                 " + Runtime.getRuntime().maxMemory() / 1000000 + "MB\n";

        str = str + "\n----------\n\n";

        str = str + "Sharding:                       " + guild.getJDA().getShardInfo().getShardString() + "\n";
        str = str + "Players playing:                " + PlayerRegistry.getPlayingPlayers().size() + "\n";
        str = str + "Listeners:                      " + PlayerRegistry.getPlayingPlayers().size() + "\n";

        str = str + "Known servers:                  " + GensokyoBot.getShardManager().getGuilds().size() + "\n";
        str = str + "Known users in servers:         " + GensokyoBot.getShardManager().getUsers().size() + "\n";
        str = str + "Distribution:                   " + Config.CONFIG.getDistribution() + "\n";
        str = str + "JDA responses total:            " + guild.getJDA().getResponseTotal() + "\n";
        str = str + "JDA version:                    " + JDAInfo.VERSION;

        str = str + "```";

        channel.sendMessage(TextUtils.prefaceWithName(invoker, str)).queue();
    }

    @Override
    public String help(Guild guild) {
        return "{0}{1}\n#Show some statistics about this bot.";
    }
}

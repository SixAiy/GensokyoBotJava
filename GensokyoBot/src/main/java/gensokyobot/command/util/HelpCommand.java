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

package gensokyobot.command.util;

import gensokyobot.Config;
import gensokyobot.commandmeta.abs.Command;
import gensokyobot.util.BotConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

public class HelpCommand extends Command  {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args) {
        channel.sendMessage(getHelpMessage(guild.getJDA())).queue();
    }

    public static MessageEmbed getHelpMessage(JDA jda) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(23, 191, 224));
        eb.setAuthor(jda.getSelfUser().getName(), null, jda.getSelfUser().getAvatarUrl());
        eb.setDescription("Invite this bot: https://sixaiy.com/invite/gensokyobot\n\n" +
                "Documentation can be found at\n" + BotConstants.GITHUB_URL +
                "\n\nNeed help or have any ideas for GensokyoBot? Perhaps you just want to hang out? Join the FredBoat community!\n" +
                BotConstants.hangoutInvite
        );
        eb.addField(Config.CONFIG.getPrefix() + "join", "#Joins your voice chat and begin playing.", true);
        eb.addField(Config.CONFIG.getPrefix() + "leave", "#Joins your voice chat and begin playing.", true);
        eb.addField(Config.CONFIG.getPrefix() + "np", "#Joins your voice chat and begin playing.", true);
        eb.addField(Config.CONFIG.getPrefix() + "stats", "Displays stats about this bo", true);
        eb.addField(Config.CONFIG.getPrefix() + "shards", "Displays shards information about this bot", true);
        eb.addField(Config.CONFIG.getPrefix() + "invite", "Displays invite link for this bot", true);
        eb.addField(Config.CONFIG.getPrefix() + "help", "Displays this help message", true);
        if(Config.CONFIG.getStreamUrl().equals(Config.GENSOKYO_RADIO_STREAM_URL)) {
            eb.setFooter("Content provided by gensokyoradio.net.The GR logo is a trademark of Gensokyo Radio. Gensokyo Radio is © LunarSpotlight.", null);
        } else {
            eb.setFooter("" + Config.CONFIG.getDistribution(), jda.getSelfUser().getAvatarUrl());
        }
        return eb.build();
    }

    @Override
    public String help(Guild guild) {
        return null;
    }
}

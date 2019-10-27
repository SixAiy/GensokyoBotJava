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
import gensokyobot.feature.I18n;
import gensokyobot.util.BotConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.text.MessageFormat;

public class HelpCommand extends Command  {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args) {
        channel.sendMessage(getHelpMessage(guild.getJDA())).queue();
    }

    public static MessageEmbed getHelpMessage(JDA jda) {
        String Description = MessageFormat.format(I18n.get().getString("helpCommandDescription"), BotConstants.hangoutInvite, BotConstants.DOCS_URL);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(23, 191, 224));
        eb.setAuthor(jda.getSelfUser().getName(), null, jda.getSelfUser().getAvatarUrl());
        eb.setDescription(Description);
        eb.addField(Config.CONFIG.getPrefix() + "join", I18n.get().getString("helpCommandJoin"), true);
        eb.addField(Config.CONFIG.getPrefix() + "leave", I18n.get().getString("helpCommandLeave"), true);
        eb.addField(Config.CONFIG.getPrefix() + "np", I18n.get().getString("helpCommandNp"), true);
        eb.addField(Config.CONFIG.getPrefix() + "stats", I18n.get().getString("helpCommandStats"), true);
        eb.addField(Config.CONFIG.getPrefix() + "shards", I18n.get().getString("helpCommandShards"), true);
        eb.addField(Config.CONFIG.getPrefix() + "invite", I18n.get().getString("helpCommandInvite"), true);
        eb.addField(Config.CONFIG.getPrefix() + "help", I18n.get().getString("helpCommandHelp"), true);
        if(Config.CONFIG.getStreamUrl().equals(Config.GENSOKYO_RADIO_STREAM_URL)) {
            eb.setFooter(I18n.get().getString("helpCommandFooter"), null);
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

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
package gensokyobot.event;

import gensokyobot.Config;
import gensokyobot.GensokyoBot;
import gensokyobot.audio.PlayerRegistry;
import gensokyobot.command.util.HelpCommand;
import gensokyobot.commandmeta.CommandManager;
import gensokyobot.commandmeta.CommandRegistry;
import gensokyobot.commandmeta.abs.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.discordbots.api.client.DiscordBotListAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.regex.Matcher;

public class EventListenerBoat extends AbstractEventListener {

    private static final Logger log = LoggerFactory.getLogger(EventListenerBoat.class);

    private static HashMap<String, Message> messagesToDeleteIfIdDeleted = new HashMap<>();
    private User lastUserToReceiveHelp;

    public EventListenerBoat() { }

    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.isFromType(ChannelType.PRIVATE)) {
            PrivateChannel privateChannel = event.getPrivateChannel();
            if (event.getAuthor() == lastUserToReceiveHelp) {
                //Ignore, they just got help! Stops any bot chain reactions
                return;
            }

            if (event.getAuthor().equals(event.getJDA().getSelfUser())) {
                //Don't reply to ourselves
                return;
            }

            event.getChannel().sendMessage(HelpCommand.getHelpMessage(event.getJDA())).queue();
            lastUserToReceiveHelp = event.getAuthor();

        }

        if (event.getAuthor().equals(event.getJDA().getSelfUser())) {
            log.info(event.getGuild().getName() + " \t " + event.getAuthor().getName() + " \t " + event.getMessage().getContentRaw());
            return;
        }

        if (event.getMessage().getContentDisplay().length() < Config.CONFIG.getPrefix().length()) {
            return;
        }

        if (event.getMessage().getContentDisplay().substring(0, Config.CONFIG.getPrefix().length()).equals(Config.CONFIG.getPrefix())) {
            Command invoked = null;
            log.info(event.getGuild().getName() + " \t " + event.getAuthor().getName() + " \t " + event.getMessage().getContentRaw());
            Matcher matcher = COMMAND_NAME_PREFIX.matcher(event.getMessage().getContentDisplay());

            if(matcher.find()) {
                String cmdName = matcher.group();
                CommandRegistry.CommandEntry entry = CommandRegistry.getCommand(cmdName);
                if(entry != null) {
                    invoked = entry.command;
                } else {
                    log.info("Unknown command:", cmdName);
                }
            }

            if (invoked == null) {
                return;
            }

            CommandManager.prefixCalled(invoked, event.getGuild(), event.getTextChannel(), event.getMember(), event.getMessage());
        }
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        if (messagesToDeleteIfIdDeleted.containsKey(event.getMessageId())) {
            Message msg = messagesToDeleteIfIdDeleted.remove(event.getMessageId());
            if (msg.getJDA() == event.getJDA()) {
                msg.delete().queue();
            }
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        PlayerRegistry.destroyPlayer(event.getGuild());

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Left Guild", null, null);
        eb.setThumbnail(event.getGuild().getIconUrl());
        eb.addField("Name", event.getGuild().getName() + "\n" + event.getGuild().getId(),  true);
        eb.addField("Total Guilds", String.valueOf(GensokyoBot.getShardManager().getGuilds()), true);


        TextChannel textChannel = event.getGuild().getTextChannelById("632005201682759712");
        textChannel.sendMessage(eb.build()).queue();

        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token(Config.CONFIG.getTopGGToken())
                .botId(event.getJDA().getSelfUser().getId())
                .build();

        int shardId = event.getJDA().getShardInfo().getShardId();
        int shardCount = event.getJDA().getShardInfo().getShardTotal();
        int serverCount = GensokyoBot.getShardManager().getGuilds().size();

        api.setStats(shardId, shardCount, serverCount);

    }

    public void onGuildJoin(GuildJoinEvent event) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Left Guild", null, null);
        eb.setThumbnail(event.getGuild().getIconUrl());
        eb.addField("Name", event.getGuild().getName() + "\n" + event.getGuild().getId(),  true);
        eb.addField("Total Guilds", String.valueOf(GensokyoBot.getShardManager().getGuilds()), true);


        TextChannel textChannel = event.getGuild().getTextChannelById("632005201682759712");
        textChannel.sendMessage(eb.build()).queue();

        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token(Config.CONFIG.getTopGGToken())
                .botId(event.getJDA().getSelfUser().getId())
                .build();

        int shardId = event.getJDA().getShardInfo().getShardId();
        int shardCount = event.getJDA().getShardInfo().getShardTotal();
        int serverCount = GensokyoBot.getShardManager().getGuilds().size();

        api.setStats(shardId, shardCount, serverCount);

    }

}

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
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class HelpCommand extends Command  {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args) {
        channel.sendMessage(getHelpMessage(guild.getJDA())).queue();
    }

    public static String getHelpMessage(JDA jda) {
        String out =  "```md\n" +
                "< Music Commands >\n" +
                ",,join\n" +
                "#Joins your voice chat and begin playing.\n" +
                ",,leave\n" +
                "#Leaves the voice chat, stopping the music\n" +
                ",,np\n" +
                "#Shows the song currently playing in a nice embed\n" +
                ",,stats\n" +
                "#Displays stats about this bot\n" +
                ",,shards\n" +
                "#Displays shards information about this bot\n" +
                ",,invite\n" +
                "#Displays invite link for this bot\n" +
                ",,help\n" +
                "#Displays this help message\n" +
                "\n\n" +
                "Invite this bot: https://discordapp.com/oauth2/authorize?&client_id=" + jda.getSelfUser().getId() + "&scope=bot\n" +
                "Source code: https://github.com/Frederikam/GensokyoBot\n\n{0}" +
                "```";

        out = out.replaceAll(",,", Config.CONFIG.getPrefix());

        if(Config.CONFIG.getStreamUrl().equals(Config.GENSOKYO_RADIO_STREAM_URL)) {
            out = out.replaceFirst("\\{0}", "Content provided by gensokyoradio.net.\nThe GR logo is a trademark of Gensokyo Radio.\nGensokyo Radio is © LunarSpotlight.\n");
        } else {
            out = out.replaceFirst("\\{0}", "");
        }

        return out;
    }

    @Override
    public String help(Guild guild) {
        return null;
    }
}

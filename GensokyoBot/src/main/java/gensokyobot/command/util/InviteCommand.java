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

import gensokyobot.commandmeta.abs.Command;
import gensokyobot.commandmeta.abs.IUtilCommand;
import gensokyobot.feature.I18n;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class InviteCommand extends Command implements IUtilCommand {
    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args)  {
        String str = "Want to add GensokyoBot to your server? If you have Manage Server permissions for your guild, you can invite GensokyoBot:\n" +
                "<https://discordapp.com/oauth2/authorize?&client_id=" + guild.getJDA().getSelfUser().getId() + "&scope=bot>" +
                "\n\n" +
                "Need help or have any ideas for GensokyoBot? Perhaps you just want to hang out? Join the FredBoat community!\n" +
                "https://discord.gg/cgPFW4q" +
                "\n\n" +
                "You cannot send GensokyoBot commands through DMs.\n" +
                "Created by Fre_d and open source contributors";
        channel.sendMessage(str).queue();
    }

    @Override
    public String help(Guild guild) {
        String usage = "{0}{1}\n#";
        return usage + I18n.get().getString("helpInviteCommand");
    }
}

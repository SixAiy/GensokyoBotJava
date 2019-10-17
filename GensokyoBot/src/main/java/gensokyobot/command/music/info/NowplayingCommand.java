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

package gensokyobot.command.music.info;

import gensokyobot.Config;
import gensokyobot.agent.GensokyoInfoAgent;
import gensokyobot.commandmeta.abs.Command;
import gensokyobot.commandmeta.abs.IMusicCommand;
import gensokyobot.feature.I18n;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;
import org.json.XML;

import java.awt.*;
import java.text.MessageFormat;

public class NowplayingCommand extends Command implements IMusicCommand {

    @Override
    public void onInvoke(Guild guild, TextChannel channel, Member invoker, Message message, String[] args) {

        if(!Config.CONFIG.getStreamUrl().equals(Config.GENSOKYO_RADIO_STREAM_URL)) {
            channel.sendMessage("Info unavailable for this stream").queue();
            return;
        }

        sendGensokyoRadioEmbed(channel);
    }

    static void sendGensokyoRadioEmbed(TextChannel channel) {
        JSONObject data = XML.toJSONObject(GensokyoInfoAgent.getInfo()).getJSONObject("GENSOKYORADIODATA");

        String rating = data.getJSONObject("MISC").getInt("TIMESRATED") == 0 ?
                I18n.get().getString("noneYet") :
                MessageFormat.format(I18n.get().getString("npRatingRange"), data.getJSONObject("MISC").getInt("RATING"), data.getJSONObject("MISC").getInt("TIMESRATED"));

        String albumArt = data.getJSONObject("MISC").getString("ALBUMART").equals("") ?
                "https://cdn.discordapp.com/attachments/240116420946427905/373019550725177344/gr-logo-placeholder.png" :
                "https://gensokyoradio.net/images/albums/original/" + data.getJSONObject("MISC").getString("ALBUMART");

        String titleUrl = data.getJSONObject("MISC").getString("CIRCLELINK").equals("") ?
                "https://gensokyoradio.net/" :
                data.getJSONObject("MISC").getString("CIRCLELINK");

        EmbedBuilder eb = new EmbedBuilder()
                .setTitle(data.getJSONObject("SONGINFO").getString("TITLE"), titleUrl)
                .addField(I18n.get().getString("album"), data.getJSONObject("SONGINFO").getString("ALBUM"), true)
                .addField(I18n.get().getString("artist"), data.getJSONObject("SONGINFO").getString("ARTIST"), true)
                .addField(I18n.get().getString("circle"), data.getJSONObject("SONGINFO").getString("CIRCLE"), true);

        if (data.getJSONObject("SONGINFO").optInt("YEAR") != 0) {
            eb.addField(I18n.get().getString("year"), Integer.toString(data.getJSONObject("SONGINFO").getInt("YEAR")), true);
        }

        eb.addField(I18n.get().getString("rating"), rating, true)
                .addField(I18n.get().getString("listeners"), Integer.toString(data.getJSONObject("SERVERINFO").getInt("LISTENERS")), true)
                .setImage(albumArt)
                .setColor(new Color(66, 16, 80))
                .setFooter("Content provided by gensokyoradio.net.\n" +
                        "The GR logo is a trademark of Gensokyo Radio." +
                        "\nGensokyo Radio is © LunarSpotlight.", null)
                .build();

        channel.sendMessage(eb.build()).queue();
    }

    @Override
    public String help(Guild guild) {
        String usage = "{0}{1}\n#";
        return usage + I18n.get().getString("helpNowplayingCommand");
    }
}

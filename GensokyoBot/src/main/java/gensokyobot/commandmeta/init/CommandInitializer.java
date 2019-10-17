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
package gensokyobot.commandmeta.init;

import gensokyobot.command.admin.BotRestartCommand;
import gensokyobot.command.admin.CompileCommand;
import gensokyobot.command.admin.EvalCommand;
import gensokyobot.command.admin.ExitCommand;
import gensokyobot.command.admin.ReviveCommand;
import gensokyobot.command.admin.UpdateCommand;
import gensokyobot.command.maintenance.ShardsCommand;
import gensokyobot.command.maintenance.StatsCommand;
import gensokyobot.command.maintenance.VersionCommand;
import gensokyobot.command.music.control.JoinCommand;
import gensokyobot.command.music.control.LeaveCommand;
import gensokyobot.command.music.info.NowplayingCommand;
import gensokyobot.command.util.HelloCommand;
import gensokyobot.command.util.HelpCommand;
import gensokyobot.command.util.InviteCommand;
import gensokyobot.commandmeta.CommandRegistry;

public class CommandInitializer {

    public static void initCommands() {
        CommandRegistry.registerCommand("help", new HelpCommand());
        CommandRegistry.registerAlias("help", "commands");

        CommandRegistry.registerCommand("join", new JoinCommand());
        CommandRegistry.registerAlias("join", "play");
        CommandRegistry.registerCommand("leave", new LeaveCommand());
        CommandRegistry.registerAlias("leave", "stop");
        CommandRegistry.registerCommand("stats", new StatsCommand());
        CommandRegistry.registerCommand("shards", new ShardsCommand());
        CommandRegistry.registerCommand("invite", new InviteCommand());
        CommandRegistry.registerCommand("version", new VersionCommand());
        CommandRegistry.registerCommand("hello", new HelloCommand());
        CommandRegistry.registerCommand("np", new NowplayingCommand());
        CommandRegistry.registerAlias("np", "nowplaying");

        //Admin commands
        CommandRegistry.registerCommand("restart", new BotRestartCommand());
        CommandRegistry.registerCommand("compile", new CompileCommand());
        CommandRegistry.registerCommand("eval", new EvalCommand());
        CommandRegistry.registerCommand("exit", new ExitCommand());
        CommandRegistry.registerCommand("revive", new ReviveCommand());
        CommandRegistry.registerCommand("update", new UpdateCommand());
    }

}

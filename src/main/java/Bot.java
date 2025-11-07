import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.EnumSet;
import java.util.Random;

public class Bot {

    public static void main(String[] args) {
        String token = "MTQzNTcwNDgzMjM4Mjk5MjU2MQ.G1NfEC.U-ZC_Ss9qQTF6EoTxHO6TgD_-9CRlzr3hE33tY";

        try {
            JDA jda = JDABuilder.createLight(token, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS))
                    .addEventListeners(new RandomQuoteCommand())
                    .addEventListeners(new RandomNameCommand())
                    .addEventListeners(new EvilCommand())
                    .setActivity(Activity.playing("Banning Tekno"))
                    .build();

            jda.awaitReady();

            String guildId = "1418570459188039751";
            String guildId2 = "1420362494282825771";
            jda.getGuildById(guildId)
                    .updateCommands()
                    .addCommands(
                            Commands.slash("randomquote", "Send a random quote from the bot's folder"),
                            Commands.slash("randomname", "Assign a random nickname"),
                            Commands.slash("evil", "Become evil")
                    )
                    .queue(result -> System.out.println("Commands registered!"),
                            error -> System.err.println("Failed to register commands: " + error));

            jda.getGuildById(guildId2)
                    .updateCommands()
                    .addCommands(
                            Commands.slash("randomquote", "Send a random quote from the bot's folder"),
                            Commands.slash("randomname", "Assign a random nickname"),
                            Commands.slash("evil", "Become evil")
                    )
                    .queue(result -> System.out.println("Commands registered!"),
                            error -> System.err.println("Failed to register commands: " + error));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class RandomQuoteCommand extends ListenerAdapter {
        private final File quoteFolder = new File("images");
        private final Random random = new Random();

        @Override
        public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
            if (!event.getName().equals("randomquote")) return;

            File[] files = quoteFolder.listFiles((dir, name) ->
                    name.matches(".*\\.(png|jpg|jpeg|gif|txt)$")
            );

            if (files == null || files.length == 0) {
                event.reply("No quotes found in the folder!").setEphemeral(true).queue();
                return;
            }

            File randomFile = files[random.nextInt(files.length)];

            if (randomFile.getName().endsWith(".txt")) {
                try {
                    String content = java.nio.file.Files.readString(randomFile.toPath());
                    System.out.println("The quote " + randomFile.getName() + " has been sent at the request of " + event.getUser().getName());
                    event.reply(content).queue();
                } catch (Exception e) {
                    event.reply("Failed to read quote file.").setEphemeral(true).queue();
                    e.printStackTrace();
                }
            } else {
                event.deferReply().queue(hook -> hook.sendFiles(FileUpload.fromData(randomFile)).queue());
                System.out.println("The quote " + randomFile.getName() + " has been sent at the request of " + event.getUser().getName());
            }
        }
    }

    public static class EvilCommand extends ListenerAdapter {

        @Override
        public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
            if (!event.getName().equals("evil")) return;

            if (event.getUser().getId().equals("1008861928375996567")) {

                Member bot = event.getGuild().getMemberById("1435704832382992561");
                String roleId = "1425837616305016842";
                var role = event.getGuild().getRoleById(roleId);

                System.out.println("Ollie executed the evil command");
                event.getChannel().sendMessage("At your service, Master Ollie. What evil things do you want me to do today").queue();
                event.getGuild().modifyNickname(bot, "Evil Bot").queue();
                event.getGuild().addRoleToMember(bot, role).queue();

            } else {

                Member user = event.getMember();
                String roleId = "1425837616305016842";
                var role = event.getGuild().getRoleById(roleId);
                Member bot = event.getGuild().getMemberById("1435704832382992561");

                System.out.println(user.getUser().getName() + " has tried to execute the evil command but failed!");

                event.getGuild().modifyNickname(bot, "Nice Bot").queue();
                event.getChannel().sendMessage(user.getAsMention() + " you're not Ollie. Go away!").queue();
                event.getGuild().removeRoleFromMember(bot, role).queue();

            }

        }
    }


    public static class RandomNameCommand extends ListenerAdapter {
        private final String[] names = {"chicken car", "chicken jackson", "chips", "june", "junesies", "july", "jujus", "ai chatbot", "spammer", "ItsDumzy", "Blueprint", "Sharky", "waffle alt", "feesh", "", "oTeknoVA",
                                            "Spammer", "aNifty", "Donald Trump", "sam the fr*nch", "duoble suicide", "blueprintist", "doomscroller", "hells customer service", "34 Felonys", "Elon Musk", "Zohran Mamdani", "Shrimpcraftian", "Teknosaurus", "salsarata"
                                            , "YUM", "angel", "bald", "Izzy", "femboylover66", "Benosita", "Benita", "Melonarta", "WATCH STARWARS", ":3", "honey", "Bumno", "stew", "SQ", "emoaurafarmingclankerfemboy67", "lil machi", "stove", "mochi", "LasVegasTV", "ohnepixel",
                                            "waffle cult member", "jujuice", "grince the unc", "Raj", "Tekno Scammerman", "sam the green", "sam the green frenchie", "YOU", "dojulymeee_12",
                                            "biceps lover", "dominant french mommy", "what the sigma", "Sau Bay", "Steve", "my munchkin", "Moldarta", "Sapscream", "lore gooner", "gooning to lore", "julstein"};

        private final Random random = new Random();

        @Override
        public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
            if (!event.getName().equals("randomname")) return;

            if (names.length == 0) {
                event.reply("No names found in the Array!").setEphemeral(true).queue();
                return;
            }

            if (event.getUser().getId() == "989200388114776114") {
                event.getChannel().sendMessage("Hi chicken car I waffle alt");
            }


            String discordId = event.getUser().getId();

            if (!discordId.equals("600934351542157312")) {
                event.getGuild().retrieveMemberById(discordId).queue(
                        member -> {
                            String newNickname = names[random.nextInt(names.length)] + "\uD83E\uDD52";
                            System.out.println("Changed " + event.getUser().getName() + "s nickname to " + newNickname);
                            event.getGuild().modifyNickname(member, newNickname).queue(
                                    success -> event.reply("Your nickname has been changed to: " + newNickname).queue(),
                                    error -> event.reply("Failed to change nickname. Do I have permission?").setEphemeral(true).queue()
                            );
                        },
                        failure -> event.reply("Could not find you in this server!").setEphemeral(true).queue()
                );
            } else{
                event.getChannel().sendMessage("Sorry Fish but u cant use this");
                System.out.println("Fish tried to use the bot lol");
            }
        }
    }
}


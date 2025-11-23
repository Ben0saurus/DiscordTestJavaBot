import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RandomNameCommand extends ListenerAdapter {


    private String cdServer = "1420362494282825771";
    private String fishServer = "1418570459188039751";

    private String cucumber = "\uD83E\uDD52";
    private String calender = "\uD83D\uDCC6";

    private String newNickname = "null";

    private final String[] names1 = {"chicken car", "chicken jackson", "chips", "june", "junesies", "july", "jujus", "ai chatbot", "spammer", "ItsDumzy",
            "Blueprint", "Sharky", "waffle alt", "feesh", "", "oTeknoVA",
             "aNifty", "Donald Trump", "sam the fr*nch", "duoble suicide", "blueprintist", "doomscroller", "hells customer service", "34 Felonys",
            "Elon Musk", "Zohran Mamdani", "Shrimpcraftian", "Teknosaurus", "salsarata"
            , "YUM", "angel", "bald", "Izzy", "femboylover66", "Benosita", "Benita", "Melonarta", "WATCH STARWARS", ":3", "honey", "Bumno", "stew", "SQ",
            "emoaurafarmingclankerfemboy67", "lil machi", "stove", "mochi", "LasVegasTV", "ohnepixel",
            "waffle cult member", "jujuice", "grince the unc", "Raj", "Tekno Scammerman", "sam the green", "sam the green frenchie", "YOU", "dojulymeee_12",
            "biceps lover", "dominant french mommy", "what the sigma", "Sau Bay", "Steve", "my munchkin", "Moldarta", "Sapscream", "lore gooner", "gooning to lore",
            "julstein", "fishenstein", "unc", "fishbowlian", "tree climber", "just krystal"};

    private final String[] names2 = {"cd player", "cd", "broken cd", "chicken car", "chicken jackson", "daneTheShort", "june", "junesies", "july", "jujus", "ai chatbot",
            "spammer", "Blueprint", "feesh", "VAoTekno", "WATCH STARWARS"};


    private final Random random = new Random();

    public static CommandData getCommandData() {
        return Commands.slash("randomname", "Assign a random nickname");
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("randomname")) return;

        var member = event.getMember();
        if (member == null) {
            event.reply("Could not find your member info!").setEphemeral(true).queue();
            return;
        }

        String userId = event.getUser().getId();

        if (event.getGuild().getId().equals(fishServer)) {

            if (userId.equals("600934351542157312")) {
                event.getChannel().sendMessage("Sorry Fish but u cant use this").queue();
                System.out.println("Fish tried to use the bot lol");
                return;
            }

            newNickname = names1[random.nextInt(names1.length)] + cucumber;
            event.getGuild().modifyNickname(member, newNickname).queue(
                    success -> event.reply("Your nickname has been changed to: " + newNickname).queue(),
                    error -> event.reply("Failed to change nickname. Do I have permission?").setEphemeral(true).queue()
            );


        } else if (event.getGuild().getId().equals(cdServer)) {

            if (userId.equals("1043019784461230190")) {
                event.getChannel().sendMessage("Sorry July but u cant use this").queue();
                System.out.println("July tried to use the bot lol");
                return;
            }

            newNickname = names2[random.nextInt(names2.length)] + calender;
            event.getGuild().modifyNickname(member, newNickname).queue(
                    success -> event.reply("Your nickname has been changed to: " + newNickname).queue(),
                    error -> event.reply("Failed to change nickname. Do I have permission?").setEphemeral(true).queue()
            );

        }



        System.out.println("Changed " + member.getUser().getName() + "'s nickname to " + newNickname);
    }
}

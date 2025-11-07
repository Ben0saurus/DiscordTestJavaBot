import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

public class Bot {

    public static void main(String[] args) {
        String token = "MTQzNTcwNDgzMjM4Mjk5MjU2MQ.G1NfEC.U-ZC_Ss9qQTF6EoTxHO6TgD_-9CRlzr3hE33tY";

        try {
            JDA jda = JDABuilder.createLight(
                            token,
                            EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                    )
                    .addEventListeners(
                            new RandomQuoteCommand(),
                            new RandomNameCommand(),
                            new EvilCommand(),
                            new MessageListener()
                    )
                    .setActivity(Activity.playing("Banning Tekno"))
                    .build();

            jda.awaitReady();

            String guildId1 = "1418570459188039751";
            String guildId2 = "1420362494282825771";

            jda.getGuildById(guildId1)
                    .updateCommands()
                    .addCommands(
                            RandomQuoteCommand.getCommandData(),
                            RandomNameCommand.getCommandData(),
                            EvilCommand.getCommandData()
                    )
                    .queue();

            if (!guildId2.equals(guildId1)) {
                jda.getGuildById(guildId2)
                        .updateCommands()
                        .addCommands(
                                RandomQuoteCommand.getCommandData(),
                                RandomNameCommand.getCommandData(),
                                EvilCommand.getCommandData()
                        )
                        .queue();
            }

            System.out.println("Commands registered successfully.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

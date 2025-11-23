import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import java.util.EnumSet;
import java.util.Scanner;

public class Bot {

    public static void main(String[] args) {

        String token = "TOKEN HERE";
        long channelId = 1436296079636434944L;

        try {
            JDA jda = JDABuilder.createLight(
                            token,
                            EnumSet.of(
                                    GatewayIntent.GUILD_MESSAGES,
                                    GatewayIntent.MESSAGE_CONTENT,
                                    GatewayIntent.GUILD_MEMBERS,
                                    GatewayIntent.DIRECT_MESSAGES,
                                    GatewayIntent.GUILD_VOICE_STATES
                            )
                    )
                    .addEventListeners(
                            new RandomQuoteCommand(),
                            new RandomNameCommand(),
                            //new EvilCommand(),
                            new MessageListener(),
                            new UploadQuoteCommand()
                    )
                    .setActivity(Activity.playing("Eating cucumbers"))
                    .build();

            jda.awaitReady();

            jda.updateCommands()
                    .addCommands(
                            RandomQuoteCommand.getCommandData(),
                            RandomNameCommand.getCommandData(),
                            UploadQuoteCommand.getCommandData()
                            //EvilCommand.getCommandData()
                    )
                    .queue();

            long guildId = 1420362494282825771L;
            jda.getGuildById(guildId)
                    .updateCommands()
                    .addCommands(
                            RandomQuoteCommand.getCommandData(),
                            RandomNameCommand.getCommandData(),
                            UploadQuoteCommand.getCommandData()
                    )
                    .queue();



            WaterDrops rms = new WaterDrops(jda, 1418570459188039754L, "Waterdrop", "Fish", 20, 150);
            WaterDrops rms2 = new WaterDrops(jda, 1437423417576915024L, "Piece of Disc", "July", 20, 250);
            rms.start();
            rms2.start();

            Scanner scanner = new Scanner(System.in);
            TextChannel channel = jda.getTextChannelById(channelId);

            if (channel == null) {
                System.out.println("Channel not found! Check the ID.");
                return;
            }

            System.out.println("Bot is ready! Type messages below to send as the bot:");

            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Shutting down...");
                    jda.shutdown();
                    break;
                }
                channel.sendMessage(input).queue();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

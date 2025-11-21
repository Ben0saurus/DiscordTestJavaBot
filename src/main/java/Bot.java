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
                            new EvilCommand(),
                            new MessageListener()
                            //new SQDetector()
                    )
                    .setActivity(Activity.playing("Eating cucumbers"))
                    .build();

            jda.awaitReady();


            WaterDrops rms = new WaterDrops(jda, 1418570459188039754L, "Waterdrop", "Fish", 40, 300);
            WaterDrops rms2 = new WaterDrops(jda, 1437423417576915024L, "Piece of Disc", "July", 67, 670);
            rms.start();
            rms2.start();


            Scanner scanner = new Scanner(System.in);
            String channelId = "1436296079636434944";
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

            jda.updateCommands()
                    .addCommands(
                            RandomQuoteCommand.getCommandData(),
                            RandomNameCommand.getCommandData(),
                            EvilCommand.getCommandData()
                    )
                    .queue();

            System.out.println("Commands registered successfully.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

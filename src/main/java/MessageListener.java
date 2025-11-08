import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.Route;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MessageListener extends ListenerAdapter {

    private final String logFile = "messages.txt";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String sender = event.getAuthor().getName();
        String message = event.getMessage().getContentRaw();
        String logEntry = sender + ": " + message;

        if (message.contains("crazy")) {

            event.getChannel().sendMessage("Crazy ? I was crazy once").queue();
            event.getChannel().sendMessage("They licked me in a room").queue();
            event.getChannel().sendMessage("A rubber room").queue();
            event.getChannel().sendMessage("A rubber room of Rajs").queue();
            event.getChannel().sendMessage("Rajs made me crazy").queue();

        }

        /*if (message.contains("67") || message.contains("6 7")) {

            event.getChannel().sendMessage("SAU BAY").queue();

        } */


        System.out.println(logEntry);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tekno = "786030406394576906";
        String ben = "756977806134607942";
        String fish = "600934351542157312";
        String july = "1043019784461230190";
        String sto = "935972754715463730";


        //if (event.getAuthor().getId().equals(tekno) || event.getAuthor().getId().equals(ben)) {         //Ben Reactions
        //    event.getMessage().addReaction(Emoji.fromUnicode("🥒")).queue();
        //}
        if (event.getAuthor().getId().equals(fish)) {                //Fish Reactions
            event.getMessage().addReaction(Emoji.fromUnicode("🐟")).queue();
        }
        //if (event.getAuthor().getId().equals(july)) {                 //July Reactions
        //    event.getMessage().addReaction(Emoji.fromUnicode("📆")).queue();
        //}


        if (event.getAuthor().getId().equals(sto)) {

            StringBuilder sb = new StringBuilder(message);
            sb.reverse();

            String newMessage = sb.toString();
            event.getChannel().sendMessage(newMessage).queue();

        }


    }
}

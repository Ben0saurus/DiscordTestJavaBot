import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.Route;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    private final String logFile = "messages.txt";
    public int messageCount = 0;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String sender = event.getAuthor().getName();
        String message = event.getMessage().getContentRaw();
        String logEntry = sender + ": " + message;

        messageCount++;


        String[] gif = {"https://tenor.com/view/cat-cucumber-animation-crazy-scared-gif-8386930629386510786", "https://cdn.discordapp.com/attachments/1389793645552734220/1432117799483867196/attachment.gif", "https://tenor.com/view/jarvis-i-saw-something-slightly-shocking-reaction-gif-13164511326693836157", "https://tenor.com/view/man-chainsaw-chainsawman-csm-what-gif-523642207999463732", "https://tenor.com/view/how-it-feels-when-you-meme-nanami-jjk-gif-10683277372535996893", "https://tenor.com/view/wtf-what-what-i-just-read-gif-20766315", "https://tenor.com/view/dance-gif-7383935663040626928", "https://tenor.com/view/yoda-master-yoda-jedi-master-yoda-shut-up-yoda-trash-gif-11895378868293021730", "https://tenor.com/view/cat-thousand-yard-stare-thousand-yard-stare-cat-ptsd-cat-soldier-cat-war-gif-9110270742088459827", "https://cdn.discordapp.com/attachments/1389793645552734220/1432817478211342366/attachment.gif", "https://tenor.com/view/type-emoji-meme-pc-computer-gif-22586965"};

        if (messageCount == 88) {
            Message lastMessage = event.getMessage();
            lastMessage.reply("Elon Musk x Trump").queue(reply -> {
                reply.delete().queue();
            });
        }

        if (message.contains("fanfic") || message.contains("yaoi")) {
            File file = new File("fanfic/fanfic.png");

            if (file.exists()) {
                event.getChannel().sendFiles(FileUpload.fromData(file)).queue();
            } else {
                event.getChannel().sendMessage("Image not found").queue();
            }
        }



        if (messageCount == 67) {

            Random rand = new Random();

            String randomGIF = gif[rand.nextInt(gif.length)];
            System.out.println("Random GIF: " + randomGIF);
            event.getChannel().sendMessage(randomGIF).queue();

            messageCount = 0;
        }


        if (event.getAuthor().getId().equals("1057059162208604230")) {

            Random rand2 = new Random();
            if (rand2.nextInt(1, 20) == 20) {
                event.getMessage().reply("you're fr*nch bro").queue();
            }

        }


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

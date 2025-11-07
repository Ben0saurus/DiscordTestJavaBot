import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String sender = event.getAuthor().getName();
        String message = event.getMessage().getContentRaw();
        String tekno = "786030406394576906";

        System.out.println(sender + ": " + message);

        if (event.getAuthor().getId().equals(tekno)) {
            event.getChannel().sendMessage("Shut up Tekno").queue();
        }

    }
}

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SQDetector extends ListenerAdapter {

    private final Random random = new Random();

    // Funny responses
    private final List<String> responses = Arrays.asList(
            "What the SQ.",
            "Stop yelling bro.",
            "stfu",
            "Breathe. It’s going to be fine.",
            "Such drama, much wow."
    );

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Member member = event.getMember();
        String name = "SQ" + "\uD83E\uDD52";

        String msg = event.getMessage().getContentRaw();

        boolean isAllCaps = msg.length() > 4 && msg.equals(msg.toUpperCase());

        if (isAllCaps) {
            String reply = responses.get(random.nextInt(responses.size()));
            event.getChannel().sendMessage(reply).queue();
            event.getGuild().modifyNickname(member, name).queue();
        }
    }
}

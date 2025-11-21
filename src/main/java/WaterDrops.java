import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WaterDrops extends ListenerAdapter {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Random random = new Random();
    private final JDA jda;
    private final long channelId;
    private final WaterDropStorage storage = new WaterDropStorage();

    private String currentWord = null;
    private boolean activeDrop = false;

    private final String name;
    private final String user;
    private final int chance2;
    private final int chance1;

    private final File cardsFolder = new File("cards");

    private final String[] wordList2 = {
            "dojulymeee_12", "esoptro", "nemoeries", "ollay__", "oteknova",
            "samguns.999", "sto_machi"
    };

    public WaterDrops(JDA jda, long channelId, String name, String user, int chance1, int chance2) {
        this.jda = jda;
        this.channelId = channelId;
        this.name = name;
        this.user = user;
        this.chance2 = chance2;
        this.chance1 = chance1;
        jda.addEventListener(this);
    }

    public void start() {
        scheduleNextDrop();
    }

    private void scheduleNextDrop() {

        long delay = chance1 + random.nextInt(chance2);

        scheduler.schedule(() -> {

            if (activeDrop) {
                scheduleNextDrop();
                return;
            }

            TextChannel channel = jda.getTextChannelById(channelId);
            if (channel == null) {
                scheduleNextDrop();
                return;
            }

            currentWord = wordList2[random.nextInt(wordList2.length)];

            File[] files = cardsFolder.listFiles((dir, name) ->
                    name.matches(".*\\.(png|jpg|jpeg|gif)$")
            );

            boolean found = false;

            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains(currentWord)) {

                        channel.sendMessage(user + " has dropped a " + name +
                                        "! Type **" + currentWord + "** first to get it!")
                                .addFiles(FileUpload.fromData(file))
                                .queue();

                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                channel.sendMessage(user + " has dropped a " + name +
                        "! Type **" + currentWord + "** first to get it!").queue();
            }

            activeDrop = true;

            scheduler.schedule(() -> {
                if (activeDrop) {
                    activeDrop = false;
                    currentWord = null;
                    channel.sendMessage("No one typed the word in time. Better luck next drop!").queue();
                }
            }, 120, TimeUnit.SECONDS);

            scheduleNextDrop();

        }, delay, TimeUnit.MINUTES);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;
        if (event.getChannel().getIdLong() != channelId) return;
        if (!activeDrop || currentWord == null) return;

        String msg = event.getMessage().getContentRaw().trim();

        if (msg.equalsIgnoreCase(currentWord.trim())) {

            storage.addWord(event.getAuthor().getId(), currentWord);

            event.getChannel().sendMessage(event.getAuthor().getAsMention() +
                    " was the **first** to catch the "+ name + "! They now own `" + currentWord + "`.").queue();

            activeDrop = false;
            currentWord = null;
        }
    }
}

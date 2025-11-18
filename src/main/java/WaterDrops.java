import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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
    private boolean isExpired = true;

    public WaterDrops(JDA jda, long channelId) {
        this.jda = jda;
        this.channelId = channelId;

        jda.addEventListener(this);
    }

    public void start() {
        scheduleNextDrop();
    }

    private final String[] wordList = {
            "benosaurus9", "ollay__", "oteknova", "sto_machi", "sleepy.j.c", "geumyume", "syrinxic",
            "xzhiyun", "dojulymeee_12", "my_dad_left_4_years_ago", "termitenus", "esoptro",
            "alittleaxolotl", "hun3ydew", "xiqp_qp", "staffsargent_cornflakes", "micah1._.",
            "nimbasa.", "imrubberduckyy", "nemoeries", "chihscake", "venzuus", "arroz.con.1eche",
            "lasvegastv", "hoot312", "awesomejett44", "korb.ii", "chazzylive", "lynzcan0n_",
            "samguns.999", "knaazs", "duoblesuicide", "aprilsthings", "queenieizzy", "malikalmalika",
            "shoonlw", "sharky", "microplastic", "oil spill", "fisch", "pacific", "atlantic", "indian",
            "southern", "arctic", "salmon", "sushi", "chips", "crab", "catfish", "seapickle", "blobfish",
            "french fish"
    };


    private void scheduleNextDrop() {

        long delay = 40 + random.nextInt(400);

        scheduler.schedule(() -> {

            // Do not override an active drop
            if (activeDrop) {
                scheduleNextDrop();
                return;
            }

            TextChannel channel = jda.getTextChannelById(channelId);
            if (channel == null) {
                scheduleNextDrop();
                return;
            }

            // Pick the new word at drop time
            currentWord = wordList[random.nextInt(wordList.length)];

            channel.sendMessage("Fish has dropped a Waterdrop! Type **" + currentWord +
                    "** first to get it!").queue();

            activeDrop = true;

            // Auto-expire after 120 seconds
            scheduler.schedule(() -> {
                if (activeDrop) {
                    activeDrop = false;
                    jda.getTextChannelById(channelId).sendMessage("Noone typed the word " + currentWord + " quickly enough. Better luck next time!").queue();
                    currentWord = null;
                }
            }, 120, TimeUnit.SECONDS);

            // Schedule the next drop
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
                            " was the **first** to catch the Waterdrop! They now own `" + currentWord + "`.")
                    .queue();

            activeDrop = false;
            currentWord = null;
        }

    }
}

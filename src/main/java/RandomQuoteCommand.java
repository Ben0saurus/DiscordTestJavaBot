import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Random;

public class RandomQuoteCommand extends ListenerAdapter {

    public static CommandData getCommandData() {
        return Commands.slash("randomquote", "Send a random quote from the bot's folder");
    }

    private final File quoteFolder = new File("images");
    private final Random random = new Random();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("randomquote")) return;

        File[] files = quoteFolder.listFiles((dir, name) ->
                name.matches(".*\\.(png|jpg|jpeg|gif|txt)$")
        );

        if (files == null || files.length == 0) {
            event.reply("No quotes found in the folder!").setEphemeral(true).queue();
            return;
        }

        File randomFile = files[random.nextInt(files.length)];

        if (randomFile.getName().endsWith(".txt")) {
            try {
                String content = java.nio.file.Files.readString(randomFile.toPath());
                System.out.println("The quote " + randomFile.getName() + " has been sent at the request of " + event.getUser().getName());
                event.reply(content).queue();
            } catch (Exception e) {
                event.reply("Failed to read quote file.").setEphemeral(true).queue();
                e.printStackTrace();
            }
        } else {
            event.deferReply().queue(hook -> hook.sendFiles(FileUpload.fromData(randomFile)).queue());
            System.out.println("The quote " + randomFile.getName() + " has been sent at the request of " + event.getUser().getName());
        }
    }
}

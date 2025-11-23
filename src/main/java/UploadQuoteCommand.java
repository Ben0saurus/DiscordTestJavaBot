import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.io.File;

public class UploadQuoteCommand extends ListenerAdapter {

    public static CommandData getCommandData() {
        return Commands.slash("add-quote", "Upload a quote image")
                .addOption(OptionType.ATTACHMENT, "image", "The quote image", true);
    }


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("add-quote"))
            return;

        Attachment image = event.getOption("image").getAsAttachment();
        if (image == null || !image.isImage()) {
            event.reply("Please upload a valid image.").setEphemeral(true).queue();
            return;
        }

        File folder = new File("quotes");
        folder.mkdirs();
        File output = new File(folder, System.currentTimeMillis() + "_" + image.getFileName());

        event.deferReply(true).queue();

        image.getProxy().downloadToFile(output).whenComplete((file, error) -> {
            if (error != null) {
                error.printStackTrace();
                event.getHook().sendMessage("Failed to save the quote image.").queue();
            } else {
                event.getHook().sendMessage("Quote image saved successfully!").queue();
            }
        });
    }
}

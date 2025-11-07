import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class EvilCommand extends ListenerAdapter {

    public static CommandData getCommandData() {
        return Commands.slash("evil", "Become evil");
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("evil")) return;

        if (event.getUser().getId().equals("1008861928375996567")) {
            Member bot = event.getGuild().getMemberById("1435704832382992561");
            var role = event.getGuild().getRoleById("1425837616305016842");

            System.out.println("Ollie executed the evil command");
            event.getChannel().sendMessage("At your service, Master Ollie. What evil things do you want me to do today").queue();
            event.getGuild().modifyNickname(bot, "Evil Bot").queue();
            event.getGuild().addRoleToMember(bot, role).queue();
        } else {
            Member user = event.getMember();
            var role = event.getGuild().getRoleById("1425837616305016842");
            Member bot = event.getGuild().getMemberById("1435704832382992561");

            System.out.println(user.getUser().getName() + " has tried to execute the evil command but failed!");
            event.getGuild().modifyNickname(bot, "Nice Bot").queue();
            event.getChannel().sendMessage(user.getAsMention() + " you're not Ollie. Go away!").queue();
            event.getGuild().removeRoleFromMember(bot, role).queue();
        }
    }
}

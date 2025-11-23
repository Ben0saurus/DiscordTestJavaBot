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


        // String[] gif = {"https://tenor.com/view/cat-cucumber-animation-crazy-scared-gif-8386930629386510786", "https://cdn.discordapp.com/attachments/1389793645552734220/1432117799483867196/attachment.gif", "https://tenor.com/view/jarvis-i-saw-something-slightly-shocking-reaction-gif-13164511326693836157", "https://tenor.com/view/man-chainsaw-chainsawman-csm-what-gif-523642207999463732", "https://tenor.com/view/how-it-feels-when-you-meme-nanami-jjk-gif-10683277372535996893", "https://tenor.com/view/wtf-what-what-i-just-read-gif-20766315", "https://tenor.com/view/dance-gif-7383935663040626928", "https://tenor.com/view/yoda-master-yoda-jedi-master-yoda-shut-up-yoda-trash-gif-11895378868293021730", "https://tenor.com/view/cat-thousand-yard-stare-thousand-yard-stare-cat-ptsd-cat-soldier-cat-war-gif-9110270742088459827", "https://cdn.discordapp.com/attachments/1389793645552734220/1432817478211342366/attachment.gif", "https://tenor.com/view/type-emoji-meme-pc-computer-gif-22586965", "https://images-ext-1.discordapp.net/external/EAVPsENS7yefAmONITogTU-eUGdkEEuaPQB5V7ruxu0/https/media.tenor.com/Zr0pYZeiVu4AAAPo/monster-versus.mp4", "https://images-ext-1.discordapp.net/external/518xJu-Py85kMEPMOvdpxV8IFnFhPF2nOsXtkrkelmg/https/media.tenor.com/dhg-ahte5ToAAAPo/mrnoob-can-we-skip-this-question-please.mp4", "https://images-ext-1.discordapp.net/external/4yXaXsvPVREUk9yKUhh7K7PKTPNafNHWJjYbP85rbuM/https/media.tenor.com/rKoa2hebhEgAAAPo/minions-tuesday.mp4", "https://images-ext-1.discordapp.net/external/KpVPJEh81QDv-v2xlrA2dMVFAafDwPsc5nxTTjCcGnU/https/media.tenor.com/neYQmYYo2gkAAAPo/shark-memoji-shark.mp4", "https://images-ext-1.discordapp.net/external/X5oB05i66wdqukM-L26yBeecDH5UjT28EhGlt04kx1g/https/media.tenor.com/oweDGlwh7ysAAAPo/kiss-anakin.mp4", "https://images-ext-1.discordapp.net/external/n0x-2avQpMbBrMEn1tFQYyMyhmnypqilDTbYpreDmYI/https/media.tenor.com/Tk0eSfL7RqoAAAPo/cat-cute-cat.mp4", "https://images-ext-1.discordapp.net/external/EIyAE3m7OQgpVFRbiFxTnFnrD3mW2b21XwZ_yXLd7co/https/media.tenor.com/neBwwqhAfesAAAPo/angry-birds-drooling-emoji.mp4", "https://images-ext-1.discordapp.net/external/_V6Z013eZJ-xnhKJhNyHf45kg8PbGbrTz-f8JZHbzT0/https/media.tenor.com/L8Bl_o6cUbMAAAPo/sure-grandpa-let%2527s-get-you-to-bed.mp4", "https://images-ext-1.discordapp.net/external/rn7yJD3MtirzzkXpFgJ8Do0acZGqUzr0vAuZKXgzcrs/https/media.tenor.com/4dQSB_Ir0bUAAAPo/frosty-frostypyromancer.mp4", "https://images-ext-1.discordapp.net/external/8g2cnl0yyOOJ2OzboKWcM5duE7SFvSmN7_8og9q0r8c/https/media.tenor.com/XohA9_u5KPEAAAPo/finding-nemo-shake.mp4", "https://images-ext-1.discordapp.net/external/uAPeufK-9QhvH3raLS6c5wLOt7YWyGV59Jh9hY4rDrY/https/media.tenor.com/lIw3wAhO5a8AAAPo/beno-benoproc.mp4", "https://images-ext-1.discordapp.net/external/rydwDxksmOcAC2Np6jXSl9cY_Pz-h4LAyCJY8GiXr7E/https/media.tenor.com/r-OOq3O_je4AAAPo/beno-beno-moment.mp4", "https://images-ext-1.discordapp.net/external/hoNejeboSbOFrFmvC4P9yKDaACdVzS9ChDMvgABFULE/https/media.tenor.com/5LVCTVAQlnQAAAPo/freaky-sonic.mp4", "https://images-ext-1.discordapp.net/external/t3kN0cRcBAfcCN_HCAeyxcsV_xbzOilgFTu2tImmSlY/https/media.tenor.com/owsPz6f26FcAAAPo/happy-cat-silly-cat.mp4", "https://media.discordapp.net/attachments/975211085651136612/1409352850802348032/togif.gif?ex=69173260&is=6915e0e0&hm=ad399ca099191ae45a4e9f6d9adf42b6348e295e5614a98d26e7f17c9ba9339d&=&width=939&height=960", "https://images-ext-1.discordapp.net/external/Bztz_jElu47637Yk3OV2U6uSyQci03Bh8P35cAfKTTg/https/media.tenor.com/GcxsA_0mgbUAAAPo/jinx-cat.mp4", "https://media.discordapp.net/attachments/1112088878543806505/1410525611327361075/caption2.gif?ex=6916d958&is=691587d8&hm=8edf8161a19b566d344554e7d107a93cf065060169f1928a0099791c0a259311&=&width=400&height=299", "https://media.discordapp.net/attachments/340244322933014549/1233781338234949712/real-1.gif?ex=69173769&is=6915e5e9&hm=a02a39b30121cb16f2b09214008a461e8eb61b0f53606a8d05b474be366d8bfa&=", "https://images-ext-1.discordapp.net/external/I05Qn6Ti0d1v3-v4kH0WlFcan7R0Cg2woGTZec0P4jg/https/media.tenor.com/xDnxHxFBy9oAAAPo/jar-jar-binks-gangnam-style.mp4", "https://media.discordapp.net/attachments/1304905379209150525/1407024982487207997/ezgif.com-animated-gif-maker.gif?ex=6916a361&is=691551e1&hm=6dc47db4ea1365fba97fa304c90ab9b309c2b4f08479d3f39e195e9a93979c1a&=&width=1019&height=960", "https://media.discordapp.net/attachments/1304905379209150525/1420492250592448542/petpet.gif?ex=6916da40&is=691588c0&hm=35851c8a85a9bfc9a194afafdf387361e7e78a799ebffc8bafffdc7aedb1bd05&=", "https://images-ext-1.discordapp.net/external/vDlF1u53rZEWymGrGfLleoa8DPevwslLZqVmX0Q8_4Q/https/media.tenor.com/LIp9JxamyBkAAAPo/kys-keep-yourself-safe.mp4", "https://images-ext-1.discordapp.net/external/PzTEmN58ULDdmAHDsT_SSbLJA9gcqYlo2rOLzfPKYMU/https/media.tenor.com/Ta_BST9ZRVUAAAPo/potato-chips.mp4", "https://images-ext-1.discordapp.net/external/ooEAPIvy2VzVEBVH1K7WYthUXe215pVfqo_DowYrSuE/https/media.tenor.com/3kvIVPYgTE8AAAPo/hmph-hmph-anime.mp4", "https://images-ext-1.discordapp.net/external/2_HLe1FMW80JI7FHL4EwJB5jQz6eRhYD3DuYiqokUBI/https/media.tenor.com/Z-jTUqQotbAAAAPo/star-wars.mp4", "https://images-ext-1.discordapp.net/external/FK5NZzrXBs3oG3j2FGEQ5FW3qTZTwy5h_yH8iYKggSw/https/media.tenor.com/jNQjRgFfBxwAAAPo/gacha-brocken.mp4", "https://images-ext-1.discordapp.net/external/EJzj9XHRmwXljXZ323fHhNBn6M9ylWXC9AM2hT1Sohs/https/media.tenor.com/0cuxgwx_y-AAAAPo/staff-furry.mp4", "https://images-ext-1.discordapp.net/external/XZmLTuT5tw0IgO-B6UtkOcLzKC7YXTkoiCLDCJfdfMk/https/media.tenor.com/vsHdQkcB9Q8AAAPo/alpha-sigma.mp4", "https://images-ext-1.discordapp.net/external/73Smg8gt0mHlm_YPg5BMzn_xrI2JRuqQeiiQ_j-vhPM/https/media.tenor.com/HJ0UtxwDdjcAAAPo/workout-adventuretime.mp4", "https://images-ext-1.discordapp.net/external/_yNlnpN9wRGZI3G55ogy2mBsytP0ABOYK164-ZCmh48/https/media.tenor.com/y357BpUidNwAAAPo/jerma.mp4", "https://images-ext-1.discordapp.net/external/NRXM-2oqw38yoq8It3tdEmy13G4VZBPx8BpTAxTlRlg/https/media.tenor.com/HgfXlLyl_ZIAAAPo/eat-out-eating-out.mp4", "https://media.discordapp.net/attachments/1407581094303236117/1409306783339774083/convert.gif?ex=69170778&is=6915b5f8&hm=1c58836614e33a54a5ad8b3095d6baee3b7a1e3be73a523905ed360eeedd0717&=", "https://images-ext-1.discordapp.net/external/Wf0cfjNmrExbdSMp9COK3LcDduVhXjNRoA_tDsTqd3I/https/media.tenor.com/T1P2EghKC44AAAPo/angle-cat.mp4", "https://images-ext-1.discordapp.net/external/oZ50JolUd-EBf_Uv9ozSuzQkQ_Ec2DDwIrWlnZttHoE/https/media.tenor.com/EeEg1qmWTxAAAAPo/the-best-you-rock.mp4", "https://images-ext-1.discordapp.net/external/l6zl7V7jnucdYqmh6Dd3kOip1A8E0Rm6i2_YnXMns7o/https/media.tenor.com/B18pTtppyY4AAAPo/emoji-foot-wave-emoji.mp4", "https://images-ext-1.discordapp.net/external/rYwSM5a8zmA0NV6C9MNq2sNSdiUA-5HtHJQO6taYLLE/https/media.tenor.com/UGc3bTzWd6UAAAPo/dog-puppy.mp4", "https://images-ext-1.discordapp.net/external/nBq3C01jQC9Qml7ZBrwJZ29NjOukThCgKDyb5YlEfCM/https/media.tenor.com/T9pnd2PQMawAAAPo/hop-on-minecraft.mp4", "https://images-ext-1.discordapp.net/external/EgTzFZFfPUMETy4JqQhurcmmJ3P0Lkif9VLnDarfgw8/https/media.tenor.com/HXbwbcyG7fcAAAPo/bald-kim-dokja.mp4", "https://images-ext-1.discordapp.net/external/K_qpzfzyRlWVG-ldgZ3sDLfDIr4VMWaLwRUAgojENPI/https/media.tenor.com/vk9up-ad1RcAAAPo/mama-mo-ikemen-horse.mp4", "https://images-ext-1.discordapp.net/external/qvNWbhAq6i_smS4RTRrNN6WiY438ng0QtfyzS0KediM/https/media.tenor.com/T4fhZVu9Oj0AAAPo/mlfiresoul-among-us.mp4", "https://images-ext-1.discordapp.net/external/ADf3q_mR6JFIbUuT6O7ZTXa5JseWG2ipHKldFBRMQvo/https/media.tenor.com/08WOlxBygD4AAAPo/marflrt.mp4", "https://images-ext-1.discordapp.net/external/jfgvHv2RwxiirwZt1pYTcRPWvLlOPYIOa4kwfbCiQ7w/https/media.tenor.com/GaqslD8_l7gAAAPo/brain-dog-brian-dog.mp4", "https://images-ext-1.discordapp.net/external/HZg6jR5PEWcqGx5BeO-9SZRPJUxia4iL4tZrWoBepIY/https/media.tenor.com/WdJ9DsKJyCMAAAPo/shocked-cat-shock.mp4", "https://images-ext-1.discordapp.net/external/jMD-PDX4sT7FfSJM6BMaS9zpx08zu-SuhephvwEN6vw/https/media.tenor.com/du0dON8-YyYAAAPo/reynamaealcos.mp4", "https://images-ext-1.discordapp.net/external/8NNxHQylpSF7MDlZjx7nbdS3RNK0FsvtCyGrsqJbibM/https/media.tenor.com/-M2RHTwqMVcAAAPo/i-sleep-now-ok-i-sleep-now.mp4"};

        if (message.contains("fanfic") || message.contains("yaoi")) {
            File file = new File("fanfic/fanfic.png");

            if (file.exists()) {
                event.getChannel().sendFiles(FileUpload.fromData(file)).queue();
            } else {
                event.getChannel().sendMessage("Image not found").queue();
            }
        }

        if (message.contains("mamdani")) {

            File file = new File("zohran/img.png");
            event.getChannel().sendFiles(FileUpload.fromData(file)).queue();

        }



        /* if (messageCount == 67) {

            Random rand = new Random();

            String randomGIF = gif[rand.nextInt(gif.length)];
            System.out.println("Random GIF: " + randomGIF);
            event.getChannel().sendMessage(randomGIF).queue();

            messageCount = 0;
        } */


        if (event.getAuthor().getId().equals("1057059162208604230")) {

            Random rand2 = new Random();
            if (rand2.nextInt(1, 20) == 20) {
                event.getMessage().reply("you're fr*nch bro").queue();
            }

        }


        /* if (message.contains("crazy")) {

            event.getChannel().sendMessage("Crazy ? I was crazy once").queue();
            event.getChannel().sendMessage("They licked me in a room").queue();
            event.getChannel().sendMessage("A rubber room").queue();
            event.getChannel().sendMessage("A rubber room of Rajs").queue();
            event.getChannel().sendMessage("Rajs made me crazy").queue();

        } */

        /* if (message.contains("july")) {

            event.getMessage().reply("june*").queue();

        } */

        if (message.contains("toes")) {

            event.getMessage().reply("\uD83E\uDD24").queue();

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


        /*if (event.getAuthor().getId().equals(tekno) || event.getAuthor().getId().equals(ben)) {         //Ben Reactions
            event.getMessage().addReaction(Emoji.fromUnicode("🥒")).queue();
        }
        if (event.getAuthor().getId().equals(fish)) {                //Fish Reactions
            event.getMessage().addReaction(Emoji.fromUnicode("🐟")).queue();
        }
        if (event.getAuthor().getId().equals(july)) {                 //July Reactions
           event.getMessage().addReaction(Emoji.fromUnicode("📆")).queue();
        } */


        /*if (event.getAuthor().getId().equals(sto)) {

            StringBuilder sb = new StringBuilder(message);
            sb.reverse();

            String newMessage = sb.toString();
            event.getChannel().sendMessage(newMessage).queue();

        } */


    }
}

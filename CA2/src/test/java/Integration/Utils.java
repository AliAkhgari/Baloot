package Integration;

import application.Baloot;
import entities.Commodity;
import entities.User;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utils {

    public static String getRandomUserId(Baloot baloot) {
        Random random = new Random();
        int randomIndex = random.nextInt(baloot.getUsers().size());

        return baloot.getUsers().get(randomIndex).getUsername();
    }

    public static int getRandomCommodityId(Baloot baloot) {
        Random random = new Random();
        int randomIndex = random.nextInt(baloot.getUsers().size());

        return baloot.getCommodities().get(randomIndex).getId();
    }

    public static String getNotExistentUserId(Baloot baloot) {
        Set<String> existingUserIds = new HashSet<>();
        for (User user : baloot.getUsers())
            existingUserIds.add(user.getUsername());

        Random random = new Random();
        String userId = String.valueOf(random.nextInt(100000));
        while (existingUserIds.contains(userId))
            userId = String.valueOf(random.nextInt(100000));

        return userId;
    }

    public static int getNotExistentCommodityId(Baloot baloot) {
        Set<Integer> existingCommodityIds = new HashSet<>();
        for (Commodity commodity : baloot.getCommodities())
            existingCommodityIds.add(commodity.getId());

        Random random = new Random();
        int commodityId = random.nextInt(100000);
        while (existingCommodityIds.contains(commodityId))
            commodityId = random.nextInt(100000);

        return commodityId;
    }
}

package services;

import entities.UserRating;
import org.springframework.stereotype.Service;
import repositories.UserRatingRepository;

@Service
public class UserRatingService {
    private final UserRatingRepository userRatingRepository;

    public UserRatingService(UserRatingRepository userRatingRepository) {
        this.userRatingRepository = userRatingRepository;
    }

    public void addRate(UserRating userRating) {
        userRatingRepository.save(userRating);
    }

    public Float getAverageScoreByCommodityId(String commodityId) {
        return userRatingRepository.getAverageScoreByCommodityId(commodityId);
    }

    public long getNumberOfRatings(String commodityId) {
        return userRatingRepository.getTableLength(commodityId);
    }
}

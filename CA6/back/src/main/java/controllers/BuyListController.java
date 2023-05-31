package controllers;

import DTO.BuyListItem;
import application.Baloot;
import entities.Commodity;
import entities.Discount;
import entities.User;
import exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.DiscountService;

import java.util.ArrayList;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BuyListController {
    // TODO: make it get and with username in url
    private DiscountService discountService;

    public BuyListController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping(value = "/buy-list")
    public ResponseEntity<ArrayList<BuyListItem>> getBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> buyListItems = new ArrayList<>();

        try {
            Map<String, Integer> buyList = Baloot.getInstance().getUserBuyList(username);
            for (Map.Entry<String, Integer> entry : buyList.entrySet()) {
                Commodity commodity = Baloot.getInstance().getCommodityById(entry.getKey());
                int quantity = entry.getValue();

                BuyListItem buyListItem = new BuyListItem(commodity, quantity);
                buyListItems.add(buyListItem);
            }
            return new ResponseEntity<>(buyListItems, HttpStatus.OK);

            // TODO: delete exception
        } catch (NotExistentUser | NotExistentCommodity ignored) {
            return new ResponseEntity<>(buyListItems, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/purchased-list")
    public ResponseEntity<ArrayList<BuyListItem>> getPurchasedList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> purchasedListItems = new ArrayList<>();

        try {
            Map<String, Integer> purchasedList = Baloot.getInstance().getUserPurchasedList(username);
            for (Map.Entry<String, Integer> entry : purchasedList.entrySet()) {
                Commodity commodity = Baloot.getInstance().getCommodityById(entry.getKey());
                int quantity = entry.getValue();

                BuyListItem buyListItem = new BuyListItem(commodity, quantity);
                purchasedListItems.add(buyListItem);
            }
            return new ResponseEntity<>(purchasedListItems, HttpStatus.OK);

            // TODO: delete exception
        } catch (NotExistentUser | NotExistentCommodity ignored) {
            return new ResponseEntity<>(purchasedListItems, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/add")
    public ResponseEntity<String> addToBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            Baloot.getInstance().addCommodityToUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity added to buy list successfully!", HttpStatus.OK);
            // TODO: delete exception
        } catch (NotExistentUser | NotExistentCommodity e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/buy-list/remove")
    public ResponseEntity<String> removeFromBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            Baloot.getInstance().removeCommodityFromUserBuyList(username, input.get("id"));
            return new ResponseEntity<>("commodity added to buy list successfully!", HttpStatus.OK);
            // TODO: delete exceptions
        } catch (MissingUserId | MissingCommodityId | NotExistentUser | NotExistentCommodity |
                 CommodityIsNotInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/purchase")
    public ResponseEntity<String> purchaseBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            User user = Baloot.getInstance().getUserById(username);
            Baloot.getInstance().withdrawPayableAmount(user);

            return new ResponseEntity<>("buy list purchased successfully!", HttpStatus.OK);
            //TODO: remove not necessary exceptions
        } catch (InsufficientCredit | NotExistentUser | NotInStock e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/buy-list/discount/{id}")
    public ResponseEntity<Object> applyDiscount(@RequestBody Map<String, String> input, @PathVariable String id) {
        try {
            Discount discount = discountService.getDiscountById(id);
            User user = Baloot.getInstance().getUserById(input.get("username"));

            Baloot.getInstance().checkDiscountExpiration(user, discount.getDiscountCode());
            user.setCurrentDiscount(discount);

            return new ResponseEntity<>(discount, HttpStatus.OK);
        } catch (NotExistentDiscount | NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ExpiredDiscount e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

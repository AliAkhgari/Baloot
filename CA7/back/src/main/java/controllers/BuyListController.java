package controllers;

import DTO.BuyListItem;
import entities.*;
import exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BuyListController {
    // TODO: make it get and with username in url
    private final DiscountService discountService;
    private final BuyListService buyListService;
    private final UserService userService;
    private final CommodityService commodityService;
    private final PurchasedListService purchasedListService;
    private final UsedDiscountService usedDiscountService;

    public BuyListController(DiscountService discountService, BuyListService buyListService, UserService userService, CommodityService commodityService, PurchasedListService purchasedListService, UsedDiscountService usedDiscountService) {
        this.discountService = discountService;
        this.buyListService = buyListService;
        this.userService = userService;
        this.commodityService = commodityService;
        this.purchasedListService = purchasedListService;
        this.usedDiscountService = usedDiscountService;
    }

    @PostMapping(value = "/buy-list")
    public ResponseEntity<ArrayList<BuyListItem>> getBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> buyListItems = new ArrayList<>();

        List<BuyList> buyLists = null;
        try {
            buyLists = buyListService.getUserItems(userService.getUserById(username));
        } catch (NotExistentUser e) {
            throw new RuntimeException(e);
        }

        for (BuyList item : buyLists) {
            Commodity commodity = item.getCommodity();
            int quantity = item.getQuantity();

            BuyListItem buyListItem = new BuyListItem(commodity, quantity);
            buyListItems.add(buyListItem);
        }
        return new ResponseEntity<>(buyListItems, HttpStatus.OK);
    }

    @PostMapping(value = "/buy-list/add")
    public ResponseEntity<String> addToBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            buyListService.addItem(commodityService.getCommodityById(input.get("id")), userService.getUserById(username));

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
            buyListService.removeItem(input.get("id"), username);
            return new ResponseEntity<>("commodity removed from buy list successfully!", HttpStatus.OK);
            // TODO: delete exceptions
        } catch (CommodityIsNotInBuyList e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/buy-list/purchase")
    public ResponseEntity<String> purchaseBuyList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        try {
            User user = userService.getUserById(username);

            List<BuyList> buyListItems = buyListService.getUserItems(user);

            float amount = buyListService.getCurrentBuyListPrice(username);
            float discount_amount = 0;
            if (userService.getCurrentDiscount() != null)
                discount_amount = (userService.getCurrentDiscount().getDiscount() / 100) * amount;

            user.checkCredit(amount - discount_amount);

            for (BuyList buyListItem : buyListItems) {
                buyListItem.getCommodity().isStockSufficient(-buyListItem.getQuantity());
            }

            for (BuyList buyListItem : buyListItems) {
                PurchasedList purchasedListItem = new PurchasedList(buyListItem.getCommodity(), user);
                buyListItem.getCommodity().updateInStock(-buyListItem.getQuantity());

                purchasedListItem.setQuantity(buyListItem.getQuantity());

                purchasedListService.addItem(purchasedListItem);
                buyListService.deleteItem(buyListItem.getId());
            }

            user.withdrawCredit(amount - discount_amount);
            userService.saveUser(user);

            if (userService.getCurrentDiscount() != null) {
                usedDiscountService.addDiscountForUser(userService.getCurrentDiscount(), user);
                userService.setCurrentDiscount(null);
            }

            return new ResponseEntity<>("buy list purchased successfully!", HttpStatus.OK);
            //TODO: remove not necessary exceptions
        } catch (InsufficientCredit | NotExistentUser | NotInStock e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ExpiredDiscount e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/buy-list/discount/{id}")
    public ResponseEntity<Object> applyDiscount(@RequestBody Map<String, String> input, @PathVariable String id) {
        try {
            Discount discount = discountService.getDiscountById(id);
            User user = userService.getUserById(input.get("username"));

            usedDiscountService.checkDiscountExpiration(discount.getDiscountCode(), user.getUsername());
            userService.setCurrentDiscount(discount);

            return new ResponseEntity<>(discount, HttpStatus.OK);
        } catch (NotExistentDiscount | NotExistentUser e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ExpiredDiscount e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/purchased-list")
    public ResponseEntity<ArrayList<BuyListItem>> getPurchasedList(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        ArrayList<BuyListItem> purchasedListItems = new ArrayList<>();

        try {
            List<PurchasedList> purchasedLists = purchasedListService.getUserItems(userService.getUserById(username));
            for (PurchasedList item : purchasedLists) {
                Commodity commodity = item.getCommodity();
                int quantity = item.getQuantity();

                BuyListItem buyListItem = new BuyListItem(commodity, quantity);
                purchasedListItems.add(buyListItem);
            }
            return new ResponseEntity<>(purchasedListItems, HttpStatus.OK);

            // TODO: delete exception
        } catch (NotExistentUser ignored) {
            return new ResponseEntity<>(purchasedListItems, HttpStatus.NOT_FOUND);
        }
    }
}

import {getComments, getCommodities, getCommodityById, rateCommodity} from "./commodities.js";
import {getUserById} from "./user.js";
import {addToBuyList, getBuyList, getPurchasedList, purchaseBuyList} from "./buyList.js";
import {getProviderById} from "./provider.js";

// getUserById("amir")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error.response.status));


// addUserCredit("amir", 10000)
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));


// getCommodities()
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// getCommodityById("1")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// rateCommodity("1", "10")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// getBuyList("amir")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// addToBuyList("amir", 10)
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));


// addToBuyList("amir", 13)
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// getProviderById("2")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// addToBuyList("amir", 2)
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// purchaseBuyList("amir")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

// getPurchasedList("amir")
//     .then(response => console.log(response.data))
//     .catch(error => console.error(error));

getComments("1")
    .then(response => console.log(response.data))
    .catch(error => console.error(error));
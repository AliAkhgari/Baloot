import React, {useEffect, useState} from "react";
import "../styles/user.css"
import "../styles/footer.css"
import Header from "./header.js";
import {addUserCredit, getUserById} from "../api/user.js";
import UserIcon from "../assets/images/icons/user.png";
import MailIcon from "../assets/images/icons/mail.png";
import CalendarIcon from "../assets/images/icons/calendar.png";
import LocationIcon from "../assets/images/icons/location.png";
import ShopIcon from "../assets/images/icons/shop.png";
import HistoryIcon from "../assets/images/icons/history.png"
import {addToBuyList, getBuyList, getPurchasedList, purchaseBuyList, removeFromBuyList} from "../api/buyList.js";

function User() {
    const username = sessionStorage.getItem('username');
    const [user, setUser] = useState({});
    const [buyList, setBuyList] = useState({});
    const [purchaseList, setPurchaseList] = useState({});

    async function fetchUser() {
        try {
            const response = await getUserById(username);
            setUser(response.data);
            // console.warn("fetch")
        } catch (error) {
        }
    }

    async function fetchBuyList() {
        try {
            const response = await getBuyList(username);
            setBuyList(response.data);
            console.warn("fetch buy list")
            // console.log(response)
        } catch (error) {
        }
    }

    async function fetchPurchasedList() {
        try {
            const response = await getPurchasedList(username);
            setPurchaseList(response.data);
            console.warn("fetch purchase list")
            // console.log(response)
        } catch (error) {
        }
    }

    useEffect(() => {
        fetchUser()
            .then(() => {
            });
        fetchBuyList()
            .then(() => {

            });
        fetchPurchasedList()
            .then(() => {

            });
    }, []);

    function UserCredits(props) {
        const {user, fetchUser} = props;
        const [amount, setAmount] = useState('');

        const handleAmountChange = (event) => {
            setAmount(event.target.value);
        };

        const handleSubmit = async (event) => {
            event.preventDefault();
            await addUserCredit(user.username, parseFloat(amount));
            setAmount("");
            fetchUser();
        };

        return (<>
                <div className="credits">
                    <table className="user-info">
                        <tbody>
                        <tr>
                            <td><img src={UserIcon} alt="user"/></td>
                            <td><span>{user.username}</span></td>
                        </tr>
                        </tbody>

                        <tbody>
                        <tr>
                            <td><img src={MailIcon} alt="mail"/></td>
                            <td><span>{user.email}</span></td>
                        </tr>
                        </tbody>
                        <tbody>
                        <tr>
                            <td><img src={CalendarIcon} alt="calendar"/></td>
                            <td><span>{user.birthDate}</span></td>
                        </tr>
                        </tbody>
                        <tbody>
                        <tr>
                            <td><img src={LocationIcon} alt="location"/></td>
                            <td><span>{user.address}</span></td>
                        </tr>
                        </tbody>
                    </table>

                    <div className="buy">
                        <span className="credit-value">${user.credit}</span>
                        <input value={amount} onChange={handleAmountChange}
                               placeholder="$Amount" className="amount"/>
                        <button type="submit" className="submit" onClick={handleSubmit}>Add More Credit</button>
                    </div>

                </div>
            </>
        );
    }

    const handleAddToBuyList = async (e, id) => {
        e.preventDefault();
        try {
            await addToBuyList(username, id);
            await fetchBuyList();
        } catch (error) {
            console.error(error);
        }
    };

    const handleRemoveFromBuyList = async (e, id) => {
        e.preventDefault();
        try {
            await removeFromBuyList(username, id);
            await fetchBuyList();
        } catch (error) {
            console.error(error);
        }
    };

    const EmptyTable = ({tableType}) => {
        return (
            <div id="empty-table">
                your {tableType.toLowerCase()} is empty
            </div>
        )
    }

    const handlePurchase = async (e) => {
        e.preventDefault();
        try {
            await purchaseBuyList(username);
            await fetchBuyList();
            await fetchPurchasedList();
        } catch (error) {
            // console.error(error);
        }
    };

    function commoditiesInfo(commodityList, isBuyList) {
        const commodityInfo = [];

        if (commodityList.length === 0) {
            commodityInfo.push(
                <EmptyTable tableType={isBuyList ? "cart" : "history"}/>
            )
        }

        for (const x of Object.values(commodityList)) {
            commodityInfo.push(
                <div className="td" key={x.commodity.id}>
                    <span>{x.commodity.name}</span>
                    <span>
                <img src={x.commodity.image} alt={x.commodity.name}/>
                </span>
                    <span>{x.commodity.categories.join(", ")}</span>
                    <span>{x.commodity.price}</span>
                    <span>{x.commodity.providerId}</span>
                    <span className="rate">{x.commodity.rating}</span>
                    <span className="stock">{x.commodity.inStock}</span>
                    <span className="in-cart">

                {isBuyList ? (
                    <>
                        <input
                            type="button"
                            onClick={(e) => handleRemoveFromBuyList(e, x.commodity.id)}
                            id="minus"
                            value="-"
                        />
                        <span id="num">{x.quantity}</span>
                        <input
                            type="button"
                            onClick={(e) => handleAddToBuyList(e, x.commodity.id)}
                            id="plus"
                            value="+"
                        />
                    </>
                ) : (
                    <span id="num">{x.quantity}</span>
                )}

            </span>
                </div>);
        }

        return commodityInfo;
    }

    function BuyList(props) {
        const {buyList, fetchBuyList} = props;

        const commodityInfo = commoditiesInfo(buyList, true);

        return (<div className="buy-list">
                <div className="title">
                    <img src={ShopIcon} alt="shop-icon"/>
                    <h4>Cart</h4>
                </div>
                <div className="buy-list-table">
                    <div className="th">
                        <span>Name</span>
                        <span>Image</span>
                        <span>Categories</span>
                        <span>Price</span>
                        <span>Provider ID</span>
                        <span>Rating</span>
                        <span>In Stock</span>
                        <span>In Cart</span>
                    </div>
                    {commodityInfo}
                </div>

                <div className="buy-list-pay">
                    <input type="button" value="Pay now!" className="submit" onClick={handlePurchase}/>
                </div>
            </div>

        )
    }

    function PurchaseHistory(props) {
        const {purchasedList, fetchPurchasedList} = props;

        const commodityInfo = commoditiesInfo(purchasedList, false);

        console.log(commodityInfo)

        return (
            <div className="history">
                <div className="title">
                    <img src={HistoryIcon} alt="history-icon"/>
                    <h4>History</h4>
                </div>

                <div className="history-table">
                    <div className="th">
                        <span>Image</span>
                        <span>Name</span>
                        <span>Categories</span>
                        <span>Price</span>
                        <span>Provider ID</span>
                        <span>Rating</span>
                        <span>In Stock</span>
                        <span>Quantity</span>
                    </div>
                    {commodityInfo}
                </div>
            </div>
        )
    }

    return (<div>
            <Header/>

            <div className="userWrapper">
                <UserCredits user={user} fetchUser={fetchUser}/>

                <BuyList buyList={buyList} fetchBuyList={fetchBuyList}/>

                <PurchaseHistory purchasedList={purchaseList} fetchPurchasedList={fetchPurchasedList}/>

            </div>

            <footer>
                <p> 2023 @UT </p>
            </footer>
        </div>
    )
}

export default User;
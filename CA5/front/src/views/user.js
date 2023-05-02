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
import Huawei from "../assets/images/commodities/Huawei_small.png";

function User() {
    const username = sessionStorage.getItem('username');
    const [user, setUser] = useState({});

    async function fetchUser() {
        try {
            const response = await getUserById(username);
            setUser(response.data);
        } catch (error) {
        }
    }

    useEffect(() => {
        fetchUser()
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

        return (
            <div>
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
            </div>
        );
    }

    function BuyList(props) {
        // async function fetchCommodity() {
        //     try {
        //         const response = await getCommodityById(username);
        //
        //         setCredit(response.data.credit);
        //         setBirth(response.data.birthDate);
        //         setEmail(response.data.email);
        //         setAddress(response.data.address);
        //     } catch (error) {
        //         console.log(error);
        //     }
        // }
        //
        // useEffect(() => {
        //     fetchUser().then((data) => {
        //
        //     });
        // }, []);


        return (
            <div className="buy-list">
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
                    <div className="td">
                        <span>Galaxy S21</span>
                        <span><img src={Huawei} alt="huawei"/></span>
                        <span>Technology, Phone</span>
                        <span>$21000000</span>
                        <span>1234</span>
                        <span className="rate">8.3</span>
                        <span className="stock">17</span>
                        <span className="in-cart">
                    <span id="minus">-</span>
                    <span id="num">1</span>
                    <span id="plus">+</span>
                </span>
                    </div>
                </div>

                <div className="buy-list-pay">
                    <input type="submit" value="Pay now!" className="submit"/>
                </div>
            </div>

        )
    }

    return (
        <div>
            <Header/>

            <div className="userWrapper">
                <UserCredits user={user} fetchUser={fetchUser}/>

                <BuyList/>


                {/*    <div className="history">*/}
                {/*        <div className="title">*/}
                {/*            <img src="../assets/images/icons/history.png" alt="history-icon"/>*/}
                {/*            <h4>History</h4>*/}
                {/*        </div>*/}

                {/*        <div className="history-table">*/}
                {/*            <div className="th">*/}
                {/*                <span>Image</span>*/}
                {/*                <span>Name</span>*/}
                {/*                <span>Categories</span>*/}
                {/*                <span>Price</span>*/}
                {/*                <span>Provider ID</span>*/}
                {/*                <span>Rating</span>*/}
                {/*                <span>In Stock</span>*/}
                {/*                <span>Quantity</span>*/}
                {/*            </div>*/}
                {/*            <div className="td">*/}
                {/*                <span><img src="../assets/images/commodities/spaghetti.png" alt="huawei"/></span>*/}
                {/*                <span>Mom’s Spaghetti</span>*/}
                {/*                <span>Food</span>*/}
                {/*                <span>$60000</span>*/}
                {/*                <span>313</span>*/}
                {/*                <span className="rate">10</span>*/}
                {/*                <span className="stock">0</span>*/}
                {/*                <span> 3 </span>*/}
                {/*            </div>*/}
                {/*            <div className="td">*/}
                {/*                <span><img src="../assets/images/commodities/mic.png" alt="huawei"/></span>*/}
                {/*                <span>Dre’s Microphone</span>*/}
                {/*                <span>Technology</span>*/}
                {/*                <span>$4200000</span>*/}
                {/*                <span>4321</span>*/}
                {/*                <span className="rate">8.5</span>*/}
                {/*                <span className="stock">22</span>*/}
                {/*                <span> 1 </span>*/}
                {/*            </div>*/}
                {/*        </div>*/}
                {/*    </div>*/}

            </div>

            <footer>
                <p> 2023 @UT </p>
            </footer>
        </div>
    )
}

export default User;
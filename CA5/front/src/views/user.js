import React, {useEffect, useState} from "react";
import "../styles/user.css"
import "../styles/footer.css"
import Header from "./header.js";
import {Modal} from 'react-bootstrap';
import {addUserCredit, getUserById} from "../api/user.js";
import UserIcon from "../assets/images/icons/user.png";
import MailIcon from "../assets/images/icons/mail.png";
import CalendarIcon from "../assets/images/icons/calendar.png";
import LocationIcon from "../assets/images/icons/location.png";
import ShopIcon from "../assets/images/icons/shop.png";
import HistoryIcon from "../assets/images/icons/history.png"
import {
    addToBuyList,
    applyDiscount,
    getBuyList,
    getPurchasedList,
    purchaseBuyList,
    removeFromBuyList
} from "../api/buyList.js";
import {Link} from "react-router-dom";
import {useDispatch} from "react-redux";
import {addToCart, removeFromCart} from "../components/cartItemCount";
import {toast, ToastContainer} from "react-toastify";

function User() {
    const username = sessionStorage.getItem('username');
    const [user, setUser] = useState({});
    const [buyList, setBuyList] = useState({});
    const [purchaseList, setPurchaseList] = useState({});
    const dispatch = useDispatch();

    async function fetchUser() {
        try {
            const response = await getUserById(username);
            setUser(response.data);
        } catch (error) {
        }
    }

    async function fetchBuyList() {
        try {
            const response = await getBuyList(username);
            setBuyList(response.data);
        } catch (error) {
        }
    }

    async function fetchPurchasedList() {
        try {
            const response = await getPurchasedList(username);
            setPurchaseList(response.data);
        } catch (error) {
            toast.error(error.response.data);
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
        const [isModalOpen, setIsModalOpen] = useState(false);

        const handleAmountChange = (event) => {
            setAmount(event.target.value);
        };

        const handleSubmit = async (event) => {
            event.preventDefault();
            console.log(amount)
            await addUserCredit(user.username, amount)
                .then(() => {})
                .catch((error) => {toast.error(error.response.data)})

            setAmount('');
            await fetchUser();
            setIsModalOpen(false);
        };

        const handleOpenModal = () => {
            setIsModalOpen(true);
        };

        const handleCloseModal = () => {
            setIsModalOpen(false);
        };

        function Modal(props) {
            const { onClose, onConfirm, children } = props;

            return (
                <>
                    <div className="modal-overlay"></div>
                    <div className="modal">
                        <div className="modal-content">
                            {children}
                            <div className="modal-buttons">
                                <button id={"cancel-button"} onClick={onClose}>Close</button>
                                <button id={"confirm-button"} onClick={onConfirm}>Confirm!</button>
                            </div>
                        </div>
                    </div>
                </>
            );
        }

        return (
            <>
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
                        <tbody>
                        <tr>
                            <Link to={`/logout`}>
                                <button type="submit" className="submit" id={"logout-button"}>
                                    logout
                                </button>
                            </Link>
                        </tr>
                        </tbody>
                    </table>

                    <div className="buy">
                        <span className="credit-value">${user.credit}</span>
                        <input
                            value={amount}
                            onChange={handleAmountChange}
                            placeholder="$Amount"
                            className="amount"
                        />
                        <button type="submit" className="submit" onClick={handleOpenModal}>
                            Add More Credit
                        </button>
                    </div>
                </div>

                {isModalOpen && (
                    <Modal
                        onClose={handleCloseModal}
                        onConfirm={(e) => handleSubmit(e)}>
                        <h2>Add Credit</h2>
                        <p>Are you sure you want to add ${amount} to your account?</p>
                    </Modal>
                )}
            </>
        );
    }

    const handleAddToBuyList = async (e, id) => {
        e.preventDefault();
        try {
            await addToBuyList(username, id);
            await fetchBuyList();

            dispatch(addToCart({id}));
        } catch (error) {
            console.error(error);
        }
    };

    const handleRemoveFromBuyList = async (e, id) => {
        e.preventDefault();
        try {
            await removeFromBuyList(username, id);
            await fetchBuyList();
            dispatch(removeFromCart({id}));
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

    const resetCart = () => ({
        type: 'cart/reset',
    });

    const handlePurchase = async (e) => {
        e.preventDefault();
        try {
            await purchaseBuyList(username);
            await fetchBuyList();
            await fetchPurchasedList();
            await fetchUser();
            dispatch(resetCart());
        } catch (error) {
            toast.error(error.response.data);
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
                        <Link to={`/product/` + x.commodity.id}>
                <img src={x.commodity.image} alt={x.commodity.name}/>
                            </Link>
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
        const {buyList} = props;
        const [isModalOpen, setIsModalOpen] = useState(false);

        const commodityInfo = commoditiesInfo(buyList, true);

        const handleOpenModal = () => {
            setIsModalOpen(true);
        };

        const handleCloseModal = () => {
            setIsModalOpen(false);
        };

        const ProductModal = ({ products, show, onClose }) => {
            const [discountCode, setDiscountCode] = useState('');
            const [discount, setDiscount] = useState(0);
            const [isSubmitted, setIsSubmitted] = useState(true);

            const totalPrice = products.reduce((total, product) => total + product.commodity.price * product.quantity, 0);
            const discountedPrice = totalPrice * (1 - discount / 100);

            const handleDiscountCodeChange = (event) => {
                setDiscountCode(event.target.value);
            };

            async function addDiscount(discountCode) {
                try {
                    const response = await applyDiscount(discountCode, username);
                    return response.data;
                } catch (error) {
                    toast.error(error.response.data);
                    return 0;
                }
            }

            const handleApplyDiscount = () => {
                addDiscount(discountCode).then((discountAmount) => {
                    if (discountAmount === 0) {
                        setDiscount(0);
                    } else {
                        setDiscount(discountAmount.discount);
                        setIsSubmitted(false);
                    }
                });
            };

            return (
                <Modal show={show} onHide={onClose} className="modal-overlay">
                    <div className="modal">
                        <div className="modal-content">
                            <h2>Your cart</h2>
                            <p>
                                {products.length > 0 ? (
                                    <>
                                        <ul>
                                            {products.map((product) => (
                                                <li>
                                                    <span className="product-name">{product.commodity.name}</span>
                                                    <span className="product-quantity">x {product.quantity}</span>
                                                    <span
                                                        className="product-subtotal">${product.commodity.price * product.quantity}</span>
                                                </li>
                                            ))}
                                        </ul>
                                        <div>
                                            <input
                                                id="discount-code"
                                                type="text"
                                                value={discountCode}
                                                placeholder={"Code"}
                                                onChange={handleDiscountCodeChange}
                                            />
                                            {isSubmitted ?
                                                (<button id={"confirm-button"}
                                                         onClick={handleApplyDiscount}>Submit</button>)
                                                : (<button id={"confirm-button-confirmed"}>Submitted</button>)
                                            }
                                        </div>
                                        <div>
                                            <p className={isSubmitted ? '' : 'invalidated-total'}>total: ${totalPrice}</p>
                                            {discount !== 0 ?
                                                (<>
                                                    <span>with discount: </span>
                                                    <span id={"discount-price"}>${discountedPrice}</span>
                                                </>)
                                                : (<></>)}
                                        </div>
                                    </>
                                ) : (
                                    <p>No products found.</p>
                                )}

                            </p>
                            <div className="modal-buttons">
                                <button id="cancel-button" onClick={onClose}>
                                    Close
                                </button>
                                {products.length > 0 ? (
                                    <button id="confirm-button" onClick={handlePurchase}>
                                        Buy!
                                    </button>) : (<></>)}
                            </div>
                        </div>
                    </div>
                </Modal>
            );
        };


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
                    <input type="button" value="Pay now!" className="submit" onClick={handleOpenModal}/>
                </div>
                {isModalOpen && (
                    <ProductModal show={isModalOpen} onClose={handleCloseModal} products={buyList}/>
                )}
            </div>

        )
    }

    function PurchaseHistory(props) {
        const {purchasedList} = props;

        const commodityInfo = commoditiesInfo(purchasedList, false);

        return (
            <div className="history">
                <div className="title">
                    <img src={HistoryIcon} alt="history-icon"/>
                    <h4>History</h4>
                </div>

                <div className="history-table">
                    <div className="th">
                        <span>Name</span>
                        <span>Image</span>
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
                <ToastContainer/>

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
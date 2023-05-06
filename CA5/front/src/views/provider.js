import Header from "./header";
import {getAllProvidedCommodities, getProviderById} from "../api/provider";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {addToCart, removeFromCart, selectCartItem} from "../components/cartItemCount";
import {addToBuyList, removeFromBuyList} from "../api/buyList";

function ProviderPage() {
    const {id} = useParams();
    const [provider, setProvider] = useState({});
    const [products, setProducts] = useState([]);
    const dispatch = useDispatch();
    const username = sessionStorage.getItem("username");


    useEffect(() => {
        fetchProvider().then(() => {
        });

        fetchAllProvidedCommodities().then(() => {
        });

    }, []);

    const fetchProvider = async () => {
        try {
            const response = await getProviderById(id);
            setProvider(response.data);

        } catch (error) {
            console.log("Error:", error.message);
        }
    };

    const fetchAllProvidedCommodities = async () => {
        try {
            const response = await getAllProvidedCommodities(id);
            console.log(response.data)
            setProducts(response.data);

        } catch (error) {
            console.log("Error:", error.message);
        }
    }

    function useCartItemNumber(id) {
        const cartItem = useSelector(state => selectCartItem(state, id));
        return cartItem || 0;
    }

    const HandleAddToCart = async (e, id) => {
        console.error(id)
        e.preventDefault();
        try {
            await addToBuyList(username, id);
            dispatch(addToCart({id}));
        } catch (error) {
            console.error(error);
        }
    };

    const HandleRemoveFromCart = async (e, id) => {
        e.preventDefault();
        try {
            await removeFromBuyList(username, id);
            dispatch(removeFromCart({id}));
        } catch (error) {
            console.error(error);
        }
    };

    function Commodity({x}) {
        const cartItemNumber = useCartItemNumber(x.id);
        return (
            <div className="products">
                <div className="cards">
                    <a href={"/product/" + x.id}>
                        <h3>{x.name}</h3>
                    </a>
                    <p>{x.inStock} left in stock</p>
                    <img src={x.image} alt={"product-img"}/>
                    <div className="price-add">
                        <h4>{x.price}$</h4>

                        {cartItemNumber === 0 ?
                            <input
                                type="button"
                                value="add to cart"
                                id={"add-to-cart-button"}
                                onClick={(e) => HandleAddToCart(e, x.id)}
                                disabled={x.inStock === 0}
                                className={x.inStock === 0 ? "disabled-button" : "enabled-button"}
                            /> :
                            <span className={"home-cart"}>
                            <input
                                type="button"
                                onClick={(e) => HandleRemoveFromCart(e, x.id)}
                                id="minus-home"
                                value="-"
                            />
                            <span id="num-home">{cartItemNumber}</span>
                            <input
                                type="button"
                                onClick={(e) => HandleAddToCart(e, x.id)}
                                id="minus-home"
                                value="+"
                            />
                        </span>}


                    </div>
                </div>
            </div>
        );
    }

    function showCommodities() {

        const suggestionInfo = [];
        for (const x of Object.values(products)) {
            // console.log(x.id)
            suggestionInfo.push(<Commodity x={x}/>);
        }


        return suggestionInfo;
    }

    return (
        <>
            <Header/>
            <div className={"provider-wrapper"}>
                <div className={"provider-info"}>
                    <img src={provider.image} alt={"provider-img"}/>
                    <p>{provider.name}</p>
                </div>
                {/*<div className={"products"}>*/}
                {/*    <p>All provided commodities</p>*/}
                {/*    <div className={"cards"}>*/}
                {/*        {showCommodities()}*/}
                {/*    </div>*/}
                {/*</div>*/}

                <div className="products">
                    <div className="title">
                        All provided commodities
                    </div>
                    <div className="products">
                        {showCommodities()}
                    </div>
                </div>
            </div>
        </>
    )
}

export default ProviderPage;
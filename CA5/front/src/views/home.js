import React, {useEffect, useState} from "react";
import "../styles/home.css";
import "../styles/toggle_switch.css";
import "../styles/commodities.css";
import Header from "./header.js";
import {getCommodities, searchCommodities} from "../api/commodities.js";
import {addToBuyList, removeFromBuyList} from "../api/buyList.js";
import {useDispatch, useSelector} from 'react-redux';
import {addToCart, removeFromCart, selectCartItem} from '../components/cartItemCount.js';

const Home = () => {
    const username = sessionStorage.getItem("username");

    const [commodities, setCommodities] = useState([]);
    const [commoditiesHtml, setCommoditiesHtml] = useState([]);
    const [shownCommodities, setShownCommodities] = useState([]);

    const [currentPage, setCurrentPage] = useState(1);
    const [commoditiesPerPage] = useState(12);

    const [sortByName, setSortByName] = useState(false);
    const [sortByPrice, setSortByPrice] = useState(true);

    const [searchOption, setSearchOption] = useState("");
    const [searchValue, setSearchValue] = useState("");

    const [showAvailableCommodities, setShowAvailableCommodities] = useState(false);

    const dispatch = useDispatch();



    useEffect(() => {
        fetchCommodities().then(() => {
        });
    }, []);

    useEffect(() => {
        // commoditiesInfo().then(r => {
        // });
        async function fetchCommoditiesInfo() {
            await commoditiesInfo();
        }

        fetchCommoditiesInfo().then(r => {
        });
    }, [commodities, searchValue, searchOption, sortByPrice, sortByName, showAvailableCommodities, currentPage]);


    function useCartItemNumber(id) {
        const cartItem = useSelector(state => selectCartItem(state, id));
        return cartItem || 0;
    }

    function handleAvailableCommodities() {
        const isChecked = document.getElementById("available-commodities-check").checked;
        if (isChecked) {
            setShowAvailableCommodities(true);
        } else {
            setShowAvailableCommodities(false);
        }
    }

    async function fetchCommodities() {
        try {
            const response = await getCommodities();
            setCommodities(response.data);
        } catch (error) {
            return [];
        }
    }

    async function fetchSearchedCommodities(searchOption, searchValue) {
        setSearchOption(searchOption);
        setSearchValue(searchValue);
    }

    const HandleAddToCart = async (e, id) => {
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

    function handleSortCommodities(sortMethod) {
        if (sortMethod === "name") {
            setSortByName(true);
            setSortByPrice(false);
        } else if (sortMethod === "price") {
            setSortByPrice(true);
            setSortByName(false);
        }

        setCurrentPage(1);
    }

    function CommodityCard({commodity}) {
        const cartItemNumber = useCartItemNumber(commodity.id);

        return (
            <div className="cards">
                <a href={"product/" + commodity.id}>
                    <h3>{commodity.name}</h3>
                </a>
                <p>{commodity.inStock} left in stock</p>
                <img src={commodity.image} alt=""/>
                <div className="price-add-home">
                    <h4>{commodity.price}$</h4>

                    {cartItemNumber === 0 ?
                        <input
                            type="button"
                            value="add to cart"
                            onClick={(e) => HandleAddToCart(e, commodity.id)}
                            disabled={commodity.inStock === 0}
                            className={commodity.inStock === 0 ? "disabled-button" : "enabled-button"}
                        /> :
                        <span className={"home-cart"}>
                            <input
                                type="button"
                                onClick={(e) => HandleRemoveFromCart(e, commodity.id)}
                                id="minus-home"
                                value="-"
                            />
                            <span id="num-home">{cartItemNumber}</span>
                            <input
                                type="button"
                                onClick={(e) => HandleAddToCart(e, commodity.id)}
                                id="plus-home"
                                value="+"
                            />
                        </span>}
                </div>
            </div>
        );
    }

    const getShownCommodities = async () => {
        let comms = commodities;

        if (searchValue !== "") {
            const results = await searchCommodities(searchOption, searchValue);
            comms = results.data;
        }

        if (sortByPrice) {
            comms = [...comms].sort((a, b) => a.price - b.price);
        }

        if (sortByName) {
            comms = [...comms].sort((a, b) => {
                if (a.name < b.name) return -1;
                if (a.name > b.name) return 1;
                return 0;
            });
        }

        if (showAvailableCommodities) {
            comms = comms.filter(commodity => commodity.inStock > 0);
        }

        setShownCommodities(comms);
    }

    const commoditiesInfo = async () => {
        const commodityInfo = [];
        await getShownCommodities();

        const indexOfLastCommodity = currentPage * commoditiesPerPage;
        const indexOfFirstCommodity = indexOfLastCommodity - commoditiesPerPage;
        const currentCommodities = shownCommodities.slice(
            indexOfFirstCommodity,
            indexOfLastCommodity
        );

        for (const x of Object.values(currentCommodities)) {
            commodityInfo.push(<CommodityCard commodity={x}/>);
        }

        setCommoditiesHtml(commodityInfo)
    };

    useEffect(() => {
        // commoditiesInfo().then(r => {
        // });
        async function fetchCommoditiesInfo() {
            await commoditiesInfo();
        }

        fetchCommoditiesInfo().then(r => {
        });
    }, [commodities, searchValue, searchOption, sortByPrice, sortByName, showAvailableCommodities, currentPage]);




    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(shownCommodities.length / commoditiesPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div>
            <Header fetchSearchedCommodities={fetchSearchedCommodities} showSearchbar={true}/>

            <div className="wrapper">
                <div className="filters">
                    <h3>Available commodities</h3>

                    <label className="switch">
                        <input id={"available-commodities-check"} type="checkbox" onClick={handleAvailableCommodities}/>
                        <span className="slider"></span>
                    </label>
                    <div className="sort">
                        <h4>sort by: </h4>
                        <input type="button" value="name" id="name-filter-button"
                               onClick={() => handleSortCommodities("name")}
                               className={sortByName ? 'button_active' : 'button_deactivate'}/>
                        <input type="button" value="price" id="price-filter-button"
                               onClick={() => handleSortCommodities("price")}
                               className={sortByPrice ? 'button_active' : 'button_deactivate'}/>
                    </div>
                </div>
                <div className="products">
                    {commoditiesHtml}
                </div>
                <div className="pagination">
                    <button
                        className={`arrow ${currentPage === 1 ? "disabled" : ""}`}
                        onClick={() => setCurrentPage(currentPage - 1)}
                        disabled={currentPage === 1}
                    >
                        {"<"}
                    </button>
                    {pageNumbers.map((number) => (
                        <button
                            key={number}
                            onClick={() => setCurrentPage(number)}
                            className={`circle ${currentPage === number ? "active" : ""}`}
                        >
                            {number}
                        </button>
                    ))}
                    <button
                        className={`arrow ${currentPage === pageNumbers.length ? "disabled" : ""}`}
                        onClick={() => setCurrentPage(currentPage + 1)}
                        disabled={currentPage === pageNumbers.length}
                    >
                        {">"}
                    </button>
                </div>
            </div>

        </div>
    );
};

export default Home;
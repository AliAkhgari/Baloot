import React, {useEffect, useState} from "react";
import "../styles/home.css";
import "../styles/toggle_switch.css";
import "../styles/commodities.css";
import Header from "./header.js";
import {getCommodities, searchCommodities} from "../api/commodities.js";
import {addToBuyList} from "../api/buyList.js";

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


    useEffect(() => {
        fetchCommodities().then(() => {
        });
    }, []);

    useEffect(() => {
        async function fetchCommoditiesInfo() {
            await commoditiesInfo();
        }

        fetchCommoditiesInfo().then(r => {
        });
    }, [commodities, searchValue, searchOption, sortByPrice, sortByName, showAvailableCommodities, currentPage]);

    function handleAvailableCommodities() {
        const isChecked = document.getElementById("available-commodities-check").checked;
        console.warn(isChecked)
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

    const handleAddToCart = async (e, id) => {
        console.warn(id)
        e.preventDefault();
        try {
            await addToBuyList(username, id);
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

    const commoditiesInfo = async () => {
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

        const commodityInfo = [];

        const indexOfLastCommodity = currentPage * commoditiesPerPage;
        const indexOfFirstCommodity = indexOfLastCommodity - commoditiesPerPage;
        const currentCommodities = comms.slice(
            indexOfFirstCommodity,
            indexOfLastCommodity
        );

        for (const x of Object.values(currentCommodities)) {
            commodityInfo.push(
                <div className="cards">
                    <a href={"/" + x.id}>
                        <h3>{x.name}</h3>
                    </a>
                    <p>{x.inStock} left in stock</p>
                    <img src={x.image} alt=""/>
                    <div className="price-add">
                        <h4>{x.price}$</h4>
                        <input
                            type="button"
                            value="add to cart"
                            onClick={(e) =>
                                handleAddToCart(e, x.id)}
                        />
                    </div>
                </div>
            );
        }

        setCommoditiesHtml(commodityInfo);
    }

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(shownCommodities.length / commoditiesPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <div>
            <Header fetchSearchedCommodities={fetchSearchedCommodities}/>

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
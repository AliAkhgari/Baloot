import React, {useEffect, useState} from "react";
import "../styles/home.css";
import "../styles/toggle_switch.css"
import "../styles/commodities.css"
import Header from "./header.js";
import {getCommodities, searchCommodities} from "../api/commodities.js";
import {addToBuyList} from "../api/buyList.js";


const Home = () => {
    const username = sessionStorage.getItem('username');
    const [commodities, setCommodities] = useState([]);
    const [allCommodities, setAllCommodities] = useState([]);

    useEffect(() => {
        fetchCommodities().then(() => {
        });
    }, []);

    function handleAvailableCommodities() {
        const isChecked = document.getElementById("available-commodities-check").checked;
        console.warn(isChecked)
        if (isChecked) {
            const filteredCommodities = commodities.filter(commodity => commodity.inStock > 0);
            setCommodities(filteredCommodities);
        } else {
            setCommodities(allCommodities);
        }
    }

    async function fetchCommodities() {
        try {
            const response = await getCommodities();
            setCommodities(response.data);
            setAllCommodities(response.data);
            console.warn("fetch commmmm")
            console.log(response.data)
        } catch (error) {
            return [];
        }
    }

    async function fetchSearchedCommodities(searchOption, searchValue) {
        try {
            const response = await searchCommodities(searchOption, searchValue);
            setCommodities(response.data);
            setAllCommodities(response.data);
            console.warn("fetch commmmm")
            console.log(response.data)
        } catch (error) {
            return [];
        }
    }

    const handleAddToCart = async (e, id) => {
        e.preventDefault();
        try {
            await addToBuyList(username, id);
            await fetchCommodities();
        } catch (error) {
            console.error(error);
        }
    };

    function commoditiesInfo(commodities) {
        console.log(commodities)
        const commodityInfo = [];

        for (const x of Object.values(commodities)) {

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
                            onClick={(e) => handleAddToCart(e, x.commodity.id)}

                        />
                    </div>
                </div>
        );
        }

        return commodityInfo;
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
                        <input type="button" value="name" id="name-filter-button"/>
                        <input type="button" value="price" id="price-filter-button"/>
                    </div>
                </div>
                <div className="products">
                    {commoditiesInfo(commodities)}
                </div>
            </div>
        </div>
    )
}


export default Home;



import React, {useEffect, useState} from "react";
import "../styles/home.css";
import "../styles/toggle_switch.css"
import "../styles/commodities.css"
import huaweiSmall from "../assets/images/commodities/Huawei_small.png"
import Header from "./header.js";
import {getCommodities, searchCommodities} from "../api/commodities.js";


const Home = () => {
    const [commodities, setCommodities] = useState([]);

    useEffect(() => {
        fetchCommodities().then(() => {
        });
    }, []);

    function handleAvailableCommodities() {
        console.log(commodities)
    }

    async function fetchCommodities() {
        try {
            const response = await getCommodities();
            setCommodities(response.data);
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
            console.warn("fetch commmmm")
            console.log(response.data)
        } catch (error) {
            return [];
        }
    }

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
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
            //     <div className="td" key={x.commodity.id}>
            //         <span>{x.commodity.name}</span>
            //         <span>
            //     <img src={x.commodity.image} alt={x.commodity.name}/>
            //     </span>
            //         <span>{x.commodity.categories.join(", ")}</span>
            //         <span>{x.commodity.price}</span>
            //         <span>{x.commodity.providerId}</span>
            //         <span className="rate">{x.commodity.rating}</span>
            //         <span className="stock">{x.commodity.inStock}</span>
            //         <span className="in-cart">
            //
            // </span>
            //     </div>
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
                        <input type="checkbox" onClick={handleAvailableCommodities}/>
                        {/*<input type="checkbox"/>*/}
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
                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}

                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    {/*<div className="cards">*/}
                    {/*    <a href="/">*/}
                    {/*        <h3>Huawei nova 9</h3>*/}
                    {/*    </a>*/}
                    {/*    <p>1 left in stock</p>*/}
                    {/*    <img src={huaweiSmall} alt=""/>*/}
                    {/*    <div className="price-add">*/}
                    {/*        <h4>300$</h4>*/}
                    {/*        <input type="button" value="add to cart"/>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                </div>
            </div>
        </div>
    )
}


export default Home;



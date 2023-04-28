import React from "react";
import "../styles/home.css";
import "../styles/toggle_switch.css"
import "../styles/commodities.css"
import huaweiSmall from "../assets/images/commodities/Huawei_small.png"


const Home = () => {
    return (
        <div className="wrapper">
            <div className="filters">
                <h3>Available commodities</h3>

                <label className="switch">
                    <input type="checkbox"/>
                    <span className="slider"></span>
                </label>
                <div className="sort">
                    <h4>sort by: </h4>
                    <input type="button" value="name" id="name-filter-button"/>
                    <input type="button" value="price" id="price-filter-button"/>
                </div>
            </div>
            <div className="products">
                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>

                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
                <div className="cards">
                    <a href="/">
                        <h3>Huawei nova 9</h3>
                    </a>
                    <p>1 left in stock</p>
                    <img src={huaweiSmall} alt=""/>
                    <div className="price-add">
                        <h4>300$</h4>
                        <input type="button" value="add to cart"/>
                    </div>
                </div>
            </div>
        </div>
    )
}


export default Home;



import React, {useEffect, useState} from "react";
import "../styles/product.css"
import {useParams} from "react-router-dom";
import {getCommodityById} from "../api/commodities.js";
import {getProviderById} from "../api/provider.js";
import Header from "./header.js";
import StarIcon from "../assets/images/icons/star.png";

function Product() {
    const {id} = useParams();
    const [commodity, setCommodity] = useState({});
    const [providerName, setProviderName] = useState("");
    const [rating, setRating] = useState(2);
    const [hover, setHover] = useState(null);

    useEffect(() => {
        fetchCommodity().then(() => {
        });

    }, []);

    const fetchCommodity = async () => {
        try {
            const responseCommodity = await getCommodityById(id);
            setCommodity(responseCommodity.data);

            const responseProvider = await getProviderById(responseCommodity.data.providerId);
            setProviderName(responseProvider.data.name);
        } catch (error) {
            console.log("Error:", error.message);
        }
    };

    function productInfo() {
        if (!commodity.categories) {
            return null;
        }
        const categoryList = [];
        console.log(commodity)
        for (const x of Object.values(commodity.categories)) {
            categoryList.push(
                <li>
                    <p>{x}</p>
                </li>
            );
        }

        return (
            <div className="product">
                <div className="commodity-picture">
                    <img src={commodity.image} alt="commodity"/>
                </div>
                <div className="commodity-info">
                    <div className="box">
                        <div className="info">
                            <p id="commodity-title">{commodity.name}</p>
                            <br></br>
                            <p id="commodity-in-stock-number">{commodity.inStock} left in stock</p>
                            <p id="provider">by <a href={"provider/" + commodity.providerId}>{providerName}</a></p>
                            <span id="category-text">Category(s)</span>
                            <ul id="categories-list">
                                {categoryList}
                            </ul>
                        </div>
                        <div className="rating">
                            <img src={StarIcon} alt="star-icon"/>
                            <span id="rate">{commodity.rating}</span>
                            <span id="rate-number">(12)</span>
                        </div>
                    </div>
                    <div className="add-to-cart">
                        <span id="price">{commodity.price}$</span>
                        <input type="button" value="add to card" id="add-to-cart-button"/>
                    </div>

                    <div className="rate-me">
                        <div className="stars">
                            <span>rate now</span>
                            <div className="rate-bar">

                            </div>
                        </div>
                        <input type="submit" value="submit" id="rate-submit-button"/>
                    </div>
                </div>

            </div>
        )
    }

    return (
        <>
            <Header showSearchbar={false}/>

            <div className="product-wrapper">
                {productInfo()}
                <div className="comments">
                    <div className="title">
                        <p id="comment-title">Comments</p>
                        <p id="number-of-comments">(2)</p>
                    </div>
                    <div className="contents">
                        <h6>This was awsome!!!!</h6>
                        <p>2023-03-20&nbsp;&nbsp;&nbsp;&nbsp;&bull;&nbsp;&nbsp;&nbsp;&nbsp;#username</p>
                        <div className="is-helpful">
                            <span>Is this comment helpful?</span>
                            <span className="like-number">1</span>
                            <img src="../assets/images/icons/like.png" alt={"like-icon"}/>
                            <span className="dislike-number">1</span>
                            <img src="../assets/images/icons/dislike.png" alt={"dislike-icon"}/>
                        </div>
                    </div>
                    <div className="contents">
                        <h6>This was awfullllllllllll!!!!</h6>
                        <p>2023-03-20&nbsp;&nbsp;&nbsp; &nbsp;&bull;&nbsp;&nbsp; &nbsp;&nbsp;#username</p>
                        <div className="is-helpful">
                            <span>Is this comment helpful?</span>
                            <span className="like-number">1</span>
                            <img src="../assets/images/icons/like.png" alt={"like-icon"}/>
                            <span className="dislike-number">1</span>
                            <img src="../assets/images/icons/dislike.png" alt={"dislike-icon"}/>
                        </div>
                    </div>
                    <div className="opinion">
                        <span id="submit-opinion-text">Submit your opinion</span>
                        <div className="opinion-box">
                            <input type="text" id="textbox" name="textbox"/>
                            <input type="submit" value="Post" id="submit-opinion-button"/>
                        </div>
                    </div>
                </div>
                <div className="suggest">
                    <div className="title">
                        You also might like...
                    </div>
                    <div className="products">
                        <div className="cards">
                            <a href="#">
                                <h3>Huawei nova 9</h3>
                            </a>
                            <p>1 left in stock</p>
                            <img src="../assets/images/commodities/Huawei_small.png"/>
                            <div className="price-add">
                                <h4>300$</h4>
                                <input type="button" value="add to cart"/>
                            </div>
                        </div>
                        <div className="cards">
                            <a href="#">
                                <h3>Huawei nova 9</h3>
                            </a>
                            <p>1 left in stock</p>
                            <img src="../assets/images/commodities/Huawei_small.png"/>
                            <div className="price-add">
                                <h4>300$</h4>
                                <input type="button" value="add to cart"/>
                            </div>
                        </div>
                        <div className="cards">
                            <a href="#">
                                <h3>Huawei nova 9</h3>
                            </a>
                            <p>1 left in stock</p>
                            <img src="../assets/images/commodities/Huawei_small.png"/>
                            <div className="price-add">
                                <h4>300$</h4>
                                <input type="button" value="add to cart"/>
                            </div>
                        </div>
                        <div className="cards">
                            <a href="#">
                                <h3>Huawei nova 9</h3>
                            </a>
                            <p>1 left in stock</p>
                            <img src="../assets/images/commodities/Huawei_small.png"/>
                            <div className="price-add">
                                <h4>300$</h4>
                                <input type="button" value="add to cart"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>

    )
}

export default Product;


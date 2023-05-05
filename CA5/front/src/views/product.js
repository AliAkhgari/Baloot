import React, {useEffect, useState} from "react";
import "../styles/product.css"
import {useParams} from "react-router-dom";
import {addComment, getComments, getCommodityById, rateCommodity} from "../api/commodities.js";
import {getProviderById} from "../api/provider.js";
import Header from "./header.js";
import StarIcon from "../assets/images/icons/star.png";
import {addToBuyList} from "../api/buyList.js";
import DislikeIcon from "../assets/images/icons/dislike.png"
import LikeIcon from "../assets/images/icons/like.png"

function Product() {
    const {id} = useParams();
    const [commodity, setCommodity] = useState({});
    const [providerName, setProviderName] = useState("");
    const [comments, setComments] = useState([]);
    const [comment, setComment] = useState("");

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

            const responseComments = await getComments(id);
            setComments(responseComments.data);
        } catch (error) {
            console.log("Error:", error.message);
        }
    };

    function StarRating() {
        const [rating, setRating] = useState(0);

        function handleStarClick(starNumber) {
            setRating(starNumber);
        }

        const handleRateSubmit = async (event) => {
            event.preventDefault();
            await rateCommodity(id, rating, sessionStorage.getItem("username"));
            setRating(0);
            await fetchCommodity();
        };

        return (
            <div className="rate-me">
                <div className="stars">
                    <span>rate now</span>
                    <div className="rate-bar">
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((starNumber) => (
                            <img
                                key={starNumber}
                                src={StarIcon}
                                alt="star-icon"
                                onClick={() => handleStarClick(starNumber)}
                                className={starNumber <= rating ? "star-icon-selected" : "star-icon-unselected"}
                            />
                        ))}
                    </div>
                </div>
                <input type="submit" value="submit" id="rate-submit-button" onClick={handleRateSubmit}/>
            </div>
        );
    }

    const handleAddToCart = async (e, id) => {
        e.preventDefault();
        try {
            await addToBuyList(sessionStorage.getItem("username"), id);
        } catch (error) {
            console.error(error);
        }
    };

    function productInfo() {
        if (!commodity.categories) {
            return null;
        }
        const categoryList = [];
        for (const x of Object.values(commodity.categories)) {
            categoryList.push(<li>
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
                        <input type="button" value="add to card" id="add-to-cart-button" onClick={handleAddToCart}/>
                    </div>
                    <StarRating/>
                </div>

            </div>
        )
    }

    function commentsSection() {

        if (!comments) {
            return null;
        }

        function handleCommentChange(event) {
            setComment(event.target.value);
        }

        const handleSubmitComment = async (event) => {
            console.log(comment)
            event.preventDefault();
            await addComment(id, sessionStorage.getItem("username"), comment);
            setComment("");
            await fetchCommodity();
        };

        function showComments() {
            const commentsInfo = [];
            for (const x of Object.values(comments)) {
                commentsInfo.push(
                    <div className="contents">
                        <h6>{x.text}</h6>
                        <p>{x.date}&nbsp;&nbsp;&nbsp;&nbsp;&bull;&nbsp;&nbsp;&nbsp;&nbsp;#{x.userEmail}</p>
                        <div className="is-helpful">
                            <span>Is this comment helpful?</span>
                            <span className="like-number">{x.like}</span>
                            <img src={LikeIcon} alt={"like-icon"}/>
                            <span className="dislike-number">{x.dislike}</span>
                            <img src={DislikeIcon} alt={"dislike-icon"}/>
                        </div>
                    </div>
                );
            }

            return commentsInfo;
        }

        console.log(comments)
        return (
            <div className="comments">
                <div className="title">
                    <p id="comment-title">Comments</p>
                    <p id="number-of-comments">({comments.length})</p>
                </div>
                <>
                    {showComments()}
                </>

                <div className="opinion">
                    <span id="submit-opinion-text">Submit your opinion</span>
                    <div className="opinion-box">
                        <input type="text" id="textbox" name="textbox" value={comment} onChange={handleCommentChange}/>
                        <input type="submit" value="Post" id="submit-opinion-button" onClick={handleSubmitComment}/>
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
                {commentsSection()}
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


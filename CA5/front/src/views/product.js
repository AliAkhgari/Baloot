import React, {useEffect, useState} from "react";
import "../styles/product.css"
import {Link, useParams} from "react-router-dom";
import {addComment, getComments, getCommodityById, getSuggestedCommodities, rateCommodity} from "../api/commodities.js";
import {getProviderById} from "../api/provider.js";
import Header from "./header.js";
import StarIcon from "../assets/images/icons/star.png";
import {addToBuyList, removeFromBuyList} from "../api/buyList.js";
import DislikeIcon from "../assets/images/icons/dislike.png"
import LikeIcon from "../assets/images/icons/like.png"
import {dislikeComment, likeComment} from "../api/comments.js";
import {useDispatch, useSelector} from "react-redux";
import {addToCart, removeFromCart, selectCartItem} from "../components/cartItemCount";
import {toast, ToastContainer} from "react-toastify";

function Product() {
    const {id} = useParams();
    const [commodity, setCommodity] = useState({});
    const [providerName, setProviderName] = useState("");
    const [comments, setComments] = useState([]);
    const [comment, setComment] = useState("");
    const [suggestedCommodities, setSuggestedCommodities] = useState([]);

    const dispatch = useDispatch();

    const username = sessionStorage.getItem("username");


    useEffect(() => {
        fetchCommodity().then(() => {
        });

    }, []);

    function useCartItemNumber(id) {
        const cartItem = useSelector(state => selectCartItem(state, id));
        return cartItem || 0;
    }

    const fetchCommodity = async () => {
        try {
            const responseCommodity = await getCommodityById(id);
            setCommodity(responseCommodity.data);

            const responseProvider = await getProviderById(responseCommodity.data.providerId);
            setProviderName(responseProvider.data.name);

            const responseComments = await getComments(id);
            setComments(responseComments.data);

            const responseSuggested = await getSuggestedCommodities(id);
            setSuggestedCommodities(responseSuggested.data);

        } catch (error) {
            toast.error(error.response.data);
        }
    };

    function StarRating() {
        const [rating, setRating] = useState(0);

        function handleStarClick(starNumber) {
            setRating(starNumber);
        }

        const handleRateSubmit = async (event) => {
            event.preventDefault();
            await rateCommodity(id, rating, username);
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


    const HandleAddToCart = async (e, id) => {
        e.preventDefault();
        try {
            await addToBuyList(username, id);
            dispatch(addToCart({id}));
        } catch (error) {
            console.error(error);
            toast.error(error.response.data);
        }
    };

    const HandleRemoveFromCart = async (e, id) => {
        e.preventDefault();
        try {
            await removeFromBuyList(username, id);
            dispatch(removeFromCart({id}));
        } catch (error) {
            console.error(error);
            toast.error(error.response.data);
        }
    };

    function ProductInfo() {
        const cartItemNumber = useCartItemNumber(commodity.id);
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
                            <p id="provider">by <a href={"/provider/" + commodity.providerId}>{providerName}</a></p>
                            <span id="category-text">Category(s)</span>
                            <ul id="categories-list">
                                {categoryList}
                            </ul>
                        </div>
                        <div className="rating">
                            <img src={StarIcon} alt="star-icon"/>
                            <span id="rate">{commodity.rating}</span>
                            <span id="rate-number">({Object.keys(commodity.userRate).length})</span>
                        </div>
                    </div>
                    <div className="add-to-cart">
                        <span id="price">{commodity.price}$</span>
                        {cartItemNumber === 0 ?
                            <input
                                type="button"
                                value="add to cart"
                                onClick={(e) => HandleAddToCart(e, commodity.id)}
                                disabled={commodity.inStock === 0}
                                className={commodity.inStock === 0 ? "disabled-button" : "enabled-button"}
                            /> :
                            <span id={"product-cart"}>
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
                                id="minus-home"
                                value="+"
                            />
                        </span>}
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
            event.preventDefault();
            await addComment(id, username, comment);
            setComment("");
            await fetchCommodity();
        };

        const handleLikeComment = async (event, commentId) => {
            event.preventDefault();
            await likeComment(commentId, username);
            await fetchCommodity();
        };

        const handleDislikeComment = async (event, commentId) => {
            event.preventDefault();
            await dislikeComment(commentId, username);
            await fetchCommodity();
        };

        function showComments() {
            const commentsInfo = [];
            for (const x of Object.values(comments)) {
                console.log(x)
                commentsInfo.push(
                    <div className="contents">
                        <h6>{x.text}</h6>
                        <p>{x.date}&nbsp;&nbsp;&nbsp;&nbsp;&bull;&nbsp;&nbsp;&nbsp;&nbsp;#{x.username}</p>
                        <div className="is-helpful">
                            <span>Is this comment helpful?</span>
                            <span className="like-number">{x.like}</span>
                            <img src={LikeIcon} alt={"like-icon"}
                                 onClick={(e) =>
                                     handleLikeComment(e, x.id)}/>
                            <span className="dislike-number">{x.dislike} </span>
                            <img src={DislikeIcon} alt={"dislike-icon"} onClick={(e) =>
                                handleDislikeComment(e, x.id)}/>
                        </div>
                    </div>
                );
            }

            return commentsInfo;
        }

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

    function suggestionSection() {

        function SuggestionCard({x}) {
            const cartItemNumber = useCartItemNumber(x.id);

            return (
                <div className="cards">
                    <a href={"/product/" + x.id}>
                        <h3>{x.name}</h3>
                    </a>
                    <p>{x.inStock} left in stock</p>
                    <Link to={`/product/` + x.id}>
                        <img src={x.image} alt={"product-img"}/>
                    </Link>
                    <div className="price-add">
                        <h4>{x.price}$</h4>

                        {cartItemNumber === 0 ?
                            <input
                                type="button"
                                value="add to cart"
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
            );
        }

        function showSuggestion() {
            const suggestionInfo = [];
            for (const x of Object.values(suggestedCommodities)) {
                suggestionInfo.push(<SuggestionCard x={x}/>);
            }

            return suggestionInfo;
        }

        return (
            <div className="suggest">
                {showSuggestion().length === 0 ?
                    (<></>)
                    : (
                        <>
                            <div className="title">
                                You also might like...
                            </div>
                            <div className="products">
                                {showSuggestion()}
                            </div>
                        </>
                    )
                }
            </div>
        )
    }

    return (
        <>
            <Header showSearchbar={false}/>

            <div className="product-wrapper">
                <ToastContainer/>
                {ProductInfo()}
                {commentsSection()}
                {suggestionSection()}

            </div>
        </>

    )
}

export default Product;


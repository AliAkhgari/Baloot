import "../styles/header.css";
import React, {useEffect, useState} from "react";
import {searchCommodities} from "../api/commodities.js";
import search from "../assets/images/icons/search.png";
import {Link} from "react-router-dom";
import logo from "../assets/images/logo.png";
import {getBuyList} from "../api/buyList.js";

const Logo = () => {
    return (
        <div className={"logo-container"}>
            <a href={"/"}>
                <img src={logo} alt={"logo"} className={"logo"}/>
            </a>
            <span className={"logo-name"}>Baloot</span>
        </div>
    )
}

const RegisterLogin = () => {
    return (
        <div className="login-signup">
            {/*TODO: convert id to class because they are same!*/}
            <Link to="/signup">
                <input type="button" value="Register" id="signup-button"/>
            </Link>
            <Link to="/login">
                <input type="button" value="Login" id="login-button"/>
            </Link>
        </div>
    )
}

const Searchbar = ({updateCommodities}) => {
    const [selectedOption, setSelectedOption] = useState("name");
    const [searchValue, setSearchValue] = useState("");

    // updateCommodities(fetchCommodities());

    const handleOptionChange = (event) => {
        setSelectedOption(event.target.value);
    };
    const handleSearchChange = (event) => {
        setSearchValue(event.target.value);
    };

    // async function fetchCommodities() {
    //     try {
    //         const response = await fetchCommodities();
    //         return response.data;
    //     } catch (error) {
    //         return [];
    //     }
    // }

    async function fetchSearchedCommodities(searchOption, searchValue) {
        try {
            const response = await searchCommodities(searchOption, searchValue);
            return response.data;
        } catch (error) {
            return [];
        }
    }

    const handleSearchClick = async () => {
        try {
            const commodities = await fetchSearchedCommodities(selectedOption, searchValue);
            updateCommodities(commodities);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className={"searchbar"}>
            <label>
                <select className={"combobox"} value={selectedOption} onChange={handleOptionChange}>
                    <option value={"name"}>name</option>
                    <option value={"category"}>category</option>
                    <option value={"provider"}>provider</option>
                </select>
            </label>
            <input type="text" placeholder="search your product ..."
                   value={searchValue} onChange={handleSearchChange}/>

            <button onClick={handleSearchClick}>
                <img src={search} alt="search-icon"/>
            </button>
        </div>
    )
}
const UserInfo = (props) => {
    const {username} = props;
    const [cartItems, setCartItems] = useState(0);

    async function fetchBuyList() {
        try {
            const response = await getBuyList(username);
            setCartItems(response.data.length);
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        fetchBuyList().then((data) => {

        });
    }, []);

    return (
        <div className="account">
            <span className="username">#{username}</span>

            {cartItems > 0 ? (
                <div className="brown-cart-product">
                    <span className="cart-text">Cart</span>
                    <span className="cart-number">{cartItems}</span>
                </div>
            ) : (
                <div className="cart-product">
                    <span className="cart-text">Cart</span>
                    <span className="cart-number">{cartItems}</span>
                </div>
            )}

        </div>
    )
}
const Header = ({updateCommodities}) => {
    const username = sessionStorage.getItem('username');

    return (
        <div className={"header"}>
            <Logo/>
            <Searchbar updateCommodities={updateCommodities}/>
            {username ? <UserInfo username={username}/> : <RegisterLogin/>}
        </div>

    )
}

export default Header;
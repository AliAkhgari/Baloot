import "../styles/header.css";
import React, {useEffect, useState} from "react";
import search from "../assets/images/icons/search.png";
import {Link} from "react-router-dom";
import logo from "../assets/images/logo.png";
import {getBuyList} from "../api/buyList.js";

const Logo = () => {
    return (
        <Link to={`/`}>
            <div className={"logo-container"}>
                <a href={"/"}>
                    <img src={logo} alt={"logo"} className={"logo"}/>
                </a>
                <span className={"logo-name"}>Baloot</span>
            </div>
        </Link>
    )
}

const RegisterLogin = () => {
    return (
        <div className="login-signup">
            <Link to="/signup">
                <input type="button" value="Register" id="signup-button"/>
            </Link>
            <Link to="/login">
                <input type="button" value="Login" id="login-button"/>
            </Link>
        </div>
    )
}

const Searchbar = (props) => {
    const {fetchSearchedCommodities} = props;
    const [selectedOption, setSelectedOption] = useState("name");
    const [searchValue, setSearchValue] = useState("");

    const handleOptionChange = (event) => {
        setSelectedOption(event.target.value);
    };
    const handleSearchChange = (event) => {
        setSearchValue(event.target.value);
    };

    const handleSearchClick = async () => {
        try {
            await fetchSearchedCommodities(selectedOption, searchValue);
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
            <Link to={`/user`} className="username">#{username}</Link>

            <Link to={`/user`}>
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
            </Link>

        </div>
    )
}
const Header = ({fetchSearchedCommodities, showSearchbar}) => {
    const username = sessionStorage.getItem('username');

    return (
        <div className={"header"}>
            <Logo/>
            {showSearchbar && <Searchbar fetchSearchedCommodities={fetchSearchedCommodities}/>}
            {username ? <UserInfo username={username}/> : <RegisterLogin/>}
        </div>

    )
}

export default Header;
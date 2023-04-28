import "../styles/header.css";
import Logo from "../components/header/logo/logo.js"
import Searchbar from "../components/header/searchbar/searchbar.js";
import RegisterLogin from "../components/header/register_login/register_login.js";
import UserInfo from "../components/header/user_info/user_info.js";

const Header = () => {
    const username = sessionStorage.getItem('username');

    return (
        <div className={"header"}>
            <Logo/>
            <Searchbar/>
            {username ? <UserInfo username={username} /> : <RegisterLogin />}
        </div>

    )
}

export default Header;


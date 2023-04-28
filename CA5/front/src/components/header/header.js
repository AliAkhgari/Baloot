import "../../styles/header.css";
import Logo from "./logo/logo.js"
import Searchbar from "./searchbar/searchbar.js";
import RegisterLogin from "./register_login/register_login.js";
import UserInfo from "./user_info/user_info.js";

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


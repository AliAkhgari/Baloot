import logo from "../../../assets/images/logo.png";
import "./logo.css"

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

export default Logo;
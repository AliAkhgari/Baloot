import "./register_login.css"
import {Link} from "react-router-dom";

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

export default RegisterLogin;
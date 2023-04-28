import "./user_info.css"
const UserInfo = (props) => {
    const { username } = props;

    return (
        <div className="account">
            <span className="username">#{username}</span>
            <div className="cart-product">
                <span className="cart-text">Cart</span>
                <span className="cart-number">0</span>
            </div>
        </div>
    )
}

export default UserInfo;
import search from "../../../assets/images/icons/search.png";
import "./searchbar.css"
import {useState} from "react";
import {searchCommodities} from "../../../api/commodities.js";

const Searchbar = () => {
    const [selectedOption, setSelectedOption] = useState("name");
    const [searchValue, setSearchValue] = useState("");

    const handleOptionChange = (event) => {
        setSelectedOption(event.target.value);
    };
    const handleSearchChange = (event) => {
        setSearchValue(event.target.value);
    };

    async function fetchCommodities(searchOption, searchValue) {
        try {
            const response = await searchCommodities(searchOption, searchValue);
            return response.data;
        } catch (error) {
            return [];
        }
    }

    const handleSearchClick = async () => {
        try {
            const commodities = await fetchCommodities(selectedOption, searchValue);
            console.log(commodities);

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

export default Searchbar;
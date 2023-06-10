import {configureStore, createSlice} from '@reduxjs/toolkit';

import {persistReducer, persistStore} from 'redux-persist';
import storage from 'redux-persist/lib/storage'

const initialState = {
    cart: {}
};

const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {
        addToCart: (state, action) => {
            const {id} = action.payload;
            if (state.cart[id]) {
                state.cart[id] += 1;
            } else {
                state.cart[id] = 1;
            }
        },
        removeFromCart: (state, action) => {
            const {id} = action.payload;
            if (state.cart[id] && state.cart[id] > 0) {
                state.cart[id] -= 1;
            }
        },
        reset: (state) => {
            state.cart = {};
        }
    }
});

const persistConfig = {
    key: 'root',
    storage: storage,
};

const persistedReducer = persistReducer(persistConfig, cartSlice.reducer);

const store = configureStore({
    reducer: {
        cart: persistedReducer
    }
});

export const {addToCart, removeFromCart} = cartSlice.actions;
export default store;

const persistor = persistStore(store);

export {persistor};

export const selectCartItem = (state, id) => {
    return state.cart.cart[id];
}


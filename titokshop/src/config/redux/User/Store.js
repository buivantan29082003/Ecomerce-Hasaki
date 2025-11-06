import { combineReducers, createStore } from "redux";
import { carts, keySearch, userInfo } from "./Reducer";

const rootReducer = combineReducers({
  carts: carts,
  userInfo: userInfo,
  keySearch:keySearch
});

const store = createStore(rootReducer);
export default store;
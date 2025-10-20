import { combineReducers, createStore } from "redux";
import { carts, userInfo } from "./Reducer";

const rootReducer = combineReducers({
  carts: carts,
  userInfo: userInfo,
});

const store = createStore(rootReducer);
export default store;
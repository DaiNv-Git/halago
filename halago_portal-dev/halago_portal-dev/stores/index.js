import { configure } from "mobx";
import AuthStoreContext from "./AuthStore";

configure({
  enforceActions: "always",
});

const stores = {
  AuthStoreContext,
};

export default stores;

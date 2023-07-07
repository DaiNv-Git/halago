import { action, makeAutoObservable, makeObservable, observable } from "mobx";
import { createContext } from "react";
import { ACTION_R, USER_B } from "utils/constants";
import { authId, authName, authRole, authToken, authLogo } from "utils/storages";

class AuthStore {
  isAuth = false;
  role = null;
  id = null;
  name = null;
  visibleModal = false;
  action = ACTION_R;
  user = USER_B;
  activeTab = USER_B + "-" + ACTION_R;

  constructor() {
    // makeAutoObservable(this);
    makeObservable(this, {
      isAuth: observable,
      role: observable,
      id: observable,
      name: observable,
      visibleModal: observable,
      action: observable,
      user: observable,
      activeTab: observable,
      setVisibleModal: action.bound,
      setUser: action.bound,
      setAction: action.bound,
      setActiveTab: action.bound,
    });
  }

  login = (data, role) => {
    this.isAuth = true;
    this.role = role;
    this.id = data.id;
    this.name = data.name;
    authToken(data.token);
    authRole(role);
    authId(data.id);
    authName(data.brandName);
    authLogo(data.logo);
  };
  logout = (cb) => {
    this.isAuth = false;
    this.role = null;
    this.id = null;
    this.name = null;
    localStorage.clear();
    cb();
  };

  get auth() {
    return this.isAuth;
  }
  setAuth() {
    this.isAuth = true;
    this.role = authRole();
    this.id = authId();
    this.name = authName();
  }
  setVisibleModal(state) {
    this.visibleModal = state;
  }
  setUser(user) {
    this.user = user;
  }
  setAction(action) {
    this.action = action;
  }
  setActiveTab(tab) {
    this.activeTab = tab;
  }
}

const AuthStoreContext = createContext(new AuthStore());

export default AuthStoreContext;

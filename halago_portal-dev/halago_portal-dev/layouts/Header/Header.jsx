import React, { useContext, useEffect, useState } from "react";
import styles from "./Header.module.scss";
import { ImageLogo } from "assets/images";
import Link from "next/link";
import { useRouter } from "next/router";
import { useTranslation } from "react-i18next";
import { Button, Divider, Form, Input, Modal, Radio, Tabs, Select, Dropdown, Menu } from "antd";
import {
  GlobalOutlined,
  MenuOutlined,
  CloseOutlined,
  CaretDownFilled,
  UserOutlined,
  LogoutOutlined,
} from "@ant-design/icons";
import FacebookLogin from "react-facebook-login";
import { authId } from "utils/storages";
import {
  APP_CAMPAIGN,
  APP_DASHBOARD_B_C,
  APP_DASHBOARD_B_P,
  APP_DASHBOARD_I_C,
  APP_DASHBOARD_I_P,
  APP_HOME,
  APP_INFLUENCER,
  APP_KOL,
  APP_REGISTER_I,
  APP_SEARCH,
  APP_SELLER,
  APP_NEWS,
  APP_ABOUT_US,
  APP_VISION,
} from "utils/paths";
import { loginFbInfluencer, loginPortal } from "services/auth";
import { ACTION_L, ACTION_R, ACTION_F, CODE_API_SUCCESS, ROLE_B, ROLE_I, USER_B, USER_I } from "utils/constants";
import { messageError, messageSuccess } from "utils/messageM";
import { baseRule } from "utils/validation";
import {
  FIELD_BRAND_LOGIN_P,
  FIELD_BRAND_LOGIN_U,
  FIELD_NAME_CREATE_BRAND_EMAIL,
  FIELD_NAME_CREATE_BRAND_NAME,
  FIELD_NAME_CREATE_BRAND_PASSWORD,
  FIELD_NAME_CREATE_BRAND_PHONE,
  FIELD_NAME_CREATE_BRAND_REGISTER,
  LIST_FIELD_BRAND_LOGIN,
  LIST_FIELD_CREATE_BRAND,
} from "./const";
import { insertBrandPortal } from "services/brand";
import AuthStoreContext from "stores/AuthStore";
import { observer } from "mobx-react-lite";
import { authRole, authToken, language, authLogo } from "utils/storages";

const { Option } = Select;
const { SubMenu } = Menu;
const menuArr = [
  {
    key: "home",
    route: APP_HOME,
    children: [
      {
        key: "aboutUs",
        route: APP_ABOUT_US,
      },
      {
        key: "vision",
        route: APP_VISION,
      },
    ],
  },
  {
    key: "campaign",
    route: APP_CAMPAIGN,
  },
  {
    key: "brand",
    route: APP_SELLER,
  },
  {
    key: "kol",
    route: APP_KOL,
  },
  {
    key: "influencer",
    route: APP_INFLUENCER,
  },
  {
    key: "search",
    route: APP_SEARCH,
  },
  {
    key: "news",
    route: APP_NEWS,
  },
];
const layoutForm = {
  labelCol: { span: 6, offset: 2 },
  wrapperCol: { span: 14 },
};

const Header = observer(() => {
  const authStore = useContext(AuthStoreContext);
  const {
    isAuth,
    role,
    visibleModal,
    setVisibleModal,
    user,
    setUser,
    action,
    setAction,
    activeTab,
    setActiveTab,
  } = authStore;
  const { t, i18n } = useTranslation();
  const router = useRouter();
  const { pathname } = router;
  const [form] = Form.useForm();

  const [loading, setLoading] = useState(false);
  const [menuCollapsed, setMenuCollapsed] = useState(false);
  const [currentMenu, setCurrentMenu] = useState("home");
  const [isNavbarSticky, setIsNavbarSticky] = useState(false);

  useEffect(() => {
    const setNavbar = () => {
      let currentScrollPos = window.pageYOffset;
      if (currentScrollPos > 70) {
        if (!isNavbarSticky) setIsNavbarSticky(true);
      } else {
        if (isNavbarSticky) setIsNavbarSticky(false);
      }
    };

    window.addEventListener("scroll", setNavbar);
    return () => window.removeEventListener("scroll", setNavbar);
  }, [isNavbarSticky]); // eslint-disable-line react-hooks/exhaustive-deps

  const onOpenModal = (action) => {
    setAction(action);
    setActiveTab(user + "-" + action);
    if (user === USER_I) setActiveTab(USER_I);
    setVisibleModal(true);
  };
  const onCloseModal = () => setVisibleModal(false);
  const onChangeUser = (e) => {
    const currentUser = e.target.value;
    setUser(currentUser);
    if (currentUser === USER_I) return setActiveTab(USER_I);
    return setActiveTab(currentUser + "-" + action);
  };
  const onChangeAction = (currentAction) => {
    setAction(currentAction);
    if (user === USER_I) return setActiveTab(USER_I);
    return setActiveTab(user + "-" + currentAction);
  };
  const handleCollapseMenu = () => setMenuCollapsed(!menuCollapsed);
  const handleSwitchLang = (lang) => {
    i18n.changeLanguage(lang);
    language(lang);
    window.location.reload();
  };
  const cb = async (r) => {
    try {
      const { accessToken, picture } = r;
      const request = {
        token: accessToken,
      };
      const { code, message, responseBase } = await loginFbInfluencer(request);
      if (code === CODE_API_SUCCESS) {
        const { id, status, fbId } = responseBase.data;

        // console.log('responseBase.data', responseBase.data)

        switch (status) {
          case 1:
            setVisibleModal(false);
            messageSuccess(message);
            authStore.login(responseBase.data, ROLE_I);
            return router.push(APP_DASHBOARD_I_C);
          case 2:
            setVisibleModal(false);
            return router.push(
              {
                pathname: APP_REGISTER_I,
                query: {
                  id,
                  fbId: fbId || r.id,
                  accessToken,
                  avatar: picture.data.url,
                },
              },
              APP_REGISTER_I,
            );
          default:
            messageError(message);
            authStore.logout(() => router.push(APP_HOME));
            return;
        }
      } else {
        messageError(message);
        authStore.logout(() => router.push(APP_HOME));
        return;
      }
    } catch (error) {
      console.log(error);
      messageError(error.toString());
    }
  };
  const onRegister = async () => {
    await form.validateFields(LIST_FIELD_CREATE_BRAND);
    try {
      setLoading(true);
      const values = form.getFieldsValue(LIST_FIELD_CREATE_BRAND);
      const { code, message, responseBase } = await insertBrandPortal({
        ...values,
        description: "",
        website: "",
      });
      if (code === CODE_API_SUCCESS) {
        authStore.login(responseBase.data, ROLE_B);
        messageSuccess(message);
        setVisibleModal(false);
        return router.push(APP_DASHBOARD_B_P);
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString());
    } finally {
      setLoading(false);
    }
  };
  const onLogin = async () => {
    await form.validateFields(LIST_FIELD_BRAND_LOGIN);
    try {
      setLoading(true);
      const values = form.getFieldsValue(LIST_FIELD_BRAND_LOGIN);
      const { code, message, responseBase } = await loginPortal({ username: values.username, password: values.pass });
      if (code === CODE_API_SUCCESS) {
        authStore.login(responseBase.data, ROLE_B);
        messageSuccess(message);
        setVisibleModal(false);
        return router.push(APP_DASHBOARD_B_C);
      } else {
        messageError(message);
      }
    } catch (error) {
      messageError(error.toString());
    }
  };
  useEffect(() => {
    if (typeof window !== "undefined") {
      if (authToken() && authRole() && authRole()) {
        authStore.setAuth();
      }
      i18n.changeLanguage(language());
    }
  }, []);

  useEffect(() => {
    switch (pathname) {
      case APP_HOME:
        setCurrentMenu("home");
        break;

      case APP_CAMPAIGN:
        setCurrentMenu("campaign");

        break;

      case APP_KOL:
        setCurrentMenu("kol");

        break;

      case APP_SELLER:
        setCurrentMenu("brand");

        break;

      case APP_INFLUENCER:
        setCurrentMenu("influencer");

        break;

      case APP_SEARCH:
        setCurrentMenu("search");

        break;

      case APP_NEWS:
        setCurrentMenu("news");

        break;

      case APP_ABOUT_US:
        setCurrentMenu("aboutUs");

        break;

      case APP_VISION:
        setCurrentMenu("vision");

        break;

      default:
        break;
    }
  }, []);

  const handleClick = (e) => {
    switch (e.key) {
      case "home":
        router.push(APP_HOME);
        break;

      case "campaign":
        router.push(APP_CAMPAIGN);

        break;

      case "kol":
        router.push(APP_KOL);

        break;

      case "brand":
        router.push(APP_SELLER);

        break;

      case "influencer":
        router.push(APP_INFLUENCER);

        break;

      case "search":
        router.push(APP_SEARCH);

        break;

      case "news":
        router.push(APP_NEWS);

        break;

      case "aboutUs":
        router.push(APP_ABOUT_US);

        break;

      case "vision":
        router.push(APP_VISION);

        break;

      default:
        break;
    }
  };

  const renderMenus = () => {
    return menuArr.map((item) => {
      if (!item.children) {
        return (
          <Menu.Item style={{ fontWeight: "bold" }} key={item.key}>
            {t(`header.${item.key}`)}
          </Menu.Item>
        );
      }

      return (
        <SubMenu key={item.key} style={{ fontWeight: "bold" }} title={t(`header.${item.key}`)}>
          {renderChildMenu(item.children)}
        </SubMenu>
      );
    });
  };

  const renderChildMenu = (arr) => {
    return arr.map((item) => {
      return (
        <Menu.Item style={{ fontWeight: "bold" }} key={item.key}>
          {t(`header.${item.key}`)}
        </Menu.Item>
      );
    });
  };

  return (
    <>
      <div
        className={styles.header}
        style={
          isNavbarSticky
            ? {
                position: "fixed",
                top: 0,
                backgroundColor: "#ffffff",
                zIndex: 100,
                borderBottom: "1px solid #eee",
              }
            : {}
        }
      >
        <div className={`container-main ${styles.header_wrapper}`}>
          <div className={styles.header_left}>
            <Link href={APP_HOME} passHref>
              <a>
                <img className={styles.header_logo} src={ImageLogo} />
              </a>
            </Link>
            <Menu onClick={handleClick} selectedKeys={[currentMenu]} mode="horizontal">
              {renderMenus()}
            </Menu>
            {/* <ul className={styles.menu}>
              {menuArr.map((item) => (
                <li key={item.key} className={styles.menu_item}>
                  <Link href={`${item.route}`} passHref>
                    <a
                      style={{
                        color: pathname === item.route ? "#02a7f6" : null,
                      }}
                    >
                      {t(`header.${item.key}`)}
                    </a>
                  </Link>
                </li>
              ))}
            </ul> */}
          </div>
          <div className={styles.header_right}>
            {isAuth ? (
              <div className="mr-16">
                <Dropdown
                  trigger={["click", "hover"]}
                  placement="bottomRight"
                  arrow
                  overlay={
                    <Menu>
                      <Menu.Item
                        onClick={() =>
                          role === ROLE_B
                            ? router.push(APP_DASHBOARD_B_P)
                            : role === ROLE_I
                            ? router.push(APP_DASHBOARD_I_P)
                            : null
                        }
                        style={{ fontWeight: "bold" }}
                      >
                        {t("header.profile")}
                      </Menu.Item>
                      <Menu.Item
                        style={{ fontWeight: "bold" }}
                        onClick={() =>
                          role === ROLE_B
                            ? router.push(APP_DASHBOARD_B_C)
                            : role === ROLE_I
                            ? router.push(APP_DASHBOARD_I_C)
                            : null
                        }
                      >
                        {t("header.myCampaign")}
                      </Menu.Item>
                      <Menu.Item
                        style={{ fontWeight: "bold" }}
                        onClick={() => authStore.logout(() => router.push(APP_HOME))}
                      >
                        <LogoutOutlined style={{ fontSize: 18 }} /> Đăng xuất
                      </Menu.Item>
                    </Menu>
                  }
                >
                  <div>
                    <Button
                      type="text"
                      icon={
                        authLogo() ? (
                          <img className={styles.logo} src={authLogo()} />
                        ) : (
                          <UserOutlined style={{ fontSize: 18 }} />
                        )
                      }
                    />
                  </div>
                </Dropdown>
              </div>
            ) : (
              <>
                <Button
                  // type="link"
                  type="default"
                  className={pathname === APP_HOME ? styles.header_register_home : styles.header_register}
                  onClick={() => onOpenModal(ACTION_L)}
                >
                  {t("header.login")}
                </Button>
                <Button
                  type="default"
                  className={pathname === APP_HOME ? styles.header_register_home : styles.header_register}
                  onClick={() => onOpenModal(ACTION_R)}
                >
                  {t("header.register")}
                </Button>
              </>
            )}
            <GlobalOutlined className={pathname === APP_HOME ? styles.header_icon_home : styles.header_icon} />
            <Button
              type="text"
              onClick={() => handleSwitchLang("eng")}
              className={pathname === APP_HOME ? styles.header_language_home : styles.header_language}
              style={{ opacity: i18n.language === "eng" ? 1 : 0.5 }}
            >
              ENG
            </Button>
            <Divider
              type="vertical"
              className={pathname === APP_HOME ? styles.header_divider_home : styles.header_divider}
            />
            <Button
              type="text"
              onClick={() => handleSwitchLang("vie")}
              className={pathname === APP_HOME ? styles.header_language_home : styles.header_language}
              style={{ opacity: i18n.language === "vie" ? 1 : 0.5 }}
            >
              VIE
            </Button>
          </div>
        </div>
        <Modal
          className={styles.modal}
          visible={visibleModal}
          title={null}
          footer={null}
          onOk={onOpenModal}
          onCancel={onCloseModal}
          closable={false}
          width={844}
        >
          <div className={styles.modal_content}>
            <div className={styles.modal_header}>
              <div className={styles.modal_header_left}>
                <Button
                  className={`${styles.modal_header_left_register} ${
                    action === ACTION_R ? styles.modal_header_left_action_active : ""
                  }`}
                  type="link"
                  onClick={() => onChangeAction(ACTION_R)}
                >
                  {t("header.modal.btnRegister")}
                </Button>
                <Button
                  className={`${styles.modal_header_left_login} ${
                    action === ACTION_L ? styles.modal_header_left_action_active : ""
                  }`}
                  type="link"
                  onClick={() => onChangeAction(ACTION_L)}
                >
                  {t("header.modal.btnLogin")}
                </Button>
              </div>
              <div className={styles.modal_header_right}>
                <Radio.Group className={styles.modal_header_right_user} value={user} onChange={(e) => onChangeUser(e)}>
                  <Radio.Button
                    className={`${styles.modal_header_right_seller} ${
                      user === USER_B ? styles.modal_header_right_user_active : null
                    }`}
                    value={USER_B}
                    type="text"
                  >
                    Brand
                  </Radio.Button>
                  <Radio.Button
                    className={`${styles.modal_header_right_reviewer} ${
                      user === USER_I ? styles.modal_header_right_user_active : null
                    }`}
                    value={USER_I}
                  >
                    Influencer
                  </Radio.Button>
                </Radio.Group>
              </div>
            </div>
            <div className={styles.modal_body}>
              <Tabs activeKey={activeTab} className={styles.modal_body_tabs}>
                <Tabs.TabPane key={USER_B + "-" + ACTION_R}>
                  <h3 className={styles.modal_body_tabs_title}>
                    {t("header.modal.titleRegisterBrand")}
                    <br />
                    {/* <span className={styles.modal_body_tabs_title_more}>Seller</span> */}
                  </h3>
                  <Form form={form} {...layoutForm} className={styles.modal_body_tabs_form}>
                    <Form.Item
                      name={FIELD_NAME_CREATE_BRAND_REGISTER}
                      rules={[...baseRule]}
                      label={t("header.modal.labelName")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input
                        placeholder={t("header.modal.plhName")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item
                      name={FIELD_NAME_CREATE_BRAND_EMAIL}
                      rules={[...baseRule]}
                      label={t("header.modal.labelEmail")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input
                        placeholder={t("header.modal.plhEmail")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item
                      name={FIELD_NAME_CREATE_BRAND_PHONE}
                      rules={[...baseRule]}
                      label={t("header.modal.labelPhone")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input
                        placeholder={t("header.modal.plhPhone")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item
                      name={FIELD_NAME_CREATE_BRAND_NAME}
                      rules={[...baseRule]}
                      label={t("header.modal.labelBrandName")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input
                        placeholder={t("header.modal.plhBrandName")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item
                      name={FIELD_NAME_CREATE_BRAND_PASSWORD}
                      rules={[...baseRule]}
                      label={t("header.modal.labelPassword")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input.Password
                        placeholder={t("header.modal.plhPassword")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item label=" " className={styles.modal_body_tabs_form_item}>
                      <Button onClick={onRegister} className={styles.modal_body_tabs_form_item_button}>
                        {t("header.modal.btnRegister")}
                      </Button>
                    </Form.Item>
                  </Form>
                </Tabs.TabPane>
                <Tabs.TabPane key={USER_B + "-" + ACTION_L}>
                  <h3 className={styles.modal_body_tabs_title}>
                    {t("header.modal.titleLoginBrand")}
                    <br />
                    {/* <span className={styles.modal_body_tabs_title_more}>Seller</span> */}
                  </h3>
                  <Form form={form} {...layoutForm} className={styles.modal_body_tabs_form}>
                    <Form.Item
                      name={FIELD_BRAND_LOGIN_U}
                      rules={[...baseRule]}
                      label={t("header.modal.labelEmail")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input
                        placeholder={t("header.modal.plhEmail")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item
                      name={FIELD_BRAND_LOGIN_P}
                      rules={[...baseRule]}
                      label={t("header.modal.labelPassword")}
                      className={styles.modal_body_tabs_form_item}
                    >
                      <Input.Password
                        placeholder={t("header.modal.plhPassword")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    {/* <Form.Item label=" " className={styles.modal_body_tabs_form_item}>
                      <a onClick={() => setActiveTab("forgot")} className={styles.modal_body_tabs_form_item_a}>
                        Quên mật khẩu?
                      </a>
                    </Form.Item> */}
                    <Form.Item label=" " className={styles.modal_body_tabs_form_item}>
                      <Button onClick={onLogin} className={styles.modal_body_tabs_form_item_button}>
                        {t("header.modal.btnLogin")}
                      </Button>
                    </Form.Item>
                  </Form>
                </Tabs.TabPane>
                <Tabs.TabPane key={ACTION_F}>
                  <h3 className={styles.modal_body_tabs_title}>
                    {t("header.modal.titleResetPassword")}
                    <br />
                    <span className={styles.modal_body_tabs_title_more}>
                      {t("header.modal.textInputEmailResetPassword")}
                    </span>
                  </h3>
                  <Form {...layoutForm} className={styles.modal_body_tabs_form}>
                    <Form.Item label={t("header.modal.labelEmail")} className={styles.modal_body_tabs_form_item}>
                      <Input
                        placeholder={t("header.modal.plhEmail")}
                        className={styles.modal_body_tabs_form_item_input}
                      />
                    </Form.Item>
                    <Form.Item label=" " className={styles.modal_body_tabs_form_item}>
                      <Button className={styles.modal_body_tabs_form_item_button} style={{ width: 200 }}>
                        {t("header.modal.titleResetPassword")}
                      </Button>
                    </Form.Item>
                  </Form>
                </Tabs.TabPane>
                <Tabs.TabPane key={USER_I}>
                  <h3 className={styles.modal_body_tabs_title}>
                    {t("header.modal.titleLoginInfluencer")}
                    <br />
                    {/* <span className={styles.modal_body_tabs_title_more}>Reviewer</span> */}
                  </h3>
                  <div className="text-center">
                    {/* <img src={IconFb} alt="" />
                    <Button className={styles.modal_body_tabs_button_fb}>đăng nhập bằng facebook</Button> */}
                    <FacebookLogin
                      appId="554203175818374"
                      fields="name,email,picture"
                      autoLoad={false}
                      callback={cb}
                      isMobile={false}
                    />
                  </div>
                </Tabs.TabPane>
              </Tabs>
            </div>
          </div>
        </Modal>
      </div>
      <div className="header-mobile">
        <div className="container">
          <nav className="navbar navbar-expand-md">
            <div className="header-mobile__left">
              <Link href="/" passHref>
                <a className="navbar-brand">
                  <img src={ImageLogo} alt="Halago Logo" />
                </a>
              </Link>
            </div>
            <div>
              <Button
                className="navbar-toggler"
                onClick={handleCollapseMenu}
                // onBlur={this.collapseMenu}
                type="button"
              >
                {menuCollapsed ? <CloseOutlined /> : <MenuOutlined />}
              </Button>
            </div>

            <div className={"navbar-collapse " + (menuCollapsed || "hidden")}>
              <div className="header-top">
                <div className="container">
                  <div className="mobile-top flex">
                    {isAuth ? (
                      <p style={{ fontWeight: "bold" }} onClick={() => authStore.logout(() => router.push(APP_HOME))}>
                        Đăng xuất
                      </p>
                    ) : (
                      <div className="mobile-top__login" style={{ cursor: "pointer", fontWeight: "bold" }}>
                        <span onClick={() => onOpenModal(ACTION_L)}>Đăng nhập</span>
                        <span> | </span>
                        <span onClick={() => onOpenModal(ACTION_R)}>Đăng ký</span>
                      </div>
                    )}

                    <div className="mobile-top__lang flex">
                      <GlobalOutlined className={styles.header_icon} />
                      <Select
                        defaultValue={i18n.language || "vn"}
                        suffixIcon={<CaretDownFilled />}
                        onChange={handleSwitchLang}
                      >
                        <Option value="vie">Vi</Option>
                        <Option value="eng">En</Option>
                      </Select>
                    </div>
                  </div>
                </div>
              </div>
              <div className="header-menu">
                <ul className="navbar-nav">
                  {menuArr.map((item) =>
                    item.children ? (
                      item.children.map((child) => (
                        <li key={child.key} className={`mobile-menu ${styles.menu_item}`}>
                          <Link href={`${child.route}`} passHref>
                            <a style={{ color: pathname === child.route ? "#02a7f6" : null }}>
                              {t(`header.${child.key}`)}
                            </a>
                          </Link>
                        </li>
                      ))
                    ) : (
                      <li key={item.key} className={`mobile-menu ${styles.menu_item}`}>
                        <Link href={`${item.route}`} passHref>
                          <a style={{ color: pathname === item.route ? "#02a7f6" : null }}>{t(`header.${item.key}`)}</a>
                        </Link>
                      </li>
                    ),
                  )}
                </ul>
              </div>
            </div>
          </nav>
        </div>
      </div>
    </>
  );
});

export default Header;

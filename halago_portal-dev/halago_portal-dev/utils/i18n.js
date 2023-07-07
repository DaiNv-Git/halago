import i18n from "i18next";
import { initReactI18next } from "react-i18next";

import eng from "assets/locales/eng.json";
import vie from "assets/locales/vie.json";

const resources = {
  eng: {
    translation: eng,
  },
  vie: {
    translation: vie,
  },
};

i18n.use(initReactI18next).init({
  resources,
  lng: "vie",
  interpolation: {
    escapeValue: false,
  },
});

export default i18n;

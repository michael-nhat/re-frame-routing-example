(ns routes-example.main-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events]
   [routes-example.my-routes :as routes]
   [routes-example.subs :as subs]
   [routes-example.home.home-view]
   [routes-example.other.about-view]
   [routes-example.account.login.login-view]
   [routes-example.account.login.register-view]
   [routes-example.apis.main]))

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div {:id "main-container"}
     [:div {:id "wrap-nav-bar"}
      [:ul {:id "nav-bar"}
       [:li {:id "nav-home" :class "nav-item"} [:a {:href "/"} "Home"]]
       [:li {:id "nav-book" :class "nav-item"} [:a {:href "/book"} "Book"]]
       [:li {:id "nav-book" :class "nav-item"} [:a {:href "/about"} "About"]]
       [:li {:id "nav-login" :class "nav-item"} [:a {:href "/login"} "Login"]]
       [:li {:id "nav-register" :class "nav-item"} [:a {:href "/register"} "Register"]]]]
     [:div {:id "page-container"} (routes/panels @active-panel)]]))

(defmethod routes/panels :main-panel [] [main-panel])

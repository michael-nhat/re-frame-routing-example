(ns routes-example.views
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.routes :as routes]
   [routes-example.subs :as subs]))

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h3
      (str "Hello from " @name "! Welcome!")]
     [:div
      [:a  {:on-click #(re-frame/dispatch
                        [::events/navigate [:about]]) :href "/about"}
       "Go to About Page"]]]))

(defmethod routes/panels :home-panel [] [home-panel])

(defn about-panel []
  [:div
   [:h1 "Books news, books hot"]
   [:div
    [:a {:href "/" :on-click #(re-frame/dispatch [::events/navigate [:home]])}
     "Go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div {:id "main-container"}
     [:div {:id "wrap-nav-bar"}
      [:ul {:id "nav-bar"}
       [:li {:id "nav-home" :class "nav-item"} [:a {:href "/"} "Home"]]
       [:li {:id "nav-book" :class "nav-item"} [:a {:href "/book"} "Book"]]
       [:li {:id "nav-book" :class "nav-item"} [:a {:href "/about"} "About"]]
       [:li {:id "nav-login" :class "nav-item"} [:a {:href "/login"} "Login"]]
       [:li {:id "nav-register" :class "nav-item"} [:a {:href "/login"} "Register"]]]]
     [:div {:id "page-container"} (routes/panels @active-panel)]]))

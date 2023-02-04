(ns routes-example.views
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.routes :as routes]
   [routes-example.subs :as subs]))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      (str "Hello from " @name ". This is the Home Page.")]
     [:div
      [:a  {:on-click #(re-frame/dispatch [::events/navigate [:about]]) :href "/about"}
       "go to About Pagexx"]]]))

(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]
   [:div
    [:a {:href "/" :on-click #(re-frame/dispatch [::events/navigate [:home]])}
     "go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div "this is on every page"
     [:ul {:id "nav-bar"}
      [:li {:id "nav-home" :class "nav-item"} [:a "Home"]]
      [:li {:id "nav-book" :class "nav-item"} [:a "Books"]]
      [:li {:id "nav-book" :class "nav-item"} [:a "About"]]
      [:li {:id "nav-book" :class "nav-item"} [:a "Chart"]]]
     [:div (routes/panels @active-panel)]]))

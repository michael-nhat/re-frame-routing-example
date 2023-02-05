(ns routes-example.home.home-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.my-routes :as routes]))

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h3
      (str "Hello from " @name "! Home page!")]
     [:div
      [:a  {;; :on-click #(re-frame/dispatch
            ;;             [::events/navigate [:login]])
            :href "/login"
            }
       "Go to About Page"]]]))

(defmethod routes/panels :home-panel [] [home-panel])

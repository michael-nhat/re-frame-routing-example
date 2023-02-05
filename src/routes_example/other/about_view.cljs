(ns routes-example.other.about-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.routes :as routes]))

(defn about-panel []
  [:div
   [:h3 "About us: ..."]
   [:div
    [:a {:href "/" ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
         }
     "Go to Home Page"]]])

(defmethod routes/panels :about-panel [] [about-panel])

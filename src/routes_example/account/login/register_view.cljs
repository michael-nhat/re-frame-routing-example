(ns routes-example.account.login.register-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.my-routes :as routes]))

(defn register-panel []
  [:div
   [:h3 "About us: ..."]
   [:div
    [:a {:href "/" ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
         }
     "Go to Home Page"]]])

(defmethod routes/panels :register-panel [] [register-panel])

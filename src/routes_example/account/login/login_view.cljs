(ns routes-example.account.login.login-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.routes :as routes]))

(defn login-panel []
  [:div
   [:h3 "Login"]
   [:div
    [:a {:href "/" ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
         }
     "Go to Home Page"]]])

(defmethod routes/panels :login-panel [] [login-panel])



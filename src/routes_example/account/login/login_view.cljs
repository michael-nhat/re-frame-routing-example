(ns routes-example.account.login.login-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.my-routes :as routes]
   [reagent.core :as r :refer [atom]]
   [routes-example.other.share.component.input :refer [username-form password-form]]
   [routes-example.apis.main :as api]))

(defn login-panel []
  (let [name (atom nil)
        password (atom nil)]
    [:div
     [:h2 "Login"]
     [:div
      [:div {:class "signup-wrapper"}
       [:form
        [username-form name]
        [password-form password]]
       [:button {:on-click #(api/j-post
                             "login"
                             {:body {:username "nhat" :password "123456"}}
                             (fn [user] (re-frame/dispatch [:events/login-success user]))
                             (fn [_] (re-frame/dispatch [::events/login-failed]))
                                        )} "Login"]]
      ;; if check pass ok dispatch , if wrong, dispatch wrong info
      [:a {:href "/forgot-password"
           ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
           }
       "Dis you miss something? Get support"]]]))

;; if click -> login success -> set session data (local storage)
;; if error -> is network, notifi network
;; if error -> return error -> Login info might be wrong

(defmethod routes/panels :login-panel [] [login-panel])

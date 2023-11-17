(ns routes-example.account.login.register-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.my-routes :as routes]
   ;; [routes-example.account.login.login-view :refer [input-element email-prompt email-form email-form]]
   [reagent.core :as r]))

(defn input-element
  "An input element which updates its value on change"
  [id name type value]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn email-input
  [email-address-atom]
  (input-element "email" "email" "email" email-address-atom))

(defn atom-input [value]
  [:input {:class "form-control"
           :type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn register-panel []
  (let [name (r/atom "")
        val (r/atom "")]
    (fn []
      [:div
       [:h3 "Register: "]
       [:div
        [:form
         [:p [atom-input val]]]
        [:form
         [email-input name]]
        [:a {:href "/" ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
             }
         "Go to Home Page"]]])))

(defmethod routes/panels :register-panel [] [register-panel])

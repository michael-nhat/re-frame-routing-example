(ns routes-example.account.login.login-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.my-routes :as routes]
   [reagent.core :as r :refer [atom]]))

(defn prompt-message
  "A prompt that will animate to help the user with a given input"
  [message]
  [:div {:class "my-messages"}
   [:div {:class "prompt message-animation"} [:p message]]])

;;specific function
(defn email-prompt
  []
  (prompt-message "What's your email address?"))

(defn input-element
  "An input element which updates its value and on focus parameters on change, blur, and focus"
  [id name type value in-focus]
  [:input {:id id
           :name name
           :class "form-control"
           :type type
           :required ""
           :value @value
           :on-change #(reset! value (-> % .-target .-value))
           ;; Below we change the state of in-focus
           :on-focus #(swap! in-focus not)
           :on-blur #(swap! in-focus not)}])

(defn input-and-label
  "Creates an input box and a prompt box that appears above the input when the input comes into focus. Also throws in a little required message"
  [label-value input-name input-type input-element-arg notice-ele required?]
  (let [input-focus (atom false)]
    (fn []
      [:div
       [:label label-value]
       (if @input-focus notice-ele [:div])
       [input-element input-name input-name input-type input-element-arg input-focus]
       (if (and required? (= "" @input-element-arg))
         [:div "Field is required!"]
         [:div])])))

(defn email-form
  [email-address-atom]
  (input-and-label "Email: "
                   "email"
                   "email"
                   email-address-atom
                   nil
                   true))

(defn username-form [name-atom]
  (input-and-label "Username: "
                   "user-name"
                   "text"
                   name-atom
                   nil
                   true))
(defn password-form [password-atom]
  (input-and-label "Password"
                   "password"
                   "password"
                   password-atom
                   nil
                   true))

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
       [:button "Login"]]

      [:a {:href "/forgot-password"
           ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
           }
       "Dis you miss something? Get support"]]]))

(defmethod routes/panels :login-panel [] [login-panel])

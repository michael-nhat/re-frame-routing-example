(ns routes-example.account.login.login-view
  (:require
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.subs :as subs]
   [routes-example.my-routes :as routes]))
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

(defn input-and-prompt
  "Creates an input box and a prompt box that appears above the input when the input comes into focus. Also throws in a little required message"
  [label-value input-name input-type input-element-arg prompt-element required?]
  (let [input-focus (atom false)]
    (fn []
      [:div
       [:label label-value]
       (if @input-focus prompt-element [:div])
       [input-element input-name input-name input-type input-element-arg input-focus]
       (if (and required? (= "" @input-element-arg))
         [:div "Field is required!"]
         [:div])])))

(defn email-form
  [email-address-atom]
  (input-and-prompt "email"
                    "email"
                    "email"
                    email-address-atom
                    [prompt-message "What's your email?"]
                    true))

(defn name-form [name-atom]
  (input-and-prompt "name"
                    "name"
                    "text"
                    name-atom
                    (prompt-message "What's your name?")
                    true))
(defn password-form [password-atom]
  (input-and-prompt "password"
                    "password"
                    "password"
                    password-atom
                    (prompt-message "What's your password?")
                    true))

(defn login-panel []
  (let [email-address (atom nil)
        name (atom nil)
        password (atom nil)]
    [:div
     [:h2 "Login"]
     [:div
      [:div {:class "signup-wrapper"}
       [:h2 "Welcome to TestChimp"]
       [:form
        [email-form email-address]
        [name-form name]
        [password-form password]]]

      [:a {:href "/forgot-password"
           ;; :on-click #(re-frame/dispatch [::events/navigate [:home]])
           }
       "do you miss something? Get support"]]]))

(defmethod routes/panels :login-panel [] [login-panel])

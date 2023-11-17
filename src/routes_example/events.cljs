(ns routes-example.events
  (:require
   [re-frame.core :as re-frame]
   [routes-example.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ;; [day8.re-frame.http-fx]
   [ajax.core :as ajax]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))

(re-frame/reg-event-fx
 ::set-route
 (fn-traced [{:keys [db]} [_ route]]
            {:db (assoc db :route route)}))

(re-frame/reg-event-db
 ::login-success
 (fn [db [_ result]]
   (print 'succ)
   (prn result)
   (assoc db :success-http-result result)))

(re-frame/reg-event-db
 ::login-failure
 (fn [db [_ result]]
   (print 'fail)
   (prn result)
   (assoc db :failure-http-result result)))

(re-frame/reg-event-fx
 :login
 (fn [{:keys [db]} _]
   (print "login")
   {:db (assoc db :loading true)
    :http-xhrio {:method :post
                 :uri "http://localhost:8081/login"
                 :params {:username "nhat" :password "123456"}
                 :on-success [::login-success]
                 :on-failure [::login-failure]
                 :timeout 5000
                 :response-format (ajax/json-response-format {:keywords? key})
                 :format (ajax/json-request-format)}}))

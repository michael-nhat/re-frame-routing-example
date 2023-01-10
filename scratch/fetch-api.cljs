(ns user
  ;; already require
  ;; (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [reagent.dom :as rdom]
   [clojure.pprint :as pp]
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.routes :as routes]
   [routes-example.views :as views]
   [routes-example.config :as config]
   [routes-example.users-index.views]
   [routes-example.user-view.views]
   [ajax.core :as ajax]
   [cljs-http.client :as http]
   ;; [cljs.core.async :refer [<!]]
   [goog.string :as str]
   [promesa.core :as p]
   [clojure.core.async :as a]))

(def d "https://dummyjson.com/")

(def response-product
  ;; (println "res: ")
  (atom
   {:data {}}
   :validator
   (fn
     [val]
     (println "changed: %s" val)
     val)))

(def response-product
  ;; (println "res: ")
  (atom
   {:data {}}
   :validator
   (fn
     [val]
     (println "changed: %s" val)
     val)))

(defn handler [response]
  ;; (.log js/console (str response))
  (pp/pprint
   "wtf res:"
   (str response)))

(defn error-handler [err]
  ;; (.log js/console (str "something bad happened: " status " " status-text))
  (pp/pprint "erro:" err)
  (swap! response-product assoc :err err))

(defn set-data [data]
  (swap!
   response-product
   #(assoc %1 :data
           {:a 3})))
(set-data nil)

(defn handler2 [response]
  ;; (.log js/console (str response))
  (swap! response-product assoc :data
         response)
  (pp/pprint response)
  )

(def my-atom (atom 0 :validator #((print "wtf") 3)))
(swap! my-atom (fn [a] 3) 3)

(def
  m1
  (atom
   {:a "A" :b "B"}
   :validator
   (fn [val] (println (str/format "changed: %s" val)) val)))
(swap! m1 assoc :a "Aaay")

(ajax/GET
  (str/format "%sproducts/1" d)
  {:handler handler2
   :error-handler error-handler})

(ajax/ajax-request {:uri (str/format "%sproducts/add" d)
                  :method :POST
                  :handler handler2
                  :error-handler error-handler
                  :params {:title "BMW Pencil"}
                  :response-format (ajax/json-response-format {:keywords? true})
                  :format (ajax/json-request-format)})

;; wtf this don't work
;; it don't work, so, don't try it
(ajax/POST
  (str/format
   "%sproducts/1" d)
  {:uri (str/format "%sproducts/add" d)
   :handler handler2
   :error-handler error-handler
   :response-format (ajax/json-response-format {:keywords? true})
   :method :POST
   :params {:title "BMW Pencil"}
   :format (ajax/json-request-format)
   ;; :headers
   ;; {"x-csrf-token" csrf-field}
   })

(p/promise
 (fn [resolve reject]
   (ajax/ajax-request {:uri (str/format "%sproducts/1" d)
                       :handler handler
                       :error-handler error-handler
                       :response-format (ajax/json-response-format {:keywords? true})
                       :format (ajax/json-request-format)})))

(->
 (p/promise
  (fn [res rej]
    ;; (ajax/ajax-request {:url (str/format "%sproducts/1" d)
    ;;                   :handler resolve
    ;;                   :error-handler reject
    ;;                   :response-format (ajax/json-response-format {:keywords? true})
    ;;                   :format (ajax/json-request-format)})
    (ajax/GET (str/format "%sproducts/1" d)
            {:headers {"Accept" "application/transit+json"}
             :handler res
             :error-handler rej})))
 (p/then handler2)
 (p/catch error-handler))

(->
 p/promise
 (fn [res rej]))

(ns user2
  ;; already require
  ;; (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [reagent.dom :as rdom]
   [clojure.pprint :as pp]
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.my-routes :as routes]
   [routes-example.views :as views]
   [routes-example.config :as config]
   [routes-example.users-index.views]
   [routes-example.user-view.views]
   [ajax.core :as ajax]
   [cljs-http.client :as http]
   ;; [cljs.core.async :refer [<!]]
   [goog.string :as gs]
   [promesa.core :as pr]
   [clojure.core.async :as a]
   [lambdaisland.fetch :as fetch]
   [kitchen-async.promise :as p]
   [goog.object :as gobj]))

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
   (fn [val] (println (gs/format "changed: %s" val)) val)))

(swap! m1 assoc :a "Aaay")

(ajax/GET
  (api "test")
  {:headers {"Access-Control-Allow-Headers" "Content-Type"
             "Access-Control-Allow-Origin" "*"}
   :handler handler2
   :error-handler error-handler})
;; error-handler

(ajax/ajax-request {:uri (gs/format "%sproducts/add" d)
                  :method :POST
                  :handler handler2
                  :error-handler error-handler
                  :params {:title "BMW Pencil"}
                  :response-format (ajax/json-response-format {:keywords? true})
                  :format (ajax/json-request-format)})

;; wtf this don't work
;; it don't work, so, don't try it
(ajax/POST
  (gs/format
   "%sproducts/1" d)
  {:uri (gs/format "%sproducts/add" d)
   :handler handler2
   :error-handler error-handler
   :response-format (ajax/json-response-format {:keywords? true})
   :method :POST
   :params {:title "BMW Pencil"}
   :format (ajax/json-request-format)
   ;; :headers
   ;; {"x-csrf-token" csrf-field}
   })

(pr/promise
 (fn [resolve reject]
   (ajax/ajax-request {:uri (api "test")
                       :handler handler
                       :error-handler error-handler
                       :response-format (ajax/json-response-format {:keywords? true})
                       :format (ajax/json-request-format)})))

(->
 (pr/promise
  (fn [res rej]
    ;; (ajax/ajax-request {:url (gs/format "%sproducts/1" d)
    ;;                   :handler resolve
    ;;                   :error-handler reject
    ;;                   :response-format (ajax/json-response-format {:keywords? true})
    ;;                   :format (ajax/json-request-format)})
    (ajax/GET (gs/format "%sproducts/1" d)
            {:headers {"Accept" "application/transit+json"}
             :handler res
             :error-handler rej})))
 (pr/then handler2)
 (pr/catch error-handler))

(->
 pn/promise
 (fn [res rej]))

(fetch/get (gs/format "%sproducts/1" d))

(http/get (api "test")
          {:headers {;; "Access-Control-Allow-Headers" "Content-Type"
                     "Access-Control-Allow-Origin" "*"
                     ;; "access-control-request-headers" "Content-Type"
                     }})

(def l "http://localhost:8081/")
(defn api [url]
  (gs/format "%s%s" l url))

(p/try
  (p/let [resp (fetch/get
                (gs/format "%sproducts/1" d)
                {:mode "no-cors"
                 ;; :accept :json
                 ;; :content-type :json
                 })]
    (prn resp))
  (p/catch :default e
     ;; log your exception here
    (prn :error e)))

;; on js must use res.text => .then(res.json
;; p/let return response with p/catch
(p/try
  (p/let [resp (fetch/get
                (api "test")
                ;; {:mode "no-cors"
                ;;  ;; :accept :json
                ;;  ;; :content-type :json
                ;;  }
                )]
    (prn (:body resp)))
  (p/catch :default e
     ;; log your exception here
    (prn :error e)))

(let
 [response (.fetch js/window "http://localhost:8081/test")]
  (-> response (.then #(js/alert %)) (.catch (js/alert "erro"))))

(-> (js/fetch "http://localhost:8081/test")
    (.then (fn [r]
             (when-not (.-ok r)
               (throw (js/Error. "Could not fetch /data")))
             (.json r)))
    (.then (fn [r]
             (prn [:result r])))
    (.catch (fn [e]
              (prn [:error e]))))

;; seem like js/fetch wrap with .json
;; might be error
(-> (fetch/get "http://localhost:8081/test")
    (.then (fn [resp]
             (-> resp
                 :body
                 ;; the actual response is a js object, not a clojure map
                 (prn )))))
(-> (fetch/post "http://localhost:8081/login" {:content-type :json
                                            :body {:username "nhat"}
                                            :accept :json
                                            })
    (.then (fn [resp]
             (-> resp
                 ;; :body
                 ;; the actual response is a js object, not a clojure map
                 (print)))))

(url "test")
(def aa "")
(def insp (atom ""))
(swap! a (fn [%] "dkkk"))
;; this cannot work cannot use js with deref ??
(js/Object.getOwnPropertyNames @a)
(js/Object.getOwnPropertyNames (js/Object.assign {} (js->clj @a)))
(js->clj (js/Object.assign {} (js->clj @a)))

(js/console.log "err: " (str @a) ":endkk")
(js/Object.entries @a)
(js/console.log )
(gs/format "fjas")
(js/JSON.stringify   @a)

(def ep "http://localhost:8082/")

(def insp (atom ""))

(defn set-inspect [val] (swap! insp (fn [_] val)))

(defn default-err-handler [e]
  "sdkjfls"
  (set-inspect e)
  ;; (js/console.log "client error: " (.message e))
  (js/alert (gs/format "Client error: %s" (.-message e))))

@insp
(j-post "login" {:body {:username "nhat" :password "1234k56"}} (fn [data] (print data)))
(js/Object.keys (gobj/clone @a))

;; no
;; good for debug, with many?? (is something still missing?)
(js/console.log (gobj/getAllPropertyNames @a))

;; write hook in send string repl that
;; clear output then dd at the end of buffer could fix that

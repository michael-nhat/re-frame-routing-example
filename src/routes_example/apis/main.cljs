(ns routes-example.apis.main
  (:require
   [kitchen-async.promise :as p]
   [goog.object :as gobj]
   [lambdaisland.fetch :as fetch]
   [clojure.core.async :as a]
   [promesa.core :as pr]
   [goog.string :as gs]
   [goog.string.format]))

(def insp (atom ""))

(defn set-inspect [val] (swap! insp (fn [_] val)))

;;endpoint
(def ep "http://localhost:8081/")
(defn url [api-url] (gs/format "%s%s" ep api-url))

;; api :message :data
;; message when something need to talk to frontend programmer (mainly convention)
;; message require when has error (might be not show to user)
;; no need to add to code
;; format code

(defn default-client-err-handler [e]
  (js/console.log "Client error: " (.-message e))
  ;; (js/alert "Client error: " (gs/format "%s" e))
  )

(defn default-err-handler [e]
  (prn e)
  (print "Server error: " e))

(defn j-opt [opts]
  (assoc opts :content-type :json
         ;; if server don't return correct json form ;; :accept :json
         ))

(defn handle-then-success [then]
  (fn [res]
    (print "res: " res)
    (if (>= (:status res) 300)
      (default-err-handler
       ;; body is #js
       (.-message (:body res)))
      (then (get-in res [:body :data])))))

;; get, json
(defn j-get
  ;; get
  ([api opts then]
   (j-get api opts then default-err-handler))
  ([api opts then catch]
   (-> (fetch/get (url api) (j-opt opts))
       (.then (handle-then-success then))
       (.catch catch))))

(defn j-post
  ;; get
  ([api opts then]
   (j-post api opts then default-err-handler))
  ([api opts then catch]
   (-> (fetch/post (url api) (j-opt opts))
       (.then (handle-then-success then))
       (.catch catch))))

;; popup?
;; default error handler
;; if network error set type network error / api error
;; authentication

;; error: type, err

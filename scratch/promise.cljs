(ns promesa2
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
   [kitchen-async.promise :as pk]
   [goog.object :as gobj]))

(print 2)
(p/promise 1)
(p/promise (ex-info "error" {}))
(p/rejected (ex-info "error" {
                              
                              }))

(defn some-fn
  [ms]
  (let [p (p/deferred)]
    (p/resolve! p nil)
    p))

@(p/create (fn [resolve reject] (resolve 1)))
@(print 2
       )

(def x (atom 1))
@x

(require '[promesa.exec :as exec])

@(p/create (fn [resolve reject] (resolve 1)) exec/default-executor)

(def p1 (p/deferred))
(def p2 (p/resolved 1))
(def p3 (p/rejected (ex-info "test" {})))

(p/pending? p1)

(p/resolved? p2)

(p/rejected? p3)

(p/done? p1)

(p/done? p2)

(p/extract p1 :no-val)
;; => :no-val

(p/extract p2 :no-val)
;; => 1

(p/extract p3 :no-val)
;; => #error {...}

@(-> (p/resolved 1)
     (p/then inc))
;; => 2

;; flatten result
@(-> (p/resolved 1)
     (p/then (fn [x] (p/resolved (inc x)))))
;; => 2

(def result
  (-> (p/resolved 1)
      (p/chain inc inc inc)))

(require '[promesa.exec :as px])

@(px/run! (fn []
            (prn "I'm running in different thread")
            1))
;; => nil

@(px/submit! (fn []
               (prn "I'm running in different thread")
               1))
;; => 1
(require '[promesa.exec :as exec])
(exec/schedule! 1000 (fn []
                       (println "hello world")))

(def task (exec/schedule! 9000 #(print "9 second")))

(p/cancel! task)

(type p/vthread)

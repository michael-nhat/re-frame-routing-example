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
   [ajax.core :as aj]
   [cljs-http.client :as http]
   ;; [cljs.core.async :refer [<!]]
   [goog.string :as str]
   [promesa.core :as p]
   [clojure.core.async.interop :as ai]
   [clojure.core.async :as a])
  #?(:clj (:gen-class)))

(def simple-chan (a/chan))

(a/put! simple-chan "1")
(a/put! simple-chan "2")
(a/put! simple-chan "3")

(a/take! simple-chan println)
(a/take! simple-chan println)
(a/take! simple-chan println)
(a/take! simple-chan println)

(def echo-chan (a/chan))
(a/go (println (a/<! echo-chan)))
(a/>!! echo-chan "ketchup")
(ai/<p! "dfs")
(ai/p->c "dfs")

(js/setTimeout
 (fn
   []
   (a/put! simple-chan "kk"))
 5000)
(do
  (a/take! simple-chan println))
(println (ai/<p! (a/take simple-chan)))

(let [c (a/chan)]
  (a/go
    (a/>! c "hello"))
  (assert (= "hello" (a/<!! (a/go (a/<! c)))))
  (a/alt)
  (a/close! c))

(defn timeout [ms]
  (let [c (a/chan)]
    (js/setTimeout (fn [] (a/close! c)) ms)
    c))

(js/setTimeout (fn [] (print "wtf")) 3000)
(def long-task-ch (a/chan))
(a/go
  (let [[val ch]
        (a/alts! [long-task-ch (a/timeout 5000)])]
    (if (= ch long-task-ch)
      (println "Task completed!" val)
      (println "Oh oh! Task timed out!" long-task-ch val))))
(a/go (println "wtf"))

(a/go (print (a/alts! [long-task-ch (timeout 5000)])))

(a/go (let [c (a/chan)]
        (a/alts! [c (a/timeout 100)])
        (println "Gave up after sfdj")))

(def x (a/chan 10))

(a/go (a/>! x 1)
    (a/>! x 2)
    (a/>! x 3))

(defn read-all [from-chan]
  (a/go-loop [res []]
    (let [[v _] (a/alts! [from-chan] :default :complete)]
      (if (= v :complete)
        res
        (recur (conj res v))))))

(read-all x) 
;; output: [1 2 3]

(read-all x)
;; output: []

(a/go (a/>! x 10)
    (a/>! x 20)
    (a/>! x 30)
    (a/>! x 40))

(read-all x)
;; output: [10 20 30 40]



(a/go (let [response (a/<! (http/get "https://api.github.com/users"
                                 {:with-credentials? false
                                  :query-params {"since" 135}}))]
      (prn (:status response))
      (prn (map :login (:body response)))))

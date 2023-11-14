(ns routes-example.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [routes-example.events :as events]
   [routes-example.my-routes :as routes]
   [routes-example.views :as views]
   [routes-example.config :as config]
   [routes-example.users-index.views]
   [routes-example.user-view.views]
   [ajax.core :as ajax]
   [cljs-http.client :as http]
   [cljs.core.async :refer [<!]]
   [routes-example.main-view :refer [main-panel]]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "cljs-app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [main-panel] root-el)))

(defn init []
  (routes/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))

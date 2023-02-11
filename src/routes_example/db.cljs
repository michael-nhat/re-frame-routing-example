(ns routes-example.db)

(def default-db
  {:name "Bland"
   ;; login has username, fullname
   :login {:logged true
           :user nil}
   :users [{:id 1 :name "on the code again"} {:id 2 :name "Gareth Cliff"}]
   :load nil
   :loading {}})

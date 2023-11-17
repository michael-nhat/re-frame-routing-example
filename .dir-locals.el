(
 (clojure-mode .
               ((nh-cider/type . :cljs)
                (cider-preferred-build-tool . shadow)
                ;; (cider-clojure-cli-aliases . ":cider")
                (cider-default-cljs-repl . shadow)
                (cider-shadow-watched-builds . ("app"))
                (cider-shadow-default-options . "app")))
 (nil .
      ((nh-cider/type . :cljs)
       (cider-preferred-build-tool . shadow)
       ;; (cider-clojure-cli-aliases . ":cider")
       (cider-default-cljs-repl . shadow)
       (cider-shadow-watched-builds . ("app"))
       (cider-shadow-default-options . "app"))))

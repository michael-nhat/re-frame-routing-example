(
    (clojure-mode .
        (
            (cider-preferred-build-tool . shadow)
            ;; (cider-clojure-cli-aliases . ":cider")
            (cider-default-cljs-repl . shadow)
            (cider-shadow-watched-builds . 
                ("app"))
            (cider-shadow-default-options . "app")
))
    (nil .
                  (
                   (cider-preferred-build-tool . shadow)
                   ;; (cider-clojure-cli-aliases . ":cider")
                   (cider-default-cljs-repl . shadow)
                   (cider-shadow-watched-builds . 
                                                ("app"))
                   (cider-shadow-default-options . "app")
                   )))

(
    (clojure-mode .
        (
            (cider-preferred-build-tool . clojure-cli)
            (cider-clojure-cli-aliases . ":cider")
            (cider-default-cljs-repl . shadow)
            ;; (cider-shadow-watched-builds . 
            ;;     ("<first-build>" "<other-build>"))
            (cider-shadow-default-options . clojure-cli)
)))
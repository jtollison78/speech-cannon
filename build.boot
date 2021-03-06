#!/usr/bin/env boot

#tailrecursion.boot.core/version "2.5.1"

(set-env!
  :dependencies '[[tailrecursion/hoplon      "5.10.23"]
                  [tailrecursion/boot.task   "2.2.4"]
                  [tailrecursion/boot.notify "2.0.2"]
                  [tailrecursion/boot.ring   "0.2.1"]]
  :src-paths    #{"src"}
  :out-path     "resources/public"
  :main-class   'hello-world.core)

;; Static resources synced to output dir
(add-sync! (get-env :out-path) #{"resources/assets"})

(require
  '[tailrecursion.hoplon.boot :refer :all]
  '[tailrecursion.boot.task.notify :refer [hear]]
  '[tailrecursion.castra.task :refer [castra-dev-server]])

(deftask heroku
  "Prepare project.clj and Procfile for Heroku deployment."
  [& [main-class]]
  (let [jar-name   "hoplon-app-standalone.jar"
        jar-path   (format "target/%s" jar-name)
        main-class (or main-class (get-env :main-class))]
    (assert main-class "missing :main-class entry in env")
    (set-env!
      :src-paths #{"resources"}
      :lein      {:min-lein-version "2.0.0" :uberjar-name jar-name})
    (comp
      (lein-generate)
      (with-pre-wrap
        (println "Writing project.clj...")
        (-> "project.clj" slurp
          (.replaceAll "(:min-lein-version)\\s+(\"[0-9.]+\")" "$1 $2")
          ((partial spit "project.clj")))
        (println "Writing Procfile...")
        (-> "web: java $JVM_OPTS -cp %s clojure.main -m %s $PORT"
          (format jar-path main-class)
          ((partial spit "Procfile")))))))

(deftask development
  "Start local dev server."
  []
  (comp
    (castra-dev-server 'hello-world.api)
    (watch)
    (hear)
    (hoplon {:prerender false})))

(deftask production
  "Compile application with Google Closure advanced optimizations."
  []
  (hoplon {:optimizations :advanced}))

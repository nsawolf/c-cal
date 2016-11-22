(defproject wolf-cal "0.1.0-SNAPSHOT"
  :description "Hello World, from Untangled!"
  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]

                 [navis/untangled-client "0.5.6-SNAPSHOT"]
                 [untangled/om-css "1.0.0"]

                 [navis/untangled-spec "0.3.8" :scope "test"]
                 [lein-doo "0.1.7" :scope "test"]

                 [com.taoensso/timbre "4.3.1"]
                 [navis/untangled-server "0.6.0"]
                 [navis/untangled-datomic "0.4.10"]
                 [com.datomic/datomic-free "0.9.5344"]
                 ]

  :plugins [[com.jakemccrary/lein-test-refresh "0.15.0"]
            [lein-cljsbuild "1.1.4"]
            [lein-doo "0.1.7"]]

  :doo {:build "automated-tests"
        :paths {:karma "node_modules/karma/bin/karma"}}

  :uberjar-name "wolf_cal.jar"

  :test-refresh {:report untangled-spec.reporters.terminal/untangled-report
                 :with-repl true
                 :changes-only true}

  :source-paths ["src/server"]
  :jvm-opts ["-server" "-Xmx1024m" "-Xms512m" "-XX:-OmitStackTraceInFastThrow"]

  :test-paths ["specs" "specs/server" "specs/config"]
  :clean-targets ^{:protect false} ["target" "resources/public/js/compiled"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :cljsbuild {:builds [{:id           "dev"
                        :figwheel     true
                        :source-paths ["src/client" "dev/client"]
                        :compiler     {:main                 cljs.user
                                       :output-to            "resources/public/js/compiled/client.js"
                                       :output-dir           "resources/public/js/compiled/dev"
                                       :asset-path           "js/compiled/dev"
                                       :source-map-timestamp true
                                       :optimizations        :none}}

                       {:id           "production"
                        :source-paths ["src/client"]
                        :jar          true
                        :compiler     {:main          wolf-cal.main
                                       :output-to     "resources/public/js/compiled/main.js"
                                       :output-dir    "resources/public/js/compiled/prod"
                                       :asset-path    "js/compiled/prod"
                                       :optimizations :simple}}

                       {:id           "test"
                        :source-paths ["specs/client" "src/client"]
                        :figwheel     true
                        :compiler     {:main                 wolf-cal.spec-main
                                       :output-to            "resources/public/js/compiled/specs.js"
                                       :output-dir           "resources/public/js/compiled/specs"
                                       :asset-path           "js/compiled/specs"
                                       :optimizations        :none}}

                       {:id           "cards"
                        :figwheel     {:devcards true}
                        :source-paths ["src/client" "src/cards"]
                        :compiler     {:main                 wolf-cal.cards
                                       :output-to            "resources/public/js/compiled/cards.js"
                                       :output-dir           "resources/public/js/compiled/cards"
                                       :asset-path           "js/compiled/cards"
                                       :optimizations        :none
                                       :source-map-timestamp true}}
                       ]}

  :profiles {:uberjar {:main       wolf-cal.core
                       :aot        :all
                       :prep-tasks ["compile"
                                    ["cljsbuild" "once" "production"]]}
             :dev {:source-paths ["dev/client" "dev/server" "src/client" "src/server"]
                   :dependencies [[binaryage/devtools "0.6.1"]

                                  [com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.7"
                                   :exclusions [org.clojure/tools.reader joda-time clj-time ring/ring-core]]
                                  [org.clojure/tools.nrepl "0.2.12"]

                                  [devcards "0.2.1-7" :exclusions [org.omcljs/om]]
                                  ]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})

(ns wolf-cal.core
  (:require
    [com.stuartsierra.component :as component]
    [taoensso.timbre :as timbre]
    [untangled.server.core :as c]
    [untangled.server.impl.components.config :refer [load-config]]
    [untangled.datomic.schema :as schema]
    [untangled.datomic.core :as dc]
    [wolf-cal.system :as sys])
  (:gen-class))

(def console (System/console))
(defn exit [exit-code]
  (System/exit exit-code))
(defn exit-if-headly [exit-code]
  (if console (exit exit-code)))

(def config-path "/usr/local/etc/wolf_cal.edn")
(def production-config-component (c/new-config config-path))

(defn -main [& args]
  (let [system (sys/make-system config-path)
        stop #(component/stop system)
        cli-config (load-config production-config-component)
        db-config (:dbs cli-config)]
    
    (if args
      (do (dc/main-handler db-config args) (exit-if-headly 0))
      (if (or (:auto-migrate cli-config) (empty? (schema/migration-status-all db-config false)))
        (do (.addShutdownHook (Runtime/getRuntime) (Thread. stop))
            (component/start system))
        (do (timbre/fatal "System startup failed! Database does not conform to all migrations")
            (exit-if-headly 1))))))

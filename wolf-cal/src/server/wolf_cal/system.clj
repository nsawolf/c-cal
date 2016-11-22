(ns wolf-cal.system
  (:require
    [untangled.server.core :as core]
    [untangled.datomic.core :as udc]
    [om.next.server :as om]
    [wolf-cal.api.read :as r]
    [wolf-cal.api.mutations :as mut]

    [taoensso.timbre :as timbre]))

(defn logging-mutate [env k params]
  (timbre/info "Entering mutation:" k)
  (mut/apimutate env k params))

(defn make-system [config-path]
  (core/make-untangled-server
    :config-path config-path
    :parser (om/parser {:read r/api-read :mutate logging-mutate})
    :parser-injections #{:config :wolf-cal-database}
    :components { 
                 :wolf-cal-database (udc/build-database :wolf-cal)
                 :logger {}
                  }))

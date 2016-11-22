(ns wolf-cal.api.read
  (:require
    ;[untangled.datomic.protocols :as udb]
    [taoensso.timbre :as timbre]
    [wolf-cal.api.mutations :as mut]))

(timbre/info "Loading API definitions for wolf-cal.api.read")

(defn api-read [{:keys [query request] :as env} disp-key params]
  ;(let [connection (udb/get-connection survey-database)])
  (case disp-key
    :hello-world {:value 42}
    :logged-in? {:value @mut/logged-in?}
    :current-user {:value {:id 42 :name "Tony Kay"}}
    (throw (ex-info "Invalid request" {:query query :key disp-key}))))

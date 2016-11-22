(ns wolf-cal.main
  (:require [wolf-cal.core :refer [app]]
            [untangled.client.core :as core]
            [wolf-cal.ui.root :as root]))

(reset! app (core/mount @app root/Root "app"))

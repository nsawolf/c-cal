(ns wolf-cal.wolf-cal-data
  (:require
    [untangled.datomic.test-helpers :refer [link-and-load-seed-data]]))

(defn make-thing [id kind & [things]]
  {:db/id id
   :thing/kind kind
   :thing/children (or things [])})

(def wolf-cal-seed-data
  [(make-thing :datomic.id/thing1 :thing.kind/foo)
   (make-thing :datomic.id/thing2 :thing.kind/bar [:datomic.id/thing1])])

(defn seed-wolf-cal [conn]
  (link-and-load-seed-data conn wolf-cal-seed-data))

(ns wolf-cal.all-tests
  (:require
    wolf-cal.tests-to-run
    [doo.runner :refer-macros [doo-all-tests]]))

(doo-all-tests #".*-spec")
